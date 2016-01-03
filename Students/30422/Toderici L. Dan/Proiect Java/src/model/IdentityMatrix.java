package model;

public class IdentityMatrix {
	public boolean checkIfIdentical(int[][] n, int[][] m) {
		for (int i = 0; i < n.length; i++)
			for (int j = 0; j < n.length; j++) {
				if (n[i][j] != m[i][j]) {
					return false;
				}
			}
		return true;
	}
}
