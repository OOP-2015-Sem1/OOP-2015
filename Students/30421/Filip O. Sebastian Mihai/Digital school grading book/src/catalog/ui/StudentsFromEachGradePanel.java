/*
package catalog.ui;


import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import catalog.brain.Main;


public class StudentsFromGrade1 extends JPanel{
	private JButton[] ClassroomsButtons;
	private Main ReadingStringFromMain;
	
	
	public StudentsFromGrade1(){
		
		setLayout(new GridLayout(35,1));
		ClassroomsButtons = new JButton [12];
		setVisible(false);
		//System.out.println(fau.Classrooms.size());
		
		for( int i=0;i<12;i++)
		{
			ClassroomsButtons[i]=new JButton(ReadingStringFromMain.Classrooms.get(i));
			add(ClassroomsButtons[i]);
		}
		
	}
}
*/

package catalog.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import catalog.brain.Main;

public class StudentsFromEachGradePanel {
	public JPanel[] StudentsPanelFromClasroom = new JPanel[13];
	public JPanel fau = new JPanel();
	private JButton[] StudentButtons;
	public StudentsFromEachGradePanel() {
		
		fau.setVisible(false);
		
		StudentButtons = new JButton[35];
		for(int i = 1;i<13;i++){
			StudentsPanelFromClasroom[i] = new JPanel();
			ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom[i],Main.stud.studentsFromGrade[i]);
		}
	}
	private void ToGetButtonsOutOfArrayLists (JPanel PanelForClassroom,ArrayList<String> grade){
		Integer StringToSize = Integer.valueOf(grade.get(0));
		PanelForClassroom.setLayout(new GridLayout(StringToSize - 1, 1));
		for (int i = 1; i < StringToSize; i++) {
			StudentButtons[i] = new JButton(grade.get(i));
			PanelForClassroom.add(StudentButtons[i]);
			PanelForClassroom.setVisible(false);
		}
	}
}
