package GameMechanics;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class RealEstateTile extends Tile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4387708779896108905L;
	public static int estatePrice;
	public static int housePrice;
	public static int[] estateRent= new int[6];
	public static JPanel houseArea= new JPanel();
	
	public boolean isBought;
	public int houses;
	
	public RealEstateTile(int ePrice, int hPrice, int[] rent){
		estatePrice= ePrice;
		housePrice= hPrice;
		estateRent= rent;
		setLayout(new BorderLayout());
	}
	
	
	
}
