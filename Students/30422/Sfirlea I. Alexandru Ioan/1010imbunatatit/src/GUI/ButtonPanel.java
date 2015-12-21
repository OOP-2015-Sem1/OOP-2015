package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import Calculation.Game;
import Main.Constants;

public class ButtonPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Game game = new Game();
	JButton buton;
	public static boolean isNew = true;

	public ButtonPanel() {
		HandlerClass handle = new HandlerClass();
		ImageIcon imageForRestart = new ImageIcon(("E:\\ALEX\\FACULTA\\OOP\\LABS\\1010\\src\\restart.png"));

		setPreferredSize(new Dimension(200, 800));
		setLayout(new GridLayout(3, 1));
		buton = new JButton("restart");

		buton.setIcon(imageForRestart);
		setVisible(true);
		add(buton);
		buton.addActionListener(handle);

		ImageIcon imageForScore = new ImageIcon(("E:\\ALEX\\FACULTA\\OOP\\LABS\\1010\\src\\highScore.png"));
		buton = new JButton("Show HS");
		buton.setIcon(imageForScore);
		setVisible(true);
		add(buton);
		buton.addActionListener(handle);

		ImageIcon imageForStyle = new ImageIcon(("E:\\ALEX\\FACULTA\\OOP\\LABS\\1010\\src\\images.png"));

		buton = new JButton(" ");
		buton.setBackground(Color.darkGray);
		buton.setIcon(imageForStyle);
		buton.addActionListener(handle);

		setVisible(true);

		add(buton);

		setBackground(Color.white);
		setVisible(true);
	}

	private class HandlerClass implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String actionCommand = ((JButton) event.getSource()).getActionCommand();
			
			if (actionCommand == "restart") {
				Constants.isNewGame = 0;
				BoardPanel.mouse_X = 999;
				game.newGame();
			}
			if (actionCommand == "Show HS") {
				if (Constants.highScoreOn)
					Constants.highScoreOn = false;
				else
					Constants.highScoreOn = true;
			}
			if (actionCommand == " ") {
				if (Constants.choseColor) {
					Constants.choseColor = false;
				} else
					Constants.choseColor = true;
			}
		}
	}
}
