import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Instructions {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Instructions window = new Instructions();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Instructions() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 800, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTextPane instructions = new JTextPane();
		instructions.setFont(new Font("Tahoma", Font.BOLD, 14));
		instructions.setForeground(Color.WHITE);
		instructions.setText("Welcome to Poker!\r\n\r\nThe basic idea is to defeat your opponent by getting a better\r\ncard combination than him. Each player gets 5 cards out of a deck\r\nof 52 and is able to change up to 3 cards. After the change was made,\r\nthe player with the best combination of cards wins. The combinations\r\nare the following, in order:\r\nHigh Card\r\nPair\r\nTwo Pairs\r\nThree of a Kind\r\nStraight\r\nFull House\r\nFlush\r\nFour of a Kind\r\nStraight Flush\r\nRoyal Flush");
		instructions.setBounds(0, 0, 794, 369);
		frame.getContentPane().add(instructions);
		instructions.setOpaque(false);
		
		JButton backButton = new JButton("Back");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Start start = new Start();
				start.frmStart.setVisible(true);
				frame.dispose();
			}
		});
		backButton.setBounds(362, 370, 125, 40);
		frame.getContentPane().add(backButton);
		
		JLabel backgroundImg = new JLabel("New label");
		backgroundImg.setIcon(new ImageIcon("C:\\Users\\George\\Desktop\\poker-cards_1920x1080_506-hd.jpg"));
		backgroundImg.setBounds(0, 0, 794, 421);
		frame.getContentPane().add(backgroundImg);
	}
}
