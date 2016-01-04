package GameMechanics;

import javax.swing.JPanel;

public class RealEstateTile extends JPanel {
	
	public static int estatePrice;
	public static int housePrice;
	public static int[] estateRent= new int[6];
	
	public boolean isBought;
	public int houses;
	
	public RealEstateTile(int ePrice, int hPrice, int[] rent){
		estatePrice= ePrice;
		housePrice= hPrice;
		estateRent= rent;
	}
	
	
	
}
