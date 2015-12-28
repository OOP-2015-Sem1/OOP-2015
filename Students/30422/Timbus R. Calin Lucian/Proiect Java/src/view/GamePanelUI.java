package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Answerable;
import model.IdentityMatrix;
import model.SudokuMatrixGenerator;
import model.TimerClass;
import model.Verification;

public class GamePanelUI extends JFrame implements ActionListener, Answerable {
	private static final int BOARD_SIZE = 1250;
	private static final long serialVersionUID = 8853494294640988939L;
	private JPanel gamePanel;
	private int[][] SudokuGameMatrix;
	private int[][] SudokuPatternMatrix;
	private SudokuMatrixGenerator generator;

	public GamePanelUI() {
		super("The game Frame");
		gamePanel = new JPanel();
		generator = new SudokuMatrixGenerator();
		SudokuGameMatrix = generator.returnSudokuMatrix();
		SudokuPatternMatrix = new int[9][9];
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				SudokuPatternMatrix[i][j] = SudokuGameMatrix[i][j];
			}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(SudokuGameMatrix[i][j] + " ");
			}
			System.out.println(" ");
		}
		System.out.println("");
		gamePanel.setLayout(new GridLayout(9, 9));
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				JButton gameButton = new SudokuButton(String.valueOf(SudokuGameMatrix[i][j]), i, j);
				if (gameButton.getText().equals(" ")) {
					SudokuPatternMatrix[i][j] = 0;
				}
				gameButton.addActionListener(this);
				gamePanel.add(gameButton);
			}
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(SudokuPatternMatrix[i][j] + " ");
			}
			System.out.println(" ");
		}

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		JMenu options = new JMenu("Options");
		menuBar.add(options);
		JMenuItem timer = new JMenuItem("Timer");
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new TimerClass();
			}
		});
		options.add(timer);
		this.add(gamePanel);
		this.setSize(BOARD_SIZE, BOARD_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new GamePanelUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		SudokuButton but = (SudokuButton) e.getSource();
		if (but.getActionCommand().equals("Alterable")) {
			int r = but.returnX();
			int c = but.returnY();
			gamePanel.removeAll();
			for (int i = 0; i < 9; i++)
				for (int j = 0; j < 9; j++) {
					if ((i == r) && (c == j)) {
						gamePanel.add(new AnswerPanel(this, i, j));
					} else {
						JButton gameButton = new SudokuButton(String.valueOf(SudokuPatternMatrix[i][j]), i, j);
						gameButton.addActionListener(this);
						gameButton.setText(String.valueOf(SudokuPatternMatrix[i][j]));
						if (SudokuPatternMatrix[i][j] == 0) {
							gameButton.setText(" ");
						}
						gamePanel.add(gameButton);
					}
				}
			gamePanel.revalidate();
			gamePanel.repaint();
			gamePanel.setFocusable(true);
		}
	}

	@Override
	public void setAnswer(int answer, int row, int column) {
		IdentityMatrix identityMatrix = new IdentityMatrix();
		if (identityMatrix.checkIfIdentical(SudokuPatternMatrix, SudokuGameMatrix)) {
			System.out.println("tralala");
			JOptionPane.showMessageDialog(this, "Congratulations, you won");
		}
		System.out.println(answer);
		Verification verify = new Verification();
		boolean result = verify.ValidateInput(this, answer, row, column, SudokuPatternMatrix);
		if (result == true) {
			System.out.println("Da ba, ii okay");
		} else {
			System.out.println("Ba, nu-i okay");
		}
		SudokuPatternMatrix[row][column] = answer;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(SudokuGameMatrix[i][j] + " ");
			}
			System.out.println(" ");
		}
		System.out.println("");
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				System.out.print(SudokuPatternMatrix[i][j] + " ");
			}
			System.out.println(" ");
		}
	}
}