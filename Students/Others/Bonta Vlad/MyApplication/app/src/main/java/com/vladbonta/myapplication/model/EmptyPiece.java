package com.vladbonta.myapplication.model;

/**
 * @author VladBonta on 03/01/16.
 */
public class EmptyPiece extends ChessPiece{
    public EmptyPiece(boolean isWhite, int x, int y){
        super(isWhite, x, y);
        this.setIsEmpty(true);
    }

    @Override
    public int getWhiteDrawableImageId() {
        return 0;
    }

    @Override
    public int getBlackDrawableImageId() {
        return 0;
    }

    @Override
    public boolean isMovePossible(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}
