package uno.java.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.ArrayList;

import javax.swing.JPanel;

import uno.java.entities.Card;
import uno.java.entities.Player;

public abstract class PlayerCardsPanel extends JPanel implements Designer {
	private Dimension dimension;
	protected int x = 0;
	protected int y = 0;
	public Handler handler = new Handler();

	public PlayerCardsPanel() {
		this.setBackground(new Color(14, 105, 32));
		this.x = 0;
		this.y = 0;

	}

	public PlayerCardsPanel(Dimension dimension) {
		this();
		this.dimension = dimension;
		this.setSize(this.dimension);
		this.setLayout(null);
	}

	@Override
	public void componentSetBounds(Component component, Rectangle bounds) {
		component.setBounds(bounds);

	}

	@Override
	public void arrangeItems() {

	}

	public void arrangeCards(ArrayList<Card> cards) {

	}

	public void resetCoordinates() {
		this.x = 0;
		this.y = 0;
	}

	public void repaintPanel() {
		this.setBackground(new Color(14, 105, 32));
	}

}
