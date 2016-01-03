package utilities;

public class Int3DPoint {
	private int x;
	private int y;
	private int z;

	public Int3DPoint(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Int3DPoint() {
		this(0, 0, 0);
	}

	public Int3DPoint add(Int3DPoint point) {
		int newX = this.getX() + point.getX();
		int newY = this.getY() + point.getY();
		int newZ = this.getZ() + point.getZ();
		return new Int3DPoint(newX, newY, newZ);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	@Override
	public String toString() {
		return "[x=" + this.getX() + ",y=" + this.getY() + ",z=" + this.getZ() + "]";
	}
}
