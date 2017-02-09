package com.example.jesper.niceshot;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jesper on 2017-01-30.
 */

public class GameObject {
    public GameObject(int x, int y) {
        this.mX = x;
        this.mY = y;
        mSpeed = 0;
    }

    public GameObject(){
        this.mX = 0;
        this.mY = 0;
        mSpeed = 0;
    }

    //render(), draw()
    protected int mX, mY, mSpeed;

    public int getX() {
        return mX;
    }

    public void setX(int mX) {
        this.mX = mX;
    }

    public int getY() {
        return mY;
    }

    public void setY(int mY) {
        this.mY = mY;
    }

    public void setSpeed(int speed){ mSpeed = speed; }

    public int getSpeed(){ return mSpeed; }

    public int getWidth(){return 0; }

    public int getHeight(){return 0; }

    public void update(){
        mX += mSpeed;
    }

    public void render(final Canvas canvas, Paint paint){

    }
}
