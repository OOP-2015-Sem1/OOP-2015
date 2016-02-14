package com.vladbonta.myapplication.board_model;

import android.view.View;

import com.vladbonta.myapplication.logic.Game;
import com.vladbonta.myapplication.model.Bishop;
import com.vladbonta.myapplication.model.ChessPiece;
import com.vladbonta.myapplication.model.EmptyPiece;
import com.vladbonta.myapplication.model.King;
import com.vladbonta.myapplication.model.Knight;
import com.vladbonta.myapplication.model.Pawn;
import com.vladbonta.myapplication.model.Queen;
import com.vladbonta.myapplication.model.Rook;

import java.util.ArrayList;

/**
 * @author VladBonta on 27/12/15.
 */
public class Board implements View.OnClickListener{
    private ArrayList<ChessPiece> pieces;
    private Game mGame;
    public Board(){
        super();
        addPiecesOnBoard();
    }
    public Board(Game game) {
        super();
        this.mGame = game;
        addPiecesOnBoard();
    }


    public void addPiecesOnBoard() {
        pieces = new ArrayList<>();
        pieces.add(new Rook(false, 1, 1));
        pieces.add(new Knight(false, 1, 2));
        pieces.add(new Bishop(false, 1, 3));
        pieces.add(new King(false, 1, 4));
        pieces.add(new Queen(false, 1, 5));
        pieces.add(new Bishop(false, 1, 6));
        pieces.add(new Knight(false, 1, 7));
        pieces.add(new Rook(false, 1, 8));
        for(int i = 1;i <= 8; i++){
            pieces.add(new Pawn(false, 2, i));
        }
        for(int i = 0;i < 32; i++){
            pieces.add(new EmptyPiece(false, 3 + i / 8, 1 + i % 8));
        }
        for(int i = 1;i <= 8; i++){
            pieces.add(new Pawn(true, 7, i));
        }
        pieces.add(new Rook(true, 8, 1));
        pieces.add(new Knight(true, 8, 2));
        pieces.add(new Bishop(true, 8, 3));
        pieces.add(new King(true, 8, 4));
        pieces.add(new Queen(true, 8, 5));
        pieces.add(new Bishop(true, 8, 6));
        pieces.add(new Knight(true, 8, 7));
        pieces.add(new Rook(true, 8, 8));
    }


    public void clearPieceAtPosition(int x, int y){
        EmptyPiece emptyChessPiece = new EmptyPiece(false, x, y);
        int pieceIndex = (x - 1) * 8 + (y - 1);
        pieces.remove(pieceIndex);
        pieces.add(pieceIndex, emptyChessPiece);
    }

    public void changePieceAtPosition(int x, int y, ChessPiece chessPiece){
        int pieceIndex = (x - 1) * 8 + (y - 1);
        pieces.remove(pieceIndex);
        chessPiece.setX(x);
        chessPiece.setY(y);
        pieces.add(pieceIndex, chessPiece);
    }

    public ArrayList<ChessPiece> getPieces(){
        ArrayList<ChessPiece> tmpPieces = new ArrayList<>();
        for (int i = 0;i < pieces.size(); i++){
            ChessPiece piece = pieces.get(i);
            piece.setX(i / 8 + 1);
            piece.setY(1 + i % 8);
            tmpPieces.add(piece);
        }
        return tmpPieces;
    }
    public void setPieces(ArrayList<ChessPiece> pieces){
        this.pieces = pieces;
    }

    @Override
    public void onClick(View view) {
        int pieceIndex = (int)view.getTag();

        ChessPiece chessPiece = pieces.get(pieceIndex);
        mGame.handlePieceTouch(chessPiece);
    }
}