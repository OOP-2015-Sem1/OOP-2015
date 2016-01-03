package com.redstonesimulator.model;

import java.util.ArrayList;

import com.redstonesimulator.model.block.Air;
import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.DelayedUpdatable;
import com.redstonesimulator.model.block.InstantBlockUpdate;
import com.redstonesimulator.model.block.InstantlyUpdateable;
import com.redstonesimulator.model.block.Usable;
import com.redstonesimulator.model.block.redstone.Redstone;
import com.redstonesimulator.model.block.redstone.RedstoneTorch;
import com.redstonesimulator.model.block.redstone.Repeater;
import com.redstonesimulator.model.block.solid.SolidBlock;
import com.redstonesimulator.model.block.solid.Stone;

import utilities.Direction;
import utilities.Int3DPoint;

public class World {
	private static final int MIN_X_SIZE = 1;
	private static final int MIN_Y_SIZE = 1;
	private static final int MIN_Z_SIZE = 1;

	private String worldName;
	private boolean modified;

	private Block[][][] blocks;
	private int xSize;
	private int ySize;
	private int zSize;

	private ArrayList<InstantlyUpdateable> instantBlockUpdateGenerators;
	private ArrayList<InstantlyUpdateable> instantBlockUpdateQueue;

	private ArrayList<DelayedUpdatable> delayedBlockUpdateGenerators;

	public World(String worldName, int xSize, int ySize, int zSize) {
		this.setWorldName(worldName);
		this.setModified(true);

		this.setXSize(xSize);
		this.setYSize(ySize);
		this.setZSize(zSize);

		int xs = this.getXSize();
		int ys = this.getYSize();
		int zs = this.getZSize();

		this.blocks = new Block[xs][ys][zs];
		for (int x = 0; x < xs; x++) {
			for (int y = 0; y < ys; y++) {
				for (int z = 0; z < zs; z++) {
					if (z == 0) {
						this.blocks[x][y][z] = new Stone();
					} else {
						this.blocks[x][y][z] = new Air();
					}
				}
			}
		}

		this.instantBlockUpdateGenerators = new ArrayList<>();
		this.instantBlockUpdateQueue = new ArrayList<>();

		this.delayedBlockUpdateGenerators = new ArrayList<>();
	}

	public void nextInstantState() {
		setModified(true);

		// Preset all instant redstone components
		for (InstantlyUpdateable block : instantBlockUpdateGenerators) {
			block.preInstantBlockUpdate();
		}

		// Perform the instant update
		this.instantBlockUpdateQueue.addAll(instantBlockUpdateGenerators);
		int index = 0;
		while (index < instantBlockUpdateQueue.size()) {
			instantBlockUpdate(instantBlockUpdateQueue.get(index));
			index++;
		}
		this.instantBlockUpdateQueue.clear();
	}

	public void nextDelayedState() {
		for (DelayedUpdatable block : delayedBlockUpdateGenerators) {
			block.delayedBlockUpdate();
		}
	}

	public void nextState() {
		setModified(true);
		nextDelayedState();
		nextInstantState();
	}

	private void instantBlockUpdate(InstantlyUpdateable sourceBlock) {
		Int3DPoint point = getPos((Block) sourceBlock);
		for (Direction direction : Direction.DIRECTION_FACE_PLANE_ADJACENT) {
			Int3DPoint adjacentPoint = point.add(direction.getPoint());
			if (this.isInRange(adjacentPoint)) {
				Block destinationBlock = this.getBlock(adjacentPoint);
				InstantBlockUpdate result = sourceBlock.instantBlockUpdate(destinationBlock, direction);

				switch (result) {
				case NoUpdate:
					break;
				case UpdateSource:
					this.instantBlockUpdateQueue.add(sourceBlock);
					break;
				case UpdateDestination:
					if (destinationBlock instanceof InstantlyUpdateable) {
						this.instantBlockUpdateQueue.add((InstantlyUpdateable) destinationBlock);
					}
					break;
				case UpdateBoth:
					this.instantBlockUpdateQueue.add(sourceBlock);
					if (destinationBlock instanceof InstantlyUpdateable) {
						this.instantBlockUpdateQueue.add((InstantlyUpdateable) destinationBlock);
					}
					break;
				default:
					break;
				}
			}
		}
	}

