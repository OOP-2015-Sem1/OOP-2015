package com.example.roxanappop.chess.Controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roxanappop.chess.Model.Colour;
import com.example.roxanappop.chess.R;
import com.example.roxanappop.chess.Storage.DummyStorage;
import com.example.roxanappop.chess.Model.Game;
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

    Board board;
    BoardGui boardGui;
    Context context;
    DeadPieces deadPieces;
    Game game;
    Move move;
    ArrayList<Position> possiblePositions;
    Storage storage;

    public Controller(Context context) {

        move = new Move();
        this.context = context;
        game = new Game();
        storage = new DummyStorage();
        deadPieces = new DeadPieces();
        board = storage.retrieveBoard();
        boardGui = new BoardGui(context);
        boardGui.syncBoards(board,game.getTurn());
        if (game.isOn()) {
            boardGui.setImageButtonListeners(new ImageButtonActionListener(context, this, move));
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        game.startGame();
        boardGui.setImageButtonListeners(new ImageButtonActionListener(context, this, move));
    }

    protected boolean selectPiece(int buttonId) {

        Position piecePosition = boardGui.getPositionFromId(buttonId, game.getTurn());
        Piece piece = board.getPieceAtPosition(piecePosition);
        if (piece == null) {
            return false;
        }
        if (!piece.isMoveable(board, game.getTurn(), game)) {
            return false;
        }
        move.setFrom(piecePosition);
        move.setCarry(piece);
        possiblePositions = piece.getPossiblePositions(board, game);
        boardGui.emphasizeButtons(possiblePositions,game.getTurn());
        return true;
    }

    protected boolean selectPosition(int buttonId) {

        boolean positionIsViable = false;
        Position piecePosition = boardGui.getPositionFromId(buttonId,game.getTurn());
        for (Position possiblePosition : possiblePositions) {
            if (piecePosition.equals(possiblePosition)) {
                positionIsViable = true;
            }
        }
        if (positionIsViable) {
            move.setTo(piecePosition);
            move.setCapturedPiece(board.getPieceAtPosition(piecePosition));
            game.makeMove(move, board, deadPieces);
            game.changeTurn();
            takeTurnChangedActions();
        }
        return positionIsViable;
    }

    private void takeTurnChangedActions() {
        //not sure how to do this
        int turn = game.getTurn();
        TextView textView = (TextView) ((Activity) context).findViewById(R.id.playersTurnTextView);
        String turnString = ((turn) == Colour.WHITE) ? "White" : "Black";
        textView.setText(turnString+"'s turn");
        if(game.isInCheck(board,turn)){
            if(game.isCheckMate(board,turn)){
                takeGameOverActions();
            }else{
                Toast.makeText(context,turnString+" in check!",Toast.LENGTH_SHORT).show();
                textView.append("\n In check!!!");
                textView.setBackgroundColor(Color.RED);
            }
        }else{
            if(turn==Colour.WHITE){
                textView.setBackgroundColor(Color.LTGRAY);
            }else{
                textView.setBackgroundColor(Color.GRAY);
            }
        }
        move.initializeMove();
        boardGui.syncBoards(board,turn);
        boardGui.setDefaultButtonsColour();
    }

    private void takeGameOverActions(){

        String winner =((Colour.getTheOtherColour(game.getTurn())) == Colour.WHITE) ? "White" : "Black";
        Toast.makeText(context,winner+" won",Toast.LENGTH_LONG).show();
        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {


                //maybe leave the board on for a certain time(as in the tic tac toe game)
                board = new Board();
                board.createInitialBoard();
                boardGui.syncBoards(board,Colour.WHITE);
                game.setTurn(Colour.WHITE);
                TextView textView = (TextView) ((Activity) context).findViewById(R.id.playersTurnTextView);
                textView.setText("White's turn");
                textView.setBackgroundColor(Color.LTGRAY);
            }
        }.start();

    }

    public void takeSurrenderActions() {
        takeGameOverActions();
    }
}

