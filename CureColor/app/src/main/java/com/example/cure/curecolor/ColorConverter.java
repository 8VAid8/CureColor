package com.example.cure.curecolor;


import android.graphics.Color;

/**
 * Created by Alexey on 4/23/16.
 */
public class ColorConverter {
    public static Integer[] ToColorsMatrix(int[][] matrix, String color)
    {
        int clr = Color.parseColor(color);
        int parts = matrix.length * matrix[0].length;

        int curr_red = Color.red(clr);
        int curr_green = Color.green(clr);
        int curr_blue = Color.blue(clr);

        int d_red = (255 - curr_red) / parts;
        int d_green = (255 - curr_green) / parts;
        int d_blue = (255 - curr_blue) / parts;

        Integer[] colors_matrix = new Integer[matrix.length * matrix[0].length];

        for(int i = 0; i < matrix.length * matrix[0].length; i++)
        {
                curr_red += d_red;
                curr_green += d_green;
                curr_blue += d_blue;

                colors_matrix[i] = Color.rgb(curr_red, curr_green, curr_blue);
        }

        return colors_matrix;
    }
}
