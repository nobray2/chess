package chess;

import java.util.*;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    ChessGame.TeamColor c;
    PieceType t;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        c = pieceColor;
        t = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return c == that.c && t == that.t;
    }

    @Override
    public int hashCode() {
        return Objects.hash(c, t);
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return c;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType(){
        return t;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> m = new HashSet<>();
        ChessPosition temp = myPosition;
        if(t == PieceType.ROOK){
            int[][] directions = {{1,0}, {-1,0},{0,1},{0,-1}};
            for(int[] d : directions){
                temp = new ChessPosition(myPosition.getRow()+d[0],myPosition.getColumn()+d[1]);
                while(temp.valid() && board.getPiece(temp)==null) {
                    m.add(new ChessMove(myPosition, temp, null));
                    temp = new ChessPosition(temp.getRow()+d[0], temp.getColumn()+d[1]);
                }
                if(temp.valid() && board.getPiece(temp).getTeamColor()!=c){
                    m.add(new ChessMove(myPosition, temp, null));
                }
            }
        }
        else if(t == PieceType.QUEEN){
            int[][] directions = {{1,0},{1,1},{0,1},{1,-1},{-1,0}, {-1,-1},{0,-1},{-1,1}};
            for(int[] d : directions){
                temp = new ChessPosition(myPosition.getRow()+d[0],myPosition.getColumn()+d[1]);
                while(temp.valid() && board.getPiece(temp)==null) {
                    m.add(new ChessMove(myPosition, temp, null));
                    temp = new ChessPosition(temp.getRow()+d[0], temp.getColumn()+d[1]);
                }
                if(temp.valid() && board.getPiece(temp).getTeamColor()!=c){
                    m.add(new ChessMove(myPosition, temp, null));
                }
            }
        }
        else if(t == PieceType.KING){
            int[][] directions = {{1,0},{1,1},{0,1},{1,-1},{-1,0}, {-1,-1},{0,-1},{-1,1}};
            for(int[] d : directions){
                temp = new ChessPosition(myPosition.getRow()+d[0],myPosition.getColumn()+d[1]);
                if(temp.valid() && board.getPiece(temp) == null){
                    m.add(new ChessMove(myPosition, temp, null));
                }
                else if(temp.valid() && board.getPiece(temp).getTeamColor()!=c){
                    m.add(new ChessMove(myPosition, temp, null));
                }
            }
        }
        else if(t == PieceType.BISHOP){
            int[][] directions = {{1,1}, {-1,-1},{1,-1},{-1,1}};
            for(int[] d : directions){
                temp = new ChessPosition(myPosition.getRow()+d[0],myPosition.getColumn()+d[1]);
                while(temp.valid() && board.getPiece(temp)==null) {
                    m.add(new ChessMove(myPosition, temp, null));
                    temp = new ChessPosition(temp.getRow()+d[0], temp.getColumn()+d[1]);
                }
                if(temp.valid() && board.getPiece(temp).getTeamColor()!=c){
                    m.add(new ChessMove(myPosition, temp, null));
                }
            }
        }
        else if(t == PieceType.KNIGHT){
            int[][] directions = {{2,1}, {-2,1},{2,-1},{-2,-1}, {1,2},{1,-2},{-1,2},{-1,-2}};
            for(int[] d : directions){
                temp = new ChessPosition(myPosition.getRow()+d[0],myPosition.getColumn()+d[1]);
                if(temp.valid() && board.getPiece(temp) == null){
                    m.add(new ChessMove(myPosition, temp, null));
                }
                else if(temp.valid() && board.getPiece(temp).getTeamColor()!=c){
                    m.add(new ChessMove(myPosition, temp, null));
                }
            }
        }
        else if(t == PieceType.PAWN){
            ArrayList<ChessMove> toAdd = new ArrayList<>();
            ArrayList<ChessMove> toDelete = new ArrayList<>();
            if(c == ChessGame.TeamColor.BLACK){
                if(myPosition.getRow()==7){
                    temp = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                    if(board.getPiece(temp) == null){
                        if(board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn())) == null){
                            m.add(new ChessMove(myPosition, temp, null));
                        }
                    }
                }
                temp = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if(board.getPiece(temp)==null){
                    m.add(new ChessMove(myPosition, temp, null));
                }
                //capture
                int[][] blackTake = {{-1,-1},{-1,1}};
                for(int[] t : blackTake){
                    temp = new ChessPosition(myPosition.getRow()+t[0], myPosition.getColumn()+t[1]);
                    if(temp.valid() && board.getPiece(temp) != null && board.getPiece(temp).getTeamColor() != ChessGame.TeamColor.BLACK){
                        m.add(new ChessMove(myPosition, temp, null));
                    }
                }

                //promote
                if(myPosition.getRow()==2){
                    for(ChessMove c : m){
                        for(PieceType p : PieceType.values()){
                            if(p != PieceType.KING && p != PieceType.PAWN){
                                toAdd.add(new ChessMove(c.getStartPosition(), c.getEndPosition(), p));
                            }
                        }
                        if(c.getPromotionPiece() == null){
                            toDelete.add(c);
                        }
                    }
                }
            }

            //white

            if(c == ChessGame.TeamColor.WHITE){
                if(myPosition.getRow()==2){
                    temp = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                    if(board.getPiece(temp) == null){
                        if(board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn())) == null){
                            m.add(new ChessMove(myPosition, temp, null));
                        }
                    }
                }
                temp = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                if(board.getPiece(temp)==null){
                    m.add(new ChessMove(myPosition, temp, null));
                }
                //capture
                int[][] blackTake = {{1,1},{1,-1}};
                for(int[] t : blackTake){
                    temp = new ChessPosition(myPosition.getRow()+t[0], myPosition.getColumn()+t[1]);
                    if(temp.valid() && board.getPiece(temp) != null && board.getPiece(temp).getTeamColor() != ChessGame.TeamColor.WHITE){
                        m.add(new ChessMove(myPosition, temp, null));
                    }
                }

                //promote
                if(myPosition.getRow()==7){
                    for(ChessMove c : m){
                        for(PieceType p : PieceType.values()){
                            if(p != PieceType.KING && p != PieceType.PAWN){
                                toAdd.add(new ChessMove(c.getStartPosition(), c.getEndPosition(), p));
                            }
                        }
                        if(c.getPromotionPiece() == null){
                            toDelete.add(c);
                        }
                    }
                }
            }

            //add/remove
            m.addAll(toAdd);
            toDelete.forEach(m::remove);

        }



        return m;
    }
    @Override
    public String toString(){
        return "t="+t+"c"+c;
    }

}
