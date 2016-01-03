package catalog.brain;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Marks implements ActionListener{
	public JPanel[] panel = new JPanel[Main.nrOfStudents];
	public JFrame frame = new JFrame();
	public JLabel Subject = new JLabel();
	public JButton[] addMarks = new JButton[8];
	// StudentsFromEachGradePanel stud = new StudentsFromEachGradePanel();
	

	public Marks() {
		for(int i=0;i<Main.nrOfStudents;i++){
			panel[i]=new JPanel();
			panel[i].setLayout(new GridLayout(8,2));
			for(int j =0;j<8;j++){
				if(j==1){
					Subject = new JLabel("Romanian Language");
					addMarks[j]=new JButton("Add Romanian Language Marks");
				}
				if(j==2){
					Subject = new JLabel("History");
					addMarks[j]=new JButton("Add History Marks");
				}	
				if(j==3){
					Subject = new JLabel("Geography");
					addMarks[j]=new JButton("Add Geography Marks");
				}	
				if(j==4){
					Subject = new JLabel("Sport");
					addMarks[j]=new JButton("Add Sport Marks");
				}
				if(j==5){
					Subject = new JLabel("Physics");
					addMarks[j]=new JButton("Add Physics Marks");
				}	
				if(j==6){
					Subject = new JLabel("Biology");
					addMarks[j]=new JButton("Add Biology Marks");
				}
				if(j==7){
					Subject = new JLabel("English");
					addMarks[j]=new JButton("Add English Marks");
				}	
				if(j==0){
					Subject = new JLabel("Math");
					addMarks[j]=new JButton("Add Math Marks");
				}		
				Subject.setVisible(true);
				panel[i].add(Subject);
				addMarks[j].addActionListener(this);
				addMarks[j].setVisible(true);
				panel[i].add(addMarks[j]);
			}
			panel[i].setVisible(true);
		}
	}


	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		 new AddMarksToSubjects(1,0); // pentru test
		for(int i=0;i<8;i++)
		if(a.getSource() == addMarks[i]){
			new AddMarksToSubjects(Main.whichStudentButtonIsPressed, i);
		}
	}
}
