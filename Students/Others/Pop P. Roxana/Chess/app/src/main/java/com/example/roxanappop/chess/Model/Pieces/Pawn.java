package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class Pawn extends Piece {

    public Pawn(int image, Position position, int colour){
        super(image,position,colour);
    }
    @Override
    public ArrayList<Position> getPossiblePositions(Board board) {
        return null;
    }
}
