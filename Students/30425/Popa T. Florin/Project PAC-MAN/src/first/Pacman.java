package first;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Canvas;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;


public class Pacman extends Canvas {

	
	private JPanel panel;
	private JPanel panel1;
	public JPanel panel2;
	public static Map map;
	CardLayout cl = new CardLayout();
	
	public Pacman(int width, int height, String title, GameEngine game) {
		
		//THE MAKING OF THE FRAME
		JFrame frame = new JFrame(title);
		frame.setPreferredSize(new Dimension(width,height));
		frame.setMaximumSize(new Dimension(width,height));
		frame.setMinimumSize(new Dimension(width,height));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		//END
		
		//THE MAIN PANEL
		panel = new JPanel();
		panel.setLayout(cl);
		frame.getContentPane().add(panel);
		//END
		
		//THE MENU PANEL
		panel1 = new JPanel();
		panel1.setBorder(new LineBorder(new Color(255, 255, 0), 10));
		panel1.setBackground(Color.BLACK);
		panel1.setLayout(null);
		panel.add(panel1, "1");
		//END
		
		//THE GAME PANEL
		panel2 = new JPanel();
		panel.add(panel2, "2");
		//END
		
		//THE LAYOUT SET TO SHOW THE FIRST PANEL
		cl.show(panel, "1");
		//END
		
		//THE NEW GAME BUTTON ADDED TO THE MENU PANEL
		JButton btnNewGame = new JButton();
		btnNewGame.setBounds(219, 239, 213, 51);
		Image new_game = new ImageIcon(this.getClass().getResource("/new_game.png")).getImage();
		btnNewGame.setIcon(new ImageIcon(new_game));
		panel1.add(btnNewGame);
		//END
		
		//THE TITLE IMAGE INSIDE A LABEL, ALSO ADDED TO THE MENU PANEL
		JLabel label = new JLabel("");
		Image img = new ImageIcon(this.getClass().getResource("/title1.png")).getImage();
		label.setIcon(new ImageIcon(img));
		label.setBounds(123, 90, 400, 80);
		panel1.add(label);
		//END
		
		//MOUSE LISTENER ADDED TO THE NEW GAME BUTTON ON THE FIRST PANEL
		HandlerClass handler = new HandlerClass();
		btnNewGame.addMouseListener(handler);
		//END
		
		//THE ADDING OF THE GAME TO THE SECOND PANEL AND THEN START IT
		panel2.add(game);
		game.start();
	}
		
	//THE MOUSE EVENT LISTENER CLASS
	public class HandlerClass implements MouseListener {
		public void mouseClicked(MouseEvent event){
			//THE FRAME SHOWS THE SECOND PANEL WHEN BUTTON CLICKED
			cl.show(panel, "2"); //I TRIED TO CALL HERE THE "start()" METHOD FROM GameEngine BUT IT CRASHED
			//END
		}
		public void mousePressed(MouseEvent e) {	
		}
		public void mouseReleased(MouseEvent e) {	
		}
		public void mouseEntered(MouseEvent e) {
		}
		public void mouseExited(MouseEvent e) {			
		}
	}
}
	//END
	
