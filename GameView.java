package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.icu.text.DateFormat;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Jesper on 2017-01-23.
 */

public class GameView extends SurfaceView implements Runnable {
    private Thread  mGameThread;
    volatile Boolean mIsRunning;
    private Paint mPaint;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Player mPlayer;

    public GameView(final Context context) {
        super(context);
        mIsRunning = false;
        mPlayer = new Player(context);
        mPaint = new Paint();
        mHolder = getHolder();
    }

    @Override
    public void run(){
        while(mIsRunning){
            //input
            update();
            render();

            try {
                mGameThread.sleep(10);
            }catch (InterruptedException ex){}

        }
    }

    public void pause() {
        mIsRunning = false;
        try{
            mGameThread.join();
        }catch (InterruptedException ex){

        }
    }

    public void resume(){
        Log.i("GW", "Reached resume!");
        mIsRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    private void update(){
        mPlayer.Update();
    }
    private void render(){
        if(!mHolder.getSurface().isValid()){
            return;
        }

        mCanvas = mHolder.lockCanvas();
        if(mCanvas == null){
            return;
        }
        mCanvas.drawColor(Color.BLACK);
        mCanvas.drawBitmap(mPlayer.getBitmap(), mPlayer.getX(), mPlayer.getY(), mPaint);
        mHolder.unlockCanvasAndPost(mCanvas);

    }


    //core gameloop
        //input
        //update
        //render
    //pause and resume
    //constructor
}
