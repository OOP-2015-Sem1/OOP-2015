package com.example.roxanappop.chess.Model;

import com.example.roxanappop.chess.Model.Pieces.Piece;

/**
 * Created by roxanappop on 12/13/2015.
 */
public class Move {

    private Position from;
    private Position to;
    private Piece carry;
    private Piece capturedPiece;
    private boolean isTestMove;

    public Position getFrom() {
        return from;
    }
    public void setFrom(Position from) {
        this.from = from;
    }
    public Position getTo() {
        return to;
    }

    public void setTo(Position to) {
        this.to = to;
    }

    public Piece getCarry() {
        return carry;
    }

    public void setCarry(Piece carry) {
        this.carry = carry;
    }
    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(Piece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    public boolean getIsTestMove(){
        return isTestMove;
    }

    public void setIsTestMove(boolean isTestMove) {
        this.isTestMove= isTestMove;
    }

    public void initializeMove(){
        from = null;
        to= null;
        carry=null;
        capturedPiece = null;
        isTestMove= false;
    }
}
