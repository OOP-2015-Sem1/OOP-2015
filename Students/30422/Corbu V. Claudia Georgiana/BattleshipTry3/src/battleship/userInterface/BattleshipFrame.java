package battleship.userInterface;

import java.awt.*;
import java.awt.event.MouseListener;

import javax.swing.*;

public class BattleshipFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private BoardPanel myPanel;
	private BoardPanel oponentPanel;
	private JButton playButton;
	private JLabel text1, text2;
	private JRadioButton horizontal, vertical;
	private ButtonGroup radiosGroup;

	public BattleshipFrame() {

		super("Battleship Game");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 700);

		myPanel = new BoardPanel(10, 50);
		oponentPanel = new BoardPanel(10, 350);
		this.add(myPanel);
		this.add(oponentPanel);

		playButton = new JButton("PLAY AGAiNST COMPUTER");
		playButton.setBackground(Color.ORANGE);
		playButton.setForeground(Color.WHITE);
		playButton.setBounds(300, 380, 150, 150);
		this.add(playButton);
		
		horizontal= new JRadioButton("horizontally");
		horizontal.setForeground(Color.WHITE);
		horizontal.setBackground(Color.ORANGE);
		horizontal.setSelected(true);
		horizontal.setBounds(300, 50, 150, 50);
		vertical= new JRadioButton("vertically");
		vertical.setForeground(Color.WHITE);
		vertical.setBackground(Color.ORANGE);
		vertical.setBounds(300, 120, 150, 50);
		radiosGroup= new ButtonGroup();
		radiosGroup.add(horizontal);
		radiosGroup.add(vertical);
		this.add(horizontal);
		this.add(vertical);
		
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
	public JButton getPlayButton(){
		return this.playButton;
	}
	public void addActionListenerToMyButtons(MouseListener actionListener) {
		myPanel.addActionListenerToButtons(actionListener);
	}

	public void addActionListenerToOponentButtons(MouseListener actionListener) {
		oponentPanel.addActionListenerToButtons(actionListener);
	}

	public boolean isHorizontallyButtonSelected() {
		return horizontal.isSelected();
	}

	public void addActionListenerToPLAYButton(MouseListener actionListener) {
		playButton.addMouseListener(actionListener);
	}

}
