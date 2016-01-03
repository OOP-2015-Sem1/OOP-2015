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
	public JLabel Materii = new JLabel();
	public JButton[] Plus = new JButton[8];
	// StudentsFromEachGradePanel stud = new StudentsFromEachGradePanel();
	

	public Marks() {
		for(int i=0;i<Main.nrOfStudents;i++){
			panel[i]=new JPanel();
			panel[i].setLayout(new GridLayout(8,2));
			for(int j =0;j<8;j++){
				if(j==1){
					Materii = new JLabel("Romanian Language");
					Plus[j]=new JButton("Add Romanian Language Marks");
				}
				if(j==2){
					Materii = new JLabel("History");
					Plus[j]=new JButton("Add History Marks");
				}	
				if(j==3){
					Materii = new JLabel("Geography");
					Plus[j]=new JButton("Add Geography Marks");
				}	
				if(j==4){
					Materii = new JLabel("Sport");
					Plus[j]=new JButton("Add Sport Marks");
				}
				if(j==5){
					Materii = new JLabel("Physics");
					Plus[j]=new JButton("Add Physics Marks");
				}	
				if(j==6){
					Materii = new JLabel("Biology");
					Plus[j]=new JButton("Add Biology Marks");
				}
				if(j==7){
					Materii = new JLabel("English");
					Plus[j]=new JButton("Add English Marks");
				}	
				if(j==0){
					Materii = new JLabel("Math");
					Plus[j]=new JButton("Add Math Marks");
				}		
				Materii.setVisible(true);
				panel[i].add(Materii);
				Plus[j].addActionListener(this);
				Plus[j].setVisible(true);
				panel[i].add(Plus[j]);
			}
			panel[i].setVisible(true);
		}
	}


	@Override
	public void actionPerformed(ActionEvent a) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<8;i++)
		if(a.getSource() == Plus[1]){
			new AllDataGUI(Main.numar, i);
			System.out.println("aici");
		}
		//new AllDataGUI(1, 0);    Daca decomentez asta imi merge dar doar pentru
		//elevul numarul 1 la materia matematica. Nu inteleg de ce nu merge si
		// cu action listner
	}
}
