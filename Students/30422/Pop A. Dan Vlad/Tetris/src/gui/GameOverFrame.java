package gui;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.Main;

public class GameOverFrame {
	private JFrame jframe;
	private JPanel mainPanel;
	private JTextField finalScore;
	private JTextField username;

	public GameOverFrame() {
		Game.jframe.setVisible(false);
		jframe = new JFrame();
		jframe.setSize(250, 100);
		jframe.setTitle("GAMEOVER");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		username = new JTextField(5);
		username.setText(Login.username);
		finalScore = new JTextField(4);
		finalScore.setText("" + Main.score);
		mainPanel.add(username);
		mainPanel.add(finalScore);
		jframe.add(mainPanel);
		jframe.setVisible(true);
	}
}