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
	public JPanel PanelForGrade1 = new JPanel();
	public JPanel PanelForGrade2 = new JPanel();
	public JPanel PanelForGrade3 = new JPanel();
	public JPanel PanelForGrade4 = new JPanel();
	public JPanel PanelForGrade5 = new JPanel();
	public JPanel PanelForGrade6 = new JPanel();
	public JPanel PanelForGrade7 = new JPanel();
	public JPanel PanelForGrade8 = new JPanel();
	public JPanel PanelForGrade9 = new JPanel();
	public JPanel PanelForGrade10 = new JPanel();
	public JPanel PanelForGrade11 = new JPanel();
	public JPanel PanelForGrade12 = new JPanel();

	public JPanel StudentsFromClassrooms = new JPanel();

	public ClassroomsFromSchoolPanel() {
		// this.RFFLBL = RFFLBL;
		setLayout(new GridLayout(12, 1));
		ClassroomsButtons = new JButton[12];
		setVisible(false);
		// System.out.println(fau.Classrooms.size());
		for (int i = 0; i < 12; i++) {
			ClassroomsButtons[i] = new JButton(Main.Classrooms.get(i));
			add(ClassroomsButtons[i]);
			ClassroomsButtons[i].addActionListener(this);
		}
		StudentsFromClassrooms.setSize(getMaximumSize());
		PanelForGrade1 = PanelForGrades.StudentsPanelFromClasroom1;
		PanelForGrade1.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade1);
		PanelForGrade2 = PanelForGrades.StudentsPanelFromClasroom2;
		PanelForGrade2.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade2);
		PanelForGrade3 = PanelForGrades.StudentsPanelFromClasroom3;
		StudentsFromClassrooms.add(PanelForGrade3);
		PanelForGrade3.setVisible(false);
		PanelForGrade4 = PanelForGrades.StudentsPanelFromClasroom4;
		PanelForGrade4.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade4);
		PanelForGrade5 = PanelForGrades.StudentsPanelFromClasroom5;
		PanelForGrade5.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade5);
		PanelForGrade6 = PanelForGrades.StudentsPanelFromClasroom6;
		PanelForGrade6.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade6);
		PanelForGrade7 = PanelForGrades.StudentsPanelFromClasroom7;
		PanelForGrade7.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade7);
		PanelForGrade8 = PanelForGrades.StudentsPanelFromClasroom8;
		PanelForGrade8.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade8);
		PanelForGrade9 = PanelForGrades.StudentsPanelFromClasroom9;
		PanelForGrade9.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade9);
		PanelForGrade10 = PanelForGrades.StudentsPanelFromClasroom10;
		PanelForGrade10.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade10);
		PanelForGrade11 = PanelForGrades.StudentsPanelFromClasroom11;
		PanelForGrade11.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade11);
		PanelForGrade12 = PanelForGrades.StudentsPanelFromClasroom12;
		PanelForGrade12.setVisible(false);
		StudentsFromClassrooms.add(PanelForGrade12);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == ClassroomsButtons[0]) {
			WhatGradeToShow = 1;
			PanelForGrade1.setVisible(true);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[1]) {
			WhatGradeToShow = 2;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(true);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[2]) {
			WhatGradeToShow = 3;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(true);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[3]) {
			WhatGradeToShow = 4;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(true);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[4]) {
			WhatGradeToShow = 5;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(true);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[5]) {
			WhatGradeToShow = 6;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(true);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[6]) {
			WhatGradeToShow = 7;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(true);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[7]) {
			WhatGradeToShow = 8;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(true);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[8]) {
			WhatGradeToShow = 9;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(true);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[9]) {
			WhatGradeToShow = 10;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(true);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[10]) {
			WhatGradeToShow = 11;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(true);
			PanelForGrade12.setVisible(false);
		}
		if (e.getSource() == ClassroomsButtons[11]) {
			WhatGradeToShow = 12;
			PanelForGrade1.setVisible(false);
			PanelForGrade2.setVisible(false);
			PanelForGrade3.setVisible(false);
			PanelForGrade4.setVisible(false);
			PanelForGrade5.setVisible(false);
			PanelForGrade6.setVisible(false);
			PanelForGrade7.setVisible(false);
			PanelForGrade8.setVisible(false);
			PanelForGrade9.setVisible(false);
			PanelForGrade10.setVisible(false);
			PanelForGrade11.setVisible(false);
			PanelForGrade12.setVisible(true);
		}
	}
}
// RFFLBL.Classrooms.get(i)