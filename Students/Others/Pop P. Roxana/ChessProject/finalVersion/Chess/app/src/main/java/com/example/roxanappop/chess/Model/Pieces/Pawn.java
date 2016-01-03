package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Game;
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
    public ArrayList<Position> getPossiblePositions(Board board,Game game) {
        //i had to override the super method as the pawn has different behavior when
        //simply moving or attacking

        //simply moving the pawn
        ArrayList<Position> possiblePositions = new ArrayList<Position>();
        int sign = (super.getColour() == Colour.WHITE) ? -1 : 1;
        Offset offset1 = new Offset(sign, 0);
        Position position1 = super.getPosition().addOffset(offset1);
        if(!board.exceedsBoard(position1)){
            boolean isPathObstructed = false;
            if (board.isPositionOccupied(position1)) {
                isPathObstructed = true;
            } else {
                if(!super.keepsKingInChess(position1,board,game)) {
                    possiblePositions.add(position1);
                }
            }
            if (!super.getWasMoved() && !isPathObstructed) {
                Offset offset2 = new Offset(2 * sign, 0);
                Position position2 = super.getPosition().addOffset(offset2);
                if (!board.isPositionOccupied(position2)) {
                    if(!super.keepsKingInChess(position2,board,game)) {
                        possiblePositions.add(position2);
                    }
                }
            }
        }
        //attacking other pieces
        int[][] offsetMatrixForWhite = {{-1, -1}, {-1, 1}};
        OffsetList offsetListForWhite = new OffsetList(offsetMatrixForWhite);
        int[][] offsetMatrixForBlack = {{1, -1}, {1, 1}};
        OffsetList offsetListForBlack = new OffsetList(offsetMatrixForBlack);
        OffsetList offsetList = (super.getColour() == Colour.WHITE) ? offsetListForWhite : offsetListForBlack;
        for (int i = 0; i < offsetList.size(); i++) {
            Position possiblePosition = super.getPosition().addOffset(offsetList.getOffset(i));
            if (!board.exceedsBoard(possiblePosition)&&board.isPositionOccupied(possiblePosition) && super.getColour() != board.getPieceAtPosition(possiblePosition).getColour()) {
                if(!super.keepsKingInChess(possiblePosition,board,game)) {
                    possiblePositions.add(possiblePosition);
                }
            }
        }
        return possiblePositions;
    }
    @Override
    public boolean canAttack(Board board,Position positionToBeAttacked){

        int widthDistance = super.getPosition().getWidthDistance(positionToBeAttacked);
        int heightDistance = super.getPosition().getHeightDistance(positionToBeAttacked);
        if(widthDistance!=1||heightDistance!=1){
            return false;
        }
        if(super.getPosition().getX()>positionToBeAttacked.getX()){
            return (super.getColour()==Colour.WHITE)?true:false;
        }else{
            return (super.getColour()==Colour.BLACK)?true:false;
        }
    }

    @Override
    protected int[][] getStepsForOffsetList() {
        return null;
    }
}
