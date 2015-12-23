package com.example.roxanappop.chess.Model;

/**
 * Created by roxanappop on 12/8/2015.
 */
public class Position {

    int x;
    int y;

    public Position(int x,int y){

        this.x = x;
        this.y = y;

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

}
