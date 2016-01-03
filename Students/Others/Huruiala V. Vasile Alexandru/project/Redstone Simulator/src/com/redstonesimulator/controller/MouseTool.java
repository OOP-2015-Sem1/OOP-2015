package com.redstonesimulator.controller;

public enum MouseTool {
	place("Place"), 
	interact("Interact"), 
	select("Select"), 
	pan("Pan");
	
	private String displayName;
	
	private MouseTool(String displayName) {
		this.displayName = displayName;
	}
	
	public int getMouseToolIndex() {
		MouseTool[] mouseTools = values();
		for (int index = 0; index < mouseTools.length; index++) {
			if (this.equals(mouseTools[index])) {
				return index;
			}
		}
		return -1;
	}

	public String getDisplayName() {
		return displayName;
	}
}
