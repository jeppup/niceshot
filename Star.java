package com.example.jesper.niceshot;

import android.content.Context;

/**
 * Created by Jesper on 2017-02-05.
 */

public class Star extends DrawableGameObject {
    private final int SPEED_SCALE;
    private int mDistanceRelativeSpeedFactor;
    public Star(Context context, double targetHeight, int x, int y){
        super(context, R.drawable.star, targetHeight, x, y);
        SPEED_SCALE = context.getResources().getInteger(R.integer.STAR_SPEED_SCALE_FACTOR);
        mDistanceRelativeSpeedFactor = (int)(-targetHeight / SPEED_SCALE);
    }

    @Override
    public void setSpeed(int speed) {
        speed = mDistanceRelativeSpeedFactor + speed;
        super.setSpeed(speed);
    }
}
