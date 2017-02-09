package com.example.jesper.niceshot;

import android.content.Context;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Jesper on 2017-02-07.
 */

public class GameObjectSpawner {
    private Context mContext;
    private int mStageHeight;
    private int mStageWidth;

    private final static int STAR_COUNT = 40;
    private final static int ENEMY_COUNT = 3;

    public GameObjectSpawner(Context context, int stageHeight, int stageWidth){
        mContext = context;
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
            double targetHeight = 10 + r.nextInt(20);
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
