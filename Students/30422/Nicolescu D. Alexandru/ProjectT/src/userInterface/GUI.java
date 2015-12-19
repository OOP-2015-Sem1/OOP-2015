package userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import tictactoe.GameManagement;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton[] btnVec;
	private static final int HARD = 0;
	private static final int PC = 1;
	private static final int PLACE_FIRST_MOVE = 10;
	private static final int TERMINATOR_SOUND = 3;
	private GameManagement game = null;
	private boolean pvp = false;
	private AccountRepository accounts = AccountRepository.getInstance();
	private Icon X = new ImageIcon("icons/X0.png");
	private Icon X1 = new ImageIcon("icons/Xbefore0.png");
	private Icon O1 = new ImageIcon("icons/Obefore0.png");
	private Icon O = new ImageIcon("icons/O0.png");
	private Icon aux;

	private JPanel tictactoePanel = new JPanel();
	private JPanel scorePanel = new JPanel();

	public GUI() {

		createFrame();
		inputAccountInformation();
		createScorePanel();
		createTTTPanel();

		setLocationRelativeTo(null);

		gameTypeChoice();
		final int difficulty = difficultyChoice();
		game = new GameManagement(difficulty);
		if (difficulty == HARD) {
			Sound s = new Sound();
			s.SoundIt(TERMINATOR_SOUND);
		}
		int playerTurn = firstTurnChoice();
		setVisible(true);
		if ((playerTurn == PC) && (!pvp))
			recordMoveInMatrix(PLACE_FIRST_MOVE);
		aux = X;
		for (int i = 0; i < 9; i++) {

			btnVec[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					JButton source = (JButton) e.getSource();
					source.setIcon(aux);
					source.setEnabled(false);
					source.setDisabledIcon(aux);
					// --------------------PVP------------------------
					if (pvp) {
						managePVPGame(source);
					}
					// --------------------PVPC------------------------
					else {
						recordMoveInMatrix(Integer.parseInt(source.getName()));
					}

				}

			});
		}

	}

	private void inputAccountInformation() {
		LoginDialog input = new LoginDialog();
		input.inputAccountInformation();
		
	}

	private void managePVPGame(JButton source) {
		if (aux == X) {
			aux = O;
			for (int j = 0; j < 9; j++)
				btnVec[j].setRolloverIcon(O1);
		} else {
			aux = X;
			for (int j = 0; j < 9; j++)
				btnVec[j].setRolloverIcon(X1);
		}

		recordMoveInMatrix(Integer.parseInt(source.getName()));
	}

	private void gameTypeChoice() {

		String[] gameType = { "Play vs computer", "Play vs friend" };
		final int type = JOptionPane.showOptionDialog(null, "Choose the game type", "TicTacToe",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, gameType, gameType[1]);
		if (type == 1)
			pvp = true;
		if (type == -1)
			System.exit(0);
	}

	private int difficultyChoice() {

		if (!pvp) {
			String[] difficultyOption = { "Psychotic", "Normal", "What is Tic Tac Toe?" };
			final int difficulty = JOptionPane.showOptionDialog(null, "Choose your destiny", "TicTacToe",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, difficultyOption,
					difficultyOption[2]);
			if (difficulty == -1)
				System.exit(0);
			return difficulty;

		}
		return -1;
	}

	private int firstTurnChoice() {

		if (!pvp) {
			String[] difficultyOption = { "Player starts", "PC starts", "Randomly decide" };
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
		return -1;

	}

	public void recordMoveInMatrix(int playerLastMove) {

		int computerResponse = game.manageLastPlayerMove(playerLastMove);

		if (computerResponse != -1) {
			btnVec[computerResponse].setIcon(O);
			btnVec[computerResponse].setEnabled(false);
			btnVec[computerResponse].setDisabledIcon(O);
		}

		game.checkWin();

	}

	private void createFrame() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("TicTacToe");
		setSize(800, 400);
		setLayout(new GridLayout(1, 2));
		add(tictactoePanel);
	}

	private void createTTTPanel() {
		tictactoePanel.setSize(400, 400);
		tictactoePanel.setLayout(new GridLayout(3, 3));
		btnVec = new JButton[9];
		Icon background = new ImageIcon("icons/background0.png");
		for (int i = 0; i < 9; i++) {

			btnVec[i] = new JButton();
			btnVec[i].setName(String.valueOf(i));
			tictactoePanel.add(btnVec[i]);
			btnVec[i].setIcon(background);
			btnVec[i].setRolloverEnabled(true);
			btnVec[i].setRolloverIcon(X1);
			btnVec[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		}
	}

	private void createScorePanel() {
		add(scorePanel);
		JButton score = new JButton();
		JButton name = new JButton();
		JButton see = new JButton();
		scorePanel.setLayout(new BorderLayout());
		scorePanel.add(name, BorderLayout.NORTH);
		scorePanel.add(score, BorderLayout.SOUTH);
		scorePanel.add(see, BorderLayout.CENTER);
		score.setEnabled(false);
		name.setEnabled(false);
		Font font = new Font("Arial", Font.BOLD, 32);
		score.setFont(font);
		name.setFont(font);
		see.setFont(font);
		score.setBorder(null);
		name.setBorder(null);
		see.setBorder(null);
		see.setText("See scores!");
		name.setText("Welcome, " + accounts.getAccountName(accounts.getAccountNr()));
		score.setText("Your score: " + Integer.toString(accounts.getAccountScore(accounts.getAccountNr())));
		see.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				for (int i = 1; i < AccountRepository.NUMBER_OF_ACCOUNTS; i++)
					System.out.println(accounts.getAccountName(i) + " - " + accounts.getAccountScore(i));
			}

		});

	}

}
