package main;

/**
 * 
 * @author gergo_000 Contains the class Polynomial, for storing Polynomial
 *         objects.
 *
 */
public class Polynomial {

	private int degree;
	private int[] pol;

	public Polynomial(int n) {
		// TODO Auto-generated constructor stub
		setDegree(n);
		initArray();
	}

	public Polynomial() {
		setPol(new int[0]);
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int[] getPol() {
		return pol;
	}

	public void setPol(int[] pol) {
		this.pol = pol;
	}

	public void initArray() {
		setPol(new int[getDegree()]);
	}

	public void updatePol(int i, int v) {
		pol[i] = v;
	}

}
