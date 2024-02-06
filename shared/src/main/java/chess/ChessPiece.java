package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
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
        return moves;
    }
}
