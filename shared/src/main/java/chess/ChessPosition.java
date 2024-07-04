package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int r;
    int c;

    public ChessPosition(int row, int col) {
        r = row;
        c = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {

        return r;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {

        return c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return r == that.r && c == that.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }

    public boolean valid(){
        if(r>=1 && r <=8 && c>=1 && c <= 8){
            return true;
        }
        return false;
    }
}
