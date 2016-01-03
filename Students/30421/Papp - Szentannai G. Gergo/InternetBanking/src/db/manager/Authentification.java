package db.manager;

import javax.swing.SwingUtilities;

import gui.Admin_home;
import gui.FailedLogin;
import gui.MainFrame;
import gui.User_home;
import main.Application;

public class Authentification {
	public void attemptUserLogin(String username, String password) {
		if (username.equals("testuser") && password.equals("password")) {
			MainFrame.user_home = new User_home(); // Reset the panel.
			User_home.welcomeText.setText(User_home.welcomeText.getText() + username);
			Application.mainFrame.changePanel(MainFrame.user_home);
		}

		else if (checkForCorrectUserPassword()) {
			// TODO: open useer_home panel, for the selected user
		}

		else {
			displayFailedLogin();
		}
	}

	private boolean checkForCorrectUserPassword() {
		// TODO Auto-generated method stub
		return false;
	}

	public void attemptAdminLogin(String username, String password) {
		if (username.equals("testadmin") && password.equals("password")) {
			MainFrame.admin_home = new Admin_home(); // Reset the panel.
			Application.mainFrame.changePanel(MainFrame.admin_home);
		} else if (checkForCorrectAdminPassword()) {

		} else {
			displayFailedLogin();
		}
	}

	private boolean checkForCorrectAdminPassword() {
		// TODO Auto-generated method stub
		return false;
	}

	private void displayFailedLogin() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				@SuppressWarnings("unused")
				FailedLogin failedLogin = new FailedLogin();
			}
		});

	}

}
