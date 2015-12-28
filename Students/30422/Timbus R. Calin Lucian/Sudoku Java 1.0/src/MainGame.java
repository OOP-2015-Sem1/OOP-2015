import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainGame extends JFrame {
	private static final int SQUARE_SIZE = 30;
	private static JPanel jpl;
	private static JButton[][] matrixButton;
	private static int sudokuSolutionGameMatrix[][] = new int[9][9];
	private static int sudokuPatternGameMatrix[][] = new int[9][9];
	private static Sudoku s = new Sudoku();
	private static Sudoku2 s2 = new Sudoku2();
	private static int totalCount = 0;

	public MainGame() {
		super("Main frame");
		createGameFrame();
		this.add(jpl);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}

	protected static void createGameFrame() {

		/// Creating a button class in order to know the coordinates of the
		/// clicked
		/// button///
		class MyButton extends JButton implements ActionListener {

			int x, y;
			String text;

			MyButton(String text, int x, int y) {
				this.x = x;
				this.y = y;
				addActionListener(this);
			}

			// Checking if the answer is te correct one

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(this.x + " " + this.y + " " + this.getText());
				String s = this.getText();
				if (s.equals(" ") == true) {
					boolean inSquare = false;
					boolean onRow = false;
					boolean onColumn = false;
					String number = JOptionPane.showInputDialog(this.getParent(), "Enter a number", "Aaa",
							JOptionPane.OK_OPTION);
					boolean isDigit = false;
					ArrayList<Integer> answers = new ArrayList<Integer>();
					for (int i = 0; i < 10; i++) {
						answers.add(i);
					}
					Integer nb = new Integer(number);
					for (Integer value : answers) {
						if (value.equals(nb)) {
							isDigit = true;
						}
					}
					if (isDigit == false) {
						JOptionPane.showMessageDialog(this.getParent(), "Please enter a digit not a word");
					} else {
						int userInput = Integer.parseInt(number);
						// Counting squares as: first line ->>column 1,2,3 etc//

						// First line //
						// First square //
						if ((this.x == 0 || this.x == 1 || this.x == 2)
								&& (this.y == 0 || this.y == 1 || this.y == 2)) {
							for (int i = 0; i < 3; i++)
								for (int j = 0; j < 3; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}

						// Second square//
						if ((this.x == 0 || this.x == 1 || this.x == 2)
								&& (this.y == 3 || this.y == 4 || this.y == 5)) {
							for (int i = 0; i < 3; i++)
								for (int j = 3; j < 6; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}

						// Third square //
						if ((this.x == 0 || this.x == 1 || this.x == 2)
								&& (this.y == 6 || this.y == 7 || this.y == 8)) {
							for (int i = 0; i < 3; i++)
								for (int j = 6; j < 9; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}

						// Second line //
						// First Square //
						if ((this.x == 3 || this.x == 4 || this.x == 5)
								&& (this.y == 0 || this.y == 1 || this.y == 2)) {
							for (int i = 3; i < 6; i++)
								for (int j = 0; j < 3; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}
						// Second Square //
						if ((this.x == 3 || this.x == 4 || this.x == 5)
								&& (this.y == 3 || this.y == 4 || this.y == 5)) {
							for (int i = 3; i < 6; i++)
								for (int j = 3; j < 6; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}
						// Third Square //
						if ((this.x == 3 || this.x == 4 || this.x == 5)
								&& (this.y == 6 || this.y == 7 || this.y == 8)) {
							for (int i = 3; i < 6; i++)
								for (int j = 6; j < 9; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}

						// Third line //
						// First square //
						if ((this.x == 6 || this.x == 7 || this.x == 8)
								&& (this.y == 0 || this.y == 1 || this.y == 2)) {
							for (int i = 6; i < 9; i++)
								for (int j = 0; j < 3; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}

						// Second square //
						if ((this.x == 6 || this.x == 7 || this.x == 8)
								&& (this.y == 3 || this.y == 4 || this.y == 5)) {
							for (int i = 6; i < 9; i++)
								for (int j = 3; j < 6; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}

						// Last square //
						if ((this.x == 6 || this.x == 7 || this.x == 8)
								&& (this.y == 3 || this.y == 4 || this.y == 5)) {
							for (int i = 6; i < 9; i++)
								for (int j = 6; j < 9; j++) {
									if (userInput == sudokuPatternGameMatrix[i][j]) {
										inSquare = true;
										break;
									}
								}
						}
						int row = this.x;
						int column = this.y;
						for (int i = 0; i < 9; i++) {
							if (sudokuPatternGameMatrix[row][i] == userInput) {
								onColumn = true;
								break;
							}
						}
						for (int j = 0; j < 9; j++) {
							if (sudokuPatternGameMatrix[j][column] == userInput) {
								onRow = true;
								break;
							}
						}
						if (onColumn || onRow || inSquare) {
							JOptionPane.showMessageDialog(this.getParent(),
									"Incorrect answer; please insert a correct digit");
						} else {
							this.setText(String.valueOf(userInput));
							this.setForeground(Color.black);
							sudokuPatternGameMatrix[this.x][this.y] = userInput;
							if (totalCount == 81) {
								JOptionPane.showMessageDialog(this.getParent(), "Congrats you finished the game!");
							} else {
								totalCount++;
							}
						}

					}
				}
			}

		}

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		matrixButton = new JButton[9][9];
		Random r = new Random();
		int gen = r.nextInt(2);
		switch (gen) {
		case 0:
			sudokuSolutionGameMatrix = s.sudokuMatrix;
		case 1:
			sudokuSolutionGameMatrix = s2.sudokuMatrix;
		}

		jpl = new JPanel();
		jpl.setLayout(gbl);
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)

			{
				JButton myButton = new MyButton(String.valueOf(sudokuSolutionGameMatrix[i][j]), i, j);
				myButton.setText(String.valueOf(sudokuSolutionGameMatrix[i][j]));
				myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				matrixButton[i][j] = myButton;

				/// Checking whether or not we want to hide the value of the
				/// button according to a pattern///
				if (j == 0 || j == 8) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
					sudokuPatternGameMatrix[i][j] = 0;
				} else if ((j == 1 || j == 7) && (i == 0 || i == 2 || i == 3 || i == 5 || i == 6 || i == 8)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
					sudokuPatternGameMatrix[i][j] = 0;
				} else if ((j == 2 || j == 6) && (i == 0 || i == 1 || i == 4 || i == 7 || i == 8)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
					sudokuPatternGameMatrix[i][j] = 0;
				} else if ((j == 3 || j == 5) && (i == 2 || i == 4 || i == 6)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
					sudokuPatternGameMatrix[i][j] = 0;
				} else if ((j == 4) && (i == 0 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 8)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
					sudokuPatternGameMatrix[i][j] = 0;
				} else {
					myButton.setText(String.valueOf(sudokuSolutionGameMatrix[i][j]));
					sudokuPatternGameMatrix[i][j] = (sudokuSolutionGameMatrix[i][j]);
					totalCount++;
				}

				setBorder(myButton);
				gbc.gridx = j;
				gbc.gridy = i;
				gbc.weightx = 0.5;
				gbc.weighty = 0.5;
				gbc.ipadx = 40;
				gbc.ipady = 40;
				jpl.add(myButton, gbc);
			}
	}

	protected static void setBorder(JButton btn) {
		btn.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, false));
	}

	public static void main(String[] args) {
		new MainGame();
	}
}