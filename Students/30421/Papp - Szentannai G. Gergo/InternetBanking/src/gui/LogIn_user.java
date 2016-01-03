/*
 * Created by JFormDesigner on Fri Jan 01 18:01:57 EET 2016
 */

package gui;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

import db.manager.Authentification;
import main.Application;

/**
 * @author Gergo Szentannai
 */
public class LogIn_user extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LogIn_user() {
		initComponents();
	}

	private void cancelActionPerformed(ActionEvent e) {
		Application.mainFrame.changePanel(MainFrame.homePanel);
	}

	private void authentificateActionPerformed(ActionEvent e) {
		Authentification authentification = new Authentification();
		char[] tempPassword = passwordField1.getPassword();
		String tempStringPassword = new String(tempPassword);
		//System.out.println(userTextField.getText() + " " + tempStringPassword); // Testing
		authentification.attemptUserLogin(userTextField.getText(), tempStringPassword);
	}

	private void button1ActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		button2 = new JButton();
		passwordField1 = new JPasswordField();
		userTextField = new JTextField();
		textPane1 = new JTextPane();
		textPane2 = new JTextPane();
		scrollPane1 = new JScrollPane();
		textPane3 = new JTextPane();
		button1 = new JButton();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//---- button2 ----
		button2.setText("Authentificate");
		button2.addActionListener(e -> authentificateActionPerformed(e));

		//---- textPane1 ----
		textPane1.setText("UserID");

		//---- textPane2 ----
		textPane2.setText("Password");

		//======== scrollPane1 ========
		{

			//---- textPane3 ----
			textPane3.setText("Please enter the User ID provided by the Bank.");
			textPane3.setEditable(false);
			scrollPane1.setViewportView(textPane3);
		}

		//---- button1 ----
		button1.setText("Cancel");
		button1.addActionListener(e -> {
			button1ActionPerformed(e);
			cancelActionPerformed(e);
		});

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(238, 238, 238)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
						.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
									.addComponent(button2)
									.addGap(18, 18, 18)
									.addComponent(button1))
								.addGroup(GroupLayout.Alignment.LEADING, layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(userTextField)
									.addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(288, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(170, 170, 170)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(userTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup()
						.addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18, 18, 18)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(button2)
						.addComponent(button1))
					.addGap(15, 15, 15)
					.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(181, Short.MAX_VALUE))
		);
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	private JButton button2;
	private JPasswordField passwordField1;
	private JTextField userTextField;
	private JTextPane textPane1;
	private JTextPane textPane2;
	private JScrollPane scrollPane1;
	private JTextPane textPane3;
	private JButton button1;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}
