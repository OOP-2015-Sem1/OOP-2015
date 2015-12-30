package main;

import java.awt.Color;

import GUI.GUI;
import GUI.GUIFoundadtionClubs;
import GUI.GUIFoundationDiamonds;
import GUI.GUIFoundationHearts;
import GUI.GUIFoundationSpades;
import GUI.GUIStockPile;
import GUI.GUITableauPile;
import GUI.GUIWastePile;
import Logic.GameLogic;
import Object.Deck;
import Object.SharedPreferences;

public class Solitaire {
	
	private static Deck sSolitaireDeck;
	
	//tableau piles
	private static GUITableauPile sTableauOne;
	private static GUITableauPile sTableauTwo;
	private static GUITableauPile sTableauThree;
	private static GUITableauPile sTableauFour;
	private static GUITableauPile sTableauFive;
	private static GUITableauPile sTableauSix;
	private static GUITableauPile sTableauSeven;
	
	//stock pile
	private static GUIStockPile sStockPile;
	
	//waste pile
	private static GUIWastePile sWastePile;
	
	//foundation piles 
	private static GUIFoundadtionClubs sFoundadtionClubs;
	private static GUIFoundationDiamonds sFoundationDiamonds;
	private static GUIFoundationHearts sFoundationHearts;
	private static GUIFoundationSpades sFoundationSpades;
	
	public static void main(String args[]) {
		
		//initiate player activity - no card selected
		SharedPreferences.setCardCliked(false);
		SharedPreferences.setPlaying(true);
		//get shuffled deck of cards
		sSolitaireDeck = new Deck();

		sTableauOne = new GUITableauPile(1, sSolitaireDeck.getRandomCards(1));
		sTableauTwo = new GUITableauPile(2, sSolitaireDeck.getRandomCards(2));
		sTableauThree = new GUITableauPile(3, sSolitaireDeck.getRandomCards(3));
		sTableauFour = new GUITableauPile(4, sSolitaireDeck.getRandomCards(4));
		sTableauFive = new GUITableauPile(5, sSolitaireDeck.getRandomCards(5));
		sTableauSix = new GUITableauPile(6, sSolitaireDeck.getRandomCards(6));
		sTableauSeven = new GUITableauPile(7, sSolitaireDeck.getRandomCards(7));
		
		sStockPile = new GUIStockPile(24, sSolitaireDeck.getRandomCards(24));
		
		sWastePile = new GUIWastePile();
		
		sFoundadtionClubs = new GUIFoundadtionClubs();
		sFoundationDiamonds = new GUIFoundationDiamonds();
		sFoundationHearts = new GUIFoundationHearts();
		sFoundationSpades = new GUIFoundationSpades();
		
		new GUI(sTableauOne, sTableauTwo, sTableauThree, sTableauFour, sTableauFive, sTableauSix, sTableauSeven,
				sStockPile, sWastePile, sFoundadtionClubs, sFoundationDiamonds, sFoundationHearts, sFoundationSpades);
		
		new GameLogic(sTableauOne, sTableauTwo, sTableauThree, sTableauFour, sTableauFive, sTableauSix, sTableauSeven,
				sStockPile, sWastePile, sFoundadtionClubs, sFoundationDiamonds, sFoundationHearts, sFoundationSpades);
		
	}
	
	public static GUIWastePile getsWastePile() {
		return sWastePile;
	}

	public static GUITableauPile getTableauOne() {
		return sTableauOne;
	}

	public static GUITableauPile getTableauTwo() {
		return sTableauTwo;
	}

	public static GUITableauPile getTableauThree() {
		return sTableauThree;
	}

	public static GUITableauPile getTableauFour() {
		return sTableauFour;
	}

	public static GUITableauPile getTableauFive() {
		return sTableauFive;
	}

		public static GUITableauPile getTableauSix() {
		return sTableauSix;
	}

	public static GUITableauPile getTableauSeven() {
		return sTableauSeven;
	}
	
	public static GUIFoundadtionClubs getsFoundadtionClubs() {
		return sFoundadtionClubs;
	}

	public static void setsFoundadtionClubs(GUIFoundadtionClubs sFoundadtionClubs) {
		Solitaire.sFoundadtionClubs = sFoundadtionClubs;
	}

	public static GUIFoundationDiamonds getsFoundationDiamonds() {
		return sFoundationDiamonds;
	}

	public static void setsFoundationDiamonds(GUIFoundationDiamonds sFoundationDiamonds) {
		Solitaire.sFoundationDiamonds = sFoundationDiamonds;
	}

	public static GUIFoundationHearts getsFoundationHearts() {
		return sFoundationHearts;
	}

	public static void setsFoundationHearts(GUIFoundationHearts sFoundationHearts) {
		Solitaire.sFoundationHearts = sFoundationHearts;
	}

	public static GUIFoundationSpades getsFoundationSpades() {
		return sFoundationSpades;
	}

	public static void setsFoundationSpades(GUIFoundationSpades sFoundationSpades) {
		Solitaire.sFoundationSpades = sFoundationSpades;
	}

	public static void manipulatePile(int bluePile, int destinationPile, int nrOfCardsToManipulate) {
		
		System.out.println("manipulate pile");
		
		Integer blueCardOrder = SharedPreferences.getBlueCardOrder()-1;
		Integer cardOrder = SharedPreferences.getCardOrder();
		
		System.out.println(blueCardOrder+"  "+cardOrder);
		
		GUITableauPile fromPile = SharedPreferences.decideGetPile(bluePile);

		GUITableauPile toPile = SharedPreferences.decideGetPile(bluePile);
		System.out.println(fromPile+"   "+toPile);
		
		for(int i = 0; i <= nrOfCardsToManipulate; i++) {
			System.out.println("sfhjb --- "+fromPile.getComponentCount());
			toPile.addCard(fromPile.getCard(blueCardOrder));
			System.out.println("sfhjb --- "+fromPile.getComponentCount());

			fromPile.getCard(blueCardOrder+i).setCardBackground(Color.WHITE);
			System.out.println("sfhjb --- "+fromPile.getComponentCount());

			fromPile.removeCard(blueCardOrder+i);
			System.out.println("sfhjb --- "+fromPile.getComponentCount());

 			SharedPreferences.setCardCliked(false);
		}
	}
}