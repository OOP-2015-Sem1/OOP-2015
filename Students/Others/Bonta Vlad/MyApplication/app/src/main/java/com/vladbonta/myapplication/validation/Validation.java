package com.vladbonta.myapplication.validation;

import com.vladbonta.myapplication.board_model.Board;
import com.vladbonta.myapplication.model.ChessPiece;
import com.vladbonta.myapplication.model.King;
import com.vladbonta.myapplication.model.Pawn;

import java.util.ArrayList;

/**
 * @author VladBonta on 06/01/16.
 */
public class Validation {
    public Validation() {
    }

    public boolean isCheckAtWhite(Board testBoard) {
        ArrayList<ChessPiece> pieces = testBoard.getPieces();
        ChessPiece whiteKing = null;
        Boolean result = false;

        for (int index = 0; index < pieces.size(); index++) {
            ChessPiece chessPiece = pieces.get(index);
            if (chessPiece.isWhite() && chessPiece.getClass() == King.class) {
                whiteKing = chessPiece;
            }
        }
        for (int index = 0; index < pieces.size(); index++) {
            ChessPiece chessPiece = pieces.get(index);
            if (!chessPiece.isWhite()) {
                boolean clearPathForSelectedPiece = true;
                assert whiteKing != null;
                ArrayList<Integer> possiblePositions = chessPiece.getPossibleMovesList(chessPiece.getX(), chessPiece.getY(), whiteKing.getX(), whiteKing.getY());
                for (int index2 = 0; index2 < possiblePositions.size(); index2++) {
                    Integer positionIndex = possiblePositions.get(index2);
                    ChessPiece piece = testBoard.getPieces().get(positionIndex);
                    if (!piece.isEmpty()) {
                        clearPathForSelectedPiece = false;
                    }
                }
                //Check not to jump over pieces and pawn cases and king cases
                if (clearPathForSelectedPiece && chessPiece.isMovePossible(chessPiece.getX(), chessPiece.getY(), whiteKing.getX(), whiteKing.getY())) {
                    result = true;
                    if (chessPiece.getClass() == Pawn.class) {
                        if (chessPiece.getY() == whiteKing.getY()) {
                            if (whiteKing.getX() - chessPiece.getX() <= 2 && whiteKing.getX() - chessPiece.getX() > 0) {
                                result = false;
                            }
                        }
                    }

                }
                if (result){
                    return true;
                }
            }
        }

        return result;
    }

    public boolean isCheckAtBlack(Board testBoard) {
        ArrayList<ChessPiece> pieces = testBoard.getPieces();
        ChessPiece blackKing = null;
        Boolean result = false;
        for (int index = 0; index < pieces.size(); index++) {
            ChessPiece chessPiece = pieces.get(index);
            if (!chessPiece.isWhite() && chessPiece.getClass() == King.class) {
                blackKing = chessPiece;
            }
        }
        for (int index = 0; index < pieces.size(); index++) {
            ChessPiece chessPiece = pieces.get(index);
            if (chessPiece.isWhite()) {
                boolean clearPathForSelectedPiece = true;
                assert blackKing != null;
                ArrayList<Integer> possiblePositions = chessPiece.getPossibleMovesList(chessPiece.getX(), chessPiece.getY(), blackKing.getX(), blackKing.getY());

                for (int index2 = 0; index2 < possiblePositions.size(); index2++) {
                    Integer positionIndex = possiblePositions.get(index2);
                    ChessPiece piece = testBoard.getPieces().get(positionIndex);
                    if (!piece.isEmpty()) {
                        clearPathForSelectedPiece = false;
                    }
                }
                if (clearPathForSelectedPiece && chessPiece.isMovePossible(chessPiece.getX(), chessPiece.getY(), blackKing.getX(), blackKing.getY())) {
                    result = true;
                    if (chessPiece.getClass() == Pawn.class) {
                        if (chessPiece.getY() == blackKing.getY()) {
                            if (blackKing.getX() - chessPiece.getX() >= -2 && blackKing.getX() - chessPiece.getX() < 0) {
                                result = false;
                            }
                        }
                    }
                }
                if (result){
                    return true;
                }
            }
        }
        return result;
    }

    public boolean selectedPieceBelongsToPlayingUser(ChessPiece pressedChessPiece, boolean whitePlayerTurn) {
        boolean result = false;
        if (pressedChessPiece.isWhite() == whitePlayerTurn) {
            result = true;
        }
        return result;
    }
}
