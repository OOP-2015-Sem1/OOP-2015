package com.redstonesimulator.model.block.redstone;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.util.ArrayList;
import java.util.LinkedList;

import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.DelayedUpdatable;
import com.redstonesimulator.model.block.InstantBlockUpdate;
import com.redstonesimulator.model.block.InstantlyUpdateable;
import com.redstonesimulator.model.block.solid.SolidBlock;
import com.redstonesimulator.model.block.solid.SolidBlockRedstoneLevel;

import utilities.Direction;

public class RedstoneTorch extends RedstoneBlock implements InstantlyUpdateable, DelayedUpdatable {
	private static final String REDSTONE_TORCH_IMAGE = "RedstoneTorch.bmp";

	private boolean inputRedstone;

	public RedstoneTorch() {
		super(REDSTONE_TORCH_IMAGE);
		setRedstoneLevel(REDSTONE_LEVEL_MAX);
		inputRedstone = true;
	}

	public boolean getInputRedstone() {
		return inputRedstone;
	}

	private void setInputRedstone(boolean inputRedstone) {
		this.inputRedstone = inputRedstone;
	}

	@Override
	public void preInstantBlockUpdate() {
		setInputRedstone(true);
	}

	@Override
	public InstantBlockUpdate instantBlockUpdate(Block block, Direction direction) {
		InstantBlockUpdate result = InstantBlockUpdate.NoUpdate;

		// A torch connected to a weakly or strongly powered solid block turns
		// off
		if (block instanceof SolidBlock) {
			SolidBlock solidBlock = (SolidBlock) block;
			SolidBlockRedstoneLevel solidBlockRedstoneLevel = solidBlock.getSolidBlockRedstoneLevel();

			if (getInputRedstone()) {
				if (solidBlockRedstoneLevel == SolidBlockRedstoneLevel.WeaklyPowered
						|| solidBlockRedstoneLevel == SolidBlockRedstoneLevel.StronglyPowered) {
					setInputRedstone(false);

					result = InstantBlockUpdate.UpdateSource;
				}
			}
		}

		if (block instanceof Redstone) {
			Redstone redstone = (Redstone) block;

			if (redstone.isNotMaxRedstoneLevel() && getBooleanRedstoneLevel()) {
				redstone.setBooleanRedstoneLevel(true);

				result = InstantBlockUpdate.UpdateDestination;
			}
		}

		return result;
	}

	@Override
	public void delayedBlockUpdate() {
		setBooleanRedstoneLevel(getInputRedstone());
	}

	@Override
	public Image getImage() {

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

		return lookupOp.filter((BufferedImage) super.getImage(), null);
	}

	// TODO: implement torch on the side of a block
	@Override
	public ArrayList<String> getDisplayDetails() {
		ArrayList<String> displayDetails = super.getDisplayDetails();
		displayDetails.add("Input Redstone:");
		displayDetails.add(String.valueOf(getInputRedstone()));
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
