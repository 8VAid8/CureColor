package com.c.cure.curecolor;

import java.util.Random;

/**
 * Created by Alexey on 4/23/16.
 */
public class MatrixGame {
    public int rows, columns;
    int[][] matrix;

    /***
     * Generate new random matrix with shuffling int items
     */
    public void RandomMatrix() {
        matrix = new int[rows][columns];

        int plc = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = plc;
                plc++;
            }
        }
        shuffle(matrix, columns, new Random());
    }

    /**
     * swap 2 elements
     * @param i1
     * @param j1
     * @param i2
     * @param j2
     */

    public void swap(int i1, int j1, int i2, int j2)
    {
        int temp = matrix[i1][j1];
        matrix[i1][j1] = matrix[i2][j2];
        matrix[i2][j2] = temp;
    }

    /***
     * Check for win. Should check after each swapping
     * @return
     */
    public boolean CheckWin()
    {
        int cnt = rows * columns;
        int buf = matrix[0][0];
        for(int i = 0; i < rows; i++)
         {
             for(int j = 0; j < columns; j++)
             {
                 if(i == 0 && j == 0) continue;
                 if(buf < matrix[i][j]){ cnt--; }
                 buf = matrix[i][j];
             }
         }
        if(cnt == rows * columns)
        {
            RwsClmnsPlus();
            return true;
        }
        cnt = rows * columns;
        buf = matrix[0][0];
        //- check2
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                if(i == 0 && j == 0) continue;
                if(buf > matrix[i][j]){ cnt--; }
                buf = matrix[i][j];
            }
        }
        if(cnt == rows * columns)
        {
            RwsClmnsPlus();
            return true;
        }
        return  false;
    }

    void RwsClmnsPlus()
    {
        rows +=1;
        columns +=1;
    }
    /** Shuffles a 2D array with the same number of columns for each row. */
    static void shuffle(int[][] matrix, int columns, Random rnd) {
        int size = matrix.length * columns;
        for (int i = size; i > 1; i--)
            swap_random(matrix, columns, i - 1, rnd.nextInt(i));
    }

    /**
     * Swaps two entries in a 2D array, where i and j are 1-dimensional indexes, looking at the
     * array from left to right and top to bottom.
     */
    static void swap_random(int[][] matrix, int columns, int i, int j) {
        int tmp = matrix[i / columns][i % columns];
        matrix[i / columns][i % columns] = matrix[j / columns][j % columns];
        matrix[j / columns][j % columns] = tmp;
    }



    public MatrixGame(int rows, int columns)
    {
        this.rows = rows;
        this.columns = columns;
    }
    public MatrixGame()
    {
        this.rows = 4;
        this.columns = 2;
    }
}
