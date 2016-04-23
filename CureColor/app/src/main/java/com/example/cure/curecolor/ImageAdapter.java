package com.example.cure.curecolor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.Arrays;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    Bitmap bitmap;
    Bitmap[] bitmaps;
    Integer fig_width;
    Integer fig_height;

    public ImageAdapter(Context c, Integer[][] fill_colors, Integer width, Integer height) {
        mContext = c;
        fig_width = width / fill_colors[0].length;
        fig_height = height / fill_colors.length;
        int[] colors = new int[fig_width * fig_height];

        bitmaps = new Bitmap[fig_width * fig_height];
        int pos = 0;


        for(int i = 0; i < fill_colors.length; i++)
        {
            for(int j = 0; j < fill_colors[0].length; j++)
            {
                Arrays.fill(colors, 0, fig_width * fig_height, fill_colors[i][j]);
                bitmaps[pos] = Bitmap.createBitmap(colors, fig_width, fig_height, Bitmap.Config.RGB_565);
                pos++;
                colors = new int[fig_width * fig_height];
            }
        }

      //  bitmap = Bitmap.createBitmap(colors, 300, 300, Bitmap.Config.RGB_565);

    }

    public int getCount() {
        return bitmaps.length;
    }

    public Object getItem(int position) {
        return bitmaps[position];
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(fig_width, fig_height));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }


        imageView.setImageBitmap(bitmaps[position]);
        return imageView;
    }



    // references to our images

}