package main_class;

import java.awt.EventQueue;
import javax.swing.JFrame;

import graphicalUserInterface.Board;

public class MainClass extends JFrame {

	private static final long serialVersionUID = -855798971391057737L;

	public MainClass() {

		initUI();
	}

	private void initUI() {

		add(new Board());

		setResizable(false);
		pack();

		setTitle("Dodger");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {

				MainClass ui = new MainClass();
				ui.setVisible(true);

			}
		});
	}
}