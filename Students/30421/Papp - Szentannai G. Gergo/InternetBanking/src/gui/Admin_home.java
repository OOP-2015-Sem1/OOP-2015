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

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();
		button3 = new JButton();
		panel2 = new JPanel();
		button4 = new JButton();
		button5 = new JButton();
		panel3 = new JPanel();
		logoutButton = new JButton();

		//======== this ========
		setPreferredSize(new Dimension(780, 480));
		setMaximumSize(new Dimension(780, 480));
		setMinimumSize(new Dimension(780, 480));

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//======== tabbedPane1 ========
		{

			//======== panel1 ========
			{

				//---- button1 ----
				button1.setText("Add user");

				//---- button2 ----
				button2.setText("Modify user");

				//---- button3 ----
				button3.setText("Delete user");

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(button2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button3, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(656, Short.MAX_VALUE))
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button1)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button2)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button3)
							.addContainerGap(338, Short.MAX_VALUE))
				);
			}
			tabbedPane1.addTab("Users", panel1);

			//======== panel2 ========
			{

				//---- button4 ----
				button4.setText("Add account");

				//---- button5 ----
				button5.setText("Delete account");

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addGroup(panel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(button5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(button4, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(640, Short.MAX_VALUE))
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(button4)
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
							.addComponent(button5)
							.addContainerGap(367, Short.MAX_VALUE))
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
							.addContainerGap(676, Short.MAX_VALUE))
				);
				panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
						.addGroup(panel3Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(logoutButton)
							.addContainerGap(396, Short.MAX_VALUE))
				);
			}
			tabbedPane1.addTab("Others", panel3);
		}

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane1)
					.addContainerGap())
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane1)
					.addContainerGap())
		);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JPanel panel2;
	private JButton button4;
	private JButton button5;
	private JPanel panel3;
	private JButton logoutButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
