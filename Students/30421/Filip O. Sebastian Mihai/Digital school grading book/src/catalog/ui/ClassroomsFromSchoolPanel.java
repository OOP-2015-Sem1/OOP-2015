package catalog.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import catalog.brain.Classroom;

public class ClassroomsFromSchoolPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 0000;
	public static int WhatGradeToShow = 12;
	public StudentsFromEachGradePanel PanelForGrades = new StudentsFromEachGradePanel();
	public JButton[] ClassroomsButtons;
	public JPanel StudentsFromClassrooms = new JPanel();
	public int whatClassroomToShow;
	public ClassroomsFromSchoolPanel() {
		// this.RFFLBL = RFFLBL;
		setLayout(new GridLayout(12, 1));
		ClassroomsButtons = new JButton[12];
		setVisible(false);
		for (int i = 0; i < 12; i++) {
			ClassroomsButtons[i] = new JButton(Classroom.Classrooms.get(i));
			add(ClassroomsButtons[i]);
			ClassroomsButtons[i].addActionListener(this);
		}
		//StudentsFromClassrooms.getLayout();
		for (int i = 1; i < 13; i++)
			StudentsFromClassrooms.add(PanelForGrades.StudentsPanelFromClasroom[i]);
		StudentsFromClassrooms.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		for (int j = 0; j < 12; j++) {
			if (e.getSource() == ClassroomsButtons[j]) {
				for (int i = 1; i < 13; i++) {
					if (i == j + 1){
						PanelForGrades.StudentsPanelFromClasroom[i].setVisible(true);
						whatClassroomToShow = i;
						//System.out.println(whatClassroomToShow);
					}
					else
						PanelForGrades.StudentsPanelFromClasroom[i].setVisible(false);
				}
			}
		}
	}
}
// RFFLBL.Classrooms.get(i)