/*
 * Created by JFormDesigner on Fri Jan 01 18:09:11 EET 2016
 */

package gui;

import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Gergo Szentannai
 */
public class Admin_home extends JPanel {
	public Admin_home() {
		initComponents();
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Gergo Szentannai
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		menuBar1 = new JMenuBar();
		menu2 = new JMenu();
		menu3 = new JMenu();
		menu4 = new JMenu();
		panel2 = new JPanel();
		menuBar2 = new JMenuBar();
		menu5 = new JMenu();
		menu6 = new JMenu();
		panel3 = new JPanel();

		//======== this ========

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

				//======== menuBar1 ========
				{

					//======== menu2 ========
					{
						menu2.setText("Add user");
					}
					menuBar1.add(menu2);

					//======== menu3 ========
					{
						menu3.setText("Modify user");
					}
					menuBar1.add(menu3);

					//======== menu4 ========
					{
						menu4.setText("Delete user");
					}
					menuBar1.add(menu4);
				}

				GroupLayout panel1Layout = new GroupLayout(panel1);
				panel1.setLayout(panel1Layout);
				panel1Layout.setHorizontalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(menuBar1, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
							.addContainerGap())
				);
				panel1Layout.setVerticalGroup(
					panel1Layout.createParallelGroup()
						.addGroup(panel1Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(menuBar1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(218, Short.MAX_VALUE))
				);
			}
			tabbedPane1.addTab("Users", panel1);

			//======== panel2 ========
			{

				//======== menuBar2 ========
				{

					//======== menu5 ========
					{
						menu5.setText("Add account");
					}
					menuBar2.add(menu5);

					//======== menu6 ========
					{
						menu6.setText("Delete account");
					}
					menuBar2.add(menu6);
				}

				GroupLayout panel2Layout = new GroupLayout(panel2);
				panel2.setLayout(panel2Layout);
				panel2Layout.setHorizontalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(menuBar2, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
							.addContainerGap())
				);
				panel2Layout.setVerticalGroup(
					panel2Layout.createParallelGroup()
						.addGroup(panel2Layout.createSequentialGroup()
							.addContainerGap()
							.addComponent(menuBar2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(218, Short.MAX_VALUE))
				);
			}
			tabbedPane1.addTab("Accounts", panel2);

			//======== panel3 ========
			{

				GroupLayout panel3Layout = new GroupLayout(panel3);
				panel3.setLayout(panel3Layout);
				panel3Layout.setHorizontalGroup(
					panel3Layout.createParallelGroup()
						.addGap(0, 375, Short.MAX_VALUE)
				);
				panel3Layout.setVerticalGroup(
					panel3Layout.createParallelGroup()
						.addGap(0, 250, Short.MAX_VALUE)
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
	private JMenuBar menuBar1;
	private JMenu menu2;
	private JMenu menu3;
	private JMenu menu4;
	private JPanel panel2;
	private JMenuBar menuBar2;
	private JMenu menu5;
	private JMenu menu6;
	private JPanel panel3;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
