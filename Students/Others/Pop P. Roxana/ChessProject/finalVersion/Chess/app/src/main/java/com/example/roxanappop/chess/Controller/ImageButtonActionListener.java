package com.example.roxanappop.chess.Controller;

import android.content.Context;
import android.view.View;

import com.example.roxanappop.chess.Model.Move;

/**
 * Created by roxanappop on 1/1/2016.
 */
public class ImageButtonActionListener implements View.OnClickListener {
    Context context;
    Controller controller;
    Move move;

    public ImageButtonActionListener(Context context, Controller controller, Move move) {
        this.context = context;
        this.controller = controller;
        this.move = move;
    }

    @Override

    public void onClick(View v) {
        int id = v.getId();

        if (move.getFrom() == null) {
            controller.selectPiece(id);

        } else {
            controller.selectPosition(id);
        }
    }


}
