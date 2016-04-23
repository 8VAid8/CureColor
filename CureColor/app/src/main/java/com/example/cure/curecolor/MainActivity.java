package com.example.cure.curecolor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSleepPlus,btnSleepMinus, btnAppetitePlus, btnAppetiteMinus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSleepPlus = (Button) findViewById(R.id.bSleepPlus);
        btnSleepMinus = (Button) findViewById(R.id.bSleepMinus);
        btnAppetitePlus = (Button) findViewById(R.id.bAppetitePlus);
        btnAppetiteMinus = (Button) findViewById(R.id.bAppetiteMinus);

        btnSleepPlus.setOnClickListener(this);
        btnSleepMinus.setOnClickListener(this);
        btnAppetitePlus.setOnClickListener(this);
        btnAppetiteMinus.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        final Random random = new Random();
        int col_n = random.nextInt(6);
        String color = "#00fa9a";
        try {
            switch (v.getId()) {
                case R.id.bSleepPlus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("type", "bSleepPlus");
                    if(col_n == 0)
                     color = "#00fa9a";
                    else if (col_n == 1 ||col_n == 2)
                        color = "#43cd80";
                    else if (col_n == 3 || col_n == 4)
                        color = "#ffaeb9";
                    else if (col_n == 5)
                        color = "#db7093";
                    intent.putExtra("color", color);
                    startActivity(intent);
                    break;
                case R.id.bSleepMinus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("type", "bSleepMinus");
                    if(col_n == 0)
                        color = "#ff3030";
                    else if (col_n == 1 ||col_n == 2)
                        color = "#eeee00";
                    else if (col_n == 3 || col_n == 4)
                        color = "#ffa500";
                    else if (col_n == 5)
                        color = "#ffa500";
                    intent.putExtra("color", color);
                    startActivity(intent);
                    break;
                case R.id.bAppetitePlus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("type", "bAppetitePlus");
                    if(col_n == 0)
                        color = "#ffff00";
                    else if (col_n == 1 ||col_n == 2)
                        color = "#ffa500";
                    else if (col_n == 3 || col_n == 4)
                        color = "#ff7f24";
                    else if (col_n == 5)
                        color = "#ffdb58";
                    intent.putExtra("color", color);
                    startActivity(intent);
                    break;
                case R.id.bAppetiteMinus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("type", "bAppetiteMinus");
                    if(col_n == 0)
                        color = "#808080";
                    else if (col_n == 1 ||col_n == 2)
                        color = "#000000";
                    else if (col_n == 3 || col_n == 4)
                        color = "#0000ff";
                    else if (col_n == 5)
                        color = "#8b00ff";
                    intent.putExtra("color", color);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
        catch (Exception ex)
        {
            Log.d("error", ex.getMessage());
        }
    }
}
