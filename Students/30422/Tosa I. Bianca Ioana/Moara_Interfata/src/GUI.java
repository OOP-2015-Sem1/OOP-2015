import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame {

	private JPanel board = new JPanel();
	private MorrisIcons icons = MorrisIcons.getInstance();
	PVP playerMovesOnBoard = new PVP();
	private GameLogic logic = GameLogic.getInstance();

	public GUI() {
		super("Board");
		setBoard();
		playerMovesOnBoard.playerVsPlayer();
	}

	public void setBoard() {
		board.setLayout(new GridLayout(13, 13));

		for (int i = 0; i < 13; i++) {
			for (int j = 0; j < 13; j++) {
				logic.b[i][j] = new JButton();
				logic.b[i][j].setName(String.valueOf(i * 100 + j));
				logic.b[i][j].setBorder(BorderFactory.createEmptyBorder());
				if (i == 0 || i == 12) {
					if (j % 6 != 0) {
						logic.b[i][j].setIcon(icons.OL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.OL);
						logic.mat[i][j] = -2;
					} else {
						logic.b[i][j].setIcon(icons.Button);
					}
				}
				if (i == 1 || i == 11) {
					if (j == 0 || j == 6 || j == 12) {
						logic.b[i][j].setIcon(icons.VL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.VL);
						logic.mat[i][j] = -3;
					} else {
						logic.b[i][j].setEnabled(false);
						logic.mat[i][j] = -1;
					}

				}

				if (i == 2 || i == 10) {
					if (j == 0 || j == 12) {
						logic.b[i][j].setIcon(icons.VL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.VL);
						logic.mat[i][j] = -3;
					} else if (j == 1 || j == 11) {
						logic.b[i][j].setEnabled(false);
						logic.mat[i][j] = -1;
					} else if (j == 3 || j == 4 || j == 5 || j == 7 || j == 8 || j == 9) {
						logic.b[i][j].setIcon(icons.OL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.OL);
						logic.mat[i][j] = -2;
					} else {
						logic.b[i][j].setIcon(icons.Button);
					}
				}

				if (i == 3 || i == 9) {
					if (j == 0 || j == 2 || j == 6 || j == 10 || j == 12) {
						logic.b[i][j].setIcon(icons.VL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.VL);
						logic.mat[i][j] = -3;
					} else {
						logic.b[i][j].setEnabled(false);
						logic.mat[i][j] = -1;
					}
				}

				if (i == 4 || i == 8) {
					if (j == 0 || j == 2 || j == 10 || j == 12) {
						logic.b[i][j].setIcon(icons.VL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.VL);
						logic.mat[i][j] = -3;
					} else if (j == 5 || j == 7) {
						logic.b[i][j].setIcon(icons.OL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.OL);
						logic.mat[i][j] = -2;
					} else if (j == 1 || j == 3 || j == 9 || j == 11) {
						logic.b[i][j].setEnabled(false);
						logic.mat[i][j] = -1;
					} else {
						logic.b[i][j].setIcon(icons.Button);
					}
				}

				if (i == 5 || i == 7) {
					if (j == 0 || j == 2 || j == 4 || j == 8 || j == 10 || j == 12) {
						logic.b[i][j].setIcon(icons.VL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.VL);
						logic.mat[i][j] = -3;
					} else {
						logic.b[i][j].setEnabled(false);
						logic.mat[i][j] = -1;
					}
				}

				if (i == 6) {
					if (j == 1 || j == 3 || j == 9 || j == 11) {
						logic.b[i][j].setIcon(icons.OL);
						logic.b[i][j].setEnabled(false);
						logic.b[i][j].setDisabledIcon(icons.OL);
						logic.mat[i][j] = -2;
					} else if (j == 5 || j == 6 || j == 7) {
						logic.b[i][j].setEnabled(false);
						logic.mat[i][j] = -1;
					} else {
						logic.b[i][j].setIcon(icons.Button);
					}
				}

				board.add(logic.b[i][j]);

			}
		}

		add(board);
		setVisible(true);
		setSize(840, 840);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	

	

	// public void setPC(int PlayerLastMove) {
	// int PCMove = g.MakeMove(PlayerLastMove);
	//
	// int i = PCMove / 100;
	// int j = PCMove % 100;
	// Icon B = new ImageIcon(getClass().getResource("Black.png"));
	// logic.b[i][j].setIcon(B);
	// logic.b[i][j].setEnabled(false);
	// logic.b[i][j].setDisabledIcon(B);
	// }

}
