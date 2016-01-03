package com.example.roxanappop.chess.Model.Board;

import com.example.roxanappop.chess.Model.Pieces.Piece;

/**
 * Created by roxanappop on 12/13/2015.
 */
public class Cell {

    private Piece piece;

    public Cell(){}
    public Cell(Piece piece){
        setPiece(piece);
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
