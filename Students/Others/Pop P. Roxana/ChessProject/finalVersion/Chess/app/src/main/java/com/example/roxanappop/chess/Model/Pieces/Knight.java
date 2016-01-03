package com.example.roxanappop.chess.Model.Pieces;

import android.util.Log;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.OffsetList;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class Knight extends Piece {

    public Knight(int image, Position position, int colour){
        super(image,position,colour);
    }

    protected  OffsetList getPieceOffsetList(Board board){
        int [][] offsetmatrix = {{-2,-1},{-2,1},{-1,-2},{-1,-2},{-1,2},{1,2},{1,-2},{2,1},{2,-1}};

        OffsetList offsetList = new OffsetList(offsetmatrix);
        return offsetList;
    }

    @Override
    protected int[][] getStepsForOffsetList() {
        return null;
    }

    @Override
    public boolean canAttack(Board board,Position positionToBeAttacked){

        int widthDistance = super.getPosition().getWidthDistance(positionToBeAttacked);
        int heightDistance = super.getPosition().getHeightDistance(positionToBeAttacked);
        if((widthDistance==2&&heightDistance==1)||(widthDistance==1&&heightDistance==2)){
            return true;
        }
        return false;
    }
}
