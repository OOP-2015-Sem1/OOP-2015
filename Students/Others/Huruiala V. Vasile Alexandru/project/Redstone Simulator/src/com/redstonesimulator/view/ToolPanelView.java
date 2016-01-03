package com.redstonesimulator.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import javax.swing.border.BevelBorder;

import com.redstonesimulator.controller.MouseTool;
import com.redstonesimulator.model.block.BlockID;

public class ToolPanelView extends JPanel {
	private static final long serialVersionUID = 4706332443857567727L;

	private JToggleButton gridButton;
	private JButton zoomInButton;
	private JButton zoomOutButton;

	private JComboBox<String> blockList;

	private JPanel mouseToolPanel;
	private ButtonGroup mouseToolButtonGroup;
	private JToggleButton[] mouseToolButtons;
	private JTextArea blockDetailsTextArea;

	public ToolPanelView() {
		super();
		setBackground(Color.yellow);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));

		SpringLayout sl = new SpringLayout();
		setLayout(sl);

		gridButton = new JToggleButton("Grid");
		sl.putConstraint(SpringLayout.NORTH, gridButton, 0, SpringLayout.NORTH, this);
		sl.putConstraint(SpringLayout.EAST, gridButton, 0, SpringLayout.EAST, this);
		sl.putConstraint(SpringLayout.WEST, gridButton, 0, SpringLayout.WEST, this);

		add(gridButton);

		zoomInButton = new JButton("Zoom In");
		sl.putConstraint(SpringLayout.NORTH, zoomInButton, 15, SpringLayout.SOUTH, gridButton);
		sl.putConstraint(SpringLayout.WEST, zoomInButton, 0, SpringLayout.WEST, this);
		sl.putConstraint(SpringLayout.EAST, zoomInButton, 0, SpringLayout.EAST, this);
		add(zoomInButton);

		zoomOutButton = new JButton("Zoom Out");
		sl.putConstraint(SpringLayout.NORTH, zoomOutButton, 0, SpringLayout.SOUTH, zoomInButton);
		sl.putConstraint(SpringLayout.WEST, zoomOutButton, 0, SpringLayout.WEST, this);
		sl.putConstraint(SpringLayout.EAST, zoomOutButton, 0, SpringLayout.EAST, this);
		add(zoomOutButton);

		blockList = new JComboBox<>(BlockID.getDisplayNames());
		sl.putConstraint(SpringLayout.NORTH, blockList, 15, SpringLayout.SOUTH, zoomOutButton);
		sl.putConstraint(SpringLayout.WEST, blockList, 0, SpringLayout.WEST, this);
		sl.putConstraint(SpringLayout.EAST, this, 0, SpringLayout.EAST, blockList);
		add(blockList);

		// Mouse Tools
		MouseTool[] mouseTools = MouseTool.values();
		int mouseToolsLength = mouseTools.length;

		mouseToolPanel = new JPanel(new GridLayout(mouseToolsLength, 1));
		mouseToolButtonGroup = new ButtonGroup();
		mouseToolButtons = new JToggleButton[mouseToolsLength];

		for (int index = 0; index < mouseToolsLength; index++) {
			MouseTool mouseTool = mouseTools[index];
			JToggleButton toggleButton = new JToggleButton(mouseTool.getDisplayName());
			if (mouseTool == MouseTool.select) {
				toggleButton.setSelected(true);
			} else {
				toggleButton.setSelected(false);
			}
			mouseToolButtonGroup.add(toggleButton);
			mouseToolPanel.add(toggleButton);

			mouseToolButtons[index] = toggleButton;
		}

		sl.putConstraint(SpringLayout.NORTH, mouseToolPanel, 15, SpringLayout.SOUTH, blockList);
		sl.putConstraint(SpringLayout.WEST, mouseToolPanel, 0, SpringLayout.WEST, this);
		sl.putConstraint(SpringLayout.EAST, mouseToolPanel, 0, SpringLayout.EAST, this);
		add(mouseToolPanel);

		blockDetailsTextArea = new JTextArea();
		blockDetailsTextArea.setEditable(false);
		sl.putConstraint(SpringLayout.NORTH, blockDetailsTextArea, 15, SpringLayout.SOUTH, mouseToolPanel);
		sl.putConstraint(SpringLayout.WEST, blockDetailsTextArea, 0, SpringLayout.WEST, this);
		sl.putConstraint(SpringLayout.EAST, blockDetailsTextArea, 0, SpringLayout.EAST, this);
		add(blockDetailsTextArea);
	}

	public JToggleButton getGridButton() {
		return gridButton;
	}

	public int getBlockListSelectedIndex() {
		return blockList.getSelectedIndex();
	}

	public void addGridButtonActionListener(ActionListener actionListener) {
		gridButton.addActionListener(actionListener);
	}

	public void addZoomInButtonActionListener(ActionListener actionListener) {
		zoomInButton.addActionListener(actionListener);
	}

	public void addZoomOutButtonActionListener(ActionListener actionListener) {
		zoomOutButton.addActionListener(actionListener);
	}

	public void addBlockListActionListener(ActionListener actionListener) {
		blockList.addActionListener(actionListener);
	}

	public void addMouseToolButtonActionListener(MouseTool mouseTool, ActionListener actionListener) {
		mouseToolButtons[mouseTool.getMouseToolIndex()].addActionListener(actionListener);
	}

	public void setBlockDetails(ArrayList<String> blockDetails) {
		StringBuilder details = new StringBuilder();
		for (String line : blockDetails) {
			details.append(line + "\n");
		}
		blockDetailsTextArea.setText(details.toString());
	}
}
