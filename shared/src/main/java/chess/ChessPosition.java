package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    int c;
    int r;

    public ChessPosition(int row, int col) {
        c = col;
        r = row;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {

        return r;
    }

    @Override
    public String toString() {
        return "[" + r + ", " + c + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChessPosition that)) return false;
        return c == that.c && r == that.r;
    }

    @Override
    public int hashCode() {
        return Objects.hash(c, r);
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {

        return c;
    }
    public boolean validPos(){
        if(r >= 1 && r <= 8 && c >= 1 && c <= 8){
            return true;
        }
        return false;
    }
}
