package gui;

import javax.swing.*;

/**
 * Opens a frame which is the graphical support of the application. The frame is
 * empty by default, but several JPanewls can be added during runtime. All
 * panels are public static, so the content can be changed from any class.
 * 
 * Only one instance of this class should be in memory.
 * 
 * @author gergo_000
 *
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame MainFrame;

	// Available panels:
	public static JPanel homePanel, login_user, login_admin, user_home, admin_home, settings;

	public MainFrame() {
		initPanels();
		initComponents();
	}

	/**
	 * Initializes all the panels that will be displayed inside the frame.
	 */
	private void initPanels() {
		homePanel = new HomePanel();
		login_user = new LogIn_user();
		login_admin = new LogIn_admin();
		user_home = new User_home();
		admin_home = new Admin_home();
		settings = new Settings(); // Not yet finished.

	}

	private void initComponents() {

		MainFrame = new JFrame();

		MainFrame.setVisible(true);
		MainFrame.setSize(800, 500);
		MainFrame.setTitle("Bank of free Money - Internet banking Management application 2015");

		// Set default panel.
		changePanel(homePanel);

	}

	/**
	 * This method should be accessed to change the panel.
	 * 
	 * @param newPanel
	 */
	public void changePanel(JPanel newPanel) {

		MainFrame.getContentPane().removeAll();
		MainFrame.getContentPane().add(newPanel);
		MainFrame.getContentPane().revalidate();
		MainFrame.getContentPane().repaint();
	}

}
