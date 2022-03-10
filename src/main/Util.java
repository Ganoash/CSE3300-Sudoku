package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class Util {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        // Example
        int[][] sudoku = readerUtil("./data/basic/size3_level0_puzzle1.txt");
    }

    public static String[] readAllSudokus(String directory) {
        File dir = new File(directory);
        return dir.list();
    }

    public static int[][] readerUtil(String location) {
        FileReader fr = null;
        try {
            fr = new FileReader(location);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Unsafe
        Scanner inFile = new Scanner(fr);

        int length = Integer.parseInt(inFile.nextLine());
        int bla = Integer.parseInt(inFile.nextLine()); // No idea what this does?

        int[][] result = new int[length*length][length*length];

        for (int i = 0; i < length * length; i++) {
            for (int j = 0; j < length * length; j++) {
                result[i][j] = Integer.parseInt(inFile.next());
            }
        }

        return result;
    }
}
