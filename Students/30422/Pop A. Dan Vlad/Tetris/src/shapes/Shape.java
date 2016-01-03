package shapes;

public class Shape {
	public int forma[][] = new int[3][3];

	public void initShape() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				forma[i][j] = 0;
			}

		}
	}
}
