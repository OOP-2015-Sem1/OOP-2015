package com.example.roxanappop.chess.Model;

/**
 * Created by roxanappop on 12/29/2015.
 */
public class Offset {

    private int xOffset;
    private int yOffset;

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }

    public Offset(int xOffset,int yOffset){
        this.xOffset=xOffset;
        this.yOffset=yOffset;
    }

    public Offset modifyBy(int x,int y){
        int newXOffset = xOffset+x;
        int newYOffset = yOffset+y;
        return new Offset(newXOffset,newYOffset);
    }
}

