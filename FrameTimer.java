package com.example.jesper.niceshot;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Jesper on 2017-02-09.
 */

public class FrameTimer {
    private final int FRAMES_PER_SECOND;
    private final Thread mGameThread;
    private final int mGameLoopTime;
    private long mTimeStamp;

    public FrameTimer(Context context, Thread gameThread){
        FRAMES_PER_SECOND = context.getResources().getInteger(R.integer.GAME_FRAMES_PER_SECOND);
        mGameLoopTime = 1000 / FRAMES_PER_SECOND;
        mGameThread = gameThread;
    }

    public void startFrame(){
        mTimeStamp = System.currentTimeMillis();
    }

    public void endFrame(){
        long executionTime = System.currentTimeMillis() - mTimeStamp;
        if(executionTime > mGameLoopTime){
            return;
        }

        try{
            mGameThread.sleep(mGameLoopTime - executionTime);
        }catch (InterruptedException ex){

        }

    }
}
