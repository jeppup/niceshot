package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jesper on 2017-02-05.
 */

public class DrawableGameObject extends GameObject {
    protected Bitmap mBitmap;
    public DrawableGameObject(Context context, int bitmapResourceId, double targetHeight){
        initializeBitmap(context, bitmapResourceId, targetHeight);
    }

    public DrawableGameObject(Context context, int bitmapResourceId, double targetHeight, int x, int y){
        super(x, y);
        initializeBitmap(context, bitmapResourceId, targetHeight);
    }

    private void initializeBitmap(Context context, int bitmapResourceId, double targetHeight){
        Bitmap tempBitmap = BitmapFactory.decodeResource(context.getResources(), bitmapResourceId);
        double scalingRatio = targetHeight/tempBitmap.getHeight();
        int newHeight = (int)(tempBitmap.getHeight() * scalingRatio);
        int newWidth = (int)(tempBitmap.getWidth() * scalingRatio);
        mBitmap = Bitmap.createScaledBitmap(tempBitmap, newWidth, newHeight, true);
        tempBitmap.recycle();
    }

    @Override
    public int getWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public int getHeight() {
        return mBitmap.getHeight();
    }

    @Override
    public void render(final Canvas canvas, Paint paint) {
        canvas.drawBitmap(mBitmap, getX(), getY(), paint);
    }
}
