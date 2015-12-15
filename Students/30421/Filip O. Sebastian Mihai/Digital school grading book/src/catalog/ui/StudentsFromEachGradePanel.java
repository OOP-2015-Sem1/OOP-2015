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

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import catalog.brain.Main;

public class StudentsFromEachGradePanel {
	public JPanel StudentsPanelFromClasroom1 = new JPanel();  
	public JPanel StudentsPanelFromClasroom2 = new JPanel();  
	public JPanel StudentsPanelFromClasroom3 = new JPanel();  
	public JPanel StudentsPanelFromClasroom4 = new JPanel();  
	public JPanel StudentsPanelFromClasroom5 = new JPanel();  
	public JPanel StudentsPanelFromClasroom6 = new JPanel();  
	public JPanel StudentsPanelFromClasroom7 = new JPanel();  
	public JPanel StudentsPanelFromClasroom8 = new JPanel();  
	public JPanel StudentsPanelFromClasroom9 = new JPanel();  
	public JPanel StudentsPanelFromClasroom10 = new JPanel();  
	public JPanel StudentsPanelFromClasroom11 = new JPanel();  
	public JPanel StudentsPanelFromClasroom12 = new JPanel(); 
	private JButton[] StudentButtons;
	public StudentsFromEachGradePanel() {
		StudentButtons = new JButton[35];
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom1,Main.Grade1);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom2,Main.Grade2);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom3,Main.Grade3);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom4,Main.Grade4);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom5,Main.Grade5);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom6,Main.Grade6);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom7,Main.Grade7);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom8,Main.Grade8);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom9,Main.Grade9);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom10,Main.Grade10);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom11,Main.Grade11);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom12,Main.Grade12);
	}
	private void ToGetButtonsOutOfArrayLists (JPanel PanelForClassroom,ArrayList<String> grade){
		Integer StringToSize = Integer.valueOf(grade.get(0));
		PanelForClassroom.setLayout(new GridLayout(StringToSize - 1, 1));
		for (int i = 1; i < StringToSize; i++) {
			StudentButtons[i] = new JButton(grade.get(i));
			PanelForClassroom.add(StudentButtons[i]);
		}
		PanelForClassroom.setVisible(false);
	}
}
