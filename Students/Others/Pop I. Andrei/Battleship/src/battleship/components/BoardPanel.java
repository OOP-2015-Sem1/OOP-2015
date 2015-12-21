package battleship.components;

import java.awt.*;
import javax.swing.*;

public class BoardPanel extends JPanel{
	
	private final int MAX_ROW, MAX_COL;
	
	public BoardPanel(int MAX_ROW, int MAX_COL) {
		
		this.MAX_ROW = MAX_ROW;
		this.MAX_COL = MAX_COL;
		setLayout(new GridLayout(this.MAX_ROW, this.MAX_COL));
		setSize(600, 600);
		setVisible(true);
	}

}
