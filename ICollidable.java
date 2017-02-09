package com.example.jesper.niceshot;

import android.graphics.Rect;

/**
 * Created by Jesper on 2017-02-08.
 */

public interface ICollidable {
    Boolean checkCollisson(ICollidable objectToCheck);
    Rect getCollisionBoundaries();
    void collisionHappend();

}
