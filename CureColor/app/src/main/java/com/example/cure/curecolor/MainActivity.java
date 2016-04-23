package com.example.cure.curecolor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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
        try {
            switch (v.getId()) {
                case R.id.bSleepPlus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("info", "улучшение сна");
                    startActivity(intent);
                    break;
                case R.id.bSleepMinus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("info", "улучшение бодрости");
                    startActivity(intent);
                    break;
                case R.id.bAppetitePlus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("info", "улучшение аппетита");
                    startActivity(intent);
                    break;
                case R.id.bAppetiteMinus:
                    intent = new Intent(this, InfoActivity.class);
                    intent.putExtra("info", "снижение аппетита");
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
