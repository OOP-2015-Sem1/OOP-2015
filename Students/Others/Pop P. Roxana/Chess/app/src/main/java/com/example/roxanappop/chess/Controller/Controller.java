package com.example.roxanappop.chess.Controller;

import android.content.Context;
import android.view.View;

import com.example.roxanappop.chess.Storage.DummyStorage;
import com.example.roxanappop.chess.Game;
import com.example.roxanappop.chess.Model.Board.Board;
import com.example.roxanappop.chess.Model.Board.Cell;
import com.example.roxanappop.chess.Model.Pieces.DeadPieces;
import com.example.roxanappop.chess.Model.Pieces.Piece;
import com.example.roxanappop.chess.Model.Position;
import com.example.roxanappop.chess.Model.Move;
import com.example.roxanappop.chess.Storage.Storage;
import com.example.roxanappop.chess.View.BoardGui;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class Controller implements Observer {

    Context context;
    Board board;
    Storage storage;
    BoardGui boardGui;
    Game game;
    ArrayList<Position> possiblePositions;
    DeadPieces deadPieces;

    public Controller(Context context){

        this.context=context;
        game = new Game();
        storage = new DummyStorage();
        deadPieces = new DeadPieces();//?
        board = storage.retrieveBoard();
        boardGui = new BoardGui(context);
        boardGui.syncBoards(board);
        if(game.isOn()){
            boardGui.setImageButtonListeners(new ImageButtonActionListener(context,this));
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        game.startGame();
        boardGui.setImageButtonListeners(new ImageButtonActionListener(context, this));
    }

     boolean selectPiece(int buttonId){

        Position piecePosition = boardGui.getPositionFromId(buttonId);
        Cell cell = board.getCellAtPosition(piecePosition);
        Piece piece = cell.getPiece();
        if(piece==null){
            return false;
        }
        if(!piece.isMoveable(board,game.getTurn())){
            return false;
        }
        Move move = Move.getInstance();
        move.from = piecePosition;
        move.carry=piece;
         possiblePositions = piece.getPossiblePositions(board);
         boardGui.emphasizeButtons(possiblePositions);
        return true;
    }

    boolean selectPosition(int buttonId){

        boolean positionIsViable = false;
        Position piecePosition = boardGui.getPositionFromId(buttonId);
        for(Position possiblePosition: possiblePositions) {
            if(piecePosition.equals(possiblePosition)){
                positionIsViable= true;
            }
        }
        if(positionIsViable){
            Move move = Move.getInstance();
            move.to = piecePosition;
            game.makeMove(move,board,deadPieces);
            move.initializeMove();
            boardGui.syncBoards(board);
            boardGui.setDefaultButtonsColour();
        }
        return positionIsViable;
    }
}

class ImageButtonActionListener implements View.OnClickListener {
    Context context;
    Controller controller;

    public ImageButtonActionListener(Context context,Controller controller){
        this.context=context;
        this.controller = controller;
    }

    @Override

    public void onClick(View v) {
        int id = v.getId();

        Move move = Move.getInstance();
        if(move.from==null){
                controller.selectPiece(id);

        }else{
                controller.selectPosition(id);
        }
    }
}

