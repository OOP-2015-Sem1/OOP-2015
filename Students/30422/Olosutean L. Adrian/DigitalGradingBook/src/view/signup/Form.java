package view.signup;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.users.Credential;

public abstract class Form extends JPanel{
	private JLabel usernameLabel = new JLabel("username: ");
	private JTextField usernameField = new JTextField();
	
	private JLabel passwordLabel = new JLabel("password: ");
	private JPasswordField passwordField = new JPasswordField();
	
	private JLabel firstNameLabel = new JLabel("first name: ");
	private JTextField firstNameField = new JTextField();
	
	private JLabel lastNameLabel = new JLabel("last name: ");
	private JTextField lastNameField = new JTextField();
	
	private JLabel phoneNumberLabel = new JLabel("phone number: ");
	private JTextField phoneNumberField = new JTextField();
	
	private JLabel emailLabel = new JLabel("email: ");
	private JTextField emailField = new JTextField();
	
	public Form() {
		super(new BorderLayout());
		JPanel grid = new JPanel(new GridLayout(6,2));
		
		grid.add(usernameLabel);
		grid.add(usernameField);
		grid.add(passwordLabel);
		grid.add(passwordField);
		grid.add(firstNameLabel);
		grid.add(firstNameField);
		grid.add(lastNameLabel);
		grid.add(lastNameField);
		grid.add(phoneNumberLabel);
		grid.add(phoneNumberField);
		grid.add(emailLabel);
		grid.add(emailField);
		add(grid, BorderLayout.NORTH);
		
	}
	public Credential getCredential() {
		Credential credential = new Credential();
		credential.username = usernameField.getText();
		char[] password = passwordField.getPassword();
		credential.password = new String(password);
		
		credential.firstName = firstNameField.getText();
		credential.lastName = lastNameField.getText();
		credential.phoneNumber = phoneNumberField.getText();
		credential.email = emailField.getText();
		
		return credential;
	}
}
