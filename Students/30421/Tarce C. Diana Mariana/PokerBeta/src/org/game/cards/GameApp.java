package org.game.cards;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GameApp {
    public static void main(String[] args) {        
        Game game = new Game();
        game.dealCards();
        game.showCards();
        JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setSize(650,700);
        window.setTitle("$// Video Poker //$");
        window.validate();
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        int n = JOptionPane.showConfirmDialog(null,"Would you like to load a previous game?","Load Game", JOptionPane.YES_NO_OPTION);
        String x = (String)JOptionPane.showInputDialog(null, "Enter your name.", "Name", JOptionPane.INFORMATION_MESSAGE, null, null, null);
        if(x == null || x.equals("")) x = "Player";
        if(n == 0) {
        	try {window.setName(x);}
        	catch(java.util.NoSuchElementException e) {
        		window.setName(x);
        	}
        }
        else {
        	window.setName(x);
        }
    }
}




     