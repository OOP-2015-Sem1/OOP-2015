package catalog.ui;

import java.awt.Color;
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
	public JPanel MAR = new JPanel();
	public Marks fau = new Marks();
	public JPanel[] panelpanel = new JPanel[Main.nrOfStudents];
	public JPanel[] StudentsPanelFromClasroom = new JPanel[13];
	public JButton[] StudentButtons;
	public int nrOfStudentButtons = 1;

	@SuppressWarnings({ "unchecked" })
	public StudentsFromEachGradePanel() {
		StudentButtons = new JButton[500];
		for (int i = 1; i < 13; i++) {
			StudentsPanelFromClasroom[i] = new JPanel();
			ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom[i], Students.studentsFromGrade[i]);
		}
		//MAR.setLayout(new GridLayout(10,10));
		for (int i = 0; i < Main.nrOfStudents; i++) {
			panelpanel[i]= new JPanel();
			panelpanel[i]=fau.panel[i];
			panelpanel[i].setVisible(false);
			MAR.add(panelpanel[i]);
		}
		panelpanel[1].setVisible(true);
		MAR.setVisible(true);
	}

	private void ToGetButtonsOutOfArrayLists(JPanel PanelForClassroom, ArrayList<String> grade) {
		Integer StringToSize = Integer.valueOf(grade.get(0));
		PanelForClassroom.setLayout(new GridLayout(StringToSize - 1, 1));
		int i = 1;
		while (i != StringToSize) {
			StudentButtons[nrOfStudentButtons] = new JButton(grade.get(i));
			StudentButtons[nrOfStudentButtons].addActionListener(this);
			PanelForClassroom.add(StudentButtons[nrOfStudentButtons]);
			PanelForClassroom.setVisible(false);
			i++;
			nrOfStudentButtons++;
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		panelpanel[1].setVisible(true);
		for (int i = 0; i < Main.nrOfStudents; i++){
			if (e.getSource() == StudentButtons[i]) {
				for (int j = 0; j < Main.nrOfStudents; j++) {
					if (j == i) {
						panelpanel[j].setVisible(true);
						// Nu intleg de ce nu merge partea asta cu ActionListener
						// In clasa ClassroomFroom... imi merge ok si am facut exact aceeasi chestie.
						// nu inteleg ce gresesc sau ce fac diferit
						System.out.println("button " + j);
						Main.numar = i;
					} else{
						panelpanel[j].setVisible(false);
					}
				}
			}
		}
	}
}
