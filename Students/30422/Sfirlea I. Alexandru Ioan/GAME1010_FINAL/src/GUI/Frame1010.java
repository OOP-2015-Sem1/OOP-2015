package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Calculation.Game;
import Main.Constants;

public class Frame1010 extends JFrame {
	private static final long serialVersionUID = -8573649221379461824L;
	public static boolean choseColor = Frame1010.queryBackgroundColorat();
	
	public Frame1010() {
		
		Game game = new Game();
		JFrame frame = new JFrame("1010");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Constants.G_w, Constants.G_h);
		setResizable(true);
		
		BoardPanel board = new BoardPanel();
		add(board,BorderLayout.CENTER);
	ButtonPanel buton  = new ButtonPanel();
	add(buton, BorderLayout.EAST);
	
	setVisible(true);
			
	}
	
	public static boolean queryBackgroundColorat() {
		int reply = JOptionPane.showConfirmDialog(null,
				"Sa fie colorat background?", "Optiune", JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}
}
