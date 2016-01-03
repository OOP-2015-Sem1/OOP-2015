package com.redstonesimulator.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.lang.annotation.Repeatable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import com.redstonesimulator.model.World;
import com.redstonesimulator.model.block.Air;
import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.BlockID;
import com.redstonesimulator.model.block.redstone.Repeater;

import utilities.Direction;
import utilities.ImageReader;
import utilities.Int3DPoint;

public class SimulationPanelView extends JPanel {
	private static final long serialVersionUID = -6134248870869276907L;

	private static final String SELECTED_BLOCK_OVERLAY_IMAGE_PATH = "SelectedBlock.bmp";

	// TODO:
	// private int width;
	// private int height;

	private static final int X_BORDER_OFFSET = 4;
	private static final int Y_BORDER_OFFSET = 4;

	private World world;

	public static final int BLOCK_SIZE_MIN = 16;
	public static final int BLOCK_SIZE_MAX = 128;
	private int blockSize;
	private int selectedZ;

	private boolean drawGrid;

	private boolean mouseHoversBlock;
	private boolean displayMouseHoverPlaceBlock;
	private BlockID mouseHoverPlaceBlockID;
	private Direction mouseInnerBlockOrientation;
	private int xMouseHoverBlock;
	private int yMouseHoverBlock;

	private boolean selectedBlock;
	private int xSelectedBlock;
	private int ySelectedBlock;
	private Image selectedBlockOverlayImage;

	private int xDisplayOffset;
	private int yDisplayOffset;

