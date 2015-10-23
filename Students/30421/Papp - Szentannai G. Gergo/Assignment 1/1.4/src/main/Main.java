package main;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sideLength = 7;

		// initialize matrix:
		int[][] m = new int[sideLength][sideLength];
		// set cursor to the middle of the matrix:
		int x = sideLength / 2 + 1;
		int y = x;

		// loop through the spiral:
		int step = 1;
		int i = 1;
		int nr = 0;
		int j = 0;
		
		while (nr < sideLength * sideLength) {

			switch (i % 4) {
			case 1:
					x++;
			case 2:
			case 3:
			case 0:
			}
		}

	}

}
