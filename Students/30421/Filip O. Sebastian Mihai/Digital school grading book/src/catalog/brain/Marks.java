package catalog.brain;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import catalog.ui.StudentsFromEachGradePanel;

public class Marks implements ActionListener {
	public JPanel MarksPanel = new JPanel();
	public JPanel[] panel = new JPanel[Main.nrOfStudents];
	private JPanel[] Materii = new JPanel[8];
	private JButton[] Plus = new JButton[8];
	public Marks() {
		JLabel labell = null;
		for (int j = 0; j < Main.nrOfStudents; j++) {
			panel[j]= new JPanel();
			panel[j].setLayout(new GridLayout(8, 3));
			for (int i = 0; i <8; i++) {
				Materii[i] = new JPanel();
				Materii[i].setLayout(new GridLayout(1,1));
				if(i==1)
					labell = new JLabel("Romana");
				if(i==2)
					labell = new JLabel("Istorie");
				if(i==3)
					labell = new JLabel("Geografie");
				if(i==4)
					labell = new JLabel("Diregentie");
				if(i==5)
					labell = new JLabel("Fizica");
				if(i==6)
					labell = new JLabel("Biologie");
				if(i==7)
					labell = new JLabel("Literatura");
				if(i==0)
					labell = new JLabel("matematica");
				Materii[i].add(labell);
				Plus[i] = new JButton("ADD");
				Plus[i].addActionListener(this);
				panel[j].add(Materii[i]);
				panel[j].add(Plus[i]);
				panel[j].setVisible(true);
				/*
				MarksPanel.setVisible(true);
				MarksPanel.add(panel[j]);
				*/
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new AllDataGUI();
	}
}
