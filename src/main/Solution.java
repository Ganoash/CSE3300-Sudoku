package main;

import java.util.*;

public class Solution {

    /**
     * Returns the filled in sudoku grid.
     * @param grid the partially filled in grid. unfilled positions are -1.
     * @return the fully filled sudoku grid.
     */
    public static int[][] solve(int[][] grid) {
        Grid start = new Grid(grid);
        Stack<Grid> gridStack = new Stack<>();
        gridStack.add(start);

        while (!gridStack.isEmpty()) {
            Grid g = gridStack.pop();
            if(g.isSolved()) {
                // g.print();
                return g.toArray();
            }
            int[] x_minimum_y =  g.getMinimumBlock();
            BitSet block = g.block(x_minimum_y[0], x_minimum_y[1]);
            int count = g.count(block);
            boolean to_continue = false;
            // sudoku easy case: a block can be filled in
            while(count <= 1) {
                if(g.isSolved()) {
                    // g.print();
                    return g.toArray();
                }
                if (count == 0) {
                    to_continue = true;
                    break;
                }
                if (count == 1) {
                    g.fillInFor(g.getFirstFlag(block), x_minimum_y[0], x_minimum_y[1]);
                }
                x_minimum_y = g.getMinimumBlock();
                block = g.block(x_minimum_y[0], x_minimum_y[1]);
                count = g.count(block);
            }
            if(to_continue) {
                continue;
            }

            while(count != 0) {
                int flag = g.getBestFlag(block);
                Grid clone = g.copy();
                clone.fillInFor(flag, x_minimum_y[0], x_minimum_y[1]);
                gridStack.push(clone);
                block.flip(flag);
                count--;
            }
        }

        return null;
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
    private final int[] flagCount;

    public Grid(int n) {
        this.n = n;
        ns = (int) Math.sqrt(n);
        // init all true flags bitset
        flags = new BitSet(n*n*n);
        flags.flip(0, n*n*n);
        // init all true propagated bitset
        propagated = new BitSet(n*n);
        propagated.flip(0, n*n);
        flagCount = new int[n];
    }

    public Grid(int[][] grid) {
        n = grid.length;
        ns = (int) Math.sqrt(n);
        // init all true flags bitset
        flags = new BitSet(n*n*n);
        flags.flip(0, n*n*n);
        // init all true propagated bitset
        propagated = new BitSet(n*n);
        flagCount = new int[n];
        init(grid);
    }

    private Grid(int n,int ns, BitSet flags, BitSet propagated, int[] flagCount) {
        this.n = n;
        this.ns = ns;
        this.flags = flags;
        this.propagated = propagated;
        this.flagCount = flagCount;
    }

    public void init(int[][] grid) {
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                if(grid[x][y] == -1) {
                    continue;
                }
                fillInFor(grid[x][y]-1, x, y);
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
            flagCount[f]++;
            flags.flip(index(x, y, f));
        }
    }

    /**
     *
     */
    public int getFirstFlag(BitSet bs) {
        for(int i = 0; i < n; i++) {
            if(bs.get(i)) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     */
    public int getBestFlag(BitSet bs) {
        int max = Integer.MAX_VALUE;
        int max_i = -1;
        for(int i = 0; i < n; i++) {
            if(bs.get(i)) {
                if(flagCount[i] < max) {
                    max = flagCount[i];
                    max_i = i;
                }
            }
        }
        return max_i;
    }

    /**
     *
     */
    public int[] getFlags(BitSet bs) {
        int[] flags = new int[count(bs)];
        int f_i = 0;
        for(int i = 0; i < n; i++) {
            if(bs.get(i)) {
                flags[f_i] = i;
                f_i++;
            }
        }
        return flags;
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
            mark(f, i, y);
            mark(f, x, i);
        }

        // Fill in local block.
        int originX = (int) Math.floor((float) x / ns);
        int originY = (int) Math.floor((float) y / ns);
        for (int i = originX*ns; i < originX*ns + ns; i++) {
            for (int j = originY*ns; j < originY*ns + ns; j++) {
                mark(f, i, j);
            }
        }

        // Set the square to the correct flag.
        // This is done last to simplify filling in (the above stuff).
        for (int i = 0; i < n; i++) {
            mark(i, x, y);
        }
        flags.set(index(x, y, f), true);
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
        BitSet newProp = new BitSet(n*n);
        int[] newflagC = flagCount.clone();
        newFlags.or(flags);
        newProp.or(propagated);

        return new Grid(n, ns, newFlags, newProp, newflagC);
    }

    public boolean isSolved() {
        boolean solved = true;
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                solved = solved && propagated.get(n*x+y);
            }
        }
        return solved;
    }

    public int[][] toArray() {
        int[][] ret = new int[n][n];
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                if(propagated.get(x*n+y)) {
                    ret[x][y] = getFirstFlag(block(x, y))+1;
                } else {
                    ret[x][y] = -1;
                }
            }
        }
        return ret;
    }

    public void print() {
        int[][] toPrint = toArray();
        for(int x = 0; x < n; x++) {
            System.out.println();
            for(int y = 0; y < n; y++) {
                System.out.print(toPrint[x][y]);
                System.out.print("\t");
            }
        }
        System.out.println();
    }



}