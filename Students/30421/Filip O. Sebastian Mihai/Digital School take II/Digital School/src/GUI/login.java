package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class login {
	JFrame loginFrame;
	JButton buton1;
	JButton buton2;

	public login() {
	loginFrame= new JFrame();
	loginFrame.setLayout(null);
	loginFrame.setSize(1000, 1000);
	buton1 = new JButton("Log in as a student");
	buton1.setSize(buton1.getPreferredSize());
	buton1.setLocation(450, 400);
	buton1.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new StudentLogIn();
			loginFrame.setVisible(false);
		}
		
	});
	buton1.setVisible(true);
	loginFrame.add(buton1);
	
	
	buton2 = new JButton("Log in as a teacher");
	buton2.setSize(buton2.getPreferredSize());
	buton2.setLocation(450, 500);
	buton2.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new TeacherLogIn();
			loginFrame.setVisible(false);
		}
		
	});
	buton2.setVisible(true);
	loginFrame.add(buton2);
	loginFrame.setVisible(true);
	}
}
