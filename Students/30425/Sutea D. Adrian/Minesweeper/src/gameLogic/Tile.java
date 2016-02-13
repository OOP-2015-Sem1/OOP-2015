package gameLogic;

import javax.swing.*;

public class Tile {
	private ImageIcon icon;
	private int type;

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public ImageIcon getIcon() {
		return icon;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
