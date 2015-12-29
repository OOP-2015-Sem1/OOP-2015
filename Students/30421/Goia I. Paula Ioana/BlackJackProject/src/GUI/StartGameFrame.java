package GUI;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StartGameFrame implements ActionListener{
	
	private JFrame startFrame;
	private JButton rButton;
	private JLabel wellcomeLabel;
	private JButton lButton;
	
	public StartGameFrame(){
 
		ImageIcon start = new ImageIcon("images/bfb.jpg");
		wellcomeLabel = new JLabel(start);
		rButton = new JButton("Register");
		lButton = new JButton("Login");
		startFrame = new JFrame(); 
		
		this.startFrame.setTitle("Wellcome To Pau's Casino");
		this.startFrame.setLayout(new GridLayout(2,1,9,9));
		this.startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.startFrame.setSize(600,400);  
		this.startFrame.setVisible(true); 
		this.startFrame.setLocationRelativeTo(null);
		
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		jp.setBackground(Color.BLACK);
		jp.add(rButton);
		jp.add(lButton);
		
		this.startFrame.add(wellcomeLabel);
		this.startFrame.add(jp);

		addActionListeners();
	}
	
	 private void addActionListeners(){
		 this.rButton.addActionListener(this);
		 this.lButton.addActionListener(this);
	 }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if( arg0.getSource() == this.rButton ){
			this.startFrame.setVisible(false);
			new RegisterFrame();
		}
		if( arg0.getSource() == this.lButton ){
			this.startFrame.setVisible(false);
			new LogInFrame();
		}
	
	}
}
