package uno.java.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerDescriptionPanel extends JPanel implements Designer {
	private ImageIcon image;
	public JLabel avatar;
	public JLabel playerName = new JLabel();
	private Dimension dimension;
	private Rectangle avatarBounds = new Rectangle(0, 0, 107, 107);
	private Rectangle playerNameBounds = new Rectangle(40, 102, 40, 40);
	private Font playerNameFont = new Font("Times New Roman", Font.BOLD, 14);

	public PlayerDescriptionPanel() {
		this.setBackground(Color.BLUE);
		this.setSize(400, 400);
	}

	public PlayerDescriptionPanel(Dimension dimension) {
		this.dimension = dimension;
		this.setSize(dimension);

		this.setLayout(null);
	}

	public PlayerDescriptionPanel(String imagePath, String playerName, Dimension dimension) {
		this(dimension);
		this.image = new ImageIcon("D:\\Java assignments\\UNO game third try\\Resorces\\" + imagePath);
		this.avatar = new JLabel(image);
		this.playerName = new JLabel(playerName, JLabel.CENTER);
		this.arrangeItems();
	}

	@Override
	public void componentSetBounds(Component component, Rectangle bounds) {
		component.setBounds(bounds);

	}

	@Override
	public void arrangeItems() {
		componentSetBounds(avatar, avatarBounds);
		this.add(avatar, BorderLayout.CENTER);
		componentSetBounds(this.playerName, this.playerNameBounds);
		this.playerName.setFont(this.playerNameFont);
		this.add(this.playerName);
	}

}
