package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by Jesper on 2017-01-23.
 */


public class Player extends GameObject {
    private Bitmap mBitmap;
    private boolean mIsBoosting;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;
    private static final int GRAVITY = 12;

    public Player(Context context){
        super(0,0);
        mIsBoosting = false;
        mSpeed = 0;
        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship1);

        double targetHeight = 72;
        double scalingRatio = targetHeight/tempBitmap.getHeight();
        int newHeight = (int)(tempBitmap.getHeight() * scalingRatio);
        int newWidth = (int)(tempBitmap.getWidth() * scalingRatio);
        mBitmap = Bitmap.createScaledBitmap(tempBitmap, newWidth, newHeight, true);
    }

    @Override
    public int getX() {
        return mX;
    }
    @Override
    public void setX(int mX) {
        this.mX = mX;
    }
    @Override
    public int getY() {
        return mY;
    }
    @Override
    public void setY(int mY) {
        this.mY = mY;
    }
    @Override
    public int getWidth(){return mBitmap.getWidth(); }
    @Override
    public int getHeight(){return mBitmap.getHeight(); }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void startBoost(){
        mIsBoosting = true;
    }

    public void stopBoost(){
        mIsBoosting = false;
    }

    //Position
    //Graphical representation (bitmap)
    //Speed
    public static int clamp(final int val, final int max, final int min) {
        //Speed restrictions
        if(val < MIN_SPEED){
            return MIN_SPEED;
        }else if(val > MAX_SPEED){
            return MAX_SPEED;
        }

        return val;
    }

    public void Update(){
        //Accelerate/Decaccelerate
        if(mIsBoosting) {
            mSpeed += 2;
        }
        else {
            mSpeed -= 5;
        }

        mSpeed = clamp(mSpeed, MAX_SPEED, MIN_SPEED);


        //Gravity
        mY -= mSpeed;
        mY += GRAVITY;
    }
}
