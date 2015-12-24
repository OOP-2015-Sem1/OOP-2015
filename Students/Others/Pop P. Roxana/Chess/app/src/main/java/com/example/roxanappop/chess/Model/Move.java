package com.example.roxanappop.chess.Model;

import com.example.roxanappop.chess.Model.Pieces.Piece;

/**
 * Created by roxanappop on 12/13/2015.
 */
public class Move {

    public Position from;
    public Position to;
    public Piece carry;//do i need it?
    private static Move instance=null;

    private Move(){}

    public static Move getInstance(){

        if(instance==null){
            instance = new Move();
        }
        return instance;
    }

    public void initializeMove(){
        from = null;
        to= null;
        carry=null;
    }
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
}
