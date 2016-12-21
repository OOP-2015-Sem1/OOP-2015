/*
 * Created by JFormDesigner on Fri Jan 01 18:09:11 EET 2016
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
public class Admin_home extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Admin_home() {
		initComponents();
	}

	private void logoutButtonActionPerformed(ActionEvent e) {
		Application.mainFrame.changePanel(MainFrame.homePanel);
		// Reset login page (and also delete user ID and password:
		MainFrame.login_admin = new LogIn_admin();
		MainFrame.admin_home = new Admin_home();
	}

	private void addUserButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
		Admin_home.innerAdminPanel = new Admin_addUserInnerPanel();
	}

	private void modifyUserButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void deleteuserButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void button4ActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void deleteAccountButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		panel4 = new JPanel();
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		addUserButton = new JButton();
		modifyUserButton = new JButton();
		deleteuserButton = new JButton();
		panel2 = new JPanel();
		button4 = new JButton();
		deleteAccountButton = new JButton();
		panel3 = new JPanel();
		logoutButton = new JButton();
		scrollPane1 = new JScrollPane();
		outputTextArea = new JTextArea();
		innerAdminPanel = new JPanel();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

		setLayout(new BorderLayout());

		//======== panel4 ========
		{
			panel4.setPreferredSize(new Dimension(780, 480));
			panel4.setMaximumSize(new Dimension(780, 480));
			panel4.setMinimumSize(new Dimension(780, 480));

			//======== tabbedPane1 ========
			{

				//======== panel1 ========
				{

					//---- addUserButton ----
					addUserButton.setText("Add user");
					addUserButton.addActionListener(e -> addUserButtonActionPerformed(e));

					//---- modifyUserButton ----
					modifyUserButton.setText("Modify user");
					modifyUserButton.addActionListener(e -> modifyUserButtonActionPerformed(e));

					//---- deleteuserButton ----
					deleteuserButton.setText("Delete user");
					deleteuserButton.addActionListener(e -> deleteuserButtonActionPerformed(e));

					GroupLayout panel1Layout = new GroupLayout(panel1);
					panel1.setLayout(panel1Layout);
					panel1Layout.setHorizontalGroup(
						panel1Layout.createParallelGroup()
							.addGroup(panel1Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(modifyUserButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(addUserButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(deleteuserButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
					panel1Layout.setVerticalGroup(
						panel1Layout.createParallelGroup()
							.addGroup(panel1Layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(addUserButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(modifyUserButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(deleteuserButton)
								.addContainerGap(240, Short.MAX_VALUE))
					);
				}
				tabbedPane1.addTab("Users", panel1);

				//======== panel2 ========
				{

					//---- button4 ----
					button4.setText("Add account");
					button4.addActionListener(e -> button4ActionPerformed(e));

					//---- deleteAccountButton ----
					deleteAccountButton.setText("Delete account");
					deleteAccountButton.addActionListener(e -> deleteAccountButtonActionPerformed(e));

					GroupLayout panel2Layout = new GroupLayout(panel2);
					panel2.setLayout(panel2Layout);
					panel2Layout.setHorizontalGroup(
						panel2Layout.createParallelGroup()
							.addGroup(panel2Layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
									.addComponent(deleteAccountButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(button4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					);
					panel2Layout.setVerticalGroup(
						panel2Layout.createParallelGroup()
							.addGroup(panel2Layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(button4)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(deleteAccountButton)
								.addContainerGap(269, Short.MAX_VALUE))
					);
				}
				tabbedPane1.addTab("Accounts", panel2);

				//======== panel3 ========
				{

					//---- logoutButton ----
					logoutButton.setText("Log out");
					logoutButton.addActionListener(e -> logoutButtonActionPerformed(e));

					GroupLayout panel3Layout = new GroupLayout(panel3);
					panel3.setLayout(panel3Layout);
					panel3Layout.setHorizontalGroup(
						panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(logoutButton)
								.addContainerGap(126, Short.MAX_VALUE))
					);
					panel3Layout.setVerticalGroup(
						panel3Layout.createParallelGroup()
							.addGroup(panel3Layout.createSequentialGroup()
								.addContainerGap()
								.addComponent(logoutButton)
								.addContainerGap(298, Short.MAX_VALUE))
					);
				}
				tabbedPane1.addTab("Others", panel3);
			}

			//======== scrollPane1 ========
			{
				scrollPane1.setViewportView(outputTextArea);
			}

			//======== innerAdminPanel ========
			{

				GroupLayout innerAdminPanelLayout = new GroupLayout(innerAdminPanel);
				innerAdminPanel.setLayout(innerAdminPanelLayout);
				innerAdminPanelLayout.setHorizontalGroup(
					innerAdminPanelLayout.createParallelGroup()
						.addGap(0, 544, Short.MAX_VALUE)
				);
				innerAdminPanelLayout.setVerticalGroup(
					innerAdminPanelLayout.createParallelGroup()
						.addGap(0, 0, Short.MAX_VALUE)
				);
			}

			GroupLayout panel4Layout = new GroupLayout(panel4);
			panel4.setLayout(panel4Layout);
			panel4Layout.setHorizontalGroup(
				panel4Layout.createParallelGroup()
					.addGroup(panel4Layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(panel4Layout.createParallelGroup()
							.addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 760, Short.MAX_VALUE)
							.addGroup(panel4Layout.createSequentialGroup()
								.addComponent(tabbedPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(innerAdminPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addContainerGap())
			);
			panel4Layout.setVerticalGroup(
				panel4Layout.createParallelGroup()
					.addGroup(GroupLayout.Alignment.TRAILING, panel4Layout.createSequentialGroup()
						.addGroup(panel4Layout.createParallelGroup()
							.addComponent(tabbedPane1)
							.addComponent(innerAdminPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
						.addContainerGap())
			);
		}
		add(panel4, BorderLayout.CENTER);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	private JPanel panel4;
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JButton addUserButton;
	private JButton modifyUserButton;
	private JButton deleteuserButton;
	private JPanel panel2;
	private JButton button4;
	private JButton deleteAccountButton;
	private JPanel panel3;
	private JButton logoutButton;
	private JScrollPane scrollPane1;
	private JTextArea outputTextArea;
	public static JPanel innerAdminPanel;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
