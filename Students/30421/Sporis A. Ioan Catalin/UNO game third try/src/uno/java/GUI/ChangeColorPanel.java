package uno.java.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class ChangeColorPanel extends JPanel {
	public JPanel blue = new JPanel();
	public JPanel green = new JPanel();
	public JPanel red = new JPanel();
	public JPanel yellow = new JPanel();

	public ChangeColorPanel() {
		this.setSize(new Dimension(80, 60));
		this.setLayout(new GridLayout(4, 1));
		blue.setBackground(Color.blue);
		this.add(blue);
		green.setBackground(Color.green);
		this.add(green);
		red.setBackground(Color.RED);
		this.add(red);
		yellow.setBackground(Color.YELLOW);
		this.add(yellow);
	}

	public void addListener(ColorHandler handler) {
		this.blue.addMouseListener(handler);
		this.green.addMouseListener(handler);
		this.yellow.addMouseListener(handler);
		this.red.addMouseListener(handler);
	}
}
