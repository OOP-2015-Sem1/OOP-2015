package com.redstonesimulator.model.block.redstone;

import java.util.ArrayList;
import java.util.LinkedList;

import com.redstonesimulator.model.block.Block;

public class RedstoneBlock extends Block {
	protected static final int REDSTONE_LEVEL_MIN = 0;
	protected static final int REDSTONE_LEVEL_MAX = 15;
	private int redstoneLevel;

	public RedstoneBlock(String imagePath) {
		super(imagePath);
		this.setRedstoneLevel(REDSTONE_LEVEL_MIN);
	}

	public int getRedstoneLevel() {
		return this.redstoneLevel;
	}

	public void setRedstoneLevel(int redstoneLevel) {
		if (redstoneLevel < REDSTONE_LEVEL_MIN) {
			this.redstoneLevel = REDSTONE_LEVEL_MIN;
		} else if (redstoneLevel > REDSTONE_LEVEL_MAX) {
			this.redstoneLevel = REDSTONE_LEVEL_MAX;
		} else {
			this.redstoneLevel = redstoneLevel;
		}
	}

	public boolean getBooleanRedstoneLevel() {
		return redstoneLevel > REDSTONE_LEVEL_MIN;
	}

	public void setBooleanRedstoneLevel(boolean redstoneLevel) {
		if (redstoneLevel) {
			this.redstoneLevel = REDSTONE_LEVEL_MAX;
		} else {
			this.redstoneLevel = REDSTONE_LEVEL_MIN;
		}
	}

	public boolean isNotMaxRedstoneLevel() {
		return redstoneLevel < REDSTONE_LEVEL_MAX;
	}

	@Override
	public ArrayList<String> getDisplayDetails() {
		ArrayList<String> displayDetails = super.getDisplayDetails();
		displayDetails.add("Redstone Level: ");
		displayDetails.add(String.valueOf(this.getRedstoneLevel()));
		return displayDetails;
	}

	@Override
	public LinkedList<String> encode() {
		LinkedList<String> encodedFields = super.encode();
		encodedFields.push(String.valueOf(getRedstoneLevel()));
		return encodedFields;
	}

	@Override
	public void decode(LinkedList<String> encodedFields) {
		setRedstoneLevel(Integer.parseInt(encodedFields.pop()));
		super.decode(encodedFields);
	}
}
