package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Colour;
import com.example.roxanappop.chess.Model.Offset;
import com.example.roxanappop.chess.Model.OffsetList;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class Pawn extends Piece {

    public Pawn(int image, Position position, int colour) {
        super(image, position, colour);
    }


    @Override
    public ArrayList<Position> getPossiblePositions(Board board) {
        //i had to override the super method as the pawn has different behaviour when
        //simply moving or attacking

        //simply moving the pawn
        ArrayList<Position> possiblePositions = new ArrayList<Position>();
        int sign = (colour == Colour.WHITE) ? -1 : 1;
        Offset offset1 = new Offset(sign, 0);
        Position position1 = position.addOffset(offset1);
        boolean isPathObstructed = false;
        if (board.isPositionOccupied(position1)) {
            isPathObstructed = true;
        } else {
            possiblePositions.add(position1);
        }
        if (!wasMoved && !isPathObstructed) {
            Offset offset2 = new Offset(2 * sign, 0);
            Position position2 = position.addOffset(offset2);
            if (!board.isPositionOccupied(position2)) {
                possiblePositions.add(position2);
            }
        }
        //atacking other pieces
        int[][] offsetMatrixForWhite = {{-1, -1}, {-1, 1}};
        OffsetList offsetListForWhite = new OffsetList(offsetMatrixForWhite);
        int[][] offsetMatrixForBlack = {{1, -1}, {1, 1}};
        OffsetList offsetListForBlack = new OffsetList(offsetMatrixForBlack);
        OffsetList offsetList = (colour == Colour.WHITE) ? offsetListForWhite : offsetListForBlack;
        for (int i = 0; i < offsetList.size(); i++) {
            Position possiblePossition = position.addOffset(offsetList.getOffset(i));
            if (board.isPositionOccupied(possiblePossition) && colour != board.getPieceAtPosition(possiblePossition).getColour()) {
                possiblePositions.add(possiblePossition);
            }
        }
        return possiblePositions;
    }

    @Override
    protected int[][] getStepsForOffsetList() {
        return null;
    }
}
