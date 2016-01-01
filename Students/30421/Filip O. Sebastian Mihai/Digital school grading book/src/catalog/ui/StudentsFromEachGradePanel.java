package catalog.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import catalog.brain.Main;
import catalog.brain.Marks;
import catalog.brain.Students;

public class StudentsFromEachGradePanel implements ActionListener {
	public JPanel[] StudentsPanelFromClasroom = new JPanel[13];
	public int numar;
	private JButton[] StudentButtons;
	private int j = 1;

	@SuppressWarnings({ "unchecked" })
	public StudentsFromEachGradePanel() {
		StudentButtons = new JButton[500];
		for (int i = 1; i < 13; i++) {
			StudentsPanelFromClasroom[i] = new JPanel();
			ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom[i], Students.studentsFromGrade[i]);
		}
	}

	private void ToGetButtonsOutOfArrayLists(JPanel PanelForClassroom, ArrayList<String> grade) {
		Integer StringToSize = Integer.valueOf(grade.get(0));
		PanelForClassroom.setLayout(new GridLayout(StringToSize - 1, 1));
		/*
		 * for (int i = 1; i < StringToSize; i++) { StudentButtons[i] = new
		 * JButton(grade.get(i)); StudentButtons[i].setSize(1, 1);
		 * StudentButtons[i].addActionListener(this);
		 * PanelForClassroom.add(StudentButtons[i]);
		 * PanelForClassroom.setVisible(false); }
		 */
		int i = 1;
		while (i != StringToSize) {
			StudentButtons[j] = new JButton(grade.get(i));
			StudentButtons[j].addActionListener(this);
			PanelForClassroom.add(StudentButtons[j]);
			PanelForClassroom.setVisible(false);
			i++;
			j++;
		}
	}

	public void actionPerformed(ActionEvent e) {
		//int numar;
		for (int i = 0; i < Main.nrOfStudents; i++) {
			if (e.getSource() == StudentButtons[i]) {
				System.out.println("button " + i);
				numar = i;
				System.out.println("numar1 = "+numar);
				new Marks();
				// for(int j=0;j<Main.nrOfStudents;j++){
				// if(j!=i){
				// Marks notePanel = new Marks();
				// notePanel.panel[i].setVisible(true);
				// }
				// else{
				// notePanel.panel[j].setVisible(true);
				// System.out.println(" fau " + j);
				// }
				// }

			}

		}
	}
}
