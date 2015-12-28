package view;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GamePanelUI extends JFrame {
	public GamePanelUI() {
		super("The game Frame");
		this.setLayout(new GridLayout(9, 9));
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				SudokuButton gameButton = new SudokuButton("trala",i,j);
				this.add(gameButton);
			}
		this.setVisible(true);
		this.setSize(800, 800);
		this.pack();
	}

	public static void main(String[] args) {
		new GamePanelUI();
	}
}
