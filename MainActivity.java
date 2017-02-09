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

        SharedPreferences prefs = getSharedPreferences(GameView.PREFS, Context.MODE_PRIVATE);
        int longestDistance = prefs.getInt(GameView.LONGEST_DIST, 0);
        final TextView highScore = (TextView)findViewById(R.id.HighScoreText);
        highScore.setText("Highscore: " + longestDistance + " km");
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }
}
