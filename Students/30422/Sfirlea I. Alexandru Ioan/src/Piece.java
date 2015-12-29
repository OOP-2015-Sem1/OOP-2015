
public class Piece {

	private String color;
	private int[][] form;

	
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

	public Piece(String color, int[][] aux) {
		this.color=color;
		this.form=aux;
	}
	public static void printPiece(Piece variable){
		System.out.println("Culoarea piesei este: "+ variable.color);
		int[][] aux= new int[5][5];
		aux=variable.getForm();
		System.out.println("forma piesei:");
		for (int i = 0; i <= 4; i++) {
			for (int j = 0; j <= 4; j++) {
				System.out.print(aux[i][j]+" ");
			}
			System.out.println();
		}

		System.out.println();
		System.out.println();
	}

		
}

