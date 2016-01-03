package robosim.math;

public class Vec2 implements Vec2_ROI {
	private double x, y;
	
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vec2() {
		this(0, 0);
	}
	
	public Vec2(Vec2_ROI rhs) {
		this(rhs.getX(), rhs.getY());
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void copy(Vec2_ROI rhs) {
		x = rhs.getX();
		y = rhs.getY();
	}
	
	public void add(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	public void add(Vec2_ROI rhs) {
		x += rhs.getX();
		y += rhs.getY();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setX(double newX) {
		x = newX;
	}
	
	public void setY(double newY) {
		y = newY;
	}
	
	public Vec2_ROI get() {
		return (Vec2_ROI) this;
	}
	
	public Vec2 getCopy() {
		return new Vec2(x, y);
	}
	
	public Vec2 getScaled(double s) {
		return new Vec2(s * x, s * y);
	}
	
	public void addScaled(double s, Vec2_ROI delta) {
		x += s * delta.getX();
		y += s * delta.getY();
	}

	public double getDot(Vec2_ROI other) {
		return x * other.getX() + y * other.getY();
	}
	
	public double getLen() {
		return Math.sqrt(x*x + y*y);
	}
	
	public double getThetaToY() {
		return Math.atan2(-x, y);
	}
	
	/**
	 *  consider this the yAxis
	 */
	public Vec2 getXaxis() {
		double len = this.getLen();
		double theta = this.getThetaToY();
		
		return new Vec2(len * Math.cos(theta), len * Math.sin(theta));
	}
	
	public Vec2 getUnit() {
		double len = this.getLen();
		return new Vec2(x / len, y / len);
	}

	@Override
	public double getProjectionLengthOn(Vec2_ROI axis) {
		Vec2 unit = axis.getUnit();
		
		return this.getDot(unit);
	}
	
	public Vec2 getVecIn(Vec2_ROI yAxis) {
		Vec2 newVec = new Vec2();
		Vec2 xAxis = yAxis.getXaxis();
		
		newVec.y = this.getProjectionLengthOn(yAxis);
		newVec.x = this.getProjectionLengthOn(xAxis);
		
		return newVec;
	}

	public void projectToRef(Vec2_ROI xAxis, Vec2_ROI yAxis) {
		x = this.getProjectionLengthOn(xAxis);
		y = this.getProjectionLengthOn(yAxis);
	}
	
	public void projectToRef(Vec2_ROI yAxis) {
		Vec2 xAxis = yAxis.getXaxis();
		projectToRef(xAxis, yAxis);
	}
	
	public void transform(double fromYtheta, double toYtheta) {
		double deltaTheta = toYtheta - fromYtheta;
		Vec2 relativeAxis = Vec2.getAxis(deltaTheta);
		
		projectToRef(relativeAxis);
	}
	
	public void transform(Vec2_ROI fromYaxis, Vec2_ROI toYaxis) {
		double fromY = fromYaxis.getThetaToY();
		double toY = toYaxis.getThetaToY();
		
		transform(fromY, toY);
	}
	
	/**
	 * transforms to (0, 1) from yAxis
	 * @param yAxis : base in which Vec2 is assumed to be
	 */
	public void transformToBaseFrom(Vec2 yAxis) {
		transform(0, yAxis.getThetaToY());
	}
	
	public void transformToBaseFrom(double yTheta) {
		transform(0, yTheta);
	}
	
	public static Vec2 getAxis(double theta) {
		return new Vec2(Math.cos(theta), Math.sin(theta));
	}
	
	public void translateTo(Vec2_ROI originCoord) {
		x -= originCoord.getX();
		y -= originCoord.getY();
	}
	
	public void rotateTo(Vec2_ROI yAxis) {
		Vec2 xAxis = yAxis.getXaxis();
		
		x = this.getProjectionLengthOn(xAxis);
		y = this.getProjectionLengthOn(yAxis);
	}
	
	public void rotateTo(double theta) {
		this.rotateTo(Vec2.getAxis(theta));
	}
	
	public void rotateBy(double theta) {
		double len = this.getLen();
		double angle = this.getThetaToY();
		angle += theta;
		
		x = - len * Math.sin(angle);
		y = len * Math.cos(angle);
	}

	public String toString() {
		return new String(Double.toString(x) + " " + Double.toString(y));
	}
}
