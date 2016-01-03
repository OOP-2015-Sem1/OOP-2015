
public class Piece {

	private String color;
	private int[][] form;
	private int dimI;
	private int dimJ;

	public void setDimI(int dimI) {
		this.dimI = dimI;
	}

	public void setDimJ(int dimJ) {
		this.dimJ = dimJ;
	}

	public int getDimI() {
		return dimI;
	}

	public int getDimJ() {
		return dimJ;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setForm(int[][] form) {
		this.form = form;
	}

	public int[][] getForm() {
		return form;
	}

	public Piece(String color, int[][] aux, int dimI,int dimJ) {
		this.color = color;
		this.form = aux;
		this.dimI=dimI;
		this.dimJ=dimJ;
	}

	public static void printPiece(Piece variable) {
		System.out.println("Culoarea piesei este: " + variable.color);
		System.out.println("dimI : " + variable.dimI);
		System.out.println("dimJ : " + variable.dimJ);
		int dimI, dimJ;
		dimI = variable.dimI;
		dimJ = variable.dimJ;
		int[][] aux = new int[dimI][dimJ];
		aux = variable.getForm();

		System.out.println("forma piesei:");
		for (int i = 0; i < dimI; i++) {
			for (int j = 0; j < dimJ; j++) {
				System.out.print(aux[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();
	}

}
