package com.redstonesimulator.model.block;

import java.lang.reflect.InvocationTargetException;

import com.redstonesimulator.model.block.redstone.Lever;
import com.redstonesimulator.model.block.redstone.Redstone;
import com.redstonesimulator.model.block.redstone.RedstoneTorch;
import com.redstonesimulator.model.block.redstone.Repeater;
import com.redstonesimulator.model.block.solid.Cobblestone;
import com.redstonesimulator.model.block.solid.RedstoneLamp;
import com.redstonesimulator.model.block.solid.Sandstone;
import com.redstonesimulator.model.block.solid.Stone;

import utilities.Direction;

public enum BlockID {
	air("Air", Air.class), 
	stone("Stone", Stone.class), 
	cobblestone("Cobblestone", Cobblestone.class), 
	sandstone( "Sandstone", Sandstone.class), 
	lever("Lever", Lever.class), 
	redstone("Redstone", Redstone.class), 
	redstone_torch("Redstone Torch", RedstoneTorch.class),
	repeater("Repeater", Repeater.class),
	redstone_lamp("Redstone Lamp", RedstoneLamp.class);

	private String displayName;
	private Class blockClass;

	private BlockID(String displayName, Class blockClass) {
		this.displayName = displayName;
		this.blockClass = blockClass;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	public static String[] getDisplayNames() {
		BlockID[] blockIDs = BlockID.values();
		int size = blockIDs.length;

		String[] displayNames = new String[size];
		for (int i = 0; i < size; i++) {
			displayNames[i] = blockIDs[i].getDisplayName();
		}
		return displayNames;
	}

	private Class getBlockClass() {
		return this.blockClass;
	}

	private static Class[] getBlockClasses() {
		BlockID[] blockIDs = BlockID.values();
		int size = blockIDs.length;

		Class[] classes = new Class[size];
		for (int i = 0; i < size; i++) {
			classes[i] = blockIDs[i].getBlockClass();
		}
		return classes;
	}
	
	public static BlockID valueOfID(int id) {
		return BlockID.values()[id];
	}

	public static BlockID valueOfBlock(Block block) {
		for (BlockID blockID : BlockID.values()) {
			if (blockID.getBlockClass().equals(block.getClass())) {
				return blockID;
			}
		}

		return air;
	}

	public static Block makeBlock(int id, Direction direction) {
		BlockID[] blockIDs = BlockID.values();
		int size = blockIDs.length;

		Block block = null;
		if (id >= 0 && id < size) {
			try {
				block = (Block) valueOfID(id).getBlockClass().getConstructor().newInstance();
				if (block instanceof Directional) {
					((Directional) block).setDirection(direction);
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
		}
		return block;
	}
	
	public Block makeBlock(Direction direction) {
		Block block = null;
		
		try {
			block = (Block) getBlockClass().getConstructor().newInstance();
			if (block instanceof Directional) {
				((Directional) block).setDirection(direction);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		
		return block;
	}
}
