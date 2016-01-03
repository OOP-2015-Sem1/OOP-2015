/*
 * Created by JFormDesigner on Fri Jan 01 17:53:37 EET 2016
 */

package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

import main.Application;

/**
 * @author Gergo Szentannai
 */
public class HomePanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HomePanel() {
		initComponents();
	}

	private void login_user(ActionEvent e) {
		// The login_user has to be reinitialized, so the stored user ID and
		// passwords are lost for security reasons.
		MainFrame.login_user = new LogIn_user();
		Application.mainFrame.changePanel(MainFrame.login_user);
	}

	private void login_admin(ActionEvent e) {
		// The login_user has to be reinitialized, so the stored user ID and
		// passwords are lost for security reasons.
		MainFrame.login_admin = new LogIn_admin();
		Application.mainFrame.changePanel(MainFrame.login_admin);
	}

	private void settingsActionPerformed(ActionEvent e) {
		// Application.mainFrame.changePanel(MainFrame.settings);
		// TODO: add settings panel, then enable the statement above
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		textPane1 = new JTextPane();
		textPane2 = new JTextPane();

		//======== this ========
		setPreferredSize(new Dimension(780, 480));
		setMinimumSize(new Dimension(780, 480));
		setMaximumSize(new Dimension(780, 480));

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//---- button1 ----
		button1.setText("Log in");
		button1.addActionListener(e -> login_user(e));

		//---- button2 ----
		button2.setText("Admin log in");
		button2.addActionListener(e -> login_admin(e));

		//---- button3 ----
		button3.setText("Settings [not available yet]");
		button3.addActionListener(e -> settingsActionPerformed(e));

		//---- textPane1 ----
		textPane1.setText("Welcome to the Internet banking application of the bank of free Money!");

		//---- textPane2 ----
		textPane2.setText("Please log in or configure your server settings! The user details are provided by the administrators of the bank.");

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(168, 168, 168)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
						.addComponent(textPane1)
						.addGroup(layout.createSequentialGroup()
							.addComponent(button1)
							.addGap(54, 54, 54)
							.addComponent(button2)
							.addGap(44, 44, 44)
							.addComponent(button3))
						.addComponent(textPane2, GroupLayout.PREFERRED_SIZE, 411, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(201, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(168, 168, 168)
					.addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addComponent(textPane2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addGap(18, 18, 18)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(button1)
						.addComponent(button2)
						.addComponent(button3))
					.addContainerGap(200, Short.MAX_VALUE))
		);
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JTextPane textPane1;
	private JTextPane textPane2;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
