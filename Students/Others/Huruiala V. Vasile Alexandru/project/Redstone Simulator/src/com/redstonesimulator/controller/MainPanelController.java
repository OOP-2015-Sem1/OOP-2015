package com.redstonesimulator.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import com.redstonesimulator.model.World;
import com.redstonesimulator.model.WorldIO;
import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.BlockID;
import com.redstonesimulator.view.MainPanelView;
import com.redstonesimulator.view.RedstoneFrame;

import utilities.Direction;

public class MainPanelController {
	private RedstoneFrame redstoneFrame;
	private MainPanelView mainPanelView;

	private World world;

	// Tool panel
	private MouseTool selectedMouseTool;
	private BlockID selectedBlockID;

	// Simulation panel
	private int xMousePos;
	private int yMousePos;

	public MainPanelController(RedstoneFrame redstoneFrame, MainPanelView mainPanelView) {
		this.redstoneFrame = redstoneFrame;
		this.mainPanelView = mainPanelView;
		this.mainPanelView.getSimulationPanelView().setSelectedZ(1);

		if (!this.loadWorld()) {
			this.newWorld();
		}

		this.selectedBlockID = BlockID.air;

		// Main panel
		this.mainPanelView.addNewFileMenuActionListener(new NewFileMenuActionListener());
		this.mainPanelView.addSaveFileMenuActionListener(new SaveFileMenuActionListener());
		this.mainPanelView.addLoadFileMenuActionListener(new LoadFileMenuActionListener());
		this.mainPanelView.addExitFileMenuActionListener(new ExitFileMenuActionListener());

		// Tool panel
		this.setSelectedMouseTool(MouseTool.select);
		this.mainPanelView.getToolPanelView().addGridButtonActionListener(new GridButtonActionListener());
		this.mainPanelView.getToolPanelView().addZoomInButtonActionListener(new ZoomInButtonActionListener());
		this.mainPanelView.getToolPanelView().addZoomOutButtonActionListener(new ZoomOutButtonActionListener());
		this.mainPanelView.getToolPanelView().addBlockListActionListener(new BlockListActionListener());
		this.mainPanelView.getToolPanelView().addMouseToolButtonActionListener(MouseTool.place,
				new PlaceToolActionListener());
		this.mainPanelView.getToolPanelView().addMouseToolButtonActionListener(MouseTool.interact,
				new InteractToolActionListener());
		this.mainPanelView.getToolPanelView().addMouseToolButtonActionListener(MouseTool.select,
				new SelectToolActionListener());
		this.mainPanelView.getToolPanelView().addMouseToolButtonActionListener(MouseTool.pan,
				new PanToolActionListener());

		// Simulation panel
		this.mainPanelView.getSimulationPanelView().addMouseListener(new SimulationPanelMouseListener());
		this.mainPanelView.getSimulationPanelView().addMouseMotionListener(new SimulationPanelMouseMotionListener());
		this.mainPanelView.getSimulationPanelView().addMouseWheelListener(new SimulationPanelMouseWheelListener());

		this.xMousePos = 0;
		this.yMousePos = 0;

		new RepaintThread().start();
		new TickThread().start();
	}

	private class RepaintThread extends Thread {

		@Override
		public void run() {
			while (true) {
				mainPanelView.getSimulationPanelView().repaint();
			}
		}
	}

	private class TickThread extends Thread {
		@Override
		public void run() {
			Timer timer = new Timer(100, new NextStateTick());
			timer.start();
		}
	}

