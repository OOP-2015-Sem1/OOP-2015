/*
 * Created by JFormDesigner on Wed Dec 30 19:44:46 EET 2015
 */

package gui;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Gergo Szentannai
 */
public class MainFrame extends JPanel {
	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		button1 = new JButton();
		button2 = new JButton();
		textPane1 = new JTextPane();

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		//---- button1 ----
		button1.setText("Log in");

		//---- button2 ----
		button2.setText("Admins");

		//---- textPane1 ----
		textPane1.setText("kjfiejrn fviwje rijfw e");

		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGroup(layout.createParallelGroup()
						.addGroup(layout.createSequentialGroup()
							.addGap(87, 87, 87)
							.addComponent(button1)
							.addGap(96, 96, 96)
							.addComponent(button2))
						.addGroup(layout.createSequentialGroup()
							.addGap(97, 97, 97)
							.addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(89, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGroup(layout.createSequentialGroup()
					.addGap(131, 131, 131)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
						.addComponent(button1)
						.addComponent(button2))
					.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
					.addComponent(textPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(55, 55, 55))
		);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	private JButton button1;
	private JButton button2;
	private JTextPane textPane1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
