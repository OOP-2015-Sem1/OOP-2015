package GUI;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class StartGame implements ActionListener{
	
	private JFrame startFrame;
	private JButton startButton;
	private JLabel wellcomeLabel;
	
	public StartGame(){
		startFrame = new JFrame();  
		ImageIcon start = new ImageIcon("images/bfb.jpg");
		wellcomeLabel = new JLabel(start);
		startButton = new JButton("START");
		JPanel jp = new JPanel();
		jp.setBackground(Color.BLACK);
		jp.add(startButton);
		this.startFrame.setTitle("Wellcome To Pau's Casino");
		this.startFrame.setLayout(new GridLayout(2,1,9,9));
		this.startFrame.add(wellcomeLabel);
		this.startFrame.add(jp);
		this.startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.startFrame.setSize(600,400);  
		this.startFrame.setVisible(true); 
		addActionListeners();
	}
	
	 private void addActionListeners(){
		 this.startButton.addActionListener(this);
	 }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if( arg0.getSource() == this.startButton ){
			this.startFrame.setVisible(false);
			new SelectNumberOfPlayers();
		}
	
	}
}
