package main;
import java.util.*;

class Solution {

    /**
     * Returns the filled in sudoku grid.
     * @param grid the partially filled in grid. unfilled positions are -1.
     * @return the fully filled sudoku grid.
     */
    public static int[][] solve(int[][] grid) {
        return grid;
    }
}

/**
 * class corresponding to a grid
 * contains a couple key values
 * n length and width
 * ns squareroot of n, length of one sub-block in the grid
 * flags Bitset containing data about the grid. Each sudoku has n bits for each square
 * each bit corresponds wether a number can be put there
 */
class Grid {
    private final int n;
    private final int ns;
    private final BitSet flags;
    private final BitSet propagated;

    public Grid(int[][] grid) {
        n = grid.length;
        ns = (int) Math.sqrt(n);
        flags = new BitSet(n*n*n);
        propagated = new BitSet(n*n);
    }

    /**
     * returns the start index for a block for a given x and y
     * @param x
     * @param y
     * @return
     */
    public int index(int x, int y) {
        return n * (x*n+y);
    }

    /**
     * returns the index for a given x, y and flag
     * @param x
     * @param y
     * @return
     */
    public int index(int x, int y, int f) {
        return index(x, y) + f;
    }

    /**
     * returns a bitset block corresponding to the flags of a square in de sudoku.
     * @param x
     * @param y
     * @return
     */
    public BitSet block(int x, int y) {
        return flags.get(index(x, y), index(x, y+1));
    }

    /**
     * Check wether a block can be solved, use isPropagated to check if it is propagated in de sudoku already
     * @param x
     * @param y
     * @return
     */
    public boolean isSolved(int x, int y) {
        BitSet bs = block(x, y);
        int count = 0;
        for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
            count+=1;
        }
        return count==1;
    }

    /**
     * marks a given flag for a given x and y in the bitset
     * @param f
     * @param x
     * @param y
     * @return
     */
    public void mark(int f, int x, int y) {
        if(!flags.get(index(x, y, f))) {
            flags.flip(index(x, y, f));
        }
    }

    /**
     * This method fills in the desired value for this x and y.
     * The location is set to propagated so it won't be changed again.
     * The consequences for filling this square, will also be handled.
     * That means filling in the relevant flags in the horizontal and
     * vertical direction, as well as the local block.
     */
    public void fillInFor(int f, int x, int y) {
        // Set this to propagated.
        propagated.set(x * n + y, true);

        // Fill in horizontal
        for (int i = 0; i < n; i++) {
            flags.set(index(i, y, f), false);
        }

        // Fill in vertical
        for (int i = 0; i < n; i++) {
            flags.set(index(x, i, f), false);
        }

        // Fill in local block.
        int originX = (int) Math.floor((float) x / ns);
        int originY = (int) Math.floor((float) y / ns);
        for (int i = originX; i < originX + ns; i++) {
            for (int j = originY; j < originY + ns; j++) {
                flags.set(index(i, j, f), false);
            }
        }

        // Set the square to the correct flag.
        // This is done last to simplify filling in (the above stuff).
        for (int i = index(x, y); i < index(x, y) + n; i++) {
            flags.set(i, false); // Assuming this is necessary.
        }
        flags.set(index(x, y, f), true);
    }

    /**
     *
     */
    public int[] getMinimumBlock() {
        // loop over all squares in the grid. Return the x and y of the square with the least 'true' flags
    }


}