package com.example.roxanappop.chess.Model;

/**
 * Created by roxanappop on 12/8/2015.
 */
public class Position {

    private int x;
    private int y;

    public Position(int x,int y){

        setX(x);
        setY(y);

    }

    public void setX(int x){

        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean equals(Position position){
        if(this.getX()==position.getX()&&this.getY()==position.getY()){
            return true;
        }else{
            return false;
        }
    }

    public Position addOffset(Offset offset){
        int newX = x+offset.getxOffset();
        int newY = y+offset.getyOffset();
        return new Position(newX,newY);
    }

    public int getWidthDistance(Position position){
        return Math.abs(this.getY()-position.getY());
    }
    public int getHeightDistance(Position position){
        return Math.abs(this.getX()-position.getX());
    }
    public int getSignedWidthDistance(Position position){
        return this.getY()-position.getY();
    }
    public int getSignedHeightDistance(Position position){
        return this.getX()-position.getX();
    }

}
