package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class Knight extends Piece {

    public Knight(int image, Position position, int colour){
        super(image,position,colour);
    }
    @Override
    public ArrayList<Position> getPossiblePositions(Board board) {
         //should I have a method like this?
        //or  would it be better to just have a cand move method that takes 2 positions as parameters(src,dest)?

        ArrayList<Position> possiblePositions = new ArrayList<Position>();

        int [][] offset = {{-2,-1},{-2,1},{-1,-2},{-1,-2},{-1,2},{1,2},{1,-2},{2,1},{2,-1}};

        for(int i=0;i<offset.length;i++){
            Position possiblePosition = new Position(position.getX()+offset[i][0],position.getY()+offset[i][1]);
            if(!board.exceedsBoard(possiblePosition)){
                if(board.isPositionOccupied(possiblePosition)){
                    Piece attackedPiece = board.getCellAtPosition(possiblePosition).getPiece();
                    if(this.colour!=attackedPiece.colour){
                        possiblePositions.add(possiblePosition);
                    }
                }else{
                    possiblePositions.add(possiblePosition);
                }
            }
        }

        return possiblePositions;
    }
}
