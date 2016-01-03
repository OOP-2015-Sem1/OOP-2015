package anoyingame.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JTextArea;

public class RandomRectangle extends JPanel {
	private JTextArea text;
	private int textColorRand;
	private int backColorRand;
	private int textRand;
	private JPanel emptyWhitePanel;

	public static int getRandomInt(int previousRandom) {
		Random rand = new Random();
		int randomNum = rand.nextInt(6);
		while (randomNum == previousRandom) // avoiding text & background having
											// the same color
											// we call it with -1 when we don't
											// need to check that
			randomNum = rand.nextInt(6);
		return randomNum;
	}

	private Color[] colorArray = new Color[] { Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN, Color.ORANGE,
			Color.MAGENTA };
	private String[] textArray = new String[] { "\n\n\n                 RED", "\n\n\n                BLUE",
			"\n\n\n              YELLOW", "\n\n\n               GREEN", "\n\n\n              ORANGE",
			"\n\n\n                PINK" };

	public RandomRectangle() {

		this.setLayout(new GridLayout(3, 3));
		text = new JTextArea();
		this.setRandomText();
		text.setEditable(false);

		for (int i = 1; i <= 9; i++) {
			if (i == 5) {
				this.add(text);
			} else {
				emptyWhitePanel = new JPanel();
				emptyWhitePanel.setBackground(Color.WHITE);
				this.add(emptyWhitePanel);
			}
		}
	}

	public void setRandomText() {
		textRand = getRandomInt(-1);
		textColorRand = getRandomInt(-1);
		backColorRand = getRandomInt(textColorRand);
		text.setText(textArray[textRand]);
		text.setForeground(colorArray[textColorRand]);
		text.setBackground(colorArray[backColorRand]);
		text.setFont(text.getFont().deriveFont(Font.BOLD));
		text.setAlignmentX(CENTER_ALIGNMENT);
		text.setAlignmentY(CENTER_ALIGNMENT);
	}

	public String getWrittenText() {
		String trimmedText = textArray[textRand].trim().replaceAll("\n ", "");
		trimmedText = trimmedText.trim().replaceAll(" ", "");
		System.out.println(trimmedText);
		return trimmedText;
	}

	public String getColor(int randColor) {
		String result;
		switch (randColor) {
		case 0:
			result = "RED";
			break;
		case 1:
			result = "BLUE";
			break;
		case 2:
			result = "YELLOW";
			break;
		case 3:
			result = "GREEN";
			break;
		case 4:
			result = "ORANGE";
			break;
		case 5:
			result = "PINK";
			break;
		default:
			result = null;
		}
		return result;
	}

	public String getRectangleColor() {
		System.out.println("back color: " + backColorRand);
		return getColor(backColorRand);
	}

	public String getTextColor() {
		System.out.println("text color:" + textColorRand);
		return getColor(textColorRand);
	}

}