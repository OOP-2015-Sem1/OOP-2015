import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Board extends JFrame{
	private static final long serialVersionUID = 2195379760914381351L;
	
	JPanel dealer = new JPanel();
	JPanel player = new JPanel();
	JPanel controls = new JPanel();
	JLabel info = new JLabel();
	
	JLabel back = new JLabel(new ImageIcon("CardImages/back.png"));
	
	Card hole = new Card("CardImages/back.png", 0);

	public Board(Controller controller){
		setLayout(new GridLayout(3,1));
		setSize(1200, 700);
		this.add(dealer);
		this.add(player);
		controls.setLayout(new GridLayout(1,4));
		JButton newButton = new JButton("New Game");
		newButton.addActionListener(controller);
		controls.add(newButton);
		JButton hitButton = new JButton("Hit");
		hitButton.addActionListener(controller);
		controls.add(hitButton);
		JButton standButton = new JButton("Stand");
		standButton.addActionListener(controller);
		controls.add(standButton);
		controls.add(info);
		this.add(controls);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void clear(){
		dealer.removeAll();
		dealer.repaint();
		player.removeAll();
		player.repaint();
	}
	
	public void drawPlayer(Card pCard){
		player.add(new JLabel(pCard.getImg()));
		player.repaint();
	}
	
	public void drawDealer(Card hole){
		if (back.getParent() == dealer){
		dealer.remove(back);
		dealer.add(new JLabel(this.hole.getImg()));
		dealer.add(back);
		}
		else{
			dealer.add(back);
		}
		this.hole = hole;
		dealer.repaint();
	}
	
	public void showDealer(){
		dealer.remove(back);
		dealer.add(new JLabel(hole.getImg()));
	}
	
	public void setInfo(String text){
		info.setText(text);
	}
	
}
