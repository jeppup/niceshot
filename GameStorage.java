package com.example.jesper.niceshot;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

/**
 * Created by Jesper on 2017-02-09.
 */

public class GameStorage {
    private SharedPreferences mPrefs;
    private SharedPreferences.Editor mEditor;
    private final String HIGHSCORE_KEY;

    public GameStorage(Context context)
    {
        Resources res = context.getResources();
        HIGHSCORE_KEY = res.getString(R.string.STORAGE_HIGHSCORE);
        String preferencesKey = res.getString(R.string.STORAGE_PREFERENCES);
        mPrefs = context.getSharedPreferences(preferencesKey, Context.MODE_PRIVATE);
        mEditor = mPrefs.edit();
    }
    public int getHighscore()
    {
        return mPrefs.getInt(HIGHSCORE_KEY, 0);
    }

    public void tryPutHighscore(int score){
        int currentHighscore = mPrefs.getInt(HIGHSCORE_KEY, 0);

        if(score > currentHighscore){
            mEditor.putInt(HIGHSCORE_KEY, score);
            mEditor.apply();
        }

    }
}
