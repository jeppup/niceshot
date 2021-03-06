package com.example.jesper.niceshot;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Created by Jesper on 2017-02-05.
 */

public class Jukebox {
    private SoundPool mSoundPool;
    private final int MAX_STREAMS = 5;
    public static int CRASH = 0;
    public static int START = 0;

    public Jukebox(Context context){
        mSoundPool = createSoundPool();
        loadSounds(context);
    }

    public void play(final int soundId){
        float leftVolume = 1.0f;
        float rightVolume = 1.0f;
        int priority = 1;
        int loop = 0;
        float rate = 1.0f;

        if(soundId > 0 ){
            mSoundPool.play(soundId, leftVolume, rightVolume, priority, loop, rate);
        }
    }

    public void destroy(){
        mSoundPool.release();
        mSoundPool = null;
    }

    private void loadSounds(Context context){
        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("explosion.ogg");
            CRASH = mSoundPool.load(descriptor, 0);
            descriptor = assetManager.openFd("start.ogg");
            START = mSoundPool.load(descriptor, 0);
        }catch (IOException ex){
            Log.e(GameView.TAG, ex.getMessage());
        }
    }

    private SoundPool createSoundPool(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            return createNewSoundPool();
        }
        return createOldSoundPool();
    }

    @SuppressWarnings("deprecation")
    private SoundPool createOldSoundPool(){
        return new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool createNewSoundPool(){
        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        return new SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(MAX_STREAMS)
                .build();
    }
}
