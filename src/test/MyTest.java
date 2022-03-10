package test;

import main.Util;
import main.Solution;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testExample() throws InvalidObjectException {
        int[][] sudoku = Util.readerUtil("./data/basic/size3_level5_puzzle1.txt");

        int[][] solution = Solution.solve(sudoku);

        assertEquals(solution[8][6], 3);
        assertEquals(solution[4][6], 5);

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
}
