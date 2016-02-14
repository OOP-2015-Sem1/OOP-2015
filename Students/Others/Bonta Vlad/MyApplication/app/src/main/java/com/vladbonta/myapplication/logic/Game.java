package com.vladbonta.myapplication.logic;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.vladbonta.myapplication.board_model.Board;
import com.vladbonta.myapplication.views.TilesAdapter;
import com.vladbonta.myapplication.validation.Validation;
import com.vladbonta.myapplication.model.ChessPiece;
import com.vladbonta.myapplication.model.King;
import com.vladbonta.myapplication.model.Pawn;
import com.vladbonta.myapplication.model.Rook;

import java.util.ArrayList;

/**
 * @author VladBonta on 27/12/15.
 */

//TO DO - Add logic paawn transformation, checkmate, structure validation in classes, structure project in packages

public class Game {
    private Board board = new Board(this);
    private Board testBoard = new Board();
    private Validation validation = new Validation();

    private TilesAdapter mTilesAdapter;
    private RecyclerView mRecyclerView;
    private ChessPiece lastPressedPiece;
    private boolean whitePlayerTurn;
    private Context mContext;

    private int nrOfReachablePositions = 0;
    public Game(RecyclerView recyclerView, Context context) {
        mContext = context;
        mRecyclerView = recyclerView;
        startNewGame(context);
    }

    private void startNewGame(Context context) {
        lastPressedPiece = null;
        whitePlayerTurn = true;
        board = new Board(this);
        copyGoodToTestPieces();
        mTilesAdapter = new TilesAdapter(context, board);
        mRecyclerView.setAdapter(mTilesAdapter);
        updateBoard();

    }

    public void updateBoard() {
        copyTestToGoodPieces();
        mTilesAdapter.setChessPieces(board.getPieces());
        mTilesAdapter.notifyDataSetChanged();
    }

    public Boolean isCheckMate() {
        copyTestToGoodPieces();
        if (isCheckMate(whitePlayerTurn)) {
            if (whitePlayerTurn) {
                showDialog("CHECKMATE", "Black is the winner!");
            } else {
                showDialog("CHECKMATE", "White is the winner!");
            }
            return true;
        }
        dehighlightPossiblePositions();
        return false;
    }

    private void copyGoodToTestPieces() {
        ArrayList<ChessPiece> pieces = board.getPieces();
        ArrayList<ChessPiece> tempPieces = new ArrayList<>();
        for (int i = 0; i < pieces.size(); i++) {
            ChessPiece chessPiece = pieces.get(i);
            tempPieces.add(chessPiece);
        }
        testBoard.setPieces(tempPieces);

    }

    private void copyTestToGoodPieces() {
        ArrayList<ChessPiece> pieces = testBoard.getPieces();
        ArrayList<ChessPiece> tempPieces = new ArrayList<>();
        for (int i = 0; i < pieces.size(); i++) {
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
            if (touchedChessPiece.isReachable()) {
                //Verify it is a possible move for given chessPiece
                if (lastPressedPiece.isMovePossible(lastPressedPiece.getX(), lastPressedPiece.getY(), touchedChessPiece.getX(), touchedChessPiece.getY())) {
                    //Validate movement for chess cases
                    movePieceAtPosition(false, lastPressedPiece, touchedChessPiece.getX(), touchedChessPiece.getY());
                    Boolean isCheckAtWhite = validation.isCheckAtWhite(testBoard);
                    Boolean isCheckAtBlack = validation.isCheckAtBlack(testBoard);
                    if (whitePlayerTurn) {
                        if (isCheckAtWhite) {
                            showDialog("CHECK", "YOUR KING is in check!");
                            lastPressedPiece = null;
                            copyGoodToTestPieces();
                            updateBoard();
                        } else if (isCheckAtBlack) {
                            touchedChessPiece.setMoved(true);
                            whitePlayerTurn = !whitePlayerTurn;
                            lastPressedPiece = null;
                            updateBoard();
                            if (!isCheckMate()) {
                                showDialog("CHECK", "White made a check!");
                            }
                        } else {
                            touchedChessPiece.setMoved(true);
                            whitePlayerTurn = !whitePlayerTurn;
                            lastPressedPiece = null;
                            updateBoard();
                        }
                    } else {
                        if (isCheckAtBlack) {
                            showDialog("CHECK", "YOUR KING is in check!");
                            lastPressedPiece = null;
                            copyGoodToTestPieces();
                            updateBoard();
                        } else if (isCheckAtWhite) {

                            touchedChessPiece.setMoved(true);
                            whitePlayerTurn = !whitePlayerTurn;
                            lastPressedPiece = null;
                            updateBoard();
                            if (!isCheckMate()) {
                                showDialog("CHECK", "Black made a check!");
                            }
                        } else {
                            touchedChessPiece.setMoved(true);
                            whitePlayerTurn = !whitePlayerTurn;
                            lastPressedPiece = null;
                            updateBoard();
                        }
                    }
                    dehighlightPossiblePositions();
                }
            }
            //Check for king "rocada" movement
            if (lastPressedPiece != null && lastPressedPiece.getClass() == King.class && !lastPressedPiece.moved()) {
                checkForRocada(touchedChessPiece.getX(), touchedChessPiece.getY());
                if ((whitePlayerTurn && validation.isCheckAtWhite(board)) || (!whitePlayerTurn && validation.isCheckAtBlack(board))) {
                    showDialog("CHECK", "King is in check!");
                    copyGoodToTestPieces();
                }
            }
        }
    }

