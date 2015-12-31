package main;

import java.awt.EventQueue;

import gui.MainFrame;

public class Main {

	// Status in needed to track the current frame that has to be displayed to
	// the user.
	private String status = "home";

	/**
	 * Initializes the MainFrame for first use. Displays welcome page.
	 */
	public Main() {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

			}
		});
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
