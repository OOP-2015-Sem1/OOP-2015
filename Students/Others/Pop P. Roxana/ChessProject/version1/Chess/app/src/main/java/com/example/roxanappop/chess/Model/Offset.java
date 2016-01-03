package com.example.roxanappop.chess.Model;

/**
 * Created by roxanappop on 12/29/2015.
 */
public class Offset {

    int xOffset;
    int yOffset;

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

