package chess;

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



        return m;
    }
    @Override
    public String toString(){
        return "t="+t+"c"+c;
    }
}
