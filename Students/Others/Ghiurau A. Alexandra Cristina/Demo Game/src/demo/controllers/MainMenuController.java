package demo.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import demo.views.FirstPanelFrame;
import demo.views.GameFrame;

public class MainMenuController extends AbstractController {

	public MainMenuController(FirstPanelFrame frame, boolean hasBackButton) {
		super(frame, hasBackButton);

		frame.setEasyButtonActionListener(new EasyButtonActionListener());
		frame.setMediumButtonActionListener(new MediumButtonActionListener());
		frame.setHardButtonActionListener(new HardButtonActionListener());
	}

	private class EasyButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			new GameController(new GameFrame("Easy Stage"), true);
		}

	}

	private class MediumButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			JOptionPane.showMessageDialog(frame, "message");

		}
	}

	private class HardButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
