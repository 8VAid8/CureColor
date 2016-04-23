package com.example.cure.curecolor;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textInfo;
    Button btn_game;
    String game_type, color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        textInfo = (TextView)findViewById(R.id.textInfo);
        btn_game = (Button)findViewById(R.id.btnGame);

        btn_game.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        game_type = bundle.getString("type");
        color = bundle.getString("color");

        textInfo.setText(game_type);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnGame:
                intent = new Intent(this, GameActivity.class);
                intent.putExtra("type",game_type);
                intent.putExtra("color", color);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
