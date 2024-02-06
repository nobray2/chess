package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessMove {
    ChessPosition s;
    ChessPosition e;

    ChessPiece.PieceType promote;

    @Override
    public String toString() {
        return "ChessMove{" +s.toString() + "  " + e.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessMove chessMove)) return false;
        return Objects.equals(s, chessMove.s) && Objects.equals(e, chessMove.e) && promote == chessMove.promote;
    }

    @Override
    public int hashCode() {
        return Objects.hash(s, e, promote);
    }

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition,
                     ChessPiece.PieceType promotionPiece) {
        s = startPosition;
        e = endPosition;
        promote = promotionPiece;
    }

    /**
     * @return ChessPosition of starting location
     */
    public ChessPosition getStartPosition() {

        return s;
    }

    /**
     * @return ChessPosition of ending location
     */
    public ChessPosition getEndPosition() {

        return e;
    }

    /**
     * Gets the type of piece to promote a pawn to if pawn promotion is part of this
     * chess move
     *
     * @return Type of piece to promote a pawn to, or null if no promotion
     */
    public ChessPiece.PieceType getPromotionPiece() {

        return promote;
    }
}
