package Movement;

import java.io.Serializable;

import Object.SharedPreferences;

public class PileClicked implements Serializable{
	
	public static void pileClicked(int pile) {
		if(SharedPreferences.getFromPile() == null) {
			SharedPreferences.setFromPile(pile);
			System.out.println("pile from clicked "+pile);
		} else if (SharedPreferences.getFromPile() != null && SharedPreferences.isCardCliked()){
			SharedPreferences.setToPile(pile);
			System.out.println("pile To clicked "+pile);
		}
	}
}
