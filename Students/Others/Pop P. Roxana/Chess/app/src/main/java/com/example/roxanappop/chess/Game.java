package com.example.roxanappop.chess;

import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Board.Cell;
import com.example.roxanappop.chess.Model.Colour;
import com.example.roxanappop.chess.Model.Move;
import com.example.roxanappop.chess.Model.Pieces.DeadPieces;
import com.example.roxanappop.chess.Model.Pieces.Piece;
import com.example.roxanappop.chess.Model.Position;

/**
 * Created by roxanappop on 12/9/2015.
 */
public class Game {

    boolean on;
    int turn;

    public Game(){

        on = false;
        turn = Colour.WHITE;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isOn(){
        return on;
    }

    boolean isCheck(Board board){
        return false;
    }

    boolean isCheckmate(Board board){
        return false;
    }

    public void startGame(){
        on = true;
    }

    public void changeTurn(){
        if(turn==Colour.WHITE){
            turn = Colour.BLACK;
        }else{
            turn = Colour.WHITE;
        }
    }

    public void makeMove(Move move,Board board,DeadPieces pieces){

        Position srcPosition = move.from;
        Position destPosition = move.to;
        Cell source = board.getCellAtPosition(srcPosition);
        Cell destination = board.getCellAtPosition(destPosition);
        Piece capturedPiece = destination.getPiece();
        if(capturedPiece!=null){
            pieces.killPiece(capturedPiece);
        }
        destination.setPiece(move.carry);
        move.carry.setPosition(destPosition);
        source.setPiece(null);
        changeTurn();
    }
}
