package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessBoard b;
    TeamColor colorTurn = TeamColor.WHITE;

    public ChessGame() {
        b = new ChessBoard();
        b.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return colorTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {

        colorTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {


        ChessPosition kingPos = new ChessPosition(1,1);
        HashSet<ChessMove> moves = new HashSet<>();

        for(int i = 1; i < 9; ++i){
            for(int j = 1; j < 9; j++){
                if(b.getPiece(new ChessPosition(i,j)) != null && b.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor && b.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING){
                    kingPos = new ChessPosition(i,j);
                }
            }
        }

        for(int i = 1; i < 9; ++i){
            for(int j = 1; j < 9; j++){
                ChessPosition piecePosition = new ChessPosition(i,j);
                if(b.getPiece(piecePosition) != null && b.getPiece(piecePosition).getTeamColor() != teamColor){
                    moves.addAll(b.getPiece(piecePosition).pieceMoves(b, piecePosition));
                }
            }
        }

        for(ChessMove m : moves) {
            System.out.println(m.getEndPosition().toString());
            System.out.println(kingPos.toString());
            if (m.getEndPosition().equals(kingPos)) {
                return true;

            }
        }

        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {

        b = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {

        return b;
    }
    public static void main(String[] args) {
        ChessGame g = new ChessGame();
        g.getBoard().addPiece(new ChessPosition(8,4), new ChessPiece(TeamColor.WHITE, ChessPiece.PieceType.ROOK));
        // Create a new instance of ChessGame
        System.out.println(g.isInCheck(TeamColor.BLACK));

    }
}
