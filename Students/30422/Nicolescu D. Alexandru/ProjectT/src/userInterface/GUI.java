package userInterface;

import java.awt.Color;
import java.awt.Font;
//import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import account.AccountRepository;
import account.LoginDialog;
import account.SingletonAccount;
import tictactoe.GameManagement;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton[] btnVec;
	private static final int DIFFICULTY = 0;
	private static final int WHO_STARTS = 1;
	private static final int HARD = 0;
	private static final int GAME_TYPE = 2;
	private static final int PVPC = 0;
	private static final int PVP = 1;

	private GameManagement c = new GameManagement();
	private Sound s = new Sound();
	private LoginDialog input = new LoginDialog();
	private AccountRepository accounts = SingletonAccount.getInstance();
	private boolean pvp = false;

	private Icon X = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/X0.png");
	private Icon O = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/O0.png");
	private Icon BACKGROUND = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/background0.png");
	private Icon X1 = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/Xbefore0.png");
	private Icon O1 = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/Obefore0.png");
	private Icon aux;
	private JPanel a = new JPanel();
	private JPanel b = new JPanel();

	public GUI() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("TicTacToe");
		setSize(800, 400);
		setLayout(new GridLayout(1, 2));
		add(a);
		a.setSize(400, 400);
		a.setLayout(new GridLayout(3, 3));

		input.Input();

		add(b);
		JButton score = new JButton();
		b.setLayout(new GridBagLayout());
		//GridBagConstraints c = new GridBagConstraints();
		b.add(score);
		System.out.println(accounts.getAccountNr());
		score.setEnabled(false);
		Font font = new Font("Arial", Font.BOLD, 32);
		score.setFont(font);
		score.setBorder(null);
		score.setText("Your score: " + Integer.toString(accounts.getAccountScore()));

		btnVec = new JButton[9];

		for (int i = 0; i < 9; i++) {

			btnVec[i] = new JButton();
			btnVec[i].setName(String.valueOf(i));
			a.add(btnVec[i]);
			btnVec[i].setIcon(BACKGROUND);
			btnVec[i].setRolloverEnabled(true);
			btnVec[i].setRolloverIcon(X1);
			btnVec[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		}

		setLocationRelativeTo(null);
	}

	public void startGame() {

		int playerTurn = 0;
		final int gameType = playerChoose(GAME_TYPE);
		final int difficulty = playerChoose(DIFFICULTY);
		if (difficulty == HARD)
			s.SoundIt(3);
		playerTurn = playerChoose(WHO_STARTS);
		a.setVisible(true);
		b.setVisible(true);
		setVisible(true);
		if ((playerTurn == 1) && (gameType == PVPC))
			PcTime(10, difficulty);

		aux = X;
		for (int i = 0; i < 9; i++) {

			btnVec[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					JButton source = (JButton) e.getSource();
					source.setIcon(aux);
					source.setEnabled(false);
					source.setDisabledIcon(aux);
					// --------------------PVP------------------------
					if (gameType == PVP) {
						if (aux == X) {
							aux = O;
							for (int j = 0; j < 9; j++)
								btnVec[j].setRolloverIcon(O1);
						} else {
							aux = X;
							for (int j = 0; j < 9; j++)
								btnVec[j].setRolloverIcon(X1);
						}

						PcTime(Integer.parseInt(source.getName()), 3);
					}

					// --------------------PVPC------------------------
					if (gameType == PVPC) {
						PcTime(Integer.parseInt(source.getName()), difficulty);
					}

				}
			});
		}

	}

	public int playerChoose(int choice) {

		if (choice == GAME_TYPE) {
			Object[] gameType = { "Play vs computer", "Play vs friend" };
			final int type = JOptionPane.showOptionDialog(null, "Choose the game type", "TicTacToe",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameType, gameType[1]);
			if (type == 1)
				pvp = true;
			if (type == -1)
				System.exit(0);
			return type;

		}
		if (!pvp) {
			if (choice == DIFFICULTY) {
				Object[] difficultyOption = { "Psychotic", "Normal", "What is Tic Tac Toe?" };
				final int difficulty = JOptionPane.showOptionDialog(null, "Choose your destiny", "TicTacToe",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficultyOption,
						difficultyOption[2]);
				if (difficulty == -1)
					System.exit(0);
				return difficulty;
			}
			if (choice == WHO_STARTS) {
				Object[] difficultyOption = { "Player starts", "PC starts", "Randomly decide" };
				final int starter = JOptionPane.showOptionDialog(null, "Choose who starts", "TicTacToe",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficultyOption,
						difficultyOption[2]);
				if (starter == -1)
					System.exit(0);
				if (starter == 2) {
					int randomVariable = (int) (Math.random() * 2);
					return randomVariable;
				}
				return starter;

			}
		}
		return -1;
	}

	public void PcTime(int playerLastMove, int Difficulty) {

		int PCMove = c.makeMove(playerLastMove, Difficulty);

		if (PCMove != -1) {
			btnVec[PCMove].setIcon(O);
			btnVec[PCMove].setEnabled(false);
			btnVec[PCMove].setDisabledIcon(O);
		}

		c.checkWin();

	}

}
