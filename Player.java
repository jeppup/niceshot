package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Jesper on 2017-01-23.
 */

public class Player {
    private int mX, mY;
    private Bitmap mBitmap;
    private int mSpeed;

    public Player(Context context){
        mX = 0;
        mY = 200;
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship1);

    }

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

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int mSpeed) {
        this.mSpeed = mSpeed;
    }

    //Position
    //Graphical representation (bitmap)
    //Speed

    public void Update(){
        mX++;
    }
}
