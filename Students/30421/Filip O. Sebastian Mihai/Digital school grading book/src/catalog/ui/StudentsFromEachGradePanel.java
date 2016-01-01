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
		for (int i = 0; i < Main.nrOfStudents; i++) {
			panelpanel[i]= new JPanel();
			panelpanel[i].setBackground(Color.BLACK);
			panelpanel[i].setVisible(false);
			MAR.add(panelpanel[i]);
		}
		MAR.setVisible(true);
	}

	private void ToGetButtonsOutOfArrayLists(JPanel PanelForClassroom, ArrayList<String> grade) {
		Integer StringToSize = Integer.valueOf(grade.get(0));
		PanelForClassroom.setLayout(new GridLayout(StringToSize - 1, 1));
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
	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < Main.nrOfStudents; i++){
			if (e.getSource() == StudentButtons[i]) {
				for (int j = 0; j < Main.nrOfStudents; j++) {
					if (j == i) {
						panelpanel[j].setVisible(true);
						System.out.println("button " + i);
					} else{
						panelpanel[j].setVisible(false);
					}
				}
			}
		}
	}
}
