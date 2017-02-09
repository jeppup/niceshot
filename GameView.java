package com.example.jesper.niceshot;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

/**
 * Created by Jesper on 2017-01-23.
 * Game art source: http://opengameart.org/content/space-ships-side-scroller
 */

public class GameView extends SurfaceView implements Runnable {
    public static final String TAG = "NiceShot";

    private final Context mContext;
    private Thread  mGameThread;
    volatile boolean mIsRunning;
    volatile boolean mShouldRestart = true;
    private boolean mGameOver;

    HashMap<Integer, DrawableGameObject> drawableObjects = new HashMap<Integer, DrawableGameObject>();
    private GameObjectSpawner spawner;
    private Jukebox mJukeBox;
    private final GameStorage mGameStorage;
    private FrameTimer framRateHandler;
    private HeadsUpDisplay mHUD;

    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Paint mPaint;

    private Player mPlayer;
    private int mDistanceTraveled = 0;

    private final int SHIELD_LEVEL;
    public static final int STAGE_HEIGHT = 720;
    public static final int STAGE_WIDTH = 1280;

    public GameView(final Context context) {
        super(context);
        mContext = context;
        mIsRunning = false;
        mGameStorage = new GameStorage(context);
        mJukeBox = new Jukebox(context);
        mPlayer = new Player(context);
        mPaint = new Paint();
        mHolder = getHolder();
        mHUD = new HeadsUpDisplay(context, STAGE_HEIGHT, STAGE_WIDTH);
        SHIELD_LEVEL = context.getResources().getInteger(R.integer.PLAYER_SHIELD);
        mHolder.setFixedSize(STAGE_WIDTH, STAGE_HEIGHT);
        spawner = new GameObjectSpawner(context, STAGE_WIDTH, STAGE_WIDTH);
    }

    private void startGame(){
        drawableObjects.clear();
        CollisionHandler.clearAll();

        for(Enemy enemy : spawner.spawnEnemies()){
            drawableObjects.put(enemy.hashCode(), enemy);
            CollisionHandler.addObject(enemy);
        }

        for (Star star : spawner.spawnStars()){
            drawableObjects.put(star.hashCode(), star);
        }

        mShouldRestart = false;
        mGameOver = false;
        mDistanceTraveled = 0;

        mPlayer.setShieldLevel(SHIELD_LEVEL);
        mJukeBox.play(mJukeBox.START);
    }

    @Override
    public void run(){
        while(mIsRunning){
            if(mShouldRestart)
            {
                startGame();
            }
            framRateHandler.startFrame();
            update();
            render();
            checkGameOver();
            framRateHandler.endFrame();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                mPlayer.stopBoost();
                if(mGameOver){
                    mShouldRestart = true;
                }
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
        framRateHandler = new FrameTimer(mContext, mGameThread);
        mGameThread.start();
    }

    private void checkGameOver(){
        if(mGameOver){
            return;
        }

        if(mPlayer.getShieldLevel() <= 0){
            mGameOver = true;
            mGameStorage.tryPutHighscore(mDistanceTraveled);
            mJukeBox.play(Jukebox.CRASH);
        }
    }
    private void update(){
        mPlayer.update();
        worldWrap(mPlayer);

        for (DrawableGameObject gameObject : drawableObjects.values()){
            gameObject.setSpeed(-mPlayer.getSpeed());
            gameObject.update();
            worldWrap(gameObject);
        }

        CollisionHandler.checkCollision(mPlayer);

        if(!mGameOver){
            mDistanceTraveled += mPlayer.getSpeed();
        }
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
        mPlayer.render(mCanvas, mPaint);

        for(DrawableGameObject go : drawableObjects.values()){
            go.render(mCanvas, mPaint);
        }

        if(!mGameOver){
            mHUD.drawInGame(mCanvas, mPaint, mPlayer);
        }else {
            mHUD.drawGameOver(mCanvas, mPaint);
        }

        mHolder.unlockCanvasAndPost(mCanvas);
    }

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

    public void destroy(){
        mJukeBox.destroy();
        mJukeBox = null;

        mGameThread = null;
        mPaint = null;
        mCanvas = null;
        mHolder = null;
        mPlayer = null;
    }
}
