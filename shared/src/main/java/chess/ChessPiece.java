package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPiece that)) return false;
        return t == that.t && color == that.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(t, color);
    }

    PieceType t;
    ChessGame.TeamColor color;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        t = type;
        color = pieceColor;
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

        return color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return t;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "t=" + t +
                ", color=" + color +
                '}';
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        HashSet<ChessMove> moves = new HashSet<>();
        ChessPosition tempPos = myPosition;
        if(t == PieceType.BISHOP){
            int[][] directions = {{1,1},{1,-1},{-1,1},{-1,-1}};
            for(int[] d : directions){
                tempPos = new ChessPosition(myPosition.getRow()+d[0], myPosition.getColumn()+d[1]);

                while(tempPos.validPos() && board.getPiece(tempPos) == null ){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                    tempPos = new ChessPosition(tempPos.getRow()+d[0], tempPos.getColumn()+d[1]);
                }

                if(tempPos.validPos() && board.getPiece(tempPos).getTeamColor() != color){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                }

            }
        }
        else if (t == PieceType.QUEEN) {
            int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for(int[] d : directions){
                tempPos = new ChessPosition(myPosition.getRow()+d[0], myPosition.getColumn()+d[1]);

                while(tempPos.validPos() && board.getPiece(tempPos) == null ){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                    tempPos = new ChessPosition(tempPos.getRow()+d[0], tempPos.getColumn()+d[1]);
                }

                if(tempPos.validPos() && board.getPiece(tempPos).getTeamColor() != color){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                }

            }
        }
        else if (t == PieceType.KING) {
            int[][] directions = {{1,1},{0,1},{-1,1},{-1,0},{-1,-1},{0,-1},{1,-1},{1,0}};
            for(int[] d : directions){
                tempPos = new ChessPosition(myPosition.getRow()+d[0], myPosition.getColumn()+d[1]);
                if(tempPos.getColumn()<=8 && tempPos.getColumn() >= 1 && tempPos.getRow()<=8 && tempPos.getRow()>=1){
                    if(board.getPiece(tempPos) == null || board.getPiece(tempPos).getTeamColor() != color){
                        moves.add(new ChessMove(myPosition, tempPos, null));
                    }
                }

            }

        }
        else if (t == PieceType.ROOK) {
            int[][] directions = {{1,0},{-1,0},{0,1},{0,-1}};

            for(int[] d : directions){
                tempPos = new ChessPosition(myPosition.getRow()+d[0], myPosition.getColumn()+d[1]);

                while(tempPos.validPos() && board.getPiece(tempPos) == null ){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                    tempPos = new ChessPosition(tempPos.getRow()+d[0], tempPos.getColumn()+d[1]);
                }

                if(tempPos.validPos() && board.getPiece(tempPos).getTeamColor() != color){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                }

            }

        }
        else if (t == PieceType.KNIGHT) {
            int[][] directions = {{2,1},{2,-1},{-2,1},{-2,-1},{-1,-2},{1,-2},{1,2},{-1,2}};
            for(int[] d : directions){
                tempPos = new ChessPosition(myPosition.getRow()+d[0], myPosition.getColumn()+d[1]);
                if(tempPos.getColumn()<=8 && tempPos.getColumn() >= 1 && tempPos.getRow()<=8 && tempPos.getRow()>=1){
                    if(board.getPiece(tempPos) == null || board.getPiece(tempPos).getTeamColor() != color){
                        moves.add(new ChessMove(myPosition, tempPos, null));
                    }
                }

            }
        }
        else if (t == PieceType.PAWN) {
            ArrayList<ChessMove> toAdd = new ArrayList<>();
            ArrayList<ChessMove> toRemove = new ArrayList<>();


            if(color == ChessGame.TeamColor.WHITE){
                if(myPosition.getRow() == 2){
                    tempPos = new ChessPosition(myPosition.getRow()+2, myPosition.getColumn());
                    if(board.getPiece(tempPos) == null){
                        if(board.getPiece(new ChessPosition(myPosition.getRow()+1, myPosition.getColumn()))==null){
                            moves.add(new ChessMove(myPosition, tempPos, null));
                        }
                    }
                }
                tempPos = new ChessPosition(myPosition.getRow()+1, myPosition.getColumn());
                if(board.getPiece(tempPos)==null){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                }

                //capturing
                int[][] whiteTakes = {{1,-1},{1,1}};
                for(int[] t : whiteTakes){
                    tempPos = new ChessPosition(myPosition.getRow()+t[0], myPosition.getColumn()+t[1]);
                    if(tempPos.validPos() && board.getPiece(tempPos) != null && board.getPiece(tempPos).getTeamColor() == ChessGame.TeamColor.BLACK){
                        moves.add(new ChessMove(myPosition, tempPos, null));
                    }
                }
                //promotion
                if(myPosition.getRow() == 7){
                    for(ChessMove m : moves){
                        for(PieceType p : PieceType.values()){
                            if(p != PieceType.PAWN && p != PieceType.KING) {
                                toAdd.add(new ChessMove(m.getStartPosition(), m.getEndPosition(), p));
                            }
                        }
                        if(m.getPromotionPiece() == null){
                            toRemove.add(m);
                        }
                    }
                }
            }

            if(color == ChessGame.TeamColor.BLACK){
                if(myPosition.getRow() == 7){
                    tempPos = new ChessPosition(myPosition.getRow()-2, myPosition.getColumn());
                    if(board.getPiece(tempPos) == null){
                        if(board.getPiece(new ChessPosition(myPosition.getRow()-1, myPosition.getColumn()))==null){
                            moves.add(new ChessMove(myPosition, tempPos, null));
                        }
                    }
                }
                tempPos = new ChessPosition(myPosition.getRow()-1, myPosition.getColumn());
                if(board.getPiece(tempPos)==null){
                    moves.add(new ChessMove(myPosition, tempPos, null));
                }

                //capturing
                int[][] blackTakes = {{-1,-1},{-1,1}};
                for(int[] t : blackTakes){
                    tempPos = new ChessPosition(myPosition.getRow()+t[0], myPosition.getColumn()+t[1]);
                    if(tempPos.validPos() && board.getPiece(tempPos) != null && board.getPiece(tempPos).getTeamColor() == ChessGame.TeamColor.WHITE){
                        moves.add(new ChessMove(myPosition, tempPos, null));
                    }
                }

                if(myPosition.getRow() == 2){
                    for(ChessMove m : moves){
                        for(PieceType p : PieceType.values()){
                            if(p != PieceType.PAWN && p != PieceType.KING) {
                                toAdd.add(new ChessMove(m.getStartPosition(), m.getEndPosition(), p));
                            }
                        }
                        if(m.getPromotionPiece() == null){
                            toRemove.add(m);
                        }
                    }
                }
            }

            moves.addAll(toAdd);
            toRemove.forEach(moves::remove);

        }
        return moves;
    }
}
