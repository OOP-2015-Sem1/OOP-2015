import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private static int sudokuGameMatrix[][] = new int[9][9];
	private static Sudoku s = new Sudoku();

	public MainGame() {
		super("Main frame");
		createGameFrame();
		this.add(jpl);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		pack();
	}

	protected static void createGameFrame() {

		class MyButton extends JButton implements ActionListener {

			int x, y;

			MyButton(String text, int x, int y) {
				super(text);
				this.x = x;
				this.y = y;
				addActionListener(this);
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(this.x + " " + this.y);
			}

		}

		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		matrixButton = new JButton[9][9];
		sudokuGameMatrix = s.sudokuMatrix;
		jpl = new JPanel();
		jpl.setLayout(gbl);
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++) {
				JButton myButton = new MyButton(String.valueOf(sudokuGameMatrix[i][j]), i, j);
				myButton.setText(String.valueOf(sudokuGameMatrix[i][j]));
				myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				matrixButton[i][j] = myButton;

				/// Checking whether or not we want to hide the value of the
				/// button according to a pattern///
				if (j == 0 || j == 8) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				} else if ((j == 1 || j == 7) && (i == 0 || i == 2 || i == 3 || i == 5 || i == 6 || i == 8)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				} else if ((j == 2 || j == 6) && (i == 0 || i == 1 || i == 4 || i == 7 || i == 8)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				} else if ((j == 3 || j == 5) && (i == 2 || i == 4 || i == 6)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				} else if ((j == 4) && (i == 0 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 8)) {
					myButton.setText(" ");
					myButton.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
				} else {
					myButton.setText(String.valueOf(sudokuGameMatrix[i][j]));
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

	/// Creating a button class in order to know the coordinates of the clicked
	/// button///
	class MyButton extends JButton implements ActionListener {

		int x, y;
		String text;
		
		MyButton(String text, int x, int y) {
			this.text = text;
			this.x = x;
			this.y = y;
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(this.x + " " + this.y + " " +this.getText());
		
		}
		public String getText(){
			return text;
		}

	}

	protected static void setBorder(JButton btn) {
		btn.setBorder(BorderFactory.createLineBorder(Color.darkGray, 1, false));
	}

	public static void main(String[] args) {
		new MainGame();
	}
}