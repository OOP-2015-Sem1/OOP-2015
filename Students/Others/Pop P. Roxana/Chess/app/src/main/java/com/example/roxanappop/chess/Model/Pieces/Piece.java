package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/8/2015.
 */
public abstract class Piece {

    //Atributes
    int image;
    Position position;
    int colour;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setColour(int colour){
        this.colour = colour;
    }

    public Piece(int image, Position position, int colour){

        setImage(image);
        setPosition(position);
        setColour(colour);
    }

    public boolean isMoveable(Board board,int turn){
        if(turn!=colour){
            return false;
        }
        ArrayList<Position> possiblePossitions = getPossiblePositions(board);
        if(possiblePossitions.size()==0){
            return false;
        }
        return true;
    }
    public abstract ArrayList<Position> getPossiblePositions(Board board);

}
