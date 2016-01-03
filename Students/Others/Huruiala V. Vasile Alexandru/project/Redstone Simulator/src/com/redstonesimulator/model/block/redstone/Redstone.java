package com.redstonesimulator.model.block.redstone;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.ShortLookupTable;
import java.util.ArrayList;
import java.util.LinkedList;

import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.InstantBlockUpdate;
import com.redstonesimulator.model.block.InstantlyUpdateable;
import com.redstonesimulator.model.block.solid.SolidBlock;
import com.redstonesimulator.model.block.solid.SolidBlockRedstoneLevel;

import utilities.Direction;

public class Redstone extends RedstoneBlock implements InstantlyUpdateable {
	private RedstoneBlockType redstoneBlockType;

	private boolean[] connectedRedstone;

	public Redstone() {
		super(null);
		setRedstoneLevel(REDSTONE_LEVEL_MIN);
		redstoneBlockType = RedstoneBlockType.RedstoneCross;
		connectedRedstone = new boolean[4];
	}

	@Override
	public void preInstantBlockUpdate() {
		setRedstoneLevel(REDSTONE_LEVEL_MIN);
	}

	@Override
	public InstantBlockUpdate instantBlockUpdate(Block block, Direction direction) {
		InstantBlockUpdate result = InstantBlockUpdate.NoUpdate;

		// Redstone will propagate with a loss of signal strength
		if (block instanceof Redstone) {
			Redstone redstone = (Redstone) block;

			int thisRedstoneLevel = this.getRedstoneLevel();
			int blockRedstoneLevel = redstone.getRedstoneLevel();

			if (thisRedstoneLevel - 1 > blockRedstoneLevel) {
				int newRedstoneLevel = thisRedstoneLevel - 1;
				if (newRedstoneLevel < 0) {
					newRedstoneLevel = 0;
				}
				redstone.setRedstoneLevel(newRedstoneLevel);
				result = InstantBlockUpdate.UpdateDestination;
			}
			if (blockRedstoneLevel - 1 > thisRedstoneLevel) {
				int newRedstoneLevel = blockRedstoneLevel - 1;
				if (newRedstoneLevel < 0) {
					newRedstoneLevel = 0;
				}
				this.setRedstoneLevel(newRedstoneLevel);
				result = InstantBlockUpdate.UpdateSource;
			}
		}

		// Redstone will get powered if it is in contact with a
		// RedstoneTorch that is turned on
		if (block instanceof RedstoneTorch) {
			RedstoneTorch redstoneTorch = (RedstoneTorch) block;
			boolean redstoneTorchLevel = redstoneTorch.getBooleanRedstoneLevel();

			if (redstoneTorchLevel && isNotMaxRedstoneLevel()) {
				this.setBooleanRedstoneLevel(true);
				result = InstantBlockUpdate.UpdateSource;
			}
		}

		// A strongly powered solid block is a Redstone source
		if (block instanceof SolidBlock) {
			SolidBlock solidBlock = (SolidBlock) block;
			SolidBlockRedstoneLevel solidBlockRedstoneLevel = solidBlock.getSolidBlockRedstoneLevel();

			if (solidBlockRedstoneLevel == SolidBlockRedstoneLevel.StronglyPowered) {
				if (isNotMaxRedstoneLevel()) {
				setBooleanRedstoneLevel(true);
				result = InstantBlockUpdate.UpdateSource;
				}
			} else if (solidBlockRedstoneLevel == SolidBlockRedstoneLevel.Unpowered){
				result = InstantBlockUpdate.UpdateDestination;
			}
		}

		// A lever that is flipped on is a redstone source
		if (block instanceof Lever) {
			Lever lever = (Lever) block;

			if (lever.getBooleanRedstoneLevel() && isNotMaxRedstoneLevel()) {
				setBooleanRedstoneLevel(true);
				result = InstantBlockUpdate.UpdateSource;
			}

		}

		// A repeater pointing to the wire will power it
		if (block instanceof Repeater) {
			Repeater repeater = (Repeater) block;

			if (repeater.getDirection().opposite() == direction) {
				if (repeater.getBooleanRedstoneLevel() && isNotMaxRedstoneLevel()) {
					setBooleanRedstoneLevel(true);
					result = InstantBlockUpdate.UpdateSource;
				}
			} else if (repeater.getDirection() == direction) {
				if (!repeater.getInputRedstone() && getBooleanRedstoneLevel()) {
					result = InstantBlockUpdate.UpdateDestination;
				}
			}
		}

		return result;
	}

