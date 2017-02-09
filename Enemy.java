package com.example.jesper.niceshot;

import android.content.Context;
import android.content.res.Resources;
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
    private final int MAX_SPEED;
    private final int MIN_SPEED;
    private final int MAX_SPAWN_DISTANCE;

    public Enemy(Context context){
        super(context, getRandomResourceId(), R.integer.ENEMY_MODEL_HEIGHT);
        Resources res = context.getResources();
        MAX_SPEED = res.getInteger(R.integer.ENEMY_SPEED_RANGE);
        MIN_SPEED = res.getInteger(R.integer.ENEMY_MIN_SPEED);
        MAX_SPAWN_DISTANCE = res.getInteger(R.integer.ENEMY_MAX_SPAWN_DISTANCE);
        respawn();
    }

    public void respawn(){
        Random r = new Random();
        mX = GameView.STAGE_WIDTH + r.nextInt(MAX_SPAWN_DISTANCE);
        mY = r.nextInt(GameView.STAGE_HEIGHT);
        mBaseSpeed = -(MIN_SPEED + r.nextInt(MAX_SPEED));
        mBoundingBox = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
    }

    @Override
    public void setSpeed(int speed) {
        super.setSpeed(speed);
        mSpeed += mBaseSpeed;
    }

    @Override
    public Boolean checkCollision(ICollidable objectToCheck) {
        return this.getCollisionBoundaries().intersect(objectToCheck.getCollisionBoundaries());
    }

    @Override
    public Rect getCollisionBoundaries() {
        mBoundingBox.set(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
        return mBoundingBox;
    }

    @Override
    public void onCollision() {
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
