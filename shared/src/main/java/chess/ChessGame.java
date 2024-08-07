package chess;

import java.util.ArrayList;
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
    TeamColor color;

    public ChessGame() {
        b = new ChessBoard();
        b.resetBoard();
        color = TeamColor.WHITE;

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {

        return color;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {

        color = team;
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
        TeamColor c = b.getPiece(startPosition).getTeamColor();
        HashSet<ChessMove> moves = (HashSet<ChessMove>) b.getPiece(startPosition).pieceMoves(b, startPosition);
        HashSet<ChessMove> validMoves = new HashSet<ChessMove>();



        for(ChessMove m : moves){
            ChessBoard testB = new ChessBoard(b);
            testB.addPiece(m.getEndPosition(), b.getPiece(startPosition));
            testB.addPiece(startPosition, null);
            if(!testBoardCheck(c, testB)){
                validMoves.add(m);
            }
        }

        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        if(b.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException("null piece");
        }
        if(b.getPiece(move.getStartPosition()).getTeamColor() != color){
            throw new InvalidMoveException("wrong color");
        }

        HashSet<ChessMove> moves = new HashSet<ChessMove>();
        moves = (HashSet<ChessMove>) validMoves(move.getStartPosition());
        if(!moves.contains(move)){
            throw new InvalidMoveException("invalid move for piece type");
        }

        if(move.getPromotionPiece() == null){
            b.addPiece(move.getEndPosition(), b.getPiece(move.getStartPosition()));
            b.addPiece(move.getStartPosition(), null);
        }
        else{
            switch (move.getPromotionPiece()) {
                case KING:
                    b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.KING));
                    b.addPiece(move.getStartPosition(), null);
                    break;
                case QUEEN:
                    b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.QUEEN));
                    b.addPiece(move.getStartPosition(), null);
                    break;
                case ROOK:
                    b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.ROOK));
                    b.addPiece(move.getStartPosition(), null);
                    break;
                case BISHOP:
                    b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.BISHOP));
                    b.addPiece(move.getStartPosition(), null);
                    break;
                case KNIGHT:
                    b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.KNIGHT));
                    b.addPiece(move.getStartPosition(), null);
                    break;
                default:
                    throw new InvalidMoveException("Unexpected promotion piece");
            }
        }

        if(color == TeamColor.WHITE){
            color = TeamColor.BLACK;
        }
        else{
            color = TeamColor.WHITE;
        }

    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        ChessPosition kingPos = new ChessPosition(1,1);
        ChessPosition piecePos;
        HashSet<ChessMove> moves = new HashSet<>();

        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPiece p = b.getPiece(NewPos(i,j));
                if(p != null && p.getTeamColor() == teamColor && p.getPieceType() == ChessPiece.PieceType.KING){
                    kingPos = NewPos(i,j);
                }
            }
        }
        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                ChessPiece p = b.getPiece(NewPos(i,j));
                if(p!=null && p.getTeamColor() != teamColor){
                    moves.addAll(p.pieceMoves(b,NewPos(i,j)));
                }
            }
        }

        for(ChessMove m : moves){
            if(m.getEndPosition().equals(kingPos)){
                return true;
            }
        }
        return false;
    }


    public ChessPosition NewPos(int r, int c){
        return new ChessPosition(r, c);
    }

    public boolean testBoardCheck(TeamColor teamColor, ChessBoard testBoard) {

        ChessPosition kingPos = new ChessPosition(1, 1);
        ChessPosition piecePos;
        HashSet<ChessMove> moves = new HashSet<>();

        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece p = testBoard.getPiece(NewPos(i, j));
                if (p != null && p.getTeamColor() == teamColor && p.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPos = NewPos(i, j);
                }
            }
        }
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                ChessPiece p = testBoard.getPiece(NewPos(i, j));
                if (p != null && p.getTeamColor() != teamColor) {
                    moves.addAll(p.pieceMoves(testBoard, NewPos(i, j)));
                }
            }
        }
        for(ChessMove m : moves){
            if(m.getEndPosition().equals(kingPos)){
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
        HashSet<ChessMove> moves = new HashSet<ChessMove>();
        ChessPosition kingPos = new ChessPosition(1,1);
        ChessPiece tempPiece;
        if(isInCheck(teamColor)){
            for(int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    tempPiece = b.getPiece(new ChessPosition(i + 1, j + 1));
                    if (tempPiece != null && tempPiece.getTeamColor() == teamColor) {
                        moves.addAll(validMoves(new ChessPosition(i + 1, j + 1)));
                    }
                }
            }
            return moves.isEmpty();

        }
        return false;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        HashSet<ChessMove> moves = new HashSet<ChessMove>();
        ChessPiece tempPiece;
        if(!isInCheck(teamColor)){
            for(int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    tempPiece = b.getPiece(new ChessPosition(i + 1, j + 1));
                    if (tempPiece != null && tempPiece.getTeamColor() == teamColor) {
                        moves.addAll(validMoves(new ChessPosition(i + 1, j + 1)));
                    }
                }
            }
            return moves.isEmpty();

        }
        return false;
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
}
