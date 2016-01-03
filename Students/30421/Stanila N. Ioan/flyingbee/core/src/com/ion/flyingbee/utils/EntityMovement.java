package com.ion.flyingbee.utils;

import com.ion.flyingbee.models.Entity;
import com.ion.flyingbee.models.EntityId;
import com.ion.flyingbee.models.Flower;
import com.ion.flyingbee.models.Wasp;

import java.util.ArrayList;

/**
 * Created by Ion on 27.12.2015.
 */
public class EntityMovement {

    public static final int UNIQUE_SPEED = 7;

    public static void moveLeft(Entity e, float speed) {
        e.move(e.getCoordinateX() - (int) speed, 0);
    }

    public static void moveRight(Entity e, float speed) {
        e.move(e.getCoordinateX() + (int) speed, 0);
    }

    public static void moveAll(ArrayList<Entity> entities, float speedRatio) {
        for (Entity e : entities) {
            switch(e.getId()) {
                case EntityId.BEE_ID:
                    // do nothing
                    break;
                case EntityId.WASP_ID:
                    moveLeft(e, UNIQUE_SPEED*speedRatio);
                    break;
                case EntityId.FLOWER_ID:
                    moveLeft(e, UNIQUE_SPEED*speedRatio);
                    break;
                default:
                    // do nothing
                    break;
            }
        }
    }
}
