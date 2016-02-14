package com.vladbonta.myapplication.model;

import com.vladbonta.myapplication.R;

import java.util.ArrayList;

/**
 * @author VladBonta on 27/12/15.
 */
public class Queen extends ChessPiece {
    public Queen(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.setIsEmpty(false);
    }

    @Override
    public int getWhiteDrawableImageId() {
        return R.drawable.wqueen;
    }

    @Override
    public int getBlackDrawableImageId() {
        return R.drawable.bqueen;
    }

    @Override
    public boolean isMovePossible(int fromX, int fromY, int toX, int toY) {
        if (!super.isMovePossible(fromX, fromY, toX, toY))
            return false;
        //diagonal
        return Math.abs(toX - fromX) == Math.abs(toY - fromY) || toX == fromX || toY == fromY;

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
            } else if (toX < fromX && toY == fromY){
                while (xPos != toX){
                    xPos -= 1;
                    if (xPos != toX) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            } else  if (toX == fromX && toY > fromY){
                while (yPos != toY){
                    yPos += 1;
                    if (yPos != toY) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            } else  if (toX > fromX && toY == fromY){
                while (xPos != toX){
                    xPos += 1;
                    if (xPos != toX) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            } else  if (toX == fromX && toY < fromY) {
                while (yPos != toY) {
                    yPos -= 1;
                    if (yPos != toY) {
                        possiblePositions.add((xPos - 1) * 8 + (yPos - 1));
                    }
                }
            }
        }
        return possiblePositions;
    }
}
