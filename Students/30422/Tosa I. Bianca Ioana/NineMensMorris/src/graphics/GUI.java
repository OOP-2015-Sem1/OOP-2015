package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import logic.GameLogic;
import logic.PVP;
import login.FileSerialization;

public class GUI extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel board = new JPanel();
	private MorrisIcons icons = MorrisIcons.getInstance();
	private PVP playerMovesOnBoard = new PVP();
	private GameLogic logic = GameLogic.getInstance();
	private FileSerialization serial = FileSerialization.getInstance();
	
	public GUI() {
		super("Board");
		setLayout(new BorderLayout());
		setBoard();
		instructionPanel();
		playerMovesOnBoard.playerVsPlayer();
		serial.login();
	}

	public void setBoard() {
		board.setLayout(new GridLayout(13, 13));

		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				logic.buttons[i][j] = new JButton();
				logic.buttons[i][j].setName(String.valueOf(i * 100 + j));
				logic.buttons[i][j].setBorder(BorderFactory.createEmptyBorder());
				if (i == 0 || i == 12) {
					if (j % 6 != 0) {
						logic.buttons[i][j].setIcon(icons.OL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.OL);
						logic.matrix[i][j] = -2;
					} else {
						logic.buttons[i][j].setIcon(icons.Button);
					}
				}
				if (i == 1 || i == 11) {
					if (j == 0 || j == 6 || j == 12) {
						logic.buttons[i][j].setIcon(icons.VL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.VL);
						logic.matrix[i][j] = -3;
					} else {
						logic.buttons[i][j].setEnabled(false);
						logic.matrix[i][j] = -1;
					}

				}
				if (i == 2 || i == 10) {
					if (j == 0 || j == 12) {
						logic.buttons[i][j].setIcon(icons.VL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.VL);
						logic.matrix[i][j] = -3;
					} else if (j == 1 || j == 11) {
						logic.buttons[i][j].setEnabled(false);
						logic.matrix[i][j] = -1;
					} else if (j == 3 || j == 4 || j == 5 || j == 7 || j == 8 || j == 9) {
						logic.buttons[i][j].setIcon(icons.OL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.OL);
						logic.matrix[i][j] = -2;
					} else {
						logic.buttons[i][j].setIcon(icons.Button);
					}
				}
				if (i == 3 || i == 9) {
					if (j == 0 || j == 2 || j == 6 || j == 10 || j == 12) {
						logic.buttons[i][j].setIcon(icons.VL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.VL);
						logic.matrix[i][j] = -3;
					} else {
						logic.buttons[i][j].setEnabled(false);
						logic.matrix[i][j] = -1;
					}
				}
				if (i == 4 || i == 8) {
					if (j == 0 || j == 2 || j == 10 || j == 12) {
						logic.buttons[i][j].setIcon(icons.VL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.VL);
						logic.matrix[i][j] = -3;
					} else if (j == 5 || j == 7) {
						logic.buttons[i][j].setIcon(icons.OL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.OL);
						logic.matrix[i][j] = -2;
					} else if (j == 1 || j == 3 || j == 9 || j == 11) {
						logic.buttons[i][j].setEnabled(false);
						logic.matrix[i][j] = -1;
					} else {
						logic.buttons[i][j].setIcon(icons.Button);
					}
				}
				if (i == 5 || i == 7) {
					if (j == 0 || j == 2 || j == 4 || j == 8 || j == 10 || j == 12) {
						logic.buttons[i][j].setIcon(icons.VL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.VL);
						logic.matrix[i][j] = -3;
					} else {
						logic.buttons[i][j].setEnabled(false);
						logic.matrix[i][j] = -1;
					}
				}
				if (i == 6) {
					if (j == 1 || j == 3 || j == 9 || j == 11) {
						logic.buttons[i][j].setIcon(icons.OL);
						logic.buttons[i][j].setEnabled(false);
						logic.buttons[i][j].setDisabledIcon(icons.OL);
						logic.matrix[i][j] = -2;
					} else if (j == 5 || j == 6 || j == 7) {
						logic.buttons[i][j].setEnabled(false);
						logic.matrix[i][j] = -1;
					} else {
						logic.buttons[i][j].setIcon(icons.Button);
					}
				}
				board.add(logic.buttons[i][j]);
			}
		}

		add(board,BorderLayout.CENTER);
		setVisible(true);
		setSize(840, 840);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void instructionPanel(){
		JPanel hintPanel = new JPanel();
		Font myFont = new Font("Calibri", Font.BOLD, 30);
		logic.hint.setFont(myFont);
		logic.hint.setBorder(null);
		logic.hint.setEnabled(false);
		hintPanel.add(logic.hint);
		add(hintPanel, BorderLayout.NORTH);
		hintPanel.setVisible(true);
		hintPanel.setSize(50,840);	
	}
	
}
