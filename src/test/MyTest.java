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
}