	private class NextStateTick implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (world != null) {
				world.nextState();
			}
		}
	}

	private void newWorld() {
		String[] saveFileNames = WorldIO.getAllSaveFilesArray();
		String worldName;
		boolean worldNameNotNew = true;
		do {
			worldName = (String) JOptionPane.showInputDialog(redstoneFrame, "Choose a name for the world: ",
					"New World", JOptionPane.PLAIN_MESSAGE, null, null, null);
			worldNameNotNew = false;
			for (String saveFileName : saveFileNames) {
				if (saveFileName.equals(worldName)) {
					worldNameNotNew = true;
				}
			}
		} while (worldNameNotNew);

		if ((worldName != null) && (worldName.length() > 0)) {
			int x = 16;
			int y = 16;
			int z = 2;

			String[] worldSizes = { "16 x 16", "32 x 32", "64 x 64" };
			String worldSize = (String) JOptionPane.showInputDialog(MainPanelController.this.redstoneFrame,
					"Choose a world to load: ", "New World Size", JOptionPane.PLAIN_MESSAGE, null, worldSizes,
					worldSizes[0]);
			if (worldSize != null) {
				String[] elements = worldSize.split(" x ");
				x = Integer.parseInt(elements[0]);
				y = Integer.parseInt(elements[1]);
			}

			world = new World(worldName, x, y, z);
			mainPanelView.getSimulationPanelView().setWorld(world);
		}
	}

	private class NewFileMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveIfModified();
			newWorld();
		}
	}

	private void saveWorld() {
		if (world != null) {
			String worldName = world.getWorldName();
			WorldIO.writeWorld(worldName, world);
			JOptionPane.showMessageDialog(redstoneFrame, "The world '" + worldName + "' has been saved.",
					"You saved the world", JOptionPane.PLAIN_MESSAGE);
		}
	}

	private void saveIfModified() {
		if (world != null && world.isModified()) {
			String[] options = { "Yes", "No" };
			int dialogResult = JOptionPane.showOptionDialog(redstoneFrame,
					"'" + world.getWorldName() + "' has been modified.\n Do you want to save the world?",
					"'" + world.getWorldName() + "' modified", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, options[0]);
			if (dialogResult == JOptionPane.YES_OPTION) {
				saveWorld();
			}
		}
	}

	private class SaveFileMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveWorld();
		}
	}

	private boolean loadWorld() {
		String[] saveFileNames = WorldIO.getAllSaveFilesArray();
		if (saveFileNames.length == 0) {
			return false;
		}
		String worldName = (String) JOptionPane.showInputDialog(MainPanelController.this.redstoneFrame,
				"Choose a world to load: ", "Load World", JOptionPane.PLAIN_MESSAGE, null, saveFileNames,
				saveFileNames[0]);

		if ((worldName != null) && (worldName.length() > 0)) {
			World loadedWorld = WorldIO.readWorld(worldName);
			if (loadedWorld != null) {
				this.world = loadedWorld;
				mainPanelView.getSimulationPanelView().setWorld(world);
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	private class LoadFileMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveIfModified();
			loadWorld();
		}
	}

	private class ExitFileMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveIfModified();
			System.exit(0);
		}
	}

	public void setSimulationPanelViewGridButton(Boolean drawGrid) {
		this.mainPanelView.getSimulationPanelView().setDrawGrid(drawGrid);
	}

	public boolean getGridButtonValue() {
		return this.mainPanelView.getToolPanelView().getGridButton().isSelected();
	}

	// Tool panel
	public MouseTool getSelectedMouseTool() {
		return this.selectedMouseTool;
	}

	public void setSelectedMouseTool(MouseTool selectedMouseTool) {
		this.selectedMouseTool = selectedMouseTool;
	}

	// Event Listener Classes
	private class GridButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setSimulationPanelViewGridButton(getGridButtonValue());
		}
	}

	private void zoom(int blockSize) {
		int oldBlockSize = mainPanelView.getSimulationPanelView().getBlockSize();
		mainPanelView.getSimulationPanelView().setBlockSize(blockSize);
		int newBlockSize = mainPanelView.getSimulationPanelView().getBlockSize();

		int blockSizeResized = newBlockSize - oldBlockSize;
		int xBlockSizeResized = blockSizeResized * world.getXSize() / 2;
		int yBlockSizeResized = blockSizeResized * world.getYSize() / 2;

		mainPanelView.getSimulationPanelView().addXDisplayOffset(-xBlockSizeResized);
		mainPanelView.getSimulationPanelView().addYDisplayOffset(-yBlockSizeResized);
	}

	private class ZoomInButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int newBlockSize = mainPanelView.getSimulationPanelView().getBlockSize() * 2;
			zoom(newBlockSize);
		}
	}

	private class ZoomOutButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int newBlockSize = mainPanelView.getSimulationPanelView().getBlockSize() / 2;
			zoom(newBlockSize);
		}
	}

	private class BlockListActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			selectedBlockID = BlockID.valueOfID(mainPanelView.getToolPanelView().getBlockListSelectedIndex());
			mainPanelView.getSimulationPanelView().setMouseHoverPlaceBlock(selectedBlockID);
		}
	}

	// Mouse Tool Buttons
	private class PlaceToolActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setSelectedMouseTool(MouseTool.place);
			mainPanelView.getSimulationPanelView().enableMouseHoverPlaceBlock();
			deselectBlock();
		}
	}

	private class InteractToolActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setSelectedMouseTool(MouseTool.interact);
			mainPanelView.getSimulationPanelView().disableMouseHoverPlaceBlock();
			deselectBlock();
		}
	}

	private class SelectToolActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setSelectedMouseTool(MouseTool.select);
			mainPanelView.getSimulationPanelView().disableMouseHoverPlaceBlock();
		}
	}

	private class PanToolActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			setSelectedMouseTool(MouseTool.pan);
			mainPanelView.getSimulationPanelView().disableMouseHoverPlaceBlock();
			deselectBlock();
		}
	}

	// Simulation Panel
	private void updateMouse(int xMouse, int yMouse) {
		if (this.world == null) {
			return;
		}

		this.xMousePos = xMouse;
		this.yMousePos = yMouse;

		int xOnGridMouse = this.xMousePos - this.mainPanelView.getSimulationPanelView().getxDrawDisplayOffset();
		int yOnGridMouse = this.yMousePos - this.mainPanelView.getSimulationPanelView().getyDrawDisplayOffset();

		int blockSize = this.mainPanelView.getSimulationPanelView().getBlockSize();

		int xMouseHoverBlock = xOnGridMouse / blockSize;
		int yMouseHoverBlock = yOnGridMouse / blockSize;
		this.mainPanelView.getSimulationPanelView().setxMouseHoverBlock(xMouseHoverBlock);
		this.mainPanelView.getSimulationPanelView().setyMouseHoverBlock(yMouseHoverBlock);

		boolean mouseHoversBlock = (this.xMousePos > this.mainPanelView.getSimulationPanelView()
				.getxDrawDisplayOffset())
				&& (this.yMousePos > this.mainPanelView.getSimulationPanelView().getyDrawDisplayOffset())
				&& (this.mainPanelView.getSimulationPanelView().getxMouseHoverBlock() >= 0)
				&& (this.mainPanelView.getSimulationPanelView().getxMouseHoverBlock() < this.world.getXSize())
				&& (this.mainPanelView.getSimulationPanelView().getyMouseHoverBlock() >= 0)
				&& (this.mainPanelView.getSimulationPanelView().getyMouseHoverBlock() < this.world.getYSize());
		this.mainPanelView.getSimulationPanelView().setMouseHoversBlock(mouseHoversBlock);

		if (mouseHoversBlock) {
			int xMouseHoverInnerBlock = xOnGridMouse % blockSize;
			int yMouseHoverInnerBlock = yOnGridMouse % blockSize;

			int distToNorth = yMouseHoverInnerBlock;
			int distToEast = blockSize - xMouseHoverInnerBlock;
			int distToSouth = blockSize - yMouseHoverInnerBlock;
			int distToWest = xMouseHoverInnerBlock;
			int[] distances = { distToNorth, distToEast, distToSouth, distToWest };
			int minIndex = 0;
			int min = distances[0];
			for (int i = 1; i < distances.length; i++) {
				if (min > distances[i]) {
					min = distances[i];
					minIndex = i;
				}
			}
			mainPanelView.getSimulationPanelView()
					.setMouseInnerBlockOrientation(Direction.DIRECTION_FACE_PLANE_ADJACENT_ORDERED[minIndex]);
		}
	}

	public void setDrawGrid(Boolean drawGrid) {
		this.mainPanelView.getSimulationPanelView().setDrawGrid(drawGrid);
	}

	private void selectBlock() {
		this.mainPanelView.getSimulationPanelView()
				.setSelectedBlock(this.mainPanelView.getSimulationPanelView().isMouseHoversBlock());
		if (this.mainPanelView.getSimulationPanelView().isSelectedBlock()) {
			this.mainPanelView.getSimulationPanelView()
					.setxSelectedBlock(this.mainPanelView.getSimulationPanelView().getxMouseHoverBlock());
			this.mainPanelView.getSimulationPanelView()
					.setySelectedBlock(this.mainPanelView.getSimulationPanelView().getyMouseHoverBlock());

			Block selectedBlock = world.getBlock(mainPanelView.getSimulationPanelView().getSelectedPoint());
			mainPanelView.getToolPanelView().setBlockDetails(selectedBlock.getDisplayDetails());
		}
	}

	private void deselectBlock() {
		mainPanelView.getSimulationPanelView().setSelectedBlock(false);
		mainPanelView.getToolPanelView().setBlockDetails(new ArrayList<>());
		mainPanelView.getSimulationPanelView().repaint();
	}

	/*
	 * Unused (see mouseWheelMoved) private int normalizeInnerZ(int selectedZ) {
	 * int maxZ = this.world.getZSize(); if (selectedZ < 0) { return 0; } else
	 * if (selectedZ >= maxZ) { return maxZ - 1; } else { return selectedZ; } }
	 */

	private class SimulationPanelMouseListener implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			updateMouse(e.getX(), e.getY());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			updateMouse(e.getX(), e.getY());

			mainPanelView.getSimulationPanelView().repaint();
		}

		@Override
		public void mouseExited(MouseEvent e) {
			updateMouse(e.getX(), e.getY());
		}

		@Override
		public void mousePressed(MouseEvent e) {
			updateMouse(e.getX(), e.getY());

			switch (selectedMouseTool) {
			case place:
				switch (e.getButton()) {
				case MouseEvent.BUTTON1:
					world.removeBlock(mainPanelView.getSimulationPanelView().getHoverPoint());
					break;
				case MouseEvent.BUTTON3:
					world.setBlock(mainPanelView.getSimulationPanelView().getHoverPoint(), selectedBlockID
							.makeBlock(mainPanelView.getSimulationPanelView().getMouseInnerBlockOrientation()));
					break;
				}
				break;
			case interact:
				if (e.getButton() == MouseEvent.BUTTON3) {
					world.useBlock(mainPanelView.getSimulationPanelView().getHoverPoint());
				}
				break;
			case select:
				selectBlock();
				break;
			case pan:
				break;
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			updateMouse(e.getX(), e.getY());
		}
	}

	private class SimulationPanelMouseMotionListener implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			int oldXMousePos = xMousePos;
			int oldYMousePos = yMousePos;
			updateMouse(e.getX(), e.getY());

			switch (selectedMouseTool) {
			case place:
				break;
			case interact:
				break;
			case select:
				break;
			case pan:
				int xDragged = xMousePos - oldXMousePos;
				int yDragged = yMousePos - oldYMousePos;
				mainPanelView.getSimulationPanelView().addXDisplayOffset(xDragged);
				mainPanelView.getSimulationPanelView().addYDisplayOffset(yDragged);
				break;
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			updateMouse(e.getX(), e.getY());
		}
	}

	private class SimulationPanelMouseWheelListener implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			updateMouse(e.getX(), e.getY());

			int oldBlockSize = mainPanelView.getSimulationPanelView().getBlockSize();
			if (e.isControlDown()) {
				/*
				 * Disabled since there is only 1 plane
				 * mainPanelView.getSimulationPanelView().setSelectedZ(
				 * normalizeInnerZ(mainPanelView.getSimulationPanelView().
				 * getSelectedZ() + e.getWheelRotation()));
				 */
			} else {
				mainPanelView.getSimulationPanelView().addBlockSize(-e.getWheelRotation());

				int blockSizeResized = mainPanelView.getSimulationPanelView().getBlockSize() - oldBlockSize;
				int xBlockSizeResized = blockSizeResized * world.getXSize() / 2;
				int yBlockSizeResized = blockSizeResized * world.getYSize() / 2;

				mainPanelView.getSimulationPanelView().addXDisplayOffset(-xBlockSizeResized);
				mainPanelView.getSimulationPanelView().addYDisplayOffset(-yBlockSizeResized);
			}
		}
	}
}
