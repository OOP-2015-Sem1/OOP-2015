package com.redstonesimulator.model.block;

import java.awt.Image;
import java.util.ArrayList;
import java.util.LinkedList;

import utilities.Direction;
import utilities.ImageReader;

public abstract class Block implements Drawable {
	private Image blockImage;

	public Block(String imagePath) {
		this.setImage(ImageReader.readImage(imagePath));
	}

	@Override
	public Image getImage() {
		return blockImage;
	}

	private void setImage(Image blockImage) {
		this.blockImage = blockImage;
	}

	public ArrayList<String> getDisplayDetails() {
		ArrayList<String> displayDetails = new ArrayList<>();
		displayDetails.add("'" + BlockID.valueOfBlock(this).getDisplayName() + "'");
		displayDetails.add("Block data: ");
		return displayDetails;
	}

	public LinkedList<String> encode() {
		LinkedList<String> encodedFields = new LinkedList<>();
		return encodedFields;
	}

	public void decode(LinkedList<String> encodedFields) {
	}
}
