package Object;
import java.io.Serializable;
import java.util.ArrayList;

import GUI.GUITableauPile;
import main.Solitaire;

public class SharedPreferences implements Serializable{
	
	private static boolean playing;
	private static boolean isCardCliked;
	private static Integer fromPile = null;
	private static Integer toPile = null;
	private static Integer blueCardPile;
	private static Integer blueCardOrder;
	private static Integer cardOrder;

	private static ArrayList<Integer> remainedCards = new ArrayList<>();
	
	public SharedPreferences() {
		
	}
	
	public static Integer getBlueCardOrder() {
		return blueCardOrder;
	}

	public static void setBlueCardOrder(Integer blueCardOrder) {
		SharedPreferences.blueCardOrder = blueCardOrder;
	}

	public static void setCardOrder(Integer card) {
		cardOrder = card;
	}
	
	public static Integer getCardOrder() {
		return cardOrder;
	}

	public static Integer getBlueCardPile() {
		return blueCardPile;
	}

	public static void setBlueCardPile(Integer blueCardPile) {
		SharedPreferences.blueCardPile = blueCardPile;
	}

	public static boolean isPlaying() {
		return playing;
	}

	public static void setPlaying(boolean playing) {
		SharedPreferences.playing = playing;
	}

	public static Integer getRemainedCards(Integer i) {
		return remainedCards.get(i);
	}

	public static void setRemainedCards(Integer i, int remain) {
		remainedCards.set(i, remain);
	}

	public static Integer getFromPile() {
		return fromPile;
	}

	public static void setFromPile(Integer fromPile) {
		SharedPreferences.fromPile = fromPile;
	}

	public static Integer getToPile() {
		return toPile;
	}

	public static void setToPile(Integer toPile) {
		SharedPreferences.toPile = toPile;
	}

	public static boolean isCardCliked() {
		return isCardCliked;
	}

	public static  void setCardCliked(boolean cardCliked) {
		isCardCliked = cardCliked;
	}

	public static GUITableauPile decideGetPile(Integer pile) {
		switch (pile) {
			case 1 :
				return Solitaire.getTableauOne();
			case 2 :
				return Solitaire.getTableauTwo();
			case 3 :
				return Solitaire.getTableauThree();
			case 4 :
				return Solitaire.getTableauFour();
			case 5 :
				return Solitaire.getTableauFive();
			case 6 :
				return Solitaire.getTableauSix();
			case 7 :
				return Solitaire.getTableauSeven();		
		}
		return null;
	}
}