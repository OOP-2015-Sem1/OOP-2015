package prototypes;

import java.awt.Point;

public class ModifiedPoint extends Point {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3975828002371641450L;
	
	private int maxL;
	private int maxK;
	
	public ModifiedPoint(int i, int j){
		super(i, j);
		setMaxL(0);
		setMaxK(0);
	}

	public int getMaxL() {
		return maxL;
	}

	public void setMaxL(int maxL) {
		this.maxL = maxL;
	}

	public int getMaxK() {
		return maxK;
	}

	public void setMaxK(int maxK) {
		this.maxK = maxK;
	}
}