	private void placeUpdate() {
		for (InstantlyUpdateable block : instantBlockUpdateGenerators) {
			Int3DPoint point = getPos((Block) block);
			if (block instanceof Redstone) {
				Redstone redstone = (Redstone) block;
				redstone.resetConnectedRedstone();

				for (Direction direction : Direction.DIRECTION_FACE_PLANE_ADJACENT) {
					Int3DPoint adjacentPoint = point.add(direction.getPoint());
					if (this.isInRange(adjacentPoint)) {
						Block adjacentBlock = this.getBlock(adjacentPoint);
						redstone.updateConnectedRedstone(adjacentBlock, direction);
					}
				}

				redstone.pushConnectedRedstone();
			}
		}
	}

	private boolean isInRange(Int3DPoint point) {
		int x = point.getX();
		int y = point.getY();
		int z = point.getZ();
		return ((x >= 0) && (x < this.getXSize())) && ((y >= 0) && (y < this.getYSize()))
				&& ((z >= 0) && (z < this.getZSize()));
	}

	public void useBlock(Int3DPoint point) {
		if (this.isInRange(point)) {
			Block block = this.getBlock(point);
			if (block instanceof Usable) {
				((Usable) block).use();
			}
		}
		nextInstantState();
	}

	public boolean removeBlock(Int3DPoint point) {
		boolean result;
		if (result = this.isInRange(point)) {
			Block block = this.getBlock(point);
			
			if (block instanceof InstantlyUpdateable) {
				instantBlockUpdateGenerators.remove((InstantlyUpdateable) block);
			}
			if (block instanceof DelayedUpdatable) {
				this.delayedBlockUpdateGenerators.remove((InstantlyUpdateable) block);
			}
			int x = point.getX();
			int y = point.getY();
			int z = point.getZ();
			blocks[x][y][z] = new Air();

			placeUpdate();
			nextInstantState();
			setModified(true);
		}

		return result;
	}

	public boolean setBlock(Int3DPoint point, Block block) {
		boolean canPlace = isInRange(point);

		if (block instanceof Redstone || block instanceof RedstoneTorch || block instanceof Repeater) {
			if (!(getBlock(point.add(Direction.Down.getPoint())) instanceof SolidBlock)) {
				canPlace = false;
			}
		}

		if (canPlace) {
			Block existingBlock = getBlock(point);
			if (!(existingBlock instanceof Air)) {
				removeBlock(point);
			}

			int x = point.getX();
			int y = point.getY();
			int z = point.getZ();
			blocks[x][y][z] = block;

			if (block instanceof InstantlyUpdateable) {
				instantBlockUpdateGenerators.add((InstantlyUpdateable) block);
			}

			if (block instanceof DelayedUpdatable) {
				delayedBlockUpdateGenerators.add((DelayedUpdatable) block);
			}

			placeUpdate();
			nextInstantState();
			setModified(true);
		}
		return canPlace;
	}

	public Block getBlock(Int3DPoint point) {
		int x = point.getX();
		int y = point.getY();
		int z = point.getZ();
		boolean inRange = this.isInRange(point);
		if (inRange) {
			return this.blocks[x][y][z];
		} else {
			return null;
		}
	}

	public Int3DPoint getPos(Block block) {
		for (int z = 0; z < getZSize(); z++) {
			for (int x = 0; x < getXSize(); x++) {
				for (int y = 0; y < getYSize(); y++) {
					Int3DPoint point = new Int3DPoint(x, y, z);
					if (block.equals(getBlock(point))) {
						return point;
					}
				}
			}
		}
		return null;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public int getXSize() {
		return this.xSize;
	}

	private void setXSize(int xSize) {
		if (xSize >= MIN_X_SIZE) {
			this.xSize = xSize;
		} else {
			this.xSize = MIN_X_SIZE;
		}
	}

	public int getYSize() {
		return this.ySize;
	}

	private void setYSize(int ySize) {
		if (ySize >= MIN_Y_SIZE) {
			this.ySize = ySize;
		} else {
			this.xSize = MIN_Y_SIZE;
		}
	}

	public int getZSize() {
		return this.zSize;
	}

	private void setZSize(int zSize) {
		if (zSize >= MIN_Z_SIZE) {
			this.zSize = zSize;
		} else {
			this.zSize = MIN_Z_SIZE;
		}
	}
}
