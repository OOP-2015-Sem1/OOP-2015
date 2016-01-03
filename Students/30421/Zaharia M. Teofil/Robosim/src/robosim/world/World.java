package robosim.world;

import java.util.ArrayList;

import robosim.world.object.MovableWorldObj;
import robosim.world.object.WorldObj;

public class World implements
	World_ROI {
	
	ArrayList<WorldObj> staticObj;
	MovableWorldObj movingObj;
	
	World() {
	}
	
	public void addStaticObj(WorldObj staticObj) {
		this.staticObj.add(staticObj);
	}
	
	public void setMovableWorldObj(MovableWorldObj movWorldObj) {
		this.movingObj = movWorldObj;
	}
	
	private boolean notCollides() {
		for (WorldObj obstacle : staticObj) {
			if (movingObj.collidesWith(obstacle)) {
				return false;
			}
		}
		
		return true;
	}
	
	public void moveObj(double timeStep) {
		if (notCollides()) {
			movingObj.move(timeStep);
		}
	}
	

}