    public void highlightPossiblePositions(ChessPiece chessPiece) {

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece piece = testBoard.getPieces().get((i - 1) * 8 + (j - 1));

                if ((chessPiece.getX() == piece.getX() && chessPiece.getY() == piece.getY())
                        || !(whitePlayerTurn != piece.isWhite() || piece.isEmpty())
                        || !hasClearPath(chessPiece, piece)
                        ) {
                    piece.setIsReachable(false);
                } else {
                    if (chessPiece.getClass() == King.class) {
                        if (!chessPiece.moved()
                                && chessPiece.getX() == piece.getX()
                                && (chessPiece.getX() == 1 || chessPiece.getX() == 8)
                                && chessPiece.getY() == 4
                                && (piece.getY() == 2 || piece.getY() == 6)) {
                            piece.setIsReachable(true);
                            if (whitePlayerTurn) {
                                if (piece.getY() == 2) {
                                    if (piece.isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j).isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j - 2).getClass() == Rook.class) {
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY());
                                        Boolean checkPosition1 = validation.isCheckAtWhite(testBoard);
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY() + 1);
                                        Boolean checkPosition2 = validation.isCheckAtWhite(testBoard);
                                        copyGoodToTestPieces();
                                        if (checkPosition1 || checkPosition2) {
                                            piece.setIsReachable(false);
                                        }
                                    } else {
                                        piece.setIsReachable(false);
                                    }
                                } else {
                                    if (piece.isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j).isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j - 2).isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j + 1).getClass() == Rook.class) {
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY());
                                        Boolean checkPosition1 = validation.isCheckAtWhite(testBoard);
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY() + 1);
                                        Boolean checkPosition2 = validation.isCheckAtWhite(testBoard);
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY() - 1);
                                        Boolean checkPosition3 = validation.isCheckAtWhite(testBoard);
                                        copyGoodToTestPieces();
                                        if (checkPosition1
                                                || checkPosition2
                                                || checkPosition3) {
                                            piece.setIsReachable(false);
                                        }
                                    } else {
                                        piece.setIsReachable(false);
                                    }
                                }
                            } else {
                                if (piece.getY() == 2) {
                                    if (piece.isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j).isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j - 2).getClass() == Rook.class) {
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY());
                                        Boolean checkPosition1 = validation.isCheckAtBlack(testBoard);
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY() + 1);
                                        Boolean checkPosition2 = validation.isCheckAtBlack(testBoard);
                                        copyGoodToTestPieces();
                                        if (checkPosition1 || checkPosition2) {
                                            piece.setIsReachable(false);
                                        }
                                    } else {
                                        piece.setIsReachable(false);
                                    }
                                } else {
                                    if (piece.isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j).isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j - 2).isEmpty()
                                            && testBoard.getPieces().get((i - 1) * 8 + j + 1).getClass() == Rook.class) {
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY());
                                        Boolean checkPosition1 = validation.isCheckAtBlack(testBoard);
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY() + 1);
                                        Boolean checkPosition2 = validation.isCheckAtBlack(testBoard);
                                        copyGoodToTestPieces();
                                        movePieceAtPosition(false, chessPiece, piece.getX(), piece.getY() - 1);
                                        Boolean checkPosition3 = validation.isCheckAtBlack(testBoard);
                                        copyGoodToTestPieces();
                                        if (checkPosition1
                                                || checkPosition2
                                                || checkPosition3) {
                                            piece.setIsReachable(false);
                                        }
                                    } else {
                                        piece.setIsReachable(false);
                                    }
                                }
                            }
                            copyGoodToTestPieces();
                        }
                    }
                    if (chessPiece.isMovePossible(chessPiece.getX(), chessPiece.getY(), i, j)) {

                        if (chessPiece.getClass() == Pawn.class) {
                            piece.setIsReachable(false);
                            if (chessPiece.getY() == piece.getY() && Math.abs(piece.getX() - chessPiece.getX()) <= 2 && piece.isEmpty()) {
                                piece.setIsReachable(true);
                            }

                            if (!piece.isEmpty() && ((chessPiece.getX() - 1 == piece.getX() && chessPiece.getY() - 1 == piece.getY())
                                    || (chessPiece.getX() - 1 == piece.getX() && chessPiece.getY() + 1 == piece.getY())
                                    || ((chessPiece.getX() + 1 == piece.getX() && chessPiece.getY() - 1 == piece.getY())
                                    || (chessPiece.getX() + 1 == piece.getX() && chessPiece.getY() + 1 == piece.getY())))
                                    ) {
                                piece.setIsReachable(true);
                            }
                        } else {
                            piece.setIsReachable(true);
                        }
                        copyGoodToTestPieces();
                        movePieceAtPosition(false, chessPiece, i, j);
                        if (whitePlayerTurn) {
                            if (validation.isCheckAtWhite(testBoard)) {
                                piece.setIsReachable(false);
                            }
                        } else if (validation.isCheckAtBlack(testBoard)) {
                            piece.setIsReachable(false);
                        }
                        copyGoodToTestPieces();
                    } else {
                        piece.setIsReachable(false);
                    }
                }
             if (piece.isReachable()){
                 nrOfReachablePositions++;
             }
            }
        }

    }

    public void dehighlightPossiblePositions() {
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
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

    public void movePieceAtPosition(boolean deleteLastPressedPiece, ChessPiece chessPiece, int xNextPosition, int yNextPosition) {
        testBoard.clearPieceAtPosition(chessPiece.getX(), chessPiece.getY());
        testBoard.changePieceAtPosition(xNextPosition, yNextPosition, chessPiece);
        if (deleteLastPressedPiece)
            lastPressedPiece = null;
    }

    private void checkForRocada(int x, int y) {
        //Check if king will not be in check on rocada moves
        if (x == lastPressedPiece.getX() && y == lastPressedPiece.getY() + 2) {
            int index = (lastPressedPiece.getX() - 1) * 8 + (lastPressedPiece.getY() - 1);
            if (whitePlayerTurn) {
                copyGoodToTestPieces();
                makeRightRocada(index + 4);
                updateBoard();
                dehighlightPossiblePositions();
                lastPressedPiece = null;

            } else {
                copyGoodToTestPieces();
                makeRightRocada(index + 4);
                updateBoard();
                dehighlightPossiblePositions();
                lastPressedPiece = null;
            }
        } else if (x == lastPressedPiece.getX() && y == lastPressedPiece.getY() - 2) {
            int index = (lastPressedPiece.getX() - 1) * 8 + (lastPressedPiece.getY() - 1);
            if (board.getPieces().get(index - 3).getClass() == Rook.class) {
                copyGoodToTestPieces();
                movePieceAtPosition(false, lastPressedPiece, x, y + 1);
                if (whitePlayerTurn) {
                    copyGoodToTestPieces();
                    makeLeftRocada(index - 3);
                    updateBoard();
                    dehighlightPossiblePositions();
                    lastPressedPiece = null;
                }
            } else {
                copyGoodToTestPieces();
                makeLeftRocada(index - 3);
                updateBoard();
                dehighlightPossiblePositions();
                lastPressedPiece = null;
            }
        }

    }

    private void makeRightRocada(int rookIndex) {
        lastPressedPiece.setMoved(true);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() + 2);
        //get ROOK piece
        lastPressedPiece = board.getPieces().get(rookIndex);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() - 3);
        whitePlayerTurn = !whitePlayerTurn;
    }

    private void makeLeftRocada(int rookIndex) {
        lastPressedPiece.setMoved(true);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() - 2);
        //get ROOK piece
        lastPressedPiece = board.getPieces().get(rookIndex);
        movePieceAtPosition(false, lastPressedPiece, lastPressedPiece.getX(), lastPressedPiece.getY() + 2);
        whitePlayerTurn = !whitePlayerTurn;
    }

    public boolean isCheckMate(Boolean checkMateAtWhite) {
        nrOfReachablePositions = 0;
        copyGoodToTestPieces();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece piece = testBoard.getPieces().get((i - 1) * 8 + (j - 1));
                if (piece.isWhite() == checkMateAtWhite && !piece.isEmpty()) {
                    highlightPossiblePositions(piece);
                }
            }
        }
        Log.e("myTag", String.valueOf(nrOfReachablePositions));

        if (nrOfReachablePositions == 0){
            return true;
        }
        copyGoodToTestPieces();
        return false;
    }

    public void showDialog(String title, String content) {
        AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(content);
        if (title.equals("CHECKMATE")) {
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startNewGame(mContext);
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


}