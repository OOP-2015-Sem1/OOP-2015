package com.example.roxanappop.chess.Model;

import android.util.Log;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Board.Cell;
import com.example.roxanappop.chess.Model.Colour;
import com.example.roxanappop.chess.Model.Move;
import com.example.roxanappop.chess.Model.Pieces.DeadPieces;
import com.example.roxanappop.chess.Model.Pieces.King;
import com.example.roxanappop.chess.Model.Pieces.Piece;
import com.example.roxanappop.chess.Model.Position;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/9/2015.
 */
public class Game {

    private boolean on;
    private int turn;
    private Position whiteKingPosition;
    private Position blackKingPosition;

    public Game() {

        on = false;
        turn = Colour.WHITE;
        whiteKingPosition = new Position(7, 4);
        blackKingPosition = new Position(0, 4);
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int colour){
        turn = colour;
    }

    public void changeTurn() {
        if (turn == Colour.WHITE) {
            turn = Colour.BLACK;
        } else {
            turn = Colour.WHITE;
        }
    }

    public boolean isOn() {
        return on;
    }

    public void startGame() {
        on = true;
    }

    public boolean isInCheck(Board board, int kingColour) {
        int enemyColour = Colour.getTheOtherColour(kingColour);
        ArrayList<Piece> enemyPieces = board.getPiecesByColour(enemyColour);
        if (enemyPieces != null) {

            for (Piece enemyPiece : enemyPieces) {
                Position kingPosition = (kingColour == Colour.WHITE) ? whiteKingPosition : blackKingPosition;
                if (enemyPiece.canAttack(board, kingPosition)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isCheckMate(Board board, int kingColour) {
        ArrayList<Piece> sameColourPieces = board.getPiecesByColour(kingColour);
        for (Piece piece : sameColourPieces) {
            if (piece.isMoveable(board, kingColour, this)) {
                return false;
            }
        }
        return true;
    }

    public void makeMove(Move move, Board board, DeadPieces pieces) {

        Position srcPosition = move.getFrom();
        Position destPosition = move.getTo();
        Cell source = board.getCellAtPosition(srcPosition);
        Cell destination = board.getCellAtPosition(destPosition);
        Piece capturedPiece = move.getCapturedPiece();
        if (capturedPiece != null) {
            pieces.killPiece(capturedPiece);
        }
        destination.setPiece(move.getCarry());
        move.getCarry().setPosition(destPosition);
        if (!move.getIsTestMove()) {
            move.getCarry().setWasMoved(true);
        }
        source.setPiece(null);
        if (move.getCarry() instanceof King) {
            if (move.getCarry().getColour() == Colour.WHITE) {
                whiteKingPosition = move.getCarry().getPosition();
            } else {
                blackKingPosition = move.getCarry().getPosition();
            }
        }
    }

    public void revertMove(Move move, Board board, DeadPieces pieces) {

        Position srcPosition = move.getFrom();
        Position destPosition = move.getTo();
        Cell source = board.getCellAtPosition(srcPosition);
        Cell destination = board.getCellAtPosition(destPosition);
        Piece capturedPiece = move.getCapturedPiece();
        if (capturedPiece != null) {
            pieces.revivePiece(capturedPiece);
        }
        destination.setPiece(capturedPiece);
        if (capturedPiece != null) {
            capturedPiece.setPosition(destPosition);
        }
        source.setPiece(move.getCarry());
        move.getCarry().setPosition(srcPosition);
        if (move.getCarry() instanceof King) {
            if (move.getCarry().getColour() == Colour.WHITE) {
                whiteKingPosition = move.getCarry().getPosition();
            } else {
                blackKingPosition = move.getCarry().getPosition();
            }
        }
    }
}
