package com.redstonesimulator.model.block.solid;

import java.util.ArrayList;
import java.util.LinkedList;

import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.InstantBlockUpdate;
import com.redstonesimulator.model.block.InstantlyUpdateable;
import com.redstonesimulator.model.block.redstone.Lever;
import com.redstonesimulator.model.block.redstone.Redstone;
import com.redstonesimulator.model.block.redstone.RedstoneBlock;
import com.redstonesimulator.model.block.redstone.RedstoneTorch;
import com.redstonesimulator.model.block.redstone.Repeater;

import utilities.Direction;

public abstract class SolidBlock extends RedstoneBlock implements InstantlyUpdateable {

	private SolidBlockRedstoneLevel solidBlockRedstoneLevel;

	public SolidBlock(String imagePath) {
		super(imagePath);
		setSolidBlockRedstoneLevel(SolidBlockRedstoneLevel.Unpowered);
	}

	@Override
	public void preInstantBlockUpdate() {
		setSolidBlockRedstoneLevel(SolidBlockRedstoneLevel.Unpowered);
	}

	@Override
	public InstantBlockUpdate instantBlockUpdate(Block block, Direction direction) {
		InstantBlockUpdate result = InstantBlockUpdate.NoUpdate;

		// Incoming modifications
		// Redstone pointing at a solid block will make it weakly powered
		if (block instanceof Redstone) {
			Redstone redstone = (Redstone) block;

			int directionIndex = direction.getIndex(Direction.DIRECTION_FACE_PLANE_ADJACENT);
			int sumConnectedRedstone = 0;
			boolean redstonePointsToSolidBlock = false;

			for (boolean connected : redstone.getConnectedRedstone()) {
				if (connected) {
					sumConnectedRedstone++;
				}
			}
			switch (sumConnectedRedstone) {
			case 0:
				redstonePointsToSolidBlock = true;
				break;
			case 1:
				redstonePointsToSolidBlock = redstone.getConnectedRedstone()[directionIndex];
				break;
			case 2:
				redstonePointsToSolidBlock = false;
				break;
			case 3:
				redstonePointsToSolidBlock = false;
				break;
			case 4:
				// Should never happen
				redstonePointsToSolidBlock = true;
				break;
			}

			if (direction == Direction.Down || redstonePointsToSolidBlock) {
				if (redstone.getBooleanRedstoneLevel()) {
					if (getSolidBlockRedstoneLevel() == SolidBlockRedstoneLevel.Unpowered) {
						setSolidBlockRedstoneLevel(SolidBlockRedstoneLevel.WeaklyPowered);
						result = InstantBlockUpdate.UpdateBoth;
					}
				}
			}
		}

		// A lever connected to a solid block strongly powers the block
		if (block instanceof Lever) {
			Lever lever = (Lever) block;
			SolidBlockRedstoneLevel solidBlockRedstoneLevel = getSolidBlockRedstoneLevel();

			if (lever.getBooleanRedstoneLevel()) {
				if (solidBlockRedstoneLevel == SolidBlockRedstoneLevel.Unpowered
						|| solidBlockRedstoneLevel == SolidBlockRedstoneLevel.WeaklyPowered) {
					setSolidBlockRedstoneLevel(SolidBlockRedstoneLevel.StronglyPowered);
					result = InstantBlockUpdate.UpdateSource;
				}
			}
		}

		// A repeater pointing to a solid block strongly powers the block
		if (block instanceof Repeater) {
			Repeater repeater = (Repeater) block;

			if (repeater.getDirection().opposite() == direction) {
				if (repeater.getBooleanRedstoneLevel()) {
					if (solidBlockRedstoneLevel == SolidBlockRedstoneLevel.Unpowered
							|| solidBlockRedstoneLevel == SolidBlockRedstoneLevel.WeaklyPowered) {
						setSolidBlockRedstoneLevel(SolidBlockRedstoneLevel.StronglyPowered);
						result = InstantBlockUpdate.UpdateSource;
					}
				}
			} else if (repeater.getDirection() == direction) {
				if (!repeater.getInputRedstone()) {
					if (solidBlockRedstoneLevel == SolidBlockRedstoneLevel.Unpowered
							|| solidBlockRedstoneLevel == SolidBlockRedstoneLevel.WeaklyPowered) {
						result = InstantBlockUpdate.UpdateDestination;
					}
				}
			}
		}

		// Outgoing modifications
		if (block instanceof RedstoneTorch) {
			RedstoneTorch redstoneTorch = (RedstoneTorch) block;
			SolidBlockRedstoneLevel solidLevel = getSolidBlockRedstoneLevel();

			if (solidLevel == SolidBlockRedstoneLevel.WeaklyPowered
					|| solidLevel == SolidBlockRedstoneLevel.StronglyPowered) {
				if (redstoneTorch.getInputRedstone()) {
					result = InstantBlockUpdate.UpdateDestination;
				}
			}
		}

		return result;

	}

	public SolidBlockRedstoneLevel getSolidBlockRedstoneLevel() {
		return solidBlockRedstoneLevel;
	}

	private void setSolidBlockRedstoneLevel(SolidBlockRedstoneLevel solidBlockRedstoneLevel) {
		this.solidBlockRedstoneLevel = solidBlockRedstoneLevel;
		switch (this.solidBlockRedstoneLevel) {
		case Unpowered:
			setBooleanRedstoneLevel(false);
			break;
		case WeaklyPowered:
			setBooleanRedstoneLevel(true);
			break;
		case StronglyPowered:
			setBooleanRedstoneLevel(true);
			break;
		}
	}

	@Override
	public ArrayList<String> getDisplayDetails() {
		ArrayList<String> displayDetails = super.getDisplayDetails();
		displayDetails.add("Solid Redstone: ");
		displayDetails.add(String.valueOf(this.solidBlockRedstoneLevel));
		return displayDetails;
	}

	@Override
	public LinkedList<String> encode() {
		LinkedList<String> encodedFields = super.encode();
		encodedFields.push(String.valueOf(getSolidBlockRedstoneLevel()));
		return encodedFields;
	}

	@Override
	public void decode(LinkedList<String> encodedFields) {
		setSolidBlockRedstoneLevel(SolidBlockRedstoneLevel.valueOf(encodedFields.pop()));
		super.decode(encodedFields);
	}
}
