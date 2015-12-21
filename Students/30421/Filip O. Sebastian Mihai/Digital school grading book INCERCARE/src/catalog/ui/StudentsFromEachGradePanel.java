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

public class StudentsFromEachGradePanel implements ActionListener {
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

	public JPanel fau = new JPanel();
	private JButton[] StudentButtons;
	public StudentsFromEachGradePanel() {
		
		fau.setVisible(false);
		
		StudentButtons = new JButton[35];
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom1,Main.stud.studentsFromGrade[1]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom2,Main.stud.studentsFromGrade[2]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom3,Main.stud.studentsFromGrade[3]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom4,Main.stud.studentsFromGrade[4]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom5,Main.stud.studentsFromGrade[5]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom6,Main.stud.studentsFromGrade[6]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom7,Main.stud.studentsFromGrade[7]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom8,Main.stud.studentsFromGrade[8]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom9,Main.stud.studentsFromGrade[9]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom10,Main.stud.studentsFromGrade[10]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom11,Main.stud.studentsFromGrade[11]);
		ToGetButtonsOutOfArrayLists(StudentsPanelFromClasroom12,Main.stud.studentsFromGrade[12]);
	}
	private void ToGetButtonsOutOfArrayLists (JPanel PanelForClassroom,ArrayList<String> grade){
		//Integer StringToSize = Integer.valueOf(grade.get(0));
		int StringToSize =35;
		PanelForClassroom.setLayout(new GridLayout(StringToSize - 1, 1));
		for (int i = 1; i < StringToSize; i++) {
			StudentButtons[i] = new JButton(grade.get(i));
			PanelForClassroom.add(StudentButtons[i]);
			StudentButtons[i].addActionListener(this);
		}
		PanelForClassroom.setVisible(false);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == StudentButtons[2]){
			JPanel hector = null;
			functie(hector);
		}
	}
	private void functie (JPanel panel){
		panel = new JPanel();
		panel.setBackground(Color.green);
		panel.setVisible(true);
		CatalogFrame.CatalogTheRealFrame.add(panel);
		
	}
}
