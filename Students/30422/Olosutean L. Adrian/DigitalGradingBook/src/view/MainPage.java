package view;
import java.awt.*;
import javax.swing.*;

import controller.signup.UserFinder;
import services.utils.TableSetter;
import view.login.LoginPage;
import view.signup.SignUpPage;


public class MainPage extends JFrame{
	
	public static CardLayout cards = new CardLayout(); 
	public static JPanel mainPanel = new JPanel();
	public JPanel loginPage;
	public JPanel signupPage;
	
	public MainPage() {
		super("Login Authentification");
		mainPanel.setLayout(cards);
		
		loginPage = new LoginPage();
		signupPage = new SignUpPage();
		
		mainPanel.add(loginPage, "LoginPage");
		mainPanel.add(signupPage, "SignupPage");
		add(mainPanel);
		cards.show(mainPanel, "LoginPage");
		
		setSize(350,370);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		TableSetter setter = new TableSetter();
		setter.setTable("Subject");
		setter.setTable("Specialization");
		setter.setTable("Class");
		MainPage mainPage = new MainPage();
		
	}

}