	public boolean[] getConnectedRedstone() {
		return connectedRedstone;
	}

	private void setConnectedRedstone(int index, boolean value) {
		connectedRedstone[index] = value;
	}

	public void resetConnectedRedstone() {
		for (int i = 0; i < this.connectedRedstone.length; i++) {
			connectedRedstone[i] = false;
		}
	}

	public void updateConnectedRedstone(Block block, Direction direction) {
		int directionIndex = direction.getIndex(Direction.DIRECTION_FACE_PLANE_ADJACENT);

		if (direction.isContained(Direction.DIRECTION_FACE_PLANE_ADJACENT)) {
			if (block instanceof Redstone || block instanceof RedstoneTorch || block instanceof Lever
					|| ((block instanceof Repeater) && ((((Repeater) block).getDirection() == direction)
							|| (((Repeater) block).getDirection().opposite() == direction)))) {
				setConnectedRedstone(directionIndex, true);
				if (block instanceof Redstone) {
					Redstone redstone = (Redstone) block;
					redstone.setConnectedRedstone(
							Direction.DIRECTION_FACE_PLANE_ADJACENT_ID_OPPOSITE_LOOKUP[directionIndex], true);
				}
			}
		}
	}

	public void pushConnectedRedstone() {
		int value = 0;
		for (int i = 0; i < connectedRedstone.length; i++) {
			value *= 10;
			if (connectedRedstone[i]) {
				value += 1;
			}
		}

		switch (value) {
		case 0:
			this.redstoneBlockType = RedstoneBlockType.RedstoneCross;
			break;
		case 1:
			this.redstoneBlockType = RedstoneBlockType.RedstoneHorizontalLine;
			break;
		case 10:
			this.redstoneBlockType = RedstoneBlockType.RedstoneHorizontalLine;
			break;
		case 11:
			this.redstoneBlockType = RedstoneBlockType.RedstoneHorizontalLine;
			break;
		case 100:
			this.redstoneBlockType = RedstoneBlockType.RedstoneVerticalLine;
			break;
		case 101:
			this.redstoneBlockType = RedstoneBlockType.RedstoneSouthWestCorner;
			break;
		case 110:
			this.redstoneBlockType = RedstoneBlockType.RedstoneSouthEastCorner;
			break;
		case 111:
			this.redstoneBlockType = RedstoneBlockType.RedstoneSouthT;
			break;
		case 1000:
			this.redstoneBlockType = RedstoneBlockType.RedstoneVerticalLine;
			break;
		case 1001:
			this.redstoneBlockType = RedstoneBlockType.RedstoneNorthWestCorner;
			break;
		case 1010:
			this.redstoneBlockType = RedstoneBlockType.RedstoneNorthEastCorner;
			break;
		case 1011:
			this.redstoneBlockType = RedstoneBlockType.RedstoneNorthT;
			break;
		case 1100:
			this.redstoneBlockType = RedstoneBlockType.RedstoneVerticalLine;
			break;
		case 1101:
			this.redstoneBlockType = RedstoneBlockType.RedstoneWestT;
			break;
		case 1110:
			this.redstoneBlockType = RedstoneBlockType.RedstoneEastT;
			break;
		case 1111:
			this.redstoneBlockType = RedstoneBlockType.RedstoneCross;
			break;
		}
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

		return lookupOp.filter((BufferedImage) redstoneBlockType.getImage(), null);
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
