package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import source.Account;
import source.User;

public class RegisterFrame implements ActionListener {
	private JFrame registerFrame;
	private JTextField  name;
	private JTextField  password;
	private JPanel regNamePanel;
	private JPanel regPasswordPanel;
	private JButton registerButton;
	private Account account;
	private User user;
	
	public RegisterFrame(){
		this.user = new User();
		this.account = new Account();
		this.name= new JTextField();
		this.password= new JTextField();
		this.regNamePanel= new JPanel();
		this.regPasswordPanel= new JPanel();
		this.registerButton= new JButton("Register");
		this.registerFrame= new JFrame();
		
		this.registerFrame.setLayout(new GridLayout(3,1,5,5));
		this.registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.registerFrame.setSize(600,400);  
		this.registerFrame.setVisible(true); 
		this.registerFrame.setLocationRelativeTo(null);

		this.regNamePanel.setLayout(new GridLayout(1,2,5,5));
		JLabel rName=new JLabel("Your name");
		this.regNamePanel.add(rName);
		this.regNamePanel.add(name);
		
		this.regPasswordPanel.setLayout(new GridLayout(1,2,5,5));
		JLabel rPassW=new JLabel("Your Password");
		this.regPasswordPanel.add(rPassW);
		this.regPasswordPanel.add(password);
		
		JPanel panelForButton =new JPanel();
		panelForButton.add(registerButton);
		
		this.registerFrame.add(regNamePanel);
		this.registerFrame.add(regPasswordPanel);
		this.registerFrame.add(panelForButton);

		addActionListeners();
	}
	private void addActionListeners(){
		 this.registerButton.addActionListener(this);
	 }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if( arg0.getSource() == this.registerButton ){
			this.registerFrame.setVisible(false);
			//create user
			this.user.setUserName(name.getText());
			this.user.setUserPassword(password.getText());
			this.user.setMoney(100);
			this.account.getUserList().add(this.user);
			this.account.write(account.getUserList());
			this.account.read();
			new LogInFrame();
		}
		
	}
}
