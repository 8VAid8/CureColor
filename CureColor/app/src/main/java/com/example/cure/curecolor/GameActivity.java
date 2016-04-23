package com.example.cure.curecolor;

        import android.graphics.Color;
        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.AppCompatActivity;
        import android.util.DisplayMetrics;
        import android.view.Display;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.Window;
        import android.view.WindowManager;
        import android.widget.ArrayAdapter;
        import android.widget.GridView;
        import android.widget.ListAdapter;
        import android.widget.TextView;

        import android.os.Bundle;
        import android.app.Activity;
        import android.content.ClipData;
        import android.view.DragEvent;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.View.OnTouchListener;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TextView;

        import org.w3c.dom.Text;

        import android.app.Activity;
        import android.content.Context;
        import android.graphics.Canvas;
        import android.graphics.Color;
        import android.graphics.Paint;
        import android.graphics.Rect;
        import android.os.Bundle;
        import android.view.View;

public class GameActivity extends Activity {
    Paint p;
    Rect rect;
    TextView drag;
    Canvas canvas;

    GridView grid_view;
    MatrixGame gm;
    ArrayAdapter<String> adapter;
    TextView text;
    String color, game_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_game);

        grid_view = (GridView)findViewById(R.id.gridView);
        grid_view.setOnTouchListener(new OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    return true;
                }
                return false;
            }

        });

        Bundle bundle = getIntent().getExtras();
        game_type = bundle.getString("type");
        color = bundle.getString("color");

        gm = new MatrixGame();
        gm.RandomMatrix();

        //заполнение GridView цветами в соответствии с gm.matrix
        Integer colors[] = ColorConverter.ToColorsMatrix(gm.matrix,color);


       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
          //      android.R.layout.two_line_list_item, colors[1]);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;

        Integer fig_width = screenWidth / gm.matrix[0].length;
        Integer fig_height = screenHeight / gm.matrix.length;

        grid_view.setNumColumns(gm.columns);

        grid_view.setAdapter(new ImageAdapter(this, colors, gm.matrix, screenWidth, screenHeight));


    }

}
