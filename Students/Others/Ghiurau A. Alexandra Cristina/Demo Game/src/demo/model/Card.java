package demo.model;

import javax.swing.JButton;

public class Card extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	private boolean matched = false;

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
