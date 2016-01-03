package com.redstonesimulator.view;

import java.awt.HeadlessException;

import javax.swing.JFrame;

import com.redstonesimulator.controller.MainPanelController;

public class RedstoneFrame extends JFrame {
	private static final long serialVersionUID = 8165994899146259387L;

	private MainPanelView mainPanelView;
	private MainPanelController mainPanelController;

	public RedstoneFrame(String arg0) throws HeadlessException {
		super(arg0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setExtendedState(JFrame.MAXIMIZED_BOTH);

		mainPanelView = new MainPanelView();
		setContentPane(mainPanelView);
		pack();

		setVisible(true);

		this.mainPanelController = new MainPanelController(this, this.mainPanelView);
	}
}
