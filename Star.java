package com.example.jesper.niceshot;

import android.content.Context;

import java.util.Random;

/**
 * Created by Jesper on 2017-02-05.
 */

public class Star extends DrawableGameObject {
    private int distanceRelativeSpeedFactor = 1;
    public Star(Context context, double targetHeight, int x, int y){
        super(context, R.drawable.star, targetHeight, x, y);
        distanceRelativeSpeedFactor = (int)(-targetHeight / 2);
    }

    @Override
    public void setSpeed(int speed) {
        speed = distanceRelativeSpeedFactor + speed;
        super.setSpeed(speed);
    }

    @Override
    public int getSpeed() {
        return  super.getSpeed();
    }

    @Override
    public void update() {
        super.update();
    }
}
