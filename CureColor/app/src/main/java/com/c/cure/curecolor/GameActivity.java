package com.c.cure.curecolor;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsoluteLayout;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Arrays;

import com.example.cure.curecolor.R;

public class GameActivity extends Activity  implements View.OnTouchListener, View.OnDragListener  {

    MatrixGame gm;
    String color, game_type;
    Bitmap[] bitmaps;
    Integer fig_width;
    Integer fig_height;
    int screenWidth;
    int screenHeight;

    RelativeLayout mainLayout;
    ImageView dragging;
    float startX, startY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        mainLayout = (RelativeLayout) findViewById(R.id.game);
        mainLayout.setOnDragListener(this);

        //provide data from previous activitys
        Bundle bundle = getIntent().getExtras();
        game_type = bundle.getString("type");
        color = bundle.getString("color");
        //game info preparation
        gm = new MatrixGame();
        gm.RandomMatrix();

        Integer colors[] = ColorConverter.ToColorsMatrix(gm.matrix,color);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;
        CreateBitmaps(colors, gm.matrix, screenWidth, screenHeight);

        for (int i = 0; i < bitmaps.length; i++)
        {
            ImageView image = new ImageView(this);
            image.setImageBitmap(bitmaps[i]);
            image.setOnTouchListener(this);

            Coords c = MasToMatrix(i, gm.matrix[0].length);
            image.setX(fig_width * c.j);
            image.setY(fig_height * c.i);
            image.setTag(i);
            mainLayout.addView(image);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            dragging = (ImageView) v;
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            dragging.startDrag(data, shadowBuilder, v, 0);
            dragging.setVisibility(View.INVISIBLE);
            startX = v.getX();
            startY = v.getY();
            return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();
        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                // do nothing
                break;
            case DragEvent.ACTION_DROP:
                // Dropped, reassign View to ViewGroup
                setAbsoluteLocation(dragging, startX, startY);
                for (int i = 0; i< bitmaps.length; i++)
                {
                    Rect rect = new Rect();
                    ImageView img = (ImageView)mainLayout.findViewWithTag(i);
                    img.getHitRect(rect);
                    if (rect.contains(x, y)) {
                        setSameAbsoluteLocation(dragging, img);
                        img.setX(startX);
                        img.setY(startY);
                        onItemsChanged((int)dragging.getTag(), (int)img.getTag());
                        int tag0 = (int)dragging.getTag(), tag1 = (int)img.getTag();
                        dragging.setTag(tag1);
                        img.setTag(tag0);
                        break;
                    }
                }
                dragging.setVisibility(View.VISIBLE);
                break;
            case DragEvent.ACTION_DRAG_EXITED:
          //  setAbsoluteLocationCentered(v, x, y);
            break;
            case DragEvent.ACTION_DRAG_ENDED:
                //v.setBackgroundDrawable(normalShape);

            default:
                break;
        }
        return true;
    }

    private void setSameAbsoluteLocation(View v1, View v2) {
        setAbsoluteLocation(v1, v2.getX(), v2.getY());
    }

    private void setAbsoluteLocationCentered(View v, int x, int y) {
        setAbsoluteLocation(v, x - v.getWidth() / 2, y - v.getHeight() / 2);
    }

    private void setAbsoluteLocation(View v, float x, float y) {
        v.setX(x);
        v.setY(y);
    }

    public void onItemsChanged(int positionOne, int positionTwo) {
        String text = "You've changed item " + positionOne + " with item "+ positionTwo;
      //  Toast.makeText(this, text, Toast.LENGTH_SHORT).show();

        int[][] buf = new int[gm.matrix.length][gm.matrix[0].length];

        int m_length = gm.matrix.length * gm.matrix[0].length;
        int col_cnt = gm.columns;

        int i1 = 0;
        int j1 = 0;

        int i0 = 0;
        for(int i = 0; i < positionOne; i++)
        {
            i0++;
            if(i0 < col_cnt)
            j1++;
            else
            {
                i0 = 0;
                j1 = 0;
                i1++;
            }
        }

        int i2 = 0;
        int j2 = 0;
        i0 = 0;
        for(int i = 0; i < positionTwo; i++)
        {
            i0++;
            if(i0 < col_cnt)
                j2++;
            else
            {
                j2 = 0;
                i0 = 0;
                i2++;
            }
        }
      //  Toast.makeText(this, "i1" + String.valueOf(i1), Toast.LENGTH_SHORT).show();

        gm.swap(i1, j1, i2, j2);
        if(gm.CheckWin())
        {
           // Toast.makeText(this, "WIIIIIIIIIIIN!!!11", Toast.LENGTH_SHORT).show();
            NewLevel();
        }

    }

    void NewLevel()
    {
        mainLayout.removeAllViews();
        //game info preparation
        gm.RandomMatrix();

        Integer colors[] = ColorConverter.ToColorsMatrix(gm.matrix,color);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenWidth = displaymetrics.widthPixels;
        screenHeight = displaymetrics.heightPixels;
        CreateBitmaps(colors, gm.matrix, screenWidth, screenHeight);

        for (int i = 0; i < bitmaps.length; i++)
        {
            ImageView image = new ImageView(this);
            image.setImageBitmap(bitmaps[i]);
            image.setOnTouchListener(this);

            Coords c = MasToMatrix(i, gm.matrix[0].length);
            image.setX(fig_width * c.j);
            image.setY(fig_height * c.i);
            image.setTag(i);
            mainLayout.addView(image);
        }
    }


    Coords MasToMatrix(int position, int col_cnt)
    {
        int i1 = 0;
        int j1 = 0;

        int i0 = 0;
        for(int i = 0; i < position; i++)
        {
            i0++;
            if(i0 < col_cnt)
                j1++;
            else
            {
                i0 = 0;
                j1 = 0;
                i1++;
            }
        }
        return new Coords(i1,j1);
    }

    class Coords
    {
        public int i, j;
        public Coords(int i, int j)
        {
            this.i = i;
            this.j = j;
        }
    }

    public void CreateBitmaps( Integer[] fill_colors, int[][] coords, Integer width, Integer height) {

        fig_width = width / coords[0].length;
        fig_height = height / coords.length;
        int[] colors = new int[fig_width * fig_height];

        bitmaps = new Bitmap[coords.length * coords[0].length];
        int pos = 0;


        for(int i = 0; i < coords.length; i++)
        {
            for(int j = 0; j < coords[0].length; j++)
            {
                Arrays.fill(colors, 0, fig_width * fig_height, fill_colors[coords[i][j]]);
                bitmaps[pos] = Bitmap.createBitmap(colors, fig_width, fig_height, Bitmap.Config.RGB_565);
                pos++;
                colors = new int[fig_width * fig_height];
            }
        }
    }

}
