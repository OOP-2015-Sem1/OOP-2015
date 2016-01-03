package robosim.world.object;

import robosim.motion.Motion;
import robosim.motion.OrientedPos;
import robosim.physics.collision_old.RectangleBoundary;

public class MovableWorldObj extends WorldObj
	implements MovableWorldObj_ROI {
	
	private Motion motion;
	
	public MovableWorldObj(
			OrientedPos orientedPos,
			RectangleBoundary boundary, 
			Motion motion) {
		super(orientedPos, boundary);
		this.motion = motion;
		this.bindMotionToPos();
	}
	
	private void bindMotionToPos() {
		this.motion.setTargetPos(orientedPos);
	}

	public void move(double timeStep) {
		motion.incremenTargetPos(timeStep);
	}
	

}
