package com.example.jesper.niceshot;

import android.app.Activity;
import android.os.Bundle;

public class GameActivity extends Activity {
    private GameView mGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mGame = new GameView(this);
        setContentView(mGame);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGame.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGame.pause();
    }
}
