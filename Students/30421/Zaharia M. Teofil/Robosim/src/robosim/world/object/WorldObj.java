package robosim.world.object;

import robosim.math.Vec2;
import robosim.motion.OrientedPos;
import robosim.motion.OrientedPos_ROI;
import robosim.physics.collision_old.RectangleBoundary;
import robosim.physics.collision_old.RectangleBoundary_ROI;

public class WorldObj implements WorldObj_ROI{
	protected OrientedPos orientedPos;
	protected RectangleBoundary boundary;
	
	public WorldObj(
			OrientedPos pos, 
			RectangleBoundary boundary) {
		this.orientedPos = pos;
		this.boundary = boundary;
	}
	
	public void moveTo(Vec2 newPos) {
		orientedPos.translateTo(newPos);
	}
	
	public void rotateTo(double newTheta) {
		orientedPos.rotateTo(newTheta);
	}
	
	public boolean collidesWith(WorldObj_ROI other) {
		return boundary.intersectsInside(other.getBoundary()) ||
				other.getBoundary().intersectsInside(boundary);
	}
	
	public OrientedPos_ROI getOrientedPos() {
		return (OrientedPos_ROI) orientedPos;
	}
	
	public RectangleBoundary_ROI getBoundary() {
		return (RectangleBoundary_ROI) boundary;
	}
	
	
}
