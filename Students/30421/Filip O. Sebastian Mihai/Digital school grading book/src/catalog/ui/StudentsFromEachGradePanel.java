package catalog.ui;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import catalog.brain.Students;

public class StudentsFromEachGradePanel {
	public JPanel[] StudentsPanelFromClasroom = new JPanel[13];
	private JButton[] StudentButtons;

	@SuppressWarnings({ "unchecked" })
	public StudentsFromEachGradePanel() {
		StudentButtons = new JButton[35];
		for (int i = 1; i < 13; i++) {
			StudentsPanelFromClasroom[i] = new JPanel();
			ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom[i], Students.studentsFromGrade[i]);
		}
	}

	private void ToGetButtonsOutOfArrayLists(JPanel PanelForClassroom, ArrayList<String> grade) {
		Integer StringToSize = Integer.valueOf(grade.get(0));
		PanelForClassroom.setLayout(new GridLayout(StringToSize - 1, 1));
		for (int i = 1; i < StringToSize; i++) {
			StudentButtons[i] = new JButton(grade.get(i));
			StudentButtons[i].setSize(1, 1);
			PanelForClassroom.add(StudentButtons[i]);
			PanelForClassroom.setVisible(false);
		}
	}
}
