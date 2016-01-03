package View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class Field extends JLabel {
	private int x; // X position in game.
	private int y; // Y position in game.

	/* Constructs the label and sets x and y positions in game. */
	public Field(int x, int y) {
		super("", CENTER);
		this.x = x;
		this.y = y;

		setPreferredSize(new Dimension(40, 40));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setOpaque(true);
	}

	/* Returns x position in game. */
	public int getFieldX() {
		return x;
	}

	/* Returns Y position in game. */
	public int getFieldY() {
		return y;
	}
}
