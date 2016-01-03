package robosim;

import robosim.boundedobj.BoundedObj;
import robosim.boundedobj.BoundedObjFactory;
import robosim.controller.RobotController;
import robosim.math.Vec2_ROI;
import robosim.motion.OrientedPos;
import robosim.physics.collision.CollisionSystem;
import robosim.physics.drive.ElectricDrive;
import robosim.physics.dynamics.NetForceComputer;
import robosim.physics.dynamics.NetTorqueComputer;
import robosim.physics.dynamics.ObjDynamics;
import robosim.physics.dynamics.RobotSpec;
import robosim.util.DoubleRefDFF;
import robosim.util.DoubleRefToVec2;
import robosim.util.Double_ROI;
import robosim.util.Vec2DFF;

public class Simulation {
	// in
	private final RobotController controller;
	// interior
	private final ElectricDrive leftDrive, rightDrive;
	private final NetForceComputer forceComputer;
	private final NetTorqueComputer torqueComputer;
	private final ObjDynamics robotDynamics;
	private final RobotSpec robotSpec;
	private final CollisionSystem collisionDetector;
	
	private final BoundedObj robotBoundary;
	private final BoundedObj[] obstacleBoundaries;
	
	private final DoubleRefToVec2 leftDriveForceConverter, rightDriveForceConverter;
	private final DoubleRefDFF robotOrientation, vLong, vLat, fNormal;
	private final Vec2DFF robotPos;
	
	public Simulation(Double_ROI TIME_STEP, String robotConfig, String worldConfigFileName) {
		controller = new RobotController();
		
		Double_ROI leftDrivePower = controller.getPowerLeft();
		Double_ROI rightDrivePower = controller.getPowerRight();
		
		// TODO: left drive and right drive into 1 class
		// to avoid code duplication
		
		vLong = new DoubleRefDFF();
		vLat = new DoubleRefDFF();
		robotOrientation = new DoubleRefDFF();
		
		
		leftDrive = new ElectricDrive(
				leftDrivePower, 
				TIME_STEP, vLong.getRef(), vLat.getRef(), fNormal.getRef());
		rightDrive = new ElectricDrive(
				rightDrivePower, 
				TIME_STEP, vLong.getRef(), vLat.getRef(), fNormal.getRef());
		
		Double_ROI fLatLeft = leftDrive.getfLat(), fLongLeft = leftDrive.getfLong();
		Double_ROI fLatRight = rightDrive.getfLat(), fLongRight = rightDrive.getfLong();
		
		leftDriveForceConverter = new DoubleRefToVec2(
				fLatLeft, fLongLeft, robotOrientation.getVal(), 0);
		rightDriveForceConverter = new DoubleRefToVec2(
				fLatRight, fLongRight, robotOrientation.getVal(), 0);
		
		Vec2_ROI absFleft = leftDriveForceConverter.getVec2();
		Vec2_ROI absFright = rightDriveForceConverter.getVec2();
		
		robotSpec = new RobotSpec(0.1, 0.1, 0.1, 0.1, 0.1);
		
		robotPos = new Vec2DFF();
		OrientedPos robotPos = new OrientedPos(robotPos.getRef(), robotOrientation.getRef().getVal(), null);
		robotBoundary = new BoundedObj(oPos, width, height);
		BoundedObjFactory objFactory = new BoundedObjFactory();
		obstacleBoundaries = objFactory.constrBoundedObjArrFrom(worldConfigFileName);
		
		forceComputer = new NetForceComputer(absFleft, absFright);
		torqueComputer = new NetTorqueComputer(absFleft, absFright, robotSpec.getDistToWheels());
		
		
		
		collisionDetector = new CollisionSystem(robotBoundary, obstacleBoundaries);
		
		robotDynamics = new ObjDynamics(robotSpec, isColliding, netAbsForce, netTorque, tIME_STEP, initialOpos)
		
		
	}
}
