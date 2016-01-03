package robosim.world.object;

import robosim.motion.OrientedPos_ROI;
import robosim.physics.collision_old.RectangleBoundary_ROI;

public interface WorldObj_ROI {
	public boolean collidesWith(WorldObj_ROI other);
	public OrientedPos_ROI getOrientedPos();
	public RectangleBoundary_ROI getBoundary();
}
