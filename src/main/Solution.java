package weblab;

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

class Grid {
    private final int block_size;

    private final int n;
    private final int ns;
    private final BitSet flags;

    public Grid(int[][] grid) {
        n = grid.length;
        ns = (int) Math.sqrt(n);
        block_size = n + 1;
        flags = new BitSet(block_size*n*n);
    }

    public boolean isSolved(int x, int y) {
        return flags.get(block_size*(x*n + y));
    }

    public boolean mark(int f, int x, int y) {
        flags.flip(block_size*(x*n + y)+f);
    }

    public boolean isKnown(int x, int y) {
        int count = 0;
        for(int i=1; i < block_size; i++) {
            if(flags.get(block_size*(x*n +y)+i)) {
                count++;
            }
        }
        return count==1;
    }

    public boolean canBeSolved(int x, int y) {
        int count = 0;
        for(int i=1; i < block_size; i++) {
            if(flags.get(block_size*(x*n +y)+i)) {
                count++;
            }
        }
        return count>=1;
    }

    public boolean solveFor(int f, int x, int y) {
        mark(0, x, y);
        // mark horizontal and vertical
        for(int i = 0; i < n; i++) {
            if(i!=y){
                mark(f,x, i);
            }
            if(i!=x) {
                mark(f,i,y);
            }
        }
        // mark block
        int block_x = (int) Math.floor(x/ns);
        int block_y = (int) Math.floor(y/ns);
        for(int i = 0; i < ns; i++) {
            for(int j = 0; j < ns; j++) {
                mark(f, block_x*ns+i, block_y*ns+j);
            }
        }
    }

    public boolean canBeSolved() {
        boolean b = true;
        for(int x = 0; x < n; x++) {
            for(int y = 0; y < n; y++) {
                b = b && canBeSolved(x, y);
            }
        }
    }

    public void solve() {

    }

}