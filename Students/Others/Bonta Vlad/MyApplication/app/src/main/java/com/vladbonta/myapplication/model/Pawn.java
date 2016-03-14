package com.vladbonta.myapplication.model;

import com.vladbonta.myapplication.R;

import java.util.ArrayList;

/**
 * @author VladBonta on 27/12/15.
 */
public class Pawn extends ChessPiece {
    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        this.setIsEmpty(false);
    }

    @Override
    public int getWhiteDrawableImageId() {
        return R.drawable.wpawn;
    }

    @Override
    public int getBlackDrawableImageId() {
        return R.drawable.bpawn;
    }

    @Override
    public boolean isMovePossible(int fromX, int fromY, int toX, int toY) {
        if (!super.isMovePossible(fromX, fromY, toX, toY))
            return false;
//        Log.d("handlePieceTouch", "Pawn movement: " + String.valueOf(fromX) + " " + fromY + " " + toX + " " + toY);


        if (fromY == toY && Math.abs(fromX - toX) == 1) {
            if (this.isWhite()){
                return fromX > toX;
            } else {
                return fromX < toX;
            }
        } else if(fromY == toY && Math.abs(fromX - toX) == 2){
            if (this.isWhite() && fromX == 7){
                return fromX > toX;
            } else if (!this.isWhite() && fromX == 2){
                return fromX < toX;
            }
        }
        if (Math.abs(fromY - toY) == 1 && Math.abs(fromX - toX) == 1){
            if (this.isWhite()){
                return fromX > toX;
            } else {
                return fromX < toX;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Integer> getPossibleMovesList(int fromX, int fromY, int toX, int toY) {
        ArrayList<Integer> possiblePositions = new ArrayList<>();
        if (this.isMovePossible(fromX, fromY, toX, toY)){
            if (fromY == toY && Math.abs(fromX - toX) == 2) {
                if (this.isWhite()) {
                    possiblePositions.add((fromX - 2) * 8 + (fromY - 1));
                } else {
                    possiblePositions.add(fromX * 8 + (fromY - 1));
                }
            }
        }
        return possiblePositions;
    }
}
