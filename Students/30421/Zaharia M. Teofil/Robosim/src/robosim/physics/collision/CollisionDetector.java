package robosim.physics.collision;

import java.awt.Shape;

import robosim.math.Vec2_ROI;
import robosim.util.BoolRef;
import robosim.util.Bool_ROI;

public class CollisionDetector {
	// in
	private final Shape[] obstacles;
	private final Vec2_ROI[] movingObjToCheck;
	// out
	private final BoolRef isColliding;
	
	public CollisionDetector(
			Shape[] obstacles, 
			Vec2_ROI[] movingObjToCheck) {
		this.obstacles = obstacles;
		this.movingObjToCheck = movingObjToCheck;
		this.isColliding = new BoolRef();
	}
	
	private boolean isCollidingWith(Shape obstacle) {
		double x, y;
		
		for (Vec2_ROI point : movingObjToCheck) {
			x = point.getX();
			y = point.getY();
			
			if (obstacle.contains(x, y)) {
				return true;
			}
		}
			
		return false;		
	}
	
	public void update() {
		isColliding.setVal(false);
		
		for (Shape obstacle : obstacles) {
			if (isCollidingWith(obstacle)) {
				isColliding.setVal(true);
				return;
			}
		}
	}
	
	public Bool_ROI getCollision() {
		return isColliding;
	}
	
}
