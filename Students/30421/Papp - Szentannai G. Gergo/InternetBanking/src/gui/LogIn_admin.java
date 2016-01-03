/*
 * Created by JFormDesigner on Fri Jan 01 18:04:50 EET 2016
 */

package gui;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

import db.manager.Authentification;
import main.Application;

/**
 * @author Gergo Szentannai
 */
public class LogIn_admin extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LogIn_admin() {
		initComponents();
	}

	private void cancelActionPerformed(ActionEvent e) {
		Application.mainFrame.changePanel(MainFrame.homePanel);
	}

	private void lognButtonActionPerformed(ActionEvent e) {
		Authentification authentification = new Authentification();
		char[] tempPassword = passwordField1.getPassword();
		String tempStringPassword = new String(tempPassword);
		//System.out.println(userTextField.getText() + " " + tempStringPassword); // Testing
		authentification.attemptAdminLogin(textField3.getText(), tempStringPassword);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		adminID = new JTextField();
		adminPassword = new JTextField();
		textField3 = new JTextField();
		passwordField1 = new JPasswordField();
		lognButton = new JButton();
		button2 = new JButton();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//---- adminID ----
		adminID.setText("Admin ID");
		adminID.setEditable(false);

		//---- adminPassword ----
		adminPassword.setText("Admin Password");
		adminPassword.setEditable(false);

		//---- lognButton ----
		lognButton.setText("Log in");
		lognButton.addActionListener(e -> lognButtonActionPerformed(e));

		//---- button2 ----
		button2.setText("Cancel");
		button2.addActionListener(e -> cancelActionPerformed(e));

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(68, 68, 68)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(lognButton)
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(adminPassword)
								.addComponent(adminID))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(textField3, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
								.addComponent(passwordField1, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))))
					.addGap(31, 31, 31)
					.addComponent(button2)
					.addContainerGap(41, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(116, 116, 116)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(textField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(adminID, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(adminPassword, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(lognButton)
						.addComponent(button2))
					.addContainerGap(85, Short.MAX_VALUE))
		);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	private JTextField adminID;
	private JTextField adminPassword;
	private JTextField textField3;
	private JPasswordField passwordField1;
	private JButton lognButton;
	private JButton button2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
