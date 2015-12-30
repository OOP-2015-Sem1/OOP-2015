package project.memorygame.models;

/**
 * Class that provides a model for a single button
 */
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Card extends JButton {
	private int id;
	private boolean matched = false;
	private boolean flipped = false;

	/**
	 * Method that flips the hard so that you can see the front of the icon with
	 * the item you want to match
	 */
	public void flip() {
		if (flipped == true) {
			if (matched != true) {
				flipped = false;
				this.setIcon(new ImageIcon(this.getClass().getResource("/ironthrone.jpg")));
				// to remote the spacing between the image and button's borders
				this.setMargin(new Insets(0, 0, 0, 0));
				// to remove the border
				this.setBorder(null);
			}
		} else {
			flipped = true;
			this.setIcon(
					new ImageIcon(this.getClass().getResource("/"+Integer.toString(id) + ".jpg")));
			// to remote the spacing between the image and button's borders
			this.setMargin(new Insets(0, 0, 0, 0));
			// to remove the border
			this.setBorder(null);
		}
	}

	/**
	 * Constructor
	 */
	public Card() {
		this.setIcon(new ImageIcon(this.getClass().getResource("/ironthrone.jpg")));
		// to remote the spacing between the image and button's borders
		this.setMargin(new Insets(0, 0, 0, 0));
		// to remove the border
		this.setBorder(null);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setMatched(boolean matched) {
		this.matched = matched;
	}

	public boolean getMatched() {
		return this.matched;
	}
}