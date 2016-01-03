package com.redstonesimulator.model.block.redstone;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.util.ArrayList;
import java.util.LinkedList;

import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.DelayedUpdatable;
import com.redstonesimulator.model.block.Directional;
import com.redstonesimulator.model.block.InstantBlockUpdate;
import com.redstonesimulator.model.block.InstantlyUpdateable;
import com.redstonesimulator.model.block.Usable;

import utilities.Direction;
import utilities.ImageReader;

public class Repeater extends RedstoneBlock implements Usable, Directional, InstantlyUpdateable, DelayedUpdatable {
	private static final String REPEATER_BASE_IMAGE_PATH = "RepeaterBase.bmp";
	private static final String REPEATER_REDSTONE_BASE_IMAGE_PATH = "RepeaterRedstoneBase.bmp";
	private static final String REPEATER_TORCH_IMAGE_PATH = "RepeaterTorch.bmp";
	private Image repeaterRedstoneBase;
	private Image repeaterTorch;

	private static final int REPEATER_DELAY_MIN = 1;
	private static final int REPEATER_DELAY_MAX = 4;
	private int repeaterDelay;

	private Direction direction;

	private boolean inputRedstone;
	private boolean[] internalRedstone;

	public Repeater() {
		super(REPEATER_BASE_IMAGE_PATH);
		repeaterTorch = ImageReader.readImage(REPEATER_TORCH_IMAGE_PATH);
		repeaterRedstoneBase = ImageReader.readImage(REPEATER_REDSTONE_BASE_IMAGE_PATH);
		setRepeaterDelay(REPEATER_DELAY_MIN);
		inputRedstone = false;
		internalRedstone = new boolean[REPEATER_DELAY_MAX];
		setDirection(Direction.North);
	}

	public boolean getInputRedstone() {
		return inputRedstone;
	}

	public void setInputRedstone(boolean inputRedstone) {
		this.inputRedstone = inputRedstone;
	}

	@Override
	public void preInstantBlockUpdate() {
		inputRedstone = false;
	}

	@Override
	public InstantBlockUpdate instantBlockUpdate(Block block, Direction direction) {
		InstantBlockUpdate result = InstantBlockUpdate.NoUpdate;

		// Input from the back
		if (getDirection().opposite() == direction) {

			if (block instanceof RedstoneBlock) {
				RedstoneBlock redstoneBlock = (RedstoneBlock) block;
				if (redstoneBlock.getBooleanRedstoneLevel() && !inputRedstone) {
					inputRedstone = true;
				}
			}
		}

		return result;
	}

	@Override
	public void delayedBlockUpdate() {
		for (int i = REPEATER_DELAY_MAX - 1; i > 0; i--) {
			internalRedstone[i] = internalRedstone[i - 1];
		}
		internalRedstone[0] = inputRedstone;

		setBooleanRedstoneLevel(internalRedstone[getRepeaterDelay() - 1]);
	}

	public int getRepeaterDelay() {
		return repeaterDelay;
	}

	private void setRepeaterDelay(int repeaterDelay) {
		// This generates the periodic behavior if you keep clicking it
		if (repeaterDelay < REPEATER_DELAY_MIN || repeaterDelay > REPEATER_DELAY_MAX) {
			this.repeaterDelay = REPEATER_DELAY_MIN;
		} else {
			this.repeaterDelay = repeaterDelay;
		}
	}

	@Override
	public void use() {
		setRepeaterDelay(getRepeaterDelay() + 1);
	}

	@Override
	public Image getImage() {
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
		imageGraphics.drawImage(super.getImage(), 0, 0, 16, 16, 0, 0, 16, 16, null);

		BufferedImage redstoneImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D redstoneImageGraphics = (Graphics2D) redstoneImage.getGraphics();
		redstoneImageGraphics.drawImage(repeaterRedstoneBase, 0, 0, 16, 16, 0, 0, 16, 16, null);
		int heightOffset = getRepeaterDelay() * 2 - 3;
		redstoneImageGraphics.drawImage(repeaterTorch, 0, heightOffset, 16, 16 + heightOffset, 0, 0, 16, 16, null);

		short[] redLookup = new short[256];
		short[] greenLookup = new short[256];
		short[] blueLookup = new short[256];
		short[] alphaLookup = new short[256];
		for (short i = 0; i < 256; i++) {
			double redRatio = ((double) this.getRedstoneLevel() / 15);
			redLookup[i] = (short) (i * redRatio);
			greenLookup[i] = 0;
			blueLookup[i] = 0;
			alphaLookup[i] = i;
		}
		short[][] lookup = { redLookup, greenLookup, blueLookup, alphaLookup };
		LookupTable lookupTable = new ShortLookupTable(0, lookup);
		LookupOp lookupOp = new LookupOp(lookupTable, null);
		redstoneImage = lookupOp.filter(redstoneImage, null);

		imageGraphics.drawImage(redstoneImage, 0, 0, 16, 16, 0, 0, 16, 16, null);

		double angle = (Math.PI / 2) * direction.getIndex(Direction.DIRECTION_FACE_PLANE_ADJACENT_ORDERED);
		AffineTransform affineTransform = AffineTransform.getRotateInstance(angle, 8, 8);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, null);

		return image = affineTransformOp.filter((BufferedImage) image, null);
	}

	@Override
	public Direction getDirection() {
		return this.direction;
	}

	@Override
	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	@Override
	public ArrayList<String> getDisplayDetails() {
		ArrayList<String> displayDetails = super.getDisplayDetails();
		displayDetails.add("Delay: ");
		displayDetails.add(String.valueOf(getRepeaterDelay()));
		displayDetails.add("Direction: ");
		displayDetails.add(String.valueOf(getDirection()));
		displayDetails.add("Input redstone: ");
		displayDetails.add(String.valueOf(inputRedstone));
		displayDetails.add("Internal redstone: ");
		for (int i = 0; i < REPEATER_DELAY_MAX; i++) {
			displayDetails.add("[" + i + "]: " + String.valueOf(internalRedstone[i]));
		}
		return displayDetails;
	}

	@Override
	public LinkedList<String> encode() {
		LinkedList<String> encodedFields = super.encode();
		encodedFields.push(String.valueOf(getRepeaterDelay()));
		encodedFields.push(String.valueOf(getDirection()));
		for (int i = 0; i < REPEATER_DELAY_MAX; i++) {
			encodedFields.push(String.valueOf(internalRedstone[i]));
		}
		return encodedFields;
	}

	@Override
	public void decode(LinkedList<String> encodedFields) {
		for (int i = REPEATER_DELAY_MAX - 1; i >= 0; i--) {
			internalRedstone[i] = Boolean.parseBoolean(encodedFields.pop());
		}
		setDirection(Direction.valueOf(encodedFields.pop()));
		setRepeaterDelay(Integer.parseInt(encodedFields.pop()));

		super.decode(encodedFields);
	}
}
