package uno.java.GUI;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.TextField;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EnterPlayerNameFrame extends JFrame implements Designer {
	private JLabel text = new JLabel("Player Name");
	private TextField playerName = new TextField(20);

	private Rectangle textBounds = new Rectangle(10, 0, 100, 30);
	private Rectangle playerNameBounds = new Rectangle(10, 40, 150, 25);

	public EnterPlayerNameFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(250, 130);
		this.setTitle("Player's Name");
		this.setVisible(true);
		this.setLayout(null);
		arrangeItems();
		this.playerName.addKeyListener(new PlayersSelectHandler());
	}

	@Override
	public void componentSetBounds(Component component, Rectangle bounds) {
		component.setBounds(bounds);
	}

	@Override
	public void arrangeItems() {
		this.componentSetBounds(text, textBounds);
		this.componentSetBounds(playerName, playerNameBounds);

		this.add(text);
		this.add(playerName);

	}

}
