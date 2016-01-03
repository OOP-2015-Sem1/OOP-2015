package robosim.math;

public interface Vec2_ROI {
	public double getX();
	public double getY();
	public Vec2_ROI get();
	public double getDot(Vec2_ROI other);
	public double getLen();
	public double getThetaToY();
	public Vec2 getXaxis();
	public Vec2 getUnit();
	public double getProjectionLengthOn(Vec2_ROI axis);
	public Vec2 getCopy();
	public Vec2 getScaled(double s);
	public Vec2 getVecIn(Vec2_ROI yAxis);
}
