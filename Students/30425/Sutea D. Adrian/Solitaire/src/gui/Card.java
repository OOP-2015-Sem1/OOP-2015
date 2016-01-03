package gui;

import javax.swing.*;

public class Card {
	private String type;
	private int value;
	private ImageIcon icon;
	public JButton button = new JButton(icon);

	public void setValue(int x) {
		this.value = x;
	}

	public int getValue() {
		return value;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public JButton getButton() {
		return button;
	}

}
