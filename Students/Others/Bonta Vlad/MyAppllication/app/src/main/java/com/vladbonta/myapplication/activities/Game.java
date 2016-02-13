package com.vladbonta.myapplication.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.vladbonta.myapplication.model.ChessPiece;
import com.vladbonta.myapplication.model.King;
import com.vladbonta.myapplication.model.Pawn;
import com.vladbonta.myapplication.model.Rook;

import java.util.ArrayList;

/**
 * @author VladBonta on 27/12/15.
 */

//TO DO - Add logic for pawn attack, paawn transformation, checkmate, structure validation in classes, structure project in packages

public class Game {
    private Board board = new Board(this);
    private Board testBoard = new Board();
    private Validation validation = new Validation();

    private TilesAdapter mTilesAdapter;
    private RecyclerView mRecyclerView;
    private ChessPiece lastPressedPiece;
    private boolean whitePlayerTurn;
    private Context mContext;

    ArrayList<ChessPiece> pieces;

    public Game(RecyclerView recyclerView, Context context) {
        mContext =  context;
        lastPressedPiece = null;
        whitePlayerTurn = true;
        mRecyclerView = recyclerView;
        mTilesAdapter = new TilesAdapter(context, board);
        mRecyclerView.setAdapter(mTilesAdapter);
        updateBoard();
    }

    public void updateBoard(){
        copyTestToGoodPieces();
        mTilesAdapter.setChessPieces(board.getPieces());
        mTilesAdapter.notifyDataSetChanged();
        if (validation.isCheckMate(board, !whitePlayerTurn)){

            if (whitePlayerTurn){
                showDialog("CHECKMATE", "White is the winner!");
            } else {
                showDialog("CHECKMATE", "Black is the winner!");
            }

        }
    }

    private void copyGoodToTestPieces(){
        ArrayList<ChessPiece> pieces = board.getPieces();
        ArrayList<ChessPiece> tempPieces = new ArrayList<ChessPiece>();
        for (int i = 0;i < pieces.size();i++){
            ChessPiece chessPiece = pieces.get(i);
            tempPieces.add(chessPiece);
        }
        testBoard.setPieces(tempPieces);

    }

    private void copyTestToGoodPieces(){
        ArrayList<ChessPiece> pieces = testBoard.getPieces();
        ArrayList<ChessPiece> tempPieces = new ArrayList<ChessPiece>();
        for (int i = 0;i < pieces.size();i++){
            ChessPiece chessPiece = pieces.get(i);
            tempPieces.add(chessPiece);
        }
        board.setPieces(tempPieces);
    }

