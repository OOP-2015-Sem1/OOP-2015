package com.ion.flyingbee.utils;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.ion.flyingbee.models.Entity;
import com.ion.flyingbee.models.EntityId;


import java.util.ArrayList;

/**
 * Created by Ion on 26.12.2015.
 */
public class CollisionAnalyzer {

//    /**
//     * Gives the length of the line described by the points a and b.
//     * @param a
//     * @param b
//     * @return
//     */
//    public static float distanceBetweenTwoPoints(Vector2 a, Vector2 b) {
//        return (float) Math.sqrt((a.x - b.x)*(a.x - b.x) + (a.y - b.y)*(a.y - b.y));
//    }
//
//    /**
//     * Gives area of the triangle described by the points a1, a2, a3.
//     * @param a1
//     * @param a2
//     * @param a3
//     * @return
//     */
//    public static float computeArea(Vector2 a1, Vector2 a2, Vector2 a3) {
//        float a1a2 = distanceBetweenTwoPoints(a1, a2);
//        float a1a3 = distanceBetweenTwoPoints(a1, a3);
//        float a2a3 = distanceBetweenTwoPoints(a2, a3);
//        float p = (a1a2 + a1a3 + a2a3)/2f; // the half-perimeter
//        float area = (float) Math.sqrt(p*(p - a1a2)*(p - a1a3)*(p-a2a3));
//        return area;
//    }
//
//    /**
//     * Check if the point a is inside or on the edge of a triangle described by b1, b2, b3.
//     * @param a
//     * @param b1
//     * @param b2
//     * @param b3
//     * @return
//     */
//    public static boolean isInsideOfTriangle(Vector2 a, Vector2 b1, Vector2 b2, Vector2 b3) {
//        float area1 = computeArea(a, b1, b2);
//        float area2 = computeArea(a, b1, b3);
//        float area3 = computeArea(a, b2, b3);
//        float areaBigTriangle = computeArea(b1, b2, b3);
//        if (area1 + area2 + area3 >= areaBigTriangle - MathUtils.FLOAT_ROUNDING_ERROR &&
//                area1 + area2 + area3 <= areaBigTriangle + MathUtils.FLOAT_ROUNDING_ERROR) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Check if the two entities collide.
//     * @param a first entity
//     * @param b second entity
//     * @return
//     */
//    public static boolean collide(Entity a, Entity b) {
//        int ax = a.getCoordinateX();
//        int ay = a.getCoordinateY();
//        int aw = a.getWidth();
//        int ah = a.getHeight();
//        int bx = b.getCoordinateX();
//        int by = b.getCoordinateY();
//        int bw = b.getWidth();
//        int bh = b.getHeight();
//        Vector2 a1 = new Vector2(ax, ay);
//        Vector2 a2 = new Vector2(ax + aw, ay);
//        Vector2 a3 = new Vector2(ax + aw, ay + ah);
//        Vector2 a4 = new Vector2(ax, ay + ah);
//        Vector2 b1 = new Vector2(bx, by);
//        Vector2 b2 = new Vector2(bx + bw, by);
//        Vector2 b3 = new Vector2(bx + bw, by + bh);
//        Vector2 b4 = new Vector2(bx, by + bh);
//        if (isInsideOfTriangle(a3, b1, b2, b4) ||
//                isInsideOfTriangle(a3, b2, b3, b4) ||
//                isInsideOfTriangle(a2, b1, b3, b4) ||
//                isInsideOfTriangle(a2, b1, b2, b3) ||
//                isInsideOfTriangle(a1, b2, b3, b4) ||
//                isInsideOfTriangle(a1, b1, b2, b3) ||
//                isInsideOfTriangle(a4, b1, b2, b3) ||
//                isInsideOfTriangle(a4, b1, b3, b4)) {
//            return true;
//        }
//        return false;
//    }

    private static Circle buildCircle(Entity e) {
        float x = e.getCoordinateX() + e.getWidth()/2;
        float y = e.getCoordinateY() + e.getHeight()/2;
        float radius = 0f;
        switch (e.getId()) {
            case EntityId.BEE_ID:
                radius = (e.getHeight() < e.getWidth()) ? e.getHeight()/3 : e.getWidth()/3;
                break;
            case EntityId.FLOWER_ID:
                radius = (e.getHeight() < e.getWidth()) ? e.getHeight()/2 : e.getWidth()/2;
                break;
            case EntityId.WASP_ID:
                radius = (e.getHeight() < e.getWidth()) ? e.getHeight()/3 : e.getWidth()/3;
                break;
            default:
                break;
        }

        return new Circle(x, y, radius);
    }

    private static boolean collide(Circle a, Circle b) {
        return Intersector.overlaps(a, b);
    }

    public static boolean collide(Entity a, Entity b) {
        int ax = a.getCoordinateX();
        int ay = a.getCoordinateY();
        int aw = a.getWidth();
        int ah = a.getHeight();
        int bx = b.getCoordinateX();
        int by = b.getCoordinateY();
        int bw = b.getWidth();
        int bh = b.getHeight();
        boolean canMeet = false;
        if (bx >= ax && bx <= ax + aw) {
            if (by >= ay && by <= ay + ah) {
                canMeet = true;
            } else if (by + bh >= ay && by + bh <= ay + ah) {
                canMeet = true;
            }
        } else if (ax >= bx && ax <= bx + bh) {
            if (ay >= by && ay <= by + bw) {
                canMeet = true;
            } else if (ay + ah >= by && ay + ah <= by + bh) {
                canMeet = true;
            }
        }
        if (canMeet) {
            return collide(buildCircle(a), buildCircle(b));
        }
        return false;
    }

    /**
     * Check for a collision in the array of entities.
     * @return index of the entity in the entity array, -1 otherwise
     */
    public static int collide(Entity a, ArrayList<Entity> entities) {
        for (Entity e : entities) {
            if (!e.equals(a)) {
                if (collide(e, a)) {
                    return entities.indexOf(e);
                }
            }
        }
        return -1;
    }
}
