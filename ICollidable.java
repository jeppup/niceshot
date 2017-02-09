package com.example.jesper.niceshot;

import android.graphics.Rect;

/**
 * Created by Jesper on 2017-02-08.
 */

public interface ICollidable {
    Boolean checkCollision(ICollidable objectToCheck);
    Rect getCollisionBoundaries();
    void onCollision();

}
