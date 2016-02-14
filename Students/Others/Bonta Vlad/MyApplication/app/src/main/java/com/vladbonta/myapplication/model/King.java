package com.vladbonta.myapplication.model;

import android.util.Log;

import com.vladbonta.myapplication.R;

/**
 * @author VladBonta on 27/12/15.
 */
public class King extends ChessPiece {
    public King(boolean isWhite, int x, int y){
        super(isWhite, x, y);
        this.setIsEmpty(false);
    }

    @Override
    public int getWhiteDrawableImageId() {
        return R.drawable.wking;
    }

    @Override
    public int getBlackDrawableImageId() {
        return R.drawable.bking;
    }

    @Override
    public boolean isMovePossible(int fromX, int fromY, int toX, int toY){
        boolean result;
        result = super.isMovePossible(fromX, fromY, toX, toY);
        int dist = (int) (Math.pow((toX - fromX), 2) + Math.pow((toY - fromY), 2));
        Log.d("kingValidation", "dist=" + String.valueOf(dist));

        if (dist != 1 && dist != 2){
            result = false;
        }
        return result;
    }
}
