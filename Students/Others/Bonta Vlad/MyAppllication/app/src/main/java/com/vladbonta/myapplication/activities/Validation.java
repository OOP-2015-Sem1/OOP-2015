package com.vladbonta.myapplication.activities;

import android.util.Log;

import com.vladbonta.myapplication.model.ChessPiece;
import com.vladbonta.myapplication.model.King;

import java.util.ArrayList;

/**
 * @author VladBonta on 06/01/16.
 */
public class Validation {
    private Board board;

    public Validation() {
    }

    public boolean isCheckAtWhite(Board testBoard, ChessPiece lastPressedPiece){
        ArrayList<ChessPiece> pieces = testBoard.getPieces();
        boolean result = false;
        ChessPiece whiteKing = null;
        for (int index = 0; index < pieces.size(); index++) {
            ChessPiece chessPiece = pieces.get(index);
            if (chessPiece.isWhite() && chessPiece.getClass() == King.class){
                whiteKing = chessPiece;
            }
        }
        for (int index = 0; index < pieces.size(); index++){
            ChessPiece chessPiece = pieces.get(index);
            if (!chessPiece.isWhite()){
                boolean clearPathForSelectedPiece = true;
                ArrayList<Integer> possiblePositions = chessPiece.getPossibleMovesList(chessPiece.getX(), chessPiece.getY(), whiteKing.getX(), whiteKing.getY());
                for (int index2 = 0; index2 < possiblePositions.size(); index2++){
                    Integer positionIndex = possiblePositions.get(index2);
                    ChessPiece piece = testBoard.getPieces().get(positionIndex);
                    if (!piece.isEmpty()){
                        clearPathForSelectedPiece = false;
                    }
                }
                //Check not to jump over pieces and pawn cases and king cases
                if (clearPathForSelectedPiece && chessPiece.isMovePossible(chessPiece.getX(), chessPiece.getY(), whiteKing.getX(), whiteKing.getY())){
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean isCheckAtBlack(Board testBoard, ChessPiece lastPressedPiece){
        ArrayList<ChessPiece> pieces = testBoard.getPieces();
        boolean result = false;
        ChessPiece blackKing = null;
        for (int index = 0; index < pieces.size(); index++) {
            ChessPiece chessPiece = pieces.get(index);
            if (!chessPiece.isWhite() && chessPiece.getClass() == King.class){
                blackKing = chessPiece;
            }
        }
        for (int index = 0; index < pieces.size(); index++){
            ChessPiece chessPiece = pieces.get(index);
            if (chessPiece.isWhite()){
                boolean clearPathForSelectedPiece = true;
                ArrayList<Integer> possiblePositions = chessPiece.getPossibleMovesList(chessPiece.getX(), chessPiece.getY(), blackKing.getX(), blackKing.getY());

                for (int index2 = 0; index2 < possiblePositions.size(); index2++){
                    Integer positionIndex = possiblePositions.get(index2);
                    ChessPiece piece = testBoard.getPieces().get(positionIndex);
                    if (!piece.isEmpty()){
                        clearPathForSelectedPiece = false;
                    }
                }
                //Check not to jump over pieces and pawn cases and king cases
                if (clearPathForSelectedPiece && chessPiece.isMovePossible(chessPiece.getX(), chessPiece.getY(), blackKing.getX(), blackKing.getY())){
                    result = true;
                }
            }
        }
        return result;
    }

    public boolean isCheckMate(Board board, Boolean checkMateAtWhite){

        return false;
    }

    public boolean selectedPieceBelongsToPlayingUser(ChessPiece pressedChessPiece, boolean whitePlayerTurn){
        boolean result = false;
        Log.d("myTag", "belongsToPlayinguser");
        if (pressedChessPiece.isWhite() == whitePlayerTurn){
            result = true;
        }
        return result;
    }
}
