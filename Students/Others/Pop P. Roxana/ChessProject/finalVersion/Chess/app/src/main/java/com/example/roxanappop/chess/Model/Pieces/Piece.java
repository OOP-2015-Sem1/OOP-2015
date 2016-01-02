package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Game;
import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Move;
import com.example.roxanappop.chess.Model.Offset;
import com.example.roxanappop.chess.Model.OffsetList;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/8/2015.
 */
public abstract class Piece {

    //Atributes
    private int image;
    private Position position;
    private int colour;
    private boolean wasMoved;

    public boolean getWasMoved(){
        return wasMoved;
    }

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


    public int getColour(){
        return colour;
    }

    public void setColour(int colour){
        this.colour = colour;
    }


    public Piece(int image, Position position, int colour){

        setImage(image);
        setPosition(position);
        setColour(colour);
    }

    public boolean isMoveable(Board board,int turn,Game game){
        if(turn!=colour){
            return false;
        }
        ArrayList<Position> possiblePossitions = getPossiblePositions(board,game);
        if(possiblePossitions.size()==0){
            return false;
        }
        return true;
    }
    public ArrayList<Position> getPossiblePositions(Board board, Game game) {

        ArrayList<Position> possiblePositions = new ArrayList<Position>();

        OffsetList offsetList = getPieceOffsetList(board);

        for(int i=0;i<offsetList.size();i++){
            Position possiblePosition = position.addOffset(offsetList.getOffset(i));
            if(!board.exceedsBoard(possiblePosition)){
                if(board.isPositionOccupied(possiblePosition)){
                    Piece attackedPiece = board.getPieceAtPosition(possiblePosition);
                    if(this.colour!=attackedPiece.colour){
                        if(!keepsKingInChess(possiblePosition,board,game)) {
                            possiblePositions.add(possiblePosition);
                        }
                    }
                }else{
                    if(!keepsKingInChess(possiblePosition,board,game)) {
                        possiblePositions.add(possiblePosition);
                    }

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

    protected boolean keepsKingInChess(Position possiblePosition,Board board,Game game){

        Move testMove = new Move();
        testMove.setFrom(position);
        testMove.setTo(possiblePosition);
        testMove.setCarry(this);
        testMove.setCapturedPiece(board.getPieceAtPosition(possiblePosition));
        testMove.setIsTestMove(true);
        DeadPieces testDeadPieces = new DeadPieces();
        game.makeMove(testMove, board, testDeadPieces);
        boolean keepsKingInChess =  (game.isInCheck(board,colour))?true:false;
        game.revertMove(testMove,board, testDeadPieces);
        return keepsKingInChess;
    }

    public boolean canAttack(Board board, Position positionToBeAttacked) {
        //for the Game inCheck method
        return board.isPathFree(position,positionToBeAttacked);
    }
}
