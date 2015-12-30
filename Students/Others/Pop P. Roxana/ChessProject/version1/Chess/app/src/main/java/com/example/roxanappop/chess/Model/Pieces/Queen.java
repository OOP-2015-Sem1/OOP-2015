package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.OffsetList;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class Queen extends Piece {

    public Queen(int image, Position position, int colour){
        super(image,position,colour);
    }
    @Override
    protected int[][] getStepsForOffsetList() {
        int steps[][] = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};
        return steps;
    }
}