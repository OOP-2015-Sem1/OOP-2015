package com.redstonesimulator.model.block.redstone;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import utilities.ImageReader;

public enum RedstoneBlockType {
	RedstoneCross("RedstoneCross.bmp", 0), 
	RedstoneVerticalLine("RedstoneLine.bmp", 1), 
	RedstoneHorizontalLine("RedstoneLine.bmp", 0), 
	RedstoneNorthEastCorner("RedstoneCorner.bmp", 0), 
	RedstoneNorthWestCorner("RedstoneCorner.bmp", 3), 
	RedstoneSouthEastCorner("RedstoneCorner.bmp", 1), 
	RedstoneSouthWestCorner("RedstoneCorner.bmp", 2), 
	RedstoneNorthT("RedstoneT.bmp", 0), 
	RedstoneSouthT("RedstoneT.bmp", 2), 
	RedstoneEastT("RedstoneT.bmp", 1), 
	RedstoneWestT("RedstoneT.bmp", 3);

	private Image image;

	private RedstoneBlockType(String imagePath, int numquadrants) {
		AffineTransform affineTransform = AffineTransform.getRotateInstance((numquadrants * Math.PI) / 2, 8, 8);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, null);
		Image readImage = ImageReader.readImage(imagePath);
		this.image = affineTransformOp.filter((BufferedImage) readImage, null);
	}

	public Image getImage() {
		return this.image;
	}
}
