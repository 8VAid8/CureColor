package com.example.cure.curecolor;


import android.graphics.Color;

/**
 * Created by Alexey on 4/23/16.
 */
public class ColorConverter {
    public static String[][] ToColorsMatrix(int[][] matrix, String color)
    {
        int clr = Color.parseColor(color);
        int parts = matrix.length * matrix[0].length;

        int curr_red = Color.red(clr);
        int curr_green = Color.green(clr);
        int curr_blue = Color.blue(clr);

        int d_red = (255 - curr_red) / parts;
        int d_green = (255 - curr_green) / parts;
        int d_blue = (255 - curr_blue) / parts;

        String[][] colors_matrix = new String[matrix.length][matrix[0].length];

        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
                curr_red += d_red;
                curr_green += d_green;
                curr_blue += d_blue;

                colors_matrix[i][j] = String.format("#%06X", (0xFFFFFF & Color.rgb(curr_red, curr_green, curr_blue)));
            }
        }

        return colors_matrix;
    }
}
