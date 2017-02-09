package com.example.jesper.niceshot;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button playButton = (Button)findViewById(R.id.startGameButton);
        playButton.setOnClickListener(this);

        GameStorage storage = new GameStorage(getApplicationContext());
        final TextView highScore = (TextView)findViewById(R.id.HighScoreText);
        highScore.setText("Highscore: " + storage.getHighscore() + " km");
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }
}
