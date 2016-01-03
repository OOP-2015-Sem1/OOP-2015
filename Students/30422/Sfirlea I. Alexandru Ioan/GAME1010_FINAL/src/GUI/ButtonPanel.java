package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Calculation.Game;
import Main.Constants;

public class ButtonPanel extends JPanel{

	private static Game game= new Game();
	JButton buton;
	public static boolean isNew= true;
	public ButtonPanel(){
		HandlerClass handle = new HandlerClass();
		setPreferredSize(new Dimension(200,800));
		setLayout(new GridLayout(3, 1));
		buton = new JButton("New Game");
		buton.setBackground(Color.pink);
		setVisible(true);
		add(buton);
		buton.addActionListener(handle);
		
		buton = new JButton("Show HS");
		setVisible(true);
		add(buton);
		
		buton = new JButton("Change Style");
		setVisible(true);
		
		add(buton);
		
		setBackground(Color.white);
		setVisible(true);
	}
	
	private class HandlerClass implements ActionListener{
		public void actionPerformed(ActionEvent event ){
			Constants.isNewGame=0;
			BoardPanel.mouse_X=999;
			game.newGame();
			}
		}
}
