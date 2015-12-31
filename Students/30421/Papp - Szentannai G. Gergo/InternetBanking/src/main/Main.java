package main;

import java.awt.EventQueue;

import gui.MainFrame;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				MainFrame frame = new MainFrame();
				frame.setVisible(true);

			}
		});
	}

}
