package com.example.roxanappop.chess.Storage;

import com.example.roxanappop.chess.Model.Board.Board;

/**
 * Created by roxanappop on 12/21/2015.
 */
public class DummyStorage implements Storage {

    @Override
    public boolean saveBoard(Board board) {
        return false;
    }

    @Override
    public Board retrieveBoard() {

        Board board = new Board();
        board.createInitialBoard();
        return board;
    }
}
