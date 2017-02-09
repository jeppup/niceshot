package com.example.jesper.niceshot;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Jesper on 2017-01-23.
 */


public class Player extends DrawableGameObject implements ICollidable {
    private boolean mIsBoosting;
    private int mShield;

    private final int MIN_SPEED;
    private final int MAX_SPEED;
    private final int GRAVITY;
    private final int COLLISION_DAMAGE;
    private final int ACCELERATION;
    private final int DEACCELERATION;

    private final Rect mBoundingBox;

    public Player(Context context){
        super(context, R.drawable.ship1, R.integer.PLAYER_MODEL_HEIGHT);

        Resources res = context.getResources();
        GRAVITY = res.getInteger(R.integer.GAME_GRAVITY);
        MIN_SPEED = res.getInteger(R.integer.PLAYER_MIN_SPEED);
        MAX_SPEED = res.getInteger(R.integer.PLAYER_MAX_SPEED);
        COLLISION_DAMAGE = res.getInteger(R.integer.PLAYER_COLLISION_DAMAGE);
        ACCELERATION = res.getInteger(R.integer.PLAYER_ACCELERATION);
        DEACCELERATION = res.getInteger(R.integer.PLAYER_DEACCELERATION);

        mShield = res.getInteger(R.integer.PLAYER_SHIELD);
        mIsBoosting = false;
        mBoundingBox = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public int clamp(final int val, final int max, final int min) {
        if(val < MIN_SPEED){
            return MIN_SPEED;
        }else if(val > MAX_SPEED){
            return MAX_SPEED;
        }

        return val;
    }

    @Override
    public void update(){
        if(mIsBoosting) {
            mSpeed += ACCELERATION;
        }
        else {
            mSpeed -= DEACCELERATION;
        }

        mSpeed = clamp(mSpeed, MAX_SPEED, MIN_SPEED);

        mY -= mSpeed;
        mY += GRAVITY;
    }

    @Override
    public Boolean checkCollision(ICollidable objectToCheck) {
        return getCollisionBoundaries().intersect(objectToCheck.getCollisionBoundaries());
    }

    @Override
    public Rect getCollisionBoundaries() {
        mBoundingBox.set(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
        return mBoundingBox;
    }

    @Override
    public void onCollision() {
        setShieldLevel(mShield - COLLISION_DAMAGE);
    }

    public void startBoost(){
        mIsBoosting = true;
    }
    public void stopBoost(){
        mIsBoosting = false;
    }
    public int getShieldLevel(){ return mShield; }
    public void setShieldLevel(int shieldLevel){ mShield = shieldLevel; }
}
