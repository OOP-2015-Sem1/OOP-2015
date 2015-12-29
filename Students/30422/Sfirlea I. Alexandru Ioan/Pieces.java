
public class Pieces {
	private String color;
	private int[][] form;
	private int dim_x;
	private int dim_y;
	private int score;

	public void setDim_x(int dim_x) {
		this.dim_x = dim_x;
	}

	public int getDim_x() {
		return dim_x;
	}

	public void setDim_y(int dim_y) {
		this.dim_y = dim_y;
	}

	public int getDim_y() {
		return dim_y;
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

	public void setScore(int score) {
		this.score = score;
	}

	public int getScore() {
		return score;
	}

	public void printer(Pieces var) {
		int i, j;
		int[][] aux = new int[5][5];
		for(i=0;i<var.dim_x;i++)
			for(j=0;j<var.dim_y;j++)
				aux[i][j]=var.form[i][j];
		
		System.out.println("Forma de tipul:");
		for (i = 0; i < var.dim_x; i++) {
			for (j = 0; j < var.dim_y; j++) {
				System.out.print(aux[i][j] + " ");
			}
			System.out.println(" ");
		}
		System.out.println("Culoare: " + var.getColor());
		System.out.println("Score :" + var.getScore());
		System.out.println("nr linii: "+ var.dim_x+ " Numar coloane: "+ var.dim_y);
		System.out.println();
		System.out.println();

	}

	public Pieces(String color, int[][] form, int dim_x, int dim_y, int score) {
		this.color = color;
		this.form = form;
		this.score = score;
		this.dim_x = dim_x;
		this.dim_y = dim_y;
	}
}
