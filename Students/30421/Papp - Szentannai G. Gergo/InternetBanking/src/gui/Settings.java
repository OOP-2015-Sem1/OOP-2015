/*
 * Created by JFormDesigner on Sat Jan 02 22:55:23 EET 2016
 */

package gui;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Gergo Szentannai
 */
public class Settings extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Settings() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai

		//======== this ========

		// JFormDesigner evaluation mark
		setBorder(new javax.swing.border.CompoundBorder(
			new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
				"JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
				java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


		GroupLayout layout = new GroupLayout(this);
		setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup()
				.addGap(0, 400, Short.MAX_VALUE)
		);
		layout.setVerticalGroup(
			layout.createParallelGroup()
				.addGap(0, 300, Short.MAX_VALUE)
		);
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Gergo Szentannai
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
