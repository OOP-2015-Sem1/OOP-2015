package com.redstonesimulator.model.block.solid;

import java.awt.Image;

import utilities.ImageReader;

public class RedstoneLamp extends SolidBlock {
	private static final String REDSTONE_LAMP_OFF_IMAGE = "RedstoneLampOff.bmp";
	private static final String REDSTONE_LAMP_ON_IMAGE = "RedstoneLampOn.bmp";
	private Image lampOnImage;

	public RedstoneLamp() {
		super(REDSTONE_LAMP_OFF_IMAGE);
		lampOnImage = ImageReader.readImage(REDSTONE_LAMP_ON_IMAGE);
	}

	@Override
	public Image getImage() {
		if (getBooleanRedstoneLevel()) {
			return lampOnImage;
		} else {
			return super.getImage();
		}
	}
}