	public SimulationPanelView() {
		super();
		setBackground(Color.RED);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED),
				BorderFactory.createBevelBorder(BevelBorder.LOWERED)));
		setLayout(new FlowLayout());

		this.setWorld(null);
		this.selectedBlockOverlayImage = ImageReader.readImage(SELECTED_BLOCK_OVERLAY_IMAGE_PATH);
	}

	public void setWorld(World world) {
		this.world = world;

		this.drawGrid = false;

		this.blockSize = 64;
		this.selectedZ = 1;

		this.xDisplayOffset = 1;
		this.yDisplayOffset = 1;

		this.mouseHoversBlock = false;
		this.displayMouseHoverPlaceBlock = false;
		this.mouseHoverPlaceBlockID = BlockID.air;
		this.xMouseHoverBlock = 0;
		this.yMouseHoverBlock = 0;

		this.selectedBlock = false;
		this.xSelectedBlock = 0;
		this.ySelectedBlock = 0;

		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (this.world == null) {
			return;
		}

		int xBlocks = this.world.getXSize();
		int yBlocks = this.world.getYSize();
		int zBlocks = this.world.getZSize();

		BufferedImage topLevelImage = new BufferedImage(xBlocks * this.blockSize, yBlocks * this.blockSize,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D topLevelImageGrahics = (Graphics2D) topLevelImage.getGraphics();

		// Draw the blocks
		for (int x = 0; x < xBlocks; x++) {
			for (int y = 0; y < yBlocks; y++) {
				for (int z = 0; z <= this.selectedZ; z++) {
					int xStart = x * this.blockSize;
					int yStart = y * this.blockSize;
					int xWidth = this.blockSize;
					int yWidth = this.blockSize;
					Block blockToDisplay = this.world.getBlock(new Int3DPoint(x, y, z));

					topLevelImageGrahics.drawImage(blockToDisplay.getImage(), xStart, yStart, xWidth, yWidth, null);
				}
			}
		}

		// Draw the grid
		if (this.drawGrid) {
			topLevelImageGrahics.setColor(Color.BLACK);
			for (int x = 0; x < xBlocks; x++) {
				for (int y = 0; y < yBlocks; y++) {
					int xStart = x * this.blockSize;
					int yStart = y * this.blockSize;
					int xWidth = this.blockSize - 1;
					int yWidth = this.blockSize - 1;
					topLevelImageGrahics.drawRect(xStart, yStart, xWidth, yWidth);
				}
			}
		}

		// Highlight the block the mouse hovers on
		if (this.mouseHoversBlock) {
			int xHoverStart = this.xMouseHoverBlock * this.blockSize;
			int yHoverStart = this.yMouseHoverBlock * this.blockSize;
			int xHoverWidth = this.blockSize - 1;
			int yHoverHeight = this.blockSize - 1;

			if (displayMouseHoverPlaceBlock && mouseHoverPlaceBlockID != null) {
				short[] redLookup = new short[256];
				short[] greenLookup = new short[256];
				short[] blueLookup = new short[256];
				short[] alphaLookup = new short[256];
				for (short i = 0; i < 256; i++) {
					redLookup[i] = i;
					greenLookup[i] = i;
					blueLookup[i] = i;
					alphaLookup[i] = (short) (i / 2);
				}
				short[][] lookup = { redLookup, greenLookup, blueLookup, alphaLookup };
				LookupTable lookupTable = new ShortLookupTable(0, lookup);
				LookupOp lookupOp = new LookupOp(lookupTable, null);
				Block blockToHoverDisplay = mouseHoverPlaceBlockID.makeBlock(mouseInnerBlockOrientation);
				topLevelImageGrahics.drawImage(lookupOp.filter((BufferedImage) blockToHoverDisplay.getImage(), null),
						xHoverStart, yHoverStart, xHoverWidth, yHoverHeight, null);
			}

			topLevelImageGrahics.setColor(Color.BLUE);
			topLevelImageGrahics.drawRect(xHoverStart, yHoverStart, xHoverWidth, yHoverHeight);
		}

		// Highlight the selected block
		if (this.selectedBlock) {
			int xSelectedStart = this.xSelectedBlock * this.blockSize;
			int ySelectedStart = this.ySelectedBlock * this.blockSize;
			int xSelectedWidth = this.blockSize;
			int ySelectedHeight = this.blockSize;
			topLevelImageGrahics.drawImage(this.selectedBlockOverlayImage, xSelectedStart, ySelectedStart,
					xSelectedWidth, ySelectedHeight, null);
		}

		// Paint the top level image on the screen
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(topLevelImage, this.getxDrawDisplayOffset(), this.getyDrawDisplayOffset(), null);
		// Repaint the border (the blocks can clip it)
		paintBorder(g);
	}

	public int getBlockSize() {
		return blockSize;
	}
	
	private int normalizeBlockSize(int blockSize) {
		if (blockSize < BLOCK_SIZE_MIN) {
			return BLOCK_SIZE_MIN;
		} else if (blockSize > BLOCK_SIZE_MAX) {
			return BLOCK_SIZE_MAX;
		} else {
			return blockSize;
		}
	}

	public void setBlockSize(int blockSize) {
		this.blockSize = normalizeBlockSize(blockSize);
	}

	public void addBlockSize(int blockSize) {
		this.setBlockSize(this.getBlockSize() + blockSize);
	}

	public int getSelectedZ() {
		return selectedZ;
	}

	public void setSelectedZ(int selectedZ) {
		this.selectedZ = selectedZ;
	}

	public void setDrawGrid(boolean drawGrid) {
		this.drawGrid = drawGrid;
	}

	public boolean isMouseHoversBlock() {
		return mouseHoversBlock;
	}

	public void setMouseHoversBlock(boolean hoverBlock) {
		this.mouseHoversBlock = hoverBlock;
	}

	public int getxMouseHoverBlock() {
		return xMouseHoverBlock;
	}

	public void setxMouseHoverBlock(int xMouseHoverBlock) {
		this.xMouseHoverBlock = xMouseHoverBlock;
	}

	public int getyMouseHoverBlock() {
		return yMouseHoverBlock;
	}

	public void setyMouseHoverBlock(int yMouseHoverBlock) {
		this.yMouseHoverBlock = yMouseHoverBlock;
	}
	
	public void setMouseHoverPlaceBlock(BlockID blockID) {
		this.mouseHoverPlaceBlockID = blockID;
	}

	public void enableMouseHoverPlaceBlock() {
		displayMouseHoverPlaceBlock = true;
	}

	public void disableMouseHoverPlaceBlock() {
		displayMouseHoverPlaceBlock = false;
	}
	
	public Direction getMouseInnerBlockOrientation() {
		return mouseInnerBlockOrientation;
	}

	public void setMouseInnerBlockOrientation(Direction mouseInnerBlockOrientation) {
		this.mouseInnerBlockOrientation = mouseInnerBlockOrientation;
	}

	public Int3DPoint getHoverPoint() {
		int x = this.getxMouseHoverBlock();
		int y = this.getyMouseHoverBlock();
		int z = this.getSelectedZ();

		return new Int3DPoint(x, y, z);
	}

	public boolean isSelectedBlock() {
		return selectedBlock;
	}

	public void setSelectedBlock(boolean selectedBlock) {
		this.selectedBlock = selectedBlock;
	}

	public int getxSelectedBlock() {
		return xSelectedBlock;
	}

	public void setxSelectedBlock(int xSelectedBlock) {
		this.xSelectedBlock = xSelectedBlock;
	}

	public int getySelectedBlock() {
		return ySelectedBlock;
	}

	public void setySelectedBlock(int ySelectedBlock) {
		this.ySelectedBlock = ySelectedBlock;
	}

	public Int3DPoint getSelectedPoint() {
		int x = this.getxSelectedBlock();
		int y = this.getySelectedBlock();
		int z = this.getSelectedZ();

		return new Int3DPoint(x, y, z);
	}

	public int getxDisplayOffset() {
		return this.xDisplayOffset;
	}

	public int getyDisplayOffset() {
		return this.yDisplayOffset;
	}

	public int getxDrawDisplayOffset() {
		return this.getxDisplayOffset() + X_BORDER_OFFSET;
	}

	public int getyDrawDisplayOffset() {
		return this.getyDisplayOffset() + Y_BORDER_OFFSET;
	}

	private void setxDisplayOffset(int xDisplayOffset) {
		this.xDisplayOffset = xDisplayOffset;
	}

	private void setyDisplayOffset(int yDisplayOffset) {
		this.yDisplayOffset = yDisplayOffset;
	}

	public void addXDisplayOffset(int xDisplayOffset) {
		this.setxDisplayOffset(this.getxDisplayOffset() + xDisplayOffset);
	}

	public void addYDisplayOffset(int yDisplayOffset) {
		this.setyDisplayOffset(this.getyDisplayOffset() + yDisplayOffset);
	}
}
