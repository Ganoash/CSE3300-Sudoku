package main;
import java.util.*;

class Solution {

    /**
     * Returns the filled in sudoku grid.
     * @param grid the partially filled in grid. unfilled positions are -1.
     * @return the fully filled sudoku grid.
     */
    public static int[][] solve(int[][] grid) {
        Grid g = new Grid(grid);
        Queue<Grid> gridQueue = new ArrayDeque<>();

        while (true) {

        }
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

    public Grid(int n) {
        this.n = n;
        ns = (int) Math.sqrt(n);
        // init all true flags bitset
        flags = new BitSet(n*n*n);
        flags.flip(0, n*n*n);
        // init all true propagated bitset
        propagated = new BitSet(n*n);
        propagated.flip(0, n*n);
    }
    public Grid(int[][] grid) {
        n = grid.length;
        ns = (int) Math.sqrt(n);
        // init all true flags bitset
        flags = new BitSet(n*n*n);
        flags.flip(0, n*n*n);
        // init all true propagated bitset
        propagated = new BitSet(n*n);
        propagated.flip(0, n*n);
        init(grid);
    }

    private Grid(int n,int ns, BitSet flags, BitSet propagated) {
        this.n = n;
        this.ns = ns;
        this.flags = flags;
        this.propagated = propagated;
    }

    public void init(int[][] grid) {
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                if(grid[x][y] == -1) {
                    continue;
                }
                fillInFor(grid[x][y], x, y);
            }
        }
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
     * returns the amount of true flags in the bitset
     * @param bs
     * @return
     */
    public int count(BitSet bs) {
        int c = 0;
        for (int i = bs.nextSetBit(0); i >= 0; i = bs.nextSetBit(i+1)) {
            c+=1;
        }
        return c;
    }

    /**
     * Check wether a block can be solved, use isPropagated to check if it is propagated in de sudoku already
     * @param x
     * @param y
     * @return
     */
    public boolean isSolved(int x, int y) {
        BitSet bs = block(x, y);
        return count(bs)==1;
    }

    /**
     * marks a given flag for a given x and y in the bitset
     * @param f
     * @param x
     * @param y
     * @return
     */
    public void mark(int f, int x, int y) {
        if(flags.get(index(x, y, f))) {
            flags.flip(index(x, y, f));
        }
    }

    /**
     *
     */
    public void fillInFor(int f, int x, int y) {
        // set propagated to 1 for this x and y
        // set all other flags to false for this x and y
        // propagate marking these flags to all other relevant squares

    }

    /**
     *
     */
    public int[] getMinimumBlock() {
        // loop over all squares in the grid. Return the x and y of the square with the least 'true' flags
        // don't consider propagated squares
        int minimum = Integer.MAX_VALUE;
        int[] x_and_y = new int[2];
        for(int x = 0 ; x < n; x++) {
            for(int y = 0; y < n; y++) {
                // block is propagated and should not be considered
                if(propagated.get(x*n+y)) {
                    continue;
                }
                //get block and count true flags
                BitSet bs = block(x, y);
                int c = count(bs);
                // iff smaller than smallest seen update x and y data
                if(c < minimum) {
                    minimum = c;
                    x_and_y[0] = x;
                    x_and_y[1] = y;
                }
            }
        }
        // return smallest x and y seen
        return x_and_y;
    }

    public Grid copy() {
        BitSet newFlags = new BitSet(n*n*n);
        BitSet newProp = new BitSet(n*n*n);
        newFlags.or(flags);
        newProp.or(propagated);

        return new Grid(n, ns, newFlags, newProp);
    }


}