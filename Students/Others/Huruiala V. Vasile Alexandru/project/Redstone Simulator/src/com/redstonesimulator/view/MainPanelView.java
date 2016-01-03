package com.redstonesimulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MainPanelView extends JPanel {
	private static final long serialVersionUID = -2397488648479943749L;

	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newFileMenu;
	private JMenuItem saveFileMenu;
	private JMenuItem loadFileMenu;
	private JMenuItem exitFileMenu;

	private ToolPanelView toolPanelView;
	private SimulationPanelView simulationPanelView;

	public MainPanelView() {
		super();
		setBackground(Color.GRAY);
		setLayout(new BorderLayout());

		// Menu
		this.menuBar = new JMenuBar();

		// File
		this.fileMenu = new JMenu("File");
		this.newFileMenu = new JMenuItem("New");
		this.fileMenu.add(this.newFileMenu);
		this.fileMenu.addSeparator();
		this.saveFileMenu = new JMenuItem("Save");
		this.fileMenu.add(this.saveFileMenu);
		this.loadFileMenu = new JMenuItem("Load");
		this.fileMenu.add(this.loadFileMenu);
		this.fileMenu.addSeparator();
		this.exitFileMenu = new JMenuItem("Exit");
		this.fileMenu.add(this.exitFileMenu);
		this.menuBar.add(this.fileMenu);

		add(this.menuBar, BorderLayout.NORTH);

		this.toolPanelView = new ToolPanelView();
		add(this.toolPanelView, BorderLayout.WEST);
		this.simulationPanelView = new SimulationPanelView();
		add(this.simulationPanelView, BorderLayout.CENTER);
	}

	public void addNewFileMenuActionListener(ActionListener actionListener) {
		this.newFileMenu.addActionListener(actionListener);
	}

	public void addSaveFileMenuActionListener(ActionListener actionListener) {
		this.saveFileMenu.addActionListener(actionListener);
	}

	public void addLoadFileMenuActionListener(ActionListener actionListener) {
		this.loadFileMenu.addActionListener(actionListener);
	}

	public void addExitFileMenuActionListener(ActionListener actionListener) {
		this.exitFileMenu.addActionListener(actionListener);
	}

	public ToolPanelView getToolPanelView() {
		return toolPanelView;
	}

	public SimulationPanelView getSimulationPanelView() {
		return simulationPanelView;
	}
}
