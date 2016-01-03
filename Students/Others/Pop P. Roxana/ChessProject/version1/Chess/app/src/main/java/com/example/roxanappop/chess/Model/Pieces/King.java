package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.OffsetList;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class King extends Piece {

    public King(int image, Position position, int colour) {
        super(image, position, colour);
    }

    protected OffsetList getPieceOffsetList(Board board){
        int [][] offsetmatrix = {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{-1,1},{1,-1},{1,1}};

        OffsetList offsetList = new OffsetList(offsetmatrix);
        return offsetList;
    }

	@Override
	protected int[][] getStepsForOffsetList() {
		return null;
	}
}
