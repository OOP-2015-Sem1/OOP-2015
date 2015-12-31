/*
 * Created by JFormDesigner on Wed Dec 30 19:41:40 EET 2015
 */

package gui;

import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

/**
 * @author Gergo Szentannai
 */
public class LoggedInUser extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public LoggedInUser() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		button2 = new JButton();
		passwordField1 = new JPasswordField();
		textField2 = new JTextField();
		textPane1 = new JTextPane();
		textPane2 = new JTextPane();
		scrollPane1 = new JScrollPane();
		textPane3 = new JTextPane();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//---- button2 ----
		button2.setText("Authentificate");

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

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(81, 81, 81)
					.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
							.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(layout.createSequentialGroup()
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18, 18, 18)
							.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(textField2, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
								.addComponent(passwordField1, GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
							.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
							.addComponent(button2)
							.addGap(41, 41, 41))))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
					.addContainerGap(108, Short.MAX_VALUE)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
						.addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
					.addGroup(layout.createParallelGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
							.addComponent(button2)
							.addComponent(passwordField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(textPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(53, 53, 53)
					.addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(48, 48, 48))
		);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	private JButton button2;
	private JPasswordField passwordField1;
	private JTextField textField2;
	private JTextPane textPane1;
	private JTextPane textPane2;
	private JScrollPane scrollPane1;
	private JTextPane textPane3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
