package com.example.cure.curecolor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {

    GridView grid_view;
    MatrixGame gm;
    ArrayAdapter<String> adapter;
    TextView text;
    String color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        text = (TextView) findViewById(R.id.textView);

        color = "#6ae145";
        gm = new MatrixGame();
        gm.RandomMatrix();

        //заполнение GridView цветами в соответствии с gm.matrix
        String colors[][] = ColorConverter.ToColorsMatrix(gm.matrix,color);
        text.setTextColor(Color.parseColor(colors[0][1]));
    }
}
