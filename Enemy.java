package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Jesper on 2017-01-30.
 */

public class Enemy extends GameObject {
    private Bitmap mBitmap;
    private Rect mBoundingBox;
    private boolean mIsBoosting;
    private int mBaseSpeed;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 20;

    public Enemy(Context context, int x, int y){
        super(x,y);
        mIsBoosting = false;
        mSpeed = -15;
        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship2);

        double targetHeight = 72;
        double scalingRatio = targetHeight/tempBitmap.getHeight();
        int newHeight = (int)(tempBitmap.getHeight() * scalingRatio);
        int newWidth = (int)(tempBitmap.getWidth() * scalingRatio);
        mBitmap = Bitmap.createScaledBitmap(tempBitmap, newWidth, newHeight, true);
        tempBitmap.recycle();
        mBoundingBox = new Rect(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());

        Random r = new Random();
        mBaseSpeed = r.nextInt(MAX_SPEED);
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

    public void update(){
        super.update();
        mX -= mBaseSpeed;
    }

    public Rect getBoundingBox(){
        mBoundingBox.set(mX, mY, mX + mBitmap.getWidth(), mY + mBitmap.getHeight());
        return mBoundingBox;
    }
}
