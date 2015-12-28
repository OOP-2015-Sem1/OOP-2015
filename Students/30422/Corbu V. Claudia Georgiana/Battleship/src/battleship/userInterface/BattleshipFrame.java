package battleship.userInterface;

import java.awt.*;
import java.awt.event.MouseListener;

import javax.swing.*;

public class BattleshipFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private BoardPanel myPanel;
	private BoardPanel oponentPanel;
	private JButton donePlaceingShipsButton;
	private JButton placeNewShipButton;
	private JLabel text1, text2;

	public BattleshipFrame() {

		super("Battleship Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 700);

		myPanel = new BoardPanel(10, 50);
		oponentPanel = new BoardPanel(10, 350);
		this.add(myPanel);
		this.add(oponentPanel);

		donePlaceingShipsButton = new JButton("Done placing ships");
		donePlaceingShipsButton.setBackground(Color.ORANGE);
		donePlaceingShipsButton.setForeground(Color.WHITE);
		donePlaceingShipsButton.setBounds(300, 380, 150, 150);
		this.add(donePlaceingShipsButton);

		placeNewShipButton = new JButton("Place a new ship");
		placeNewShipButton.setBackground(Color.ORANGE);
		placeNewShipButton.setForeground(Color.WHITE);
		placeNewShipButton.setBounds(300, 50, 150, 150);
		this.add(placeNewShipButton);
		
		text1 = new JLabel("MY BOARD");
		text1.setBounds(100, 20, 250, 30);
		text1.setForeground(Color.WHITE);
		this.add(text1);

		text2 = new JLabel("OPONENT BOARD");
		text2.setBounds(80, 320, 250, 30);
		text2.setForeground(Color.WHITE);
		this.add(text2);

		JPanel p = new JPanel();
		p.setBackground(new Color(0, 100, 150));
		this.add(p);

		this.setResizable(false);
		this.setVisible(true);

	}
	public BoardPanel getMyPanel(){
		return this.myPanel;
	}
	public BoardPanel getOponentPanel() {
		return this.oponentPanel;
	}
	public JButton getDonePlacingShipsButton(){
		return this.donePlaceingShipsButton;
	}
	public void addActionListenerToMyButtons(MouseListener actionListener) {
		myPanel.addActionListenerToButtons(actionListener);
	}

	public void addActionListenerToOponentButtons(MouseListener actionListener) {
		oponentPanel.addActionListenerToButtons(actionListener);
	}

	public void addActionListenerToPlaceNewShipButton(MouseListener actionListener) {
		placeNewShipButton.addMouseListener(actionListener);
	}

	public void addActionListenerToDonePlaceingShipsButton(MouseListener actionListener) {
		donePlaceingShipsButton.addMouseListener(actionListener);
	}

}
