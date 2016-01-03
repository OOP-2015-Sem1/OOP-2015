package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.ScoreFileReader;

public class Login {
	public static JFrame jframe;
	private JPanel mainPanel;
	private JButton ok;
	public static JTextField user;
	public static String username;
	public static int userScore;
	public static boolean start;

	public Login() {
		jframe = new JFrame();
		jframe.setSize(200, 200);
		jframe.setTitle("LOGIN");
		jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());
		user = new JTextField(10);
		user.setText("");
		mainPanel.add(user);
		ok = new JButton();
		ok.setText("OK");
		mainPanel.add(ok);
		jframe.add(mainPanel);
		jframe.setVisible(true);
		ok.addActionListener(new ButtonListener());
		userScore = 0;
		start = false;
	}

}

class ButtonListener implements ActionListener {
	ButtonListener() {
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("OK")) {
			Login.username = Login.user.getText();
			System.out.println(Login.username);
			ScoreFileReader r = new ScoreFileReader();
			r.openFile();
			r.readFille();
			Login.start = true;
			Login.jframe.dispose();
			Game.jframe.setVisible(true);
			Game.game.setFocusable(true);
			Game.game.requestFocusInWindow();
		}
	}
}
