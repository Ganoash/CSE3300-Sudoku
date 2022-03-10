package test;

import main.Util;
import main.Solution;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import java.io.InvalidObjectException;
import java.util.Arrays;

public class MyTest {

    @Test
    void addition() {
        assertEquals(2, 1 + 1);
    }

    @Test
    void testUtility() {
        int[][] sudoku = Util.readerUtil("./data/basic/size3_level0_puzzle1.txt");
        assertEquals(2, 1 + 1);
        assertEquals(sudoku[0][0], 9);
        assertEquals(sudoku[8][8], 5);
    }

    @Test
    void testRounding() {
        assertEquals(util(0), 0);
        assertEquals(util(1), 0);
        assertEquals(util(2), 0);
        assertEquals(util(3), 1);
        assertEquals(util(4), 1);
        assertEquals(util(5), 1);
        assertEquals(util(6), 2);
        assertEquals(util(7), 2);
        assertEquals(util(8), 2);
    }

    int util(int val) {
        return (int) Math.floor((float) val / 3);
    }

    @Test
    void testExample2() throws InvalidObjectException {
        int[][] sudoku = Util.readerUtil("./data/basic/size3_level10_puzzle2.txt");

        int[][] solution = Solution.solve(sudoku);

        assertEquals(solution[2][3], 3);
        assertEquals(solution[2][6], 7);
        assertEquals(solution[2][8], 1);
        assertEquals(solution[5][0], 5);
        assertEquals(solution[7][2], 4);
        assertEquals(solution[7][3], 8);
        assertEquals(solution[8][5], 1);
    }

    @Test
    void testExample3() throws InvalidObjectException {
        int[][] sudoku = Util.readerUtil("./data/pruning/size3_level20_puzzle1.txt");

        int[][] solution = Solution.solve(sudoku);

        assertEquals(solution[0][1], 9);
        assertEquals(solution[0][3], 5);
        assertEquals(solution[0][7], 2);
        assertEquals(solution[1][6], 3);
        assertEquals(solution[2][4], 2);
        assertEquals(solution[3][2], 7);
        assertEquals(solution[3][5], 3);
        assertEquals(solution[4][7], 8);
        assertEquals(solution[5][0], 8);
        assertEquals(solution[6][3], 3);
        assertEquals(solution[6][4], 8);
        assertEquals(solution[6][6], 1);
        assertEquals(solution[8][6], 8);
        assertEquals(solution[8][7], 4);
    }

    @Test
    void testExample4() throws InvalidObjectException {
        int[][] sudoku = Util.readerUtil("./data/basic/size3_level10_puzzle2.txt");

        int[][] solution = Solution.solve(sudoku);

        assertTrue(check(sudoku, solution));
    }

    @Test
    void testExample5() throws InvalidObjectException {
        int[][] sudoku = Util.readerUtil("./data/pruning/size3_level20_puzzle1.txt");

        int[][] solution = Solution.solve(sudoku);

        assertTrue(check(sudoku, solution));
    }

    @Test
    public void testAllSudoku() {
        long total = 0;
        for (int i = 0; i < 1; i++) {
            for (String dir : new String[]{"./data/basic/", "./data/pruning/", "./data/var_selection/"}) {
                String[] local = Util.readAllSudokus(dir);
                for (String f : local) {
                    int[][] sudoku = Util.readerUtil(dir + f);
                    long time = System.currentTimeMillis();
                    int[][] solution = Solution.solve(sudoku);
                    long elapsed = System.currentTimeMillis() - time;
                    total += elapsed;
                    assertTrue(check(sudoku, solution));
                }
            }
        }
        System.out.println("Total time elapsed: " + total);
    }

    public boolean check(int[][] original, int[][] sudoku) {
        if (sudoku == null || sudoku.length == 0) {
            return false;
        }

        int shouldBe = (sudoku.length) * (sudoku.length + 1) / 2;
        for (int i = 0; i < sudoku.length; i++ ){
            int sum = 0;
            for (int j = 0; j < sudoku.length; j++) {
                sum += sudoku[i][j];
            }
            if (sum != shouldBe) {
                System.out.println("HORIZONTAL: returning false because: " + sum + " is not equal to: " + shouldBe);
                return false;
            }
        }

        // Check vertical
        for (int i = 0; i < sudoku.length; i++ ){
            int sum = 0;
            for (int j = 0; j < sudoku.length; j++) {
                sum += sudoku[j][i];
            }
            if (sum != shouldBe) {
                System.out.println("VERTICAL: returning false because: " + sum + " is not equal to: " + shouldBe);
                return false;
            }
        }

        // Check blocks
        int ns = (int) Math.sqrt((double) sudoku.length);
        for (int blockI = 0; blockI < ns; blockI++) {
            for (int blockJ = 0; blockJ < ns; blockJ++) {
                // Check this block!
                // System.out.println("Checking a new block!");
                int sum = 0;
                for (int i = blockI*ns; i < blockI*ns + ns; i++) {
                    for (int j = blockJ*ns; j < blockJ*ns + ns; j++) {
                        // System.out.printf("Checking the block at (%d, %d)\n", i, j);
                        sum += sudoku[i][j];
                    }
                }
                if (sum != shouldBe) {
                    System.out.println("BLOCK: returning false because: " + sum + " is not equal to: " + shouldBe);
                    return false;
                }
            }
        }

        // Check no changes
        for (int i = 0; i < sudoku.length; i++ ){
            for (int j = 0; j < sudoku.length; j++) {
                if (original[i][j] != sudoku[i][j] && original[i][j] != -1) {
                    System.out.println("The matrix was changed in illegal ways");
                }
            }
        }

        return true;
    }
}
