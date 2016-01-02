package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Offset;
import com.example.roxanappop.chess.Model.OffsetList;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/8/2015.
 */
public abstract class Piece {

    //Atributes
    int image;
    Position position;
    protected int colour;
    boolean wasMoved;

    public void setWasMoved(boolean wasMoved) {
        this.wasMoved = wasMoved;
    }

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

    public int getColour(){
        return colour;
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
    public ArrayList<Position> getPossiblePositions(Board board) {

        ArrayList<Position> possiblePositions = new ArrayList<Position>();

        OffsetList offsetList = getPieceOffsetList(board);

        for(int i=0;i<offsetList.size();i++){
            Position possiblePosition = position.addOffset(offsetList.getOffset(i));
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

    protected  OffsetList getPieceOffsetList(Board board){

        int steps[][] = getStepsForOffsetList();
        OffsetList offsetList = new OffsetList();

        for(int i=0;i<steps.length;i++){
            boolean isPathObstructed = false;
            Offset offsetToBeAdded=new Offset(steps[i][0],steps[i][1]);
            Position newPos = position.addOffset(offsetToBeAdded);
            while(!isPathObstructed&&!board.exceedsBoard(newPos)){
                if(board.isPositionOccupied(newPos)){
                    isPathObstructed=true;
                }
                offsetList.add(offsetToBeAdded);
                offsetToBeAdded=offsetToBeAdded.modifyBy(steps[i][0],steps[i][1]);
                newPos = position.addOffset(offsetToBeAdded);

            }
        }
        return offsetList;
    };

    protected abstract int[][] getStepsForOffsetList();

}
