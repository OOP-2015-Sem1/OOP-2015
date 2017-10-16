package com.vladbonta.myapplication.model;

import com.vladbonta.myapplication.R;

import java.util.ArrayList;

/**
 * @author VladBonta on 27/12/15.
 */
public class Bishop extends ChessPiece {
    public Bishop(boolean isWhite, int x, int y){
        super(isWhite, x, y);
        this.setIsEmpty(false);
    }

    @Override
      public int getWhiteDrawableImageId() {
        return R.drawable.wbishop;
    }

    @Override
    public int getBlackDrawableImageId() {
        return R.drawable.bbishop;
    }

    @Override
    public boolean isMovePossible(int fromX, int fromY, int toX, int toY) {
        return super.isMovePossible(fromX, fromY, toX, toY) && Math.abs(toX - fromX) == Math.abs(toY - fromY);

    }

    @Override
    public ArrayList<Integer> getPossibleMovesList(int fromX, int fromY, int toX, int toY) {
        ArrayList<Integer> possiblePositions = new ArrayList<>();
        if (this.isMovePossible(fromX, fromY, toX, toY)){
            Integer xPos = fromX;
            Integer yPos = fromY;
            if (toX < fromX && toY > fromY){
                while (xPos != toX || yPos != toY){
                    xPos -= 1;
                    yPos += 1;
                    if (xPos != toX || yPos != toY) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            } else  if (toX > fromX && toY > fromY){
                while (xPos != toX || yPos != toY){
                    xPos += 1;
                    yPos += 1;
                    if (xPos != toX || yPos != toY) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            } else  if (toX > fromX && toY < fromY){
                while (xPos != toX || yPos != toY){
                    xPos += 1;
                    yPos -= 1;
                    if (xPos != toX || yPos != toY) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            } else  if (toX < fromX && toY < fromY) {
                while (xPos != toX || yPos != toY) {
                    xPos -= 1;
                    yPos -= 1;
                    if (xPos != toX || yPos != toY) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            }
        }
        return possiblePositions;
    }
}
