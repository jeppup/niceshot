package com.example.jesper.niceshot;

import android.content.Context;
import android.content.res.Resources;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jesper on 2017-02-07.
 */

public class GameObjectSpawner {
    private Context mContext;
    private int mStageHeight;
    private int mStageWidth;

    private final int STAR_COUNT;
    private final int ENEMY_COUNT;
    private final int STAR_MIN_HEIGHT;
    private final int STAR_HEIGHT_RANGE;

    public GameObjectSpawner(Context context, int stageHeight, int stageWidth){
        mContext = context;
        Resources res = context.getResources();
        STAR_COUNT = res.getInteger(R.integer.SPAWNER_STAR_COUNT);
        ENEMY_COUNT = res.getInteger(R.integer.SPAWNER_ENEMY_COUNT);
        STAR_MIN_HEIGHT = res.getInteger(R.integer.STAR_MIN_HEIGHT);
        STAR_HEIGHT_RANGE = res.getInteger(R.integer.STAR_HEIGHT_VARIATION_RANGE);

        mStageHeight = stageHeight;
        mStageWidth = stageWidth;
    }

    public ArrayList<DrawableGameObject> spawnWorld()
    {
        ArrayList<DrawableGameObject> worldObjects = new ArrayList<DrawableGameObject>();
        worldObjects.addAll(spawnStars());
        worldObjects.addAll(spawnEnemies());

        return worldObjects;
    }

    public ArrayList<Star> spawnStars(){
        ArrayList<Star> stars = new ArrayList<Star>();
        Random r = new Random();

        for(int i = 0; i < STAR_COUNT; i++)
        {
            int x = r.nextInt(mStageWidth);
            int y = r.nextInt(mStageHeight);
            double targetHeight = STAR_MIN_HEIGHT + r.nextInt(STAR_HEIGHT_RANGE);
            stars.add(new Star(mContext, targetHeight, x, y));
        }

        return stars;
    }

    public ArrayList<Enemy> spawnEnemies(){
        ArrayList<Enemy> enemies = new ArrayList<Enemy>();

        for(int i = 0; i < ENEMY_COUNT; i++)
        {
            enemies.add(new Enemy(mContext));
        }

        return enemies;
    }
}
