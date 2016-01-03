package robosim.physics.collision;

import java.awt.Shape;

import robosim.boundedobj.BoundedObj;
import robosim.math.Vec2_ROI;
import robosim.util.Bool_ROI;

public class CollisionSystem {
	// in
//	private final BoundedObj target;
//	private final BoundedObj[] obstacles;
	// out
	private final Bool_ROI isColliding;
	// interior nodes
	private final Shape[] obstacleShapes;
	private final Vec2_ROI[] targetBoundary;
	// permanent components
	private final Vec2Poly boundedObjToPointArr;
	private final CollisionDetector collisionDetector;
	
	public CollisionSystem(BoundedObj target, BoundedObj[] obstacles) {
//		this.target = target;
//		this.obstacles = obstacles;
		
		ShapeFactory shapeFactory = new ShapeFactory();
		obstacleShapes = shapeFactory.createShapeArrFrom(obstacles);
		shapeFactory = null; 
		// destroy, not needed anymore, temporary component;
		
		this.boundedObjToPointArr = new Vec2Poly(target); 
		targetBoundary = this.boundedObjToPointArr.getPoints();
		
		this.collisionDetector = new CollisionDetector(obstacleShapes, targetBoundary);
		this.isColliding = this.collisionDetector.getCollision();
	}
	
	public void update() {
		this.boundedObjToPointArr.update();
		this.collisionDetector.update();
	}
	
	public Bool_ROI getCollisionDetection() {
		return isColliding;
	}
	
	
		
}
