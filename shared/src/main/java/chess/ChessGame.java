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
        TeamColor  color = b.getPiece(startPosition).getTeamColor();
        HashSet<ChessMove> valids = (HashSet<ChessMove>) b.getPiece(startPosition).pieceMoves(b, startPosition);
        ArrayList<ChessMove> toRemove = new ArrayList<>();

        ChessPiece originalPiece = b.getPiece(startPosition);

        for(ChessMove m : valids){
            ChessPiece replacedPiece = b.getPiece(m.getEndPosition());
            b.removePiece(m.getStartPosition());
            b.removePiece(m.getEndPosition());

            b.addPiece(m.getEndPosition(), originalPiece);


            if(isInCheck(color)){
                toRemove.add(m);
            }

            b.removePiece(m.getEndPosition());
            b.removePiece(m.getStartPosition());

            b.addPiece(m.getStartPosition(), originalPiece);
            b.addPiece(m.getEndPosition(), replacedPiece);

        }
        toRemove.forEach(valids::remove);
        return valids;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition oldPosition = new ChessPosition(move.getStartPosition().getRow(),move.getStartPosition().getColumn());
        ChessPiece oldPiece = (ChessPiece) b.getPiece(move.getEndPosition());

        if(b.getPiece(move.getStartPosition()) == null){
            throw new InvalidMoveException("null piece");
        }

        if(b.getPiece(move.getStartPosition()).getTeamColor() != colorTurn){
            throw new InvalidMoveException("wrong color piece for this players turn");
        }
        HashSet<ChessMove> moves = new HashSet<>();
        moves = (HashSet<ChessMove>) validMoves(move.getStartPosition());



        if(!moves.contains(move)){
            throw new InvalidMoveException("invalid move for that piece");
        }

        //promotion stuff
        if(move.getEndPosition().getRow() == 8 || move.getEndPosition().getRow() == 1) {
            if(move.getPromotionPiece() != null) {
                switch (move.getPromotionPiece()){
                    case ROOK -> {
                        b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.ROOK));
                    }
                    case QUEEN -> {
                        b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.QUEEN));
                    }
                    case KNIGHT -> {
                        b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.KNIGHT));
                    }
                    case BISHOP -> {
                        b.addPiece(move.getEndPosition(), new ChessPiece(b.getPiece(move.getStartPosition()).getTeamColor(), ChessPiece.PieceType.BISHOP));
                    }
                }
                b.removePiece(move.getStartPosition());
            }

        }
        if(move.getPromotionPiece() == null){
            b.addPiece(move.getEndPosition(),  b.getPiece(move.getStartPosition()));
            b.removePiece(move.getStartPosition());
        }


        if(isInCheck(colorTurn)){
            b.addPiece(move.getStartPosition(), b.getPiece(move.getEndPosition()));
            b.addPiece(move.getEndPosition(), oldPiece);
            throw new InvalidMoveException("");
        }





        if(colorTurn == TeamColor.WHITE){
            colorTurn = TeamColor.BLACK;
        }
        else{
            colorTurn = TeamColor.WHITE;
        };
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
        HashSet<ChessMove> moves = new HashSet<>();
        ChessPosition kingPos = new ChessPosition(1,1);
        if(isInCheck(teamColor)){
            for(int i = 1; i < 9; ++i){
                for(int j = 1; j < 9; j++){
                    if(b.getPiece(new ChessPosition(i,j)) != null && b.getPiece(new ChessPosition(i, j)).getTeamColor() == teamColor && b.getPiece(new ChessPosition(i, j)).getPieceType() == ChessPiece.PieceType.KING){
                        kingPos = new ChessPosition(i,j);
                    }
                }
            }
            moves = (HashSet<ChessMove>) validMoves(kingPos);
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
        HashSet<ChessMove> moves = new HashSet<>();
        for(int i = 1; i < 9; ++i){
            for(int j = 1; j < 9; j++){
                ChessPosition tempPos = new ChessPosition(i,j);
                if(b.getPiece(tempPos) != null && b.getPiece(tempPos).getTeamColor() == teamColor){
                    moves.addAll(validMoves(tempPos));
                }
            }
        }
        return moves.isEmpty();
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


    }
}
