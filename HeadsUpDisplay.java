package com.example.jesper.niceshot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.camera2.params.StreamConfigurationMap;

/**
 * Created by Jesper on 2017-02-09.
 */

public class HeadsUpDisplay {
    private final int STAGE_WIDTH;
    private final int STAGE_HEIGHT;

    private final String SHIELD_TEXT;
    private final String SPEED_TEXT;
    private final String GAME_OVER_TEXT;
    private final String RESTART_TEXT;
    private final int TEXT_HEIGHT;

    public HeadsUpDisplay(Context context, int stageHeight, int stageWidth){
        STAGE_HEIGHT = stageHeight;
        STAGE_WIDTH = stageWidth;

        Resources res = context.getResources();
        SHIELD_TEXT = res.getString(R.string.HUD_SHIELD);
        SPEED_TEXT = res.getString(R.string.HUD_SPEED);
        GAME_OVER_TEXT = res.getString(R.string.HUD_GAMEOVER);
        RESTART_TEXT = res.getString(R.string.HUD_RESTART);
        TEXT_HEIGHT = res.getInteger(R.integer.HUD_TEXT_HEIGHT);
    }

    public void drawInGame(Canvas canvas, Paint paint, Player player) {
        paint.setColor(Color.WHITE);
        paint.setTextSize(TEXT_HEIGHT);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText(SPEED_TEXT + player.getSpeed(), 10, TEXT_HEIGHT, paint);
        canvas.drawText(SHIELD_TEXT + player.getShieldLevel(), STAGE_WIDTH*0.5f, TEXT_HEIGHT, paint);
    }

    public void drawGameOver(Canvas canvas, Paint paint){
        paint.setColor(Color.WHITE);
        paint.setTextSize(TEXT_HEIGHT);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(GAME_OVER_TEXT, STAGE_WIDTH*0.5f, TEXT_HEIGHT*2, paint);
        canvas.drawText(RESTART_TEXT, STAGE_WIDTH*0.5f, STAGE_HEIGHT-TEXT_HEIGHT, paint);
    }
}
