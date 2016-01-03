package com.example.roxanappop.chess.Storage;

import com.example.roxanappop.chess.Model.Board.Board;

/**
 * Created by roxanappop on 12/21/2015.
 */
public interface Storage {

    public boolean saveBoard(Board board);
    public Board retrieveBoard();
}


