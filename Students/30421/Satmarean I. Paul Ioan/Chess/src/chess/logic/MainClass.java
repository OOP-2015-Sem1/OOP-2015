package chess.logic;

import chess.ui.Menu;

import javax.swing.*;

/**
 * Created by Paul on 12.11.2015.
 */
public class MainClass {

    public static void main(String[] args) {
        Menu gameMenu = new Menu();
        gameMenu.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        gameMenu.setSize(300,200);
        gameMenu.setVisible(true);
    }
}