/*
 * Created by JFormDesigner on Fri Jan 01 18:14:46 EET 2016
 */

package gui;

import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.GroupLayout;

import db.manager.DatabaseConnect;
import db.manager.UserManager;
import main.Application;

/**
 * When editing this don't forget to set JTextArea welcomeText as public static!
 * (the variable is automatically set to public (not static) from the
 * JFormDesigner)
 * 
 * @author Gergo Szentannai
 */
public class User_home extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public User_home() {
		initComponents();
	}

	private void logoutButtonActionPerformed(ActionEvent e) {
		Application.mainFrame.changePanel(MainFrame.homePanel);
		// Reset login page (and also delete user ID and password:
		MainFrame.login_user = new LogIn_user();
		MainFrame.user_home = new User_home();
	}

	private void viewAccountsButtonActionPerformed(ActionEvent e) {
		String userID = new String(LogIn_user.user_id);
		String sqlString = new String(
				"SELECT account_id from accounts_table join users_table on (accounts_table.user_id = users_table.user_id) where users_table.user_id = ");
		sqlString = sqlString + "'" + userID + "'";

		String outSQL = "Your accounts are the following:\n\n"
				+ Application.databaseConnect.executeSqlStatementForStrings(sqlString);

		textPane1.setText(outSQL);

	}

	private void viewuserDetailsButtonActionPerformed(ActionEvent e) {
		// TODO
		String outSQL = new String("Your user details are the following:\n [user ID] [User Name]\n\n");
		String userID = new String(LogIn_user.user_id);
		UserManager userManager = new UserManager();
		outSQL = outSQL + userManager.getUser(userID);
		textPane1.setText(outSQL);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		welcomeText = new JTextArea();
		viewAccountsButton = new JButton();
		viewuserDetailsButton = new JButton();
		scrollPane1 = new JScrollPane();
		textPane1 = new JTextPane();
		logoutButton = new JButton();
		transferMoneyButton = new JButton();

		// ======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(
				new javax.swing.border.EmptyBorder(0, 0, 0, 0), "JFormDesigner Evaluation",
				javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM,
				new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.Color.red), getBorder()));
		addPropertyChangeListener(new java.beans.PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent e) {
				if ("border".equals(e.getPropertyName()))
					throw new RuntimeException();
			}
		});

		// ---- welcomeText ----
		welcomeText.setText("Welcome, ");

		// ---- viewAccountsButton ----
		viewAccountsButton.setText("View accounts");
		viewAccountsButton.addActionListener(e -> viewAccountsButtonActionPerformed(e));

		// ---- viewuserDetailsButton ----
		viewuserDetailsButton.setText("View user details");
		viewuserDetailsButton.addActionListener(e -> viewuserDetailsButtonActionPerformed(e));

		// ======== scrollPane1 ========
		{

			// ---- textPane1 ----
			textPane1.setText("Select the left-hand-side buttons to display information!");
			scrollPane1.setViewportView(textPane1);
		}

		// ---- logoutButton ----
		logoutButton.setText("Log out");
		logoutButton.addActionListener(e -> logoutButtonActionPerformed(e));

		// ---- transferMoneyButton ----
		transferMoneyButton.setText("Transfer money");

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup()
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
												.addComponent(viewuserDetailsButton, GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(viewAccountsButton, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(logoutButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(transferMoneyButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)).addGap(18, 18, 18)
						.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)).addGroup(
								layout.createSequentialGroup()
										.addComponent(welcomeText, GroupLayout.PREFERRED_SIZE, 264,
												GroupLayout.PREFERRED_SIZE)
										.addGap(0, 496, Short.MAX_VALUE)))
						.addContainerGap()));
		layout.setVerticalGroup(
				layout.createParallelGroup()
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addComponent(welcomeText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(39, 39,
												39)
								.addGroup(layout.createParallelGroup()
										.addGroup(layout.createSequentialGroup().addComponent(viewAccountsButton)
												.addGap(18, 18, 18).addComponent(viewuserDetailsButton)
												.addGap(18, 18, 18).addComponent(transferMoneyButton)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 269,
														Short.MAX_VALUE)
												.addComponent(logoutButton))
										.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
										.addContainerGap()));
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	public static JTextArea welcomeText;
	private JButton viewAccountsButton;
	private JButton viewuserDetailsButton;
	private JScrollPane scrollPane1;
	private JTextPane textPane1;
	private JButton logoutButton;
	private JButton transferMoneyButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
