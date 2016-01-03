package uno.java.entities;

import java.awt.Color;
import java.util.ArrayList;

public interface CardDealer {
	public ArrayList<Card> createNormalCards(Color color);

	public ArrayList<Card> createSpecialCards(String cardName, int draw, boolean wild, boolean skip, boolean reverse,
			Color color);

}