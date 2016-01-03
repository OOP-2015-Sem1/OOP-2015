package view.login;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.swing.*;

import controller.DatabaseController;
import model.users.Credential;
import services.login.LoginHandler;	
import services.signup.Authenthication_I;
import view.MainPage;


public class LoginPage extends JPanel implements Authenthication_I{
	
	private JPanel login = new JPanel();
	private JPanel signup = new JPanel();
	private JLabel loginState = new JLabel("");
	
	private JLabel signupLabel = new JLabel("Not Registered? Sign up");
	private JButton signupButton = new JButton("Sign up");
	
	private JComboBox userType = new JComboBox(new String[]{"Teacher","Student"});
	
	private JTextField usernameField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	
	private JButton submit = new JButton("Submit");
	private JLabel usernameLabel = new JLabel("Username");
	private JLabel passwordLabel = new JLabel("Password");
	
	public LoginPage() {
		
		makeLoginPart();
		makeSignupPart();
		
		setLayout(new BorderLayout());
		add(login,BorderLayout.CENTER);
		add(signup,BorderLayout.SOUTH);
		addActionListeners();
	}
	
	public void makeLoginPart() {
		login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));
		
		usernameField.setMaximumSize(new Dimension(150,20));
		passwordField.setMaximumSize(new Dimension(150,20));

		login.add(Box.createVerticalStrut(10));
		
		userType.setMaximumSize(new Dimension(150,20));
		login.add(userType);
		login.add(Box.createVerticalStrut(30));
		
		ArrayList<JComponent> components = new ArrayList<>();
		components.add(usernameLabel);
		components.add(usernameField);
		components.add(passwordLabel);
		components.add(passwordField);
		components.add(submit);
		components.add(loginState);
		
		centerAndSeparate(components);
	} 
	
	private void centerAndSeparate(ArrayList<JComponent> components) {
		for (JComponent component : components) {
			component.setAlignmentX(CENTER_ALIGNMENT);
			login.add(component);
			login.add(Box.createVerticalStrut(10));
		}
	}
	
	public void makeSignupPart() {
		signup.setLayout(new BoxLayout(signup,BoxLayout.Y_AXIS));
		
		signupLabel.setAlignmentX(CENTER_ALIGNMENT);
		signup.add(signupLabel);
		signup.add(Box.createVerticalStrut(10));
		
		signupButton.setAlignmentX(CENTER_ALIGNMENT);
		signup.add(signupButton);
		signup.add(Box.createVerticalStrut(10));

	}
	
	public void addActionListeners() {

		submit.addActionListener(new LoginHandler(this));
		
		signupButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				MainPage.cards.show(MainPage.mainPanel,"SignupPage");
			}
		});
	}
	

	public String getUserType(){
		return (String) userType.getSelectedItem();
	}
	public void setLoginState(String state){
		loginState.setText(state);
	}

	public Credential getCredential() {
		Credential credential = new Credential();
		credential.username = usernameField.getText();
		char[] password = passwordField.getPassword();
		
		credential.password = new String(password);
		return credential;
	}

	@Override
	public void setState(String state) {
		loginState.setText(state);
	}
}
