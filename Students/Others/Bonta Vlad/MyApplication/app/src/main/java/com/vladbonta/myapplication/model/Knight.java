package com.vladbonta.myapplication.model;

import com.vladbonta.myapplication.R;

/**
 * @author VladBonta on 27/12/15.
 */
public class Knight extends ChessPiece {
    public Knight(boolean isWhite, int x, int y){
        super(isWhite, x, y);
        this.setIsEmpty(false);
    }

    @Override
    public int getWhiteDrawableImageId() {
        return R.drawable.wknight;
    }

    @Override
    public int getBlackDrawableImageId() {
        return R.drawable.bknight;
    }

    @Override
    public boolean isMovePossible(int fromX, int fromY, int toX, int toY) {
        return super.isMovePossible(fromX, fromY, toX, toY) && ((fromX == toX - 2 && fromY == toY + 1) || (fromX == toX - 1 && fromY == toY + 2) || (fromX == toX + 1 && fromY == toY + 2) || (fromX == toX + 2 && fromY == toY + 1) || (fromX == toX + 2 && fromY == toY - 1) || (fromX == toX + 1 && fromY == toY - 2) || (fromX == toX - 1 && fromY == toY - 2) || (fromX == toX - 2 && fromY == toY - 1));

    }
}
