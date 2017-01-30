package com.example.jesper.niceshot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jesper on 2017-01-23.
 *Game art source: http://opengameart.org/content/space-ships-side-scroller
 * TO DO:
 * Stars
 *      Update to scroll
 *      needs to match player velocity
 *      need recycling
 *
 * Enemy
 *      render() / update()
 *      getHitBox()
 *
 * update()
 *      checkCollissions()
 *      end game / restart
 *
 * Jukebox
 *      loading and playing sound effects
 *
 *  Highscores / previous results
 *        saving and lodaing persistent data
 */

public class GameView extends SurfaceView implements Runnable {
    private Thread  mGameThread;
    volatile Boolean mIsRunning;
    private Paint mPaint;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Player mPlayer;
    private static final String TAG = "NICESHOT";
    private final int DELAY = 1000/60;
    private final int STAGE_HEIGHT = 720;
    private final int STAGE_WIDTH;
    private final int STAR_COUNT = 40;
    private final int ENEMY_COUNT  = 3;
    private final ArrayList<GameObject> mStars = new ArrayList<GameObject>();
    private final ArrayList<Enemy> mEnemis = new ArrayList<Enemy>();

    public GameView(final Context context) {
        super(context);
        mIsRunning = false;
        mPlayer = new Player(context);
        mPaint = new Paint();
        mHolder = getHolder();

        double targetHeight = (double)STAGE_HEIGHT;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        double scaleRatio = targetHeight / dm.heightPixels;
        int newWidth = (int) (dm.widthPixels * scaleRatio);
        STAGE_WIDTH = (int)(dm.widthPixels * scaleRatio);
        mHolder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);

        Random generator = new Random();
        for(int i = 0; i < STAR_COUNT; i++)
        {
            int x = generator.nextInt(STAGE_WIDTH);
            int y = generator.nextInt(STAGE_HEIGHT);
            mStars.add(new GameObject(x, y));
        }
        for(int i = 0; i < ENEMY_COUNT; i++)
        {
            int x = STAGE_WIDTH + generator.nextInt(100);
            int y = generator.nextInt(STAGE_HEIGHT);
            mEnemis.add(new Enemy(context, x, y));
        }
    }

    @Override
    public void run(){
        while(mIsRunning){
            //input
            update();
            render();
            limitFps();
        }
    }

    private void limitFps(){
        try {
            mGameThread.sleep(DELAY);
        }catch (InterruptedException ex){
            Log.d(TAG, "Failed to sleep somehow");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                mPlayer.stopBoost();
                break;
            case MotionEvent.ACTION_DOWN:
                mPlayer.startBoost();
                break;
        }

        return true;
    }

    public void pause() {
        mIsRunning = false;
        try{
            mGameThread.join();
        }catch (InterruptedException ex){
            Log.d(TAG, Log.getStackTraceString(ex.getCause()));
        }
    }

    public void resume(){
        mIsRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    private void update(){
        mPlayer.Update();
        worldWrap(mPlayer);

        for(GameObject star : mStars){
            star.setSpeed(-mPlayer.getSpeed());
            star.update();
            worldWrap(star);
        }

        for(Enemy enemy : mEnemis){
            enemy.setSpeed(-mPlayer.getSpeed());
            enemy.update();
            worldWrap(enemy);
            if(isColliding(mPlayer, enemy)){
                Log.d(TAG, "COLL!!");
            }
        }


    }

    private boolean isColliding(Player a, Enemy b){
        return a.getBoundingBox().intersect(b.getBoundingBox());
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

        //Draw stars
        mPaint.setColor(Color.YELLOW);
        for(GameObject star : mStars){
            mCanvas.drawPoint(star.getX(), star.getY(), mPaint);
        }

        for(Enemy enemy : mEnemis){
            mCanvas.drawBitmap(enemy.getBitmap(), enemy.getX(), enemy.getY(), mPaint);
        }

        //Draw bitmap to screen
        mCanvas.drawBitmap(mPlayer.getBitmap(), mPlayer.getX(), mPlayer.getY(), mPaint);
        mHolder.unlockCanvasAndPost(mCanvas);

    }


    //core gameloop
        //input
        //update
        //render
    //pause and resume
    //constructor

    private void worldWrap(GameObject go){
        final int maxX = STAGE_WIDTH - go.getWidth();
        final int maxY = STAGE_HEIGHT - go.getHeight();
        final int minX = -go.getWidth();
        final int minY = -go.getHeight();


        if(go.getX() < minX){
            go.setX(maxX);
        }else if(go.getX() > maxX){
            go.setX(minX);
        }

        if(go.getY() < minY){
            go.setY(maxY);
        }else if(go.getY() > maxY){
            go.setY(minY);
        }
    }

    private static String getDensityName(DisplayMetrics dm) {
        float density = dm.density;
        if (density >= 4.0) {
            return "xxxhigh density";
        }
        if (density >= 3.0) {
            return "xxhigh density";
        }
        if (density >= 2.0) {
            return "xhigh density";
        }
        if (density >= 1.5) {
            return "high density";
        }
        if (density >= 1.0) {
            return "medium density";
        }
        return "low density";
    }
}
