package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import source.Account;
import source.User;

public class LogInFrame implements ActionListener {
	private JFrame logInFrame;
	private JTextField  logName;
	private JTextField  logPassword;
	private JPanel logNamePanel;
	private JPanel logPasswordPanel;
	private JButton logButton;
	private User user;
	private Account account;
	
	public LogInFrame(){
		logInFrame= new JFrame();
		logName= new JTextField();
		logPassword= new JTextField();
		logNamePanel= new JPanel();
		logPasswordPanel= new JPanel();
		logButton= new JButton("Log In");
		
		this.user = new User();
		this.account = new Account();
		
		this.logNamePanel.setLayout(new GridLayout(1,2,5,5));
		this.logPasswordPanel.setLayout(new GridLayout(1,2,5,5));
		
		this.logInFrame.setLayout(new GridLayout(3,1,5,5));
		this.logInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.logInFrame.setSize(600,400);  
		this.logInFrame.setVisible(true); 
		this.logInFrame.setLocationRelativeTo(null);
		
		JLabel rName=new JLabel("Your name");
		this.logNamePanel.add(rName);
		this.logNamePanel.add(logName);
		
		JLabel rPassW=new JLabel("Your Password");
		this.logPasswordPanel.add(rPassW);
		this.logPasswordPanel.add(logPassword);
		
		JPanel panelForButton =new JPanel();
		panelForButton.add(logButton);

		this.logInFrame.add(logNamePanel);
		this.logInFrame.add(logPasswordPanel);
		this.logInFrame.add(panelForButton);

		addActionListeners();
	}
	private void addActionListeners(){
		 this.logButton.addActionListener(this);
	 }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if( arg0.getSource() == this.logButton ){
			this.user.setUserName(logName.getText());
			this.user.setUserPassword(this.logPassword.getText());
			this.user.setMoney(this.account.getMoneyOfTheLoggedInUser(user));
			
			if ( this.user.getMoney() != 0){
				this.logInFrame.setVisible(false);
				new SelectNumberOfPlayersFrame(this.user);
			}
			else{
				JOptionPane.showMessageDialog(null, "Wrong user/password.Please introduce them again");
			}
		}
		
	}
}
