package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

/**
 * Created by Jesper on 2017-01-30.
 */

public class Enemy extends DrawableGameObject implements ICollidable {
    private Rect mBoundingBox;
    private int mBaseSpeed;
    private static final int TARGET_HEIGHT = 72;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;

    public Enemy(Context context){
        super(context, getRandomResourceId(), TARGET_HEIGHT);
        respawn();
    }

    public void respawn(){
        Random r = new Random();
        mX = GameView.STAGE_WIDTH + r.nextInt(100);
        mY = r.nextInt(GameView.STAGE_HEIGHT);
        mSpeed = -15;
        mBaseSpeed = -r.nextInt(MAX_SPEED);
        mBoundingBox = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    public void onCollision(){
        respawn();
    }

    public void update(){
        super.update();
        mX -= mBaseSpeed;
    }

    public Rect getBoundingBox(){
        mBoundingBox.set(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
        return mBoundingBox;
    }

    @Override
    public Boolean checkCollisson(ICollidable objectToCheck) {
        return this.getCollisionBoundaries().intersect(objectToCheck.getCollisionBoundaries());
    }

    @Override
    public Rect getCollisionBoundaries() {
        mBoundingBox.set(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
        return mBoundingBox;
    }

    @Override
    public void collisionHappend() {
        respawn();
    }

    private static int getRandomResourceId(){
        Random r = new Random();
        int shipIndex = r.nextInt(7);

        switch (shipIndex){
            case 0:
                return R.drawable.ship2;
            case 1:
                return R.drawable.ship3;
            case 2:
                return R.drawable.ship4;
            case 4:
                return R.drawable.ship5;
            case 5:
                return R.drawable.ship6;
            default:
                return R.drawable.ship7;
        }


    }
}
