import java.awt.EventQueue;

import javax.swing.JFrame;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class Start {

	public JFrame frmStart;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Start window = new Start();
					window.frmStart.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Start() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStart = new JFrame();
		frmStart.setTitle("Start");
		frmStart.setBounds(100, 100, 800, 450);
		frmStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStart.getContentPane().setLayout(null);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(90, 63, 125, 40);
		frmStart.getContentPane().add(btnStart);
		
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Instructions instr = new Instructions();
				instr.frame.setVisible(true);
				frmStart.dispose();
			}
		});
		btnInstructions.setBounds(90, 125, 125, 40);
		frmStart.getContentPane().add(btnInstructions);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmStart.dispose();
			}
		});
		btnExit.setBounds(90, 194, 125, 40);
		frmStart.getContentPane().add(btnExit);
		
		JLabel backgroundImg = new JLabel("New label");
		backgroundImg.setIcon(new ImageIcon("C:\\Users\\George\\Desktop\\poker-cards_1920x1080_506-hd.jpg"));
		backgroundImg.setBounds(0, 0, 850, 450);
		frmStart.getContentPane().add(backgroundImg);
	}
}
