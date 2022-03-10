package test;

import main.Util;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

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
}
