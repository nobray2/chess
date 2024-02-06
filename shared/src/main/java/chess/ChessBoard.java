package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] positions;
    public ChessBoard() {
        positions = new ChessPiece[8][8];
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {

        positions[position.getRow()-1][position.getColumn()-1] = piece;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessBoard that)) return false;
        return Arrays.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(positions);
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {

        return positions[position.getRow()-1][position.getColumn()-1];
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for(ChessPiece[] x : positions){
            for(ChessPiece p : x){
                p = null;
            }
        }
        for(int i = 0; i < 8; i++){
            positions[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
        for(int i = 0; i < 8; i++){
            positions[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        }

        //white pieces
        positions[7][0] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        positions[7][1] = new ChessPiece(ChessGame.TeamColor.BLACK,ChessPiece.PieceType.KNIGHT);
        positions[7][2] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        positions[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK ,ChessPiece.PieceType.QUEEN);
        positions[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        positions[7][5] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        positions[7][6] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        positions[7][7] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);

        //black pieces
        positions[0][0] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        positions[0][1] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        positions[0][2] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        positions[0][3] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        positions[0][4] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        positions[0][5] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        positions[0][6] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        positions[0][7] = new ChessPiece( ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
    }
}
