package gui;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame MainFrame;

	protected static JPanel homePanel, login_user, login_admin, user_home, admin_home;

	public MainFrame() {
		initPanels();
		initComponents();
		
	}

	private void initPanels() {
		homePanel = new HomePanel();
		login_user = new LogIn_user();
		login_admin = new LogIn_admin();
		user_home = new User_home();
		admin_home = new Admin_home();

	}

	private void initComponents() {

		MainFrame = new JFrame();

		MainFrame.setVisible(true);
		MainFrame.setSize(500, 300);
		MainFrame.setTitle("Bank of free Money - Internet banking Management application 2015");
		Container MainFrameContentPane = MainFrame.getContentPane();
		//MainFrame.add(login_admin);
		//MainFrameContentPane.add(homePanel);
		changePanel(homePanel);
		
	}
	/**
	 * This method should be accessed to change the panel.
	 * @param panel
	 */
	public void changePanel(JPanel panel){
		MainFrame.getContentPane().removeAll();
		MainFrame.getContentPane().add(panel);
		MainFrame.getContentPane().revalidate();
		MainFrame.getContentPane().repaint();
	}

}
