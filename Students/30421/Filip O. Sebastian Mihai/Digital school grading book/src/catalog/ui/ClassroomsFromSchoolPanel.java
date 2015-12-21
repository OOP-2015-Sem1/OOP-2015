package catalog.ui;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import catalog.brain.Main;

public class ClassroomsFromSchoolPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 0000;
	Container c;
	public static int WhatGradeToShow = 12;
	private StudentsFromEachGradePanel PanelForGrades = new StudentsFromEachGradePanel();
	private JButton[] ClassroomsButtons;
	public JPanel[] PanelForGrade = new JPanel[13];
	public JPanel StudentsFromClassrooms = new JPanel();

	public ClassroomsFromSchoolPanel() {
		// this.RFFLBL = RFFLBL;
		setLayout(new GridLayout(12, 1));
		ClassroomsButtons = new JButton[12];
		setVisible(false);
		// System.out.println(fau.Classrooms.size());
		for (int i = 0; i < 12; i++) {
			ClassroomsButtons[i] = new JButton(Main.stud.Classrooms.get(i));
			add(ClassroomsButtons[i]);
			ClassroomsButtons[i].addActionListener(this);
		}
		// StudentsFromClassrooms.setLayout(new GridLayout(1,1));
		for (int i = 0; i < 13; i++) {
			PanelForGrade[i] = new JPanel();
			PanelForGrade[i].setVisible(false);
			PanelForGrade[i] = PanelForGrades.StudentsPanelFromClasroom[i];
		}

		for (int i = 1; i < 13; i++)
			StudentsFromClassrooms.add(PanelForGrade[i]);
		StudentsFromClassrooms.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int j = 0; j < 12; j++) {
			if (e.getSource() == ClassroomsButtons[j]) {
				for (int i = 1; i < 13; i++) {
					if (i == j+1)
						PanelForGrade[i].setVisible(true);
					else
						PanelForGrade[i].setVisible(false);
				}
			}
		}
	}
}
// RFFLBL.Classrooms.get(i)