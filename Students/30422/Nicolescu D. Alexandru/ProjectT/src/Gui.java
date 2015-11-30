
import java.awt.Color;
import java.awt.FlowLayout;
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

public class Gui extends JFrame {

	
	private static final long serialVersionUID = 1L;
	private JButton[] btnVec;
	private static final int DIFFICULTY = 0;
	private static final int WHO_STARTS = 1;
	private static final int HARD = 0;
	private static final int GAME_TYPE = 2;
	private static final int PVPC = 0;
	private static final int PVP = 1;

	AI c = new AI();
	Sound s = new Sound();
	LoginDialog input = new LoginDialog();
	AccountRepository accounts=SingletonAccount.getInstance();
	boolean pvp = false;

	Icon X = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/X0.png");
	Icon O = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/O0.png");
	Icon BACKGROUND = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/background0.png");
	Icon X1 = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/Xbefore0.png");
	Icon O1 = new ImageIcon("C:/Users/Andi/workspace/ProjectT/src/Icons/Obefore0.png");
	Icon Aux;
	JPanel a = new JPanel();
	JPanel b = new JPanel();

	public Gui() {

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("TicTacToe");
		setSize(800, 400);
		setLayout(new GridLayout(1,2));
		add(a);
		a.setSize(400, 400);
		a.setLayout(new GridLayout(3, 3));
		
		input.Input();
		
		add(b);
		JButton score=new JButton();
		b.setLayout(new FlowLayout());
		b.add(score);
		System.out.println(accounts.getAccountNr());
		score.setText(Integer.toString(accounts.getAccountScore()));
		
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

	public void GG() {

		int playerTurn = 0;
		final int gameType = PlayerChoose(GAME_TYPE);
		final int Difficulty = PlayerChoose(DIFFICULTY);
		if (Difficulty == HARD) {
			// playerTurn = PC;
			s.SoundIt(3);
		}
		playerTurn = PlayerChoose(WHO_STARTS);
		a.setVisible(true);
		b.setVisible(true);
		setVisible(true);
		if ((playerTurn == 1) && (gameType == PVPC))
			PcTime(10, Difficulty);

		Aux = X;
		for (int i = 0; i < 9; i++) {

			btnVec[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {

					JButton source = (JButton) e.getSource();
					source.setIcon(Aux);
					source.setEnabled(false);
					source.setDisabledIcon(Aux);
					// --------------------PVP------------------------
					if (gameType == PVP) {
						if (Aux == X) {
							Aux = O;
							for (int j = 0; j < 9; j++)
								btnVec[j].setRolloverIcon(O1);
						} else {
							Aux = X;
							for (int j = 0; j < 9; j++)
								btnVec[j].setRolloverIcon(X1);
						}

						PcTime(Integer.parseInt(source.getName()), 3);
					}

					// --------------------PVPC------------------------
					if (gameType == PVPC) {
						PcTime(Integer.parseInt(source.getName()), Difficulty);
					}

				}
			});
		}

	}

	public int PlayerChoose(int choice) {

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

		int PCMove = c.MakeMove(playerLastMove, Difficulty);

		if (PCMove != -1) {
			btnVec[PCMove].setIcon(O);
			btnVec[PCMove].setEnabled(false);
			btnVec[PCMove].setDisabledIcon(O);
		}

		c.CheckWin();

	}

}
