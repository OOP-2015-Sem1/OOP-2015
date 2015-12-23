package demo.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import demo.views.MainFrame;
import demo.views.utilities.FrameStack;

public class AbstractController {

	protected MainFrame frame;

	public AbstractController(MainFrame frame, boolean hasBackButton) {
		this.frame = frame;
		if (hasBackButton) {
			frame.setToolBar(new BackButtonListener());
		}
	}

	private class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
			FrameStack.getInstance().pop();
		}
	}
}
