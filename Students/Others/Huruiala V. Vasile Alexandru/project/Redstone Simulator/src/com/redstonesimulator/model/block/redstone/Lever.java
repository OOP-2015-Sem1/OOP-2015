package com.redstonesimulator.model.block.redstone;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.InstantBlockUpdate;
import com.redstonesimulator.model.block.Usable;
import com.redstonesimulator.model.block.solid.SolidBlock;
import com.redstonesimulator.model.block.solid.SolidBlockRedstoneLevel;

import utilities.Direction;
import utilities.ImageReader;

public class Lever extends RedstoneBlock implements Usable {
	private static final String LEVER_BASE_IMAGE_PATH = "LeverBase.bmp";
	private static final String LEVER_CRANK_IMAGE_PATH = "LeverCrank.bmp";
	private Image leverCrankImage;

	public Lever() {
		super(LEVER_BASE_IMAGE_PATH);
		leverCrankImage = ImageReader.readImage(LEVER_CRANK_IMAGE_PATH);
		setBooleanRedstoneLevel(false);
	}

	@Override
	public Image getImage() {
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
		imageGraphics.drawImage(super.getImage(), 0, 0, 16, 16, 0, 0, 16, 16, null);
		
		double angle = 0;
		if (getBooleanRedstoneLevel()) {
			angle = Math.PI;
		}
		AffineTransform affineTransform = AffineTransform.getRotateInstance(angle, 8, 8);
		AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, null);
		Image rotatedLeverCrankImage = affineTransformOp.filter((BufferedImage) this.leverCrankImage, null);
		imageGraphics.drawImage(rotatedLeverCrankImage, 0, 0, 16, 16, 0, 0, 16, 16, null);

		return image;
	}

	@Override
	public void use() {
		setBooleanRedstoneLevel(!getBooleanRedstoneLevel());
	}
	
	@Override
	public ArrayList<String> getDisplayDetails() {
		ArrayList<String> displayDetails = super.getDisplayDetails();
		return displayDetails;
	}

	@Override
	public LinkedList<String> encode() {
		LinkedList<String> encodedFields = super.encode();
		return encodedFields;
	}

	@Override
	public void decode(LinkedList<String> encodedFields) {
		super.decode(encodedFields);
	}
}