    public void handlePieceTouch(ChessPiece touchedChessPiece) {
        //validate selected piece to move
        if (lastPressedPiece == null) {
            if (!touchedChessPiece.isEmpty() && validation.selectedPieceBelongsToPlayingUser(touchedChessPiece, whitePlayerTurn)) {
                touchedChessPiece.setIsSelected(true);
                lastPressedPiece = touchedChessPiece;
                highlightPossiblePositions(touchedChessPiece);
                updateBoard();
            }
        } else if (lastPressedPiece.equals(touchedChessPiece)) {
            touchedChessPiece.setIsSelected(false);
            lastPressedPiece = null;
            dehighlightPossiblePositions();
            updateBoard();
        } else {
            //Validate the next position for selected piece
            copyGoodToTestPieces();
            // testBoard.setPieces(board.getPieces());
            if (touchedChessPiece.isReachable() && hasClearPath(lastPressedPiece, touchedChessPiece)) {
                //Next position is valid if it is an empty place or a different color piece
                if (touchedChessPiece.isWhite() != lastPressedPiece.isWhite() || touchedChessPiece.isEmpty()) {
                    //Verify it is a possible move for given chessPiece
                    if (lastPressedPiece.isMovePossible(lastPressedPiece.getX(), lastPressedPiece.getY(), touchedChessPiece.getX(), touchedChessPiece.getY())) {
                        //Validate movement for chess cases
                        movePieceAtPosition(false, lastPressedPiece, touchedChessPiece.getX(), touchedChessPiece.getY());
                        Boolean isCheckAtWhite = validation.isCheckAtWhite(testBoard, lastPressedPiece);
                        Boolean isCheckAtBlack = validation.isCheckAtBlack(testBoard, lastPressedPiece);
                        if (whitePlayerTurn) {
                            if (isCheckAtWhite) {
                                showDialog("CHECK", "YOUR KING is in check!");
//                                lastPressedPiece = null;
                                copyGoodToTestPieces();
                                updateBoard();
                            } else if (isCheckAtBlack) {
                                showDialog("CHECK", "White made a check!");
                                touchedChessPiece.setMoved(true);
                                whitePlayerTurn = !whitePlayerTurn;
                                lastPressedPiece = null;
                                updateBoard();
                            } else {
                                touchedChessPiece.setMoved(true);
                                whitePlayerTurn = !whitePlayerTurn;
                                lastPressedPiece = null;
                                updateBoard();
                            }
                        } else {
                            if (isCheckAtBlack) {
                                showDialog("CHECK", "YOUR KING is in check!");
//                                lastPressedPiece = null;
                                copyGoodToTestPieces();
                                updateBoard();
                            } else if (isCheckAtWhite) {
                                showDialog("CHECK", "Black made a check!");
                                touchedChessPiece.setMoved(true);
                                whitePlayerTurn = !whitePlayerTurn;
                                lastPressedPiece = null;
                                updateBoard();
                            } else {
                                touchedChessPiece.setMoved(true);
                                whitePlayerTurn = !whitePlayerTurn;
                                lastPressedPiece = null;
                                updateBoard();
                            }
                        }
                        dehighlightPossiblePositions();
                    } else {
                        //Check for king "rocada" movement
                        if (lastPressedPiece.getClass() == King.class && !lastPressedPiece.moved()) {
                            checkForRocada(touchedChessPiece.getX(), touchedChessPiece.getY());
                            if ((whitePlayerTurn && validation.isCheckAtWhite(board, lastPressedPiece)) || (!whitePlayerTurn && validation.isCheckAtBlack(board, lastPressedPiece))) {
                                showDialog("CHECK", "King is in check!");
                                copyGoodToTestPieces();
                            }
                        }
                    }
                }

            }
        }
    }
    public void highlightPossiblePositions(ChessPiece chessPiece){
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                ChessPiece piece = testBoard.getPieces().get((i - 1) * 8 + (j - 1));
                if (whitePlayerTurn != piece.isWhite() || piece.isEmpty()) {
                    if (chessPiece.isMovePossible(chessPiece.getX(), chessPiece.getY(), i, j)) {

                        if (chessPiece.getClass() == Pawn.class) {
                            if (chessPiece.getY() == piece.getY() && Math.abs(piece.getX() - chessPiece.getX()) <= 2 && piece.isEmpty()) {
                                piece.setIsReachable(true);
                            }

                            if (!piece.isEmpty() && ((chessPiece.getX() - 1 == piece.getX() && chessPiece.getY() - 1 == piece.getY())
                                    || (chessPiece.getX() - 1 == piece.getX() && chessPiece.getY() + 1 == piece.getY())
                                    ||((chessPiece.getX() + 1 == piece.getX() && chessPiece.getY() - 1 == piece.getY())
                                    || (chessPiece.getX() + 1 == piece.getX() && chessPiece.getY() + 1 == piece.getY())))
                                    )
                            {
                                piece.setIsReachable(true);
                            }
                        } else {
                            piece.setIsReachable(true);
                        }
                        if (!hasClearPath(chessPiece, piece)){
                            piece.setIsReachable(false);
                        }

                    }
                }
            }
        }
    }

    public void dehighlightPossiblePositions(){
        for (int i = 1; i <= 8; i++){
            for (int j = 1; j <= 8; j++){
                ChessPiece piece = testBoard.getPieces().get((i - 1) * 8 + (j - 1));
                piece.setIsReachable(false);
                piece.setIsSelected(false);
            }
        }
    }

    public Boolean hasClearPath(ChessPiece chessPiece, ChessPiece nextPositionPiece) {
        copyGoodToTestPieces();
        boolean clearPathForSelectedPiece = true;
        ArrayList<Integer> possiblePositions = chessPiece.getPossibleMovesList(chessPiece.getX(), chessPiece.getY(), nextPositionPiece.getX(), nextPositionPiece.getY());
        for (int index = 0; index < possiblePositions.size(); index++) {
            Integer positionIndex = possiblePositions.get(index);
            ChessPiece piece = testBoard.getPieces().get(positionIndex);
            if (!piece.isEmpty()) {
                clearPathForSelectedPiece = false;
            }
        }
        return clearPathForSelectedPiece;
    }

    public void movePieceAtPosition(boolean deleteLastPressedPiece,ChessPiece chessPiece, int xNextPosition, int yNextPosition){
        Log.e("NextPosition", "x:"+ xNextPosition + "y:"+yNextPosition);
        Log.e("lastPressedPiece", "x:" + lastPressedPiece.getX() + "y:" + lastPressedPiece.getY());
        lastPressedPiece.setIsSelected(false);
        testBoard.clearPieceAtPosition(chessPiece.getX(), chessPiece.getY());
        testBoard.changePieceAtPosition(xNextPosition, yNextPosition, chessPiece);
        if (deleteLastPressedPiece)
            lastPressedPiece = null;
    }

    private void checkForRocada(int x, int y){
        //Check if king will not be in check on rocada moves
        if (x == lastPressedPiece.getX() && y == lastPressedPiece.getY() + 2){
            int index = (lastPressedPiece.getX() - 1) * 8 + (lastPressedPiece.getY() - 1);
            if (board.getPieces().get(index + 1).isEmpty() && board.getPieces().get(index + 2). isEmpty()) {
                if (board.getPieces().get(index + 4).getClass() == Rook.class) {
                    copyGoodToTestPieces();
                    movePieceAtPosition(false, lastPressedPiece, x, y - 1);
                    if (whitePlayerTurn && !validation.isCheckAtWhite(testBoard, lastPressedPiece)){
                        copyGoodToTestPieces();
                        movePieceAtPosition(false, lastPressedPiece, x, y);
                        if (!validation.isCheckAtWhite(testBoard, lastPressedPiece)){
                            copyGoodToTestPieces();
                            movePieceAtPosition(false, lastPressedPiece, x, y + 1);
                            if (!validation.isCheckAtWhite(testBoard, lastPressedPiece)){
                                copyGoodToTestPieces();
                                makeRightRocada(index + 4);
                            }
                        }
                    } else if (!whitePlayerTurn){
                        copyGoodToTestPieces();
                        movePieceAtPosition(false, lastPressedPiece, x, y - 1);
                        if (whitePlayerTurn && !validation.isCheckAtBlack(testBoard, lastPressedPiece)) {
                            copyGoodToTestPieces();
                            movePieceAtPosition(false, lastPressedPiece, x, y);
                            if (!validation.isCheckAtBlack(testBoard, lastPressedPiece)) {
                                copyGoodToTestPieces();
                                movePieceAtPosition(false, lastPressedPiece, x, y + 1);
                                if (!validation.isCheckAtBlack(testBoard, lastPressedPiece)) {
                                    copyGoodToTestPieces();
                                    makeRightRocada(index + 4);
                                }
                            }
                        }
                    }
                }
            }
        } else if (x == lastPressedPiece.getX() && y == lastPressedPiece.getY() - 2){
            int index = (lastPressedPiece.getX() - 1) * 8 + (lastPressedPiece.getY() - 1);
            if (board.getPieces().get(index  - 1).isEmpty() && board.getPieces().get(index - 2). isEmpty()  && board.getPieces().get(index - 3). isEmpty()) {
                if (board.getPieces().get(index - 3).getClass() == Rook.class) {

                    copyGoodToTestPieces();
                    movePieceAtPosition(false, lastPressedPiece, x, y + 1);
                    if (whitePlayerTurn && !validation.isCheckAtWhite(testBoard, lastPressedPiece)){
                        copyGoodToTestPieces();
                        movePieceAtPosition(false, lastPressedPiece, x, y);
                        if (!validation.isCheckAtWhite(testBoard, lastPressedPiece)){
                            copyGoodToTestPieces();
                            makeLeftRocada(index - 3);
                        }
                    } else if (!whitePlayerTurn){
                        copyGoodToTestPieces();
                        movePieceAtPosition(false, lastPressedPiece, x, y + 1);
                        if (whitePlayerTurn && !validation.isCheckAtBlack(testBoard, lastPressedPiece)) {
                            copyGoodToTestPieces();
                            movePieceAtPosition(false, lastPressedPiece, x, y);
                            if (!validation.isCheckAtBlack(testBoard, lastPressedPiece)) {
                                copyGoodToTestPieces();
                                makeLeftRocada(index - 3);
                            }
                        }
                    }
                }
            }
        }
    }

    private void makeRightRocada(int rookIndex){
        lastPressedPiece.setMoved(true);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() + 2);
        //get ROOK piece
        lastPressedPiece = board.getPieces().get(rookIndex);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() - 3);
        whitePlayerTurn = !whitePlayerTurn;
    }

    private void makeLeftRocada(int rookIndex){
        lastPressedPiece.setMoved(true);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() - 2);
        //get ROOK piece
        lastPressedPiece = board.getPieces().get(rookIndex);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() + 2);
        whitePlayerTurn = !whitePlayerTurn;
    }

    public void showDialog(String title, String content){
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(content);
        if (title.equals("CHECKMATE")){
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            board = new Board();
                            dialog.dismiss();
                        }
                    });
        } else {
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
        }

        alertDialog.show();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}