package utilities;

public enum Direction {
	North(new Int3DPoint(0, -1, 0)),
	South(new Int3DPoint(0, 1, 0)),
	East(new Int3DPoint(1, 0, 0)),
	West(new Int3DPoint(-1, 0, 0)),
	
	Up(new Int3DPoint(0, 0, 1)),
	Down(new Int3DPoint(0, 0, -1));
	
	private Int3DPoint point;
	
	private Direction(Int3DPoint point) {
		this.point = point;
	}
	
	public static final Direction[] DIRECTION_FACE_ADJACENT = { North, South, East, West, Up, Down };
	public static final Direction[] DIRECTION_FACE_PLANE_ADJACENT = { North, South, East, West };
	
	public static final Direction[] DIRECTION_FACE_PLANE_UP_ADJACENT = { North, South, East, West, Up };
	public static final Direction[] DIRECTION_FACE_PLANE_DOWN_ADJACENT = { North, South, East, West, Down };
	
	public static final int[] DIRECTION_FACE_PLANE_ADJACENT_ID_OPPOSITE_LOOKUP = { 1, 0, 3, 2};
	public static final Direction[] DIRECTION_FACE_PLANE_ADJACENT_OPPOSITE = { South, North, West, East };
	public static final Direction[] DIRECTION_FACE_PLANE_ADJACENT_ORDERED = { North, East, South, West };
	
	public static int getIndex(Direction[] directionArray, int index) {
		return directionArray[index].getIndex(directionArray);
	}
	
	public int getIndex(Direction[] directionArray) {
		for (int index = 0; index < directionArray.length; index++) {
			if (this.equals(directionArray[index])) {
				return index;
			}
		}
		
		return -1;
	}
	
	public boolean isContained(Direction[] directionArray) {
		return this.getIndex(directionArray) != -1;
	}
	
	public int getIndex() {
		return getIndex(DIRECTION_FACE_PLANE_ADJACENT);
	}
	
	public Direction opposite() {
		return DIRECTION_FACE_PLANE_ADJACENT_OPPOSITE[getIndex()];
	}
	
	public Int3DPoint getPoint() {
		return point;
	}
}
