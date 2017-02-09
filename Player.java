package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Jesper on 2017-01-23.
 */


public class Player extends DrawableGameObject implements ICollidable {
    private boolean mIsBoosting;
    private static int mShield = 3;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;
    private static final int GRAVITY = 12;
    private static final int mTargetHeight = 72;
    private final Rect mBoundingBox;

    public Player(Context context){
        super(context, R.drawable.ship1, mTargetHeight);
        mIsBoosting = false;
        mSpeed = 0;
        mBoundingBox = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
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

    @Override
    public void update(){
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

    public void onCollision(){
        mShield--;
        if(mShield <= 0){
            Log.d("PLAYER", "You lost son");
        }
    }

    public Rect getBoundingBox(){
        mBoundingBox.set(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
        return mBoundingBox;
    }

    @Override
    public Boolean checkCollisson(ICollidable objectToCheck) {
        return getCollisionBoundaries().intersect(objectToCheck.getCollisionBoundaries());
    }

    @Override
    public Rect getCollisionBoundaries() {
        mBoundingBox.set(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
        return mBoundingBox;
    }

    @Override
    public void collisionHappend() {
        setShieldLevel(mShield - 1);
    }

    public int getShieldLevel(){ return mShield; }
    public void setShieldLevel(int shieldLevel){ mShield = shieldLevel; }
}
