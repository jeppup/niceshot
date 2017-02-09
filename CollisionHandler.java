package com.example.jesper.niceshot;

import java.util.HashMap;

/**
 * Created by Jesper on 2017-02-08.
 */

public class CollisionHandler {
    private final static HashMap<Integer, ICollidable> collidableObjects = new HashMap<Integer, ICollidable>();

    public static void addObject(ICollidable collisionObject){
        collidableObjects.put(collisionObject.hashCode() ,collisionObject);
    }

    public static void removeObject(ICollidable collisionObject){
        collidableObjects.remove(collisionObject.hashCode());
    }

    public static void checkCollision(ICollidable objectToCheck){
        for(ICollidable obj : collidableObjects.values()){
            if(objectToCheck.checkCollisson(obj)){
                objectToCheck.collisionHappend();
                obj.collisionHappend();
            }
        }
    }

    public static void clearAll(){
        collidableObjects.clear();
    }
}
