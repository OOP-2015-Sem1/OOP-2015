package com.example.roxanappop.chess.Model.Pieces;

import com.example.roxanappop.chess.Model.Pieces.Piece;

import java.util.ArrayList;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class DeadPieces {

    private ArrayList<Piece> deadPieces;

    public DeadPieces(){

        deadPieces = new ArrayList<Piece>();
    }

    public ArrayList<Piece> getDeadPieces() {
        return deadPieces;
    }

    public void setDeadPieces(ArrayList<Piece> deadPieces) {
        this.deadPieces = deadPieces;
    }

    public void killPiece(Piece piece){
        deadPieces.add(piece);
    }

    public void revivePiece(Piece piece){
        deadPieces.remove(piece);
    }

}

