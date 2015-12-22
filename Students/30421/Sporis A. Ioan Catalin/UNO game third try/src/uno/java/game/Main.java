package uno.java.game;

import java.awt.Rectangle;

import javax.swing.JFrame;

import uno.java.GUI.ChangeColorPanel;

public class Main {
	public static void main(String[] args){
		Game.startGame(true);
		//Game.startWindow = new StartWindow();
		//NrOfPlayersFrame nrP = new NrOfPlayersFrame();
		//frame.add(btn);
		//GameWindow frame = new GameWindow();
		//EnterPlayerNameFrame name = new EnterPlayerNameFrame();
		//Game.dealCards();
		//System.out.println(Game.deckOfCards.popFromDeck().getCardName());
		/*JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setSize(900, 500);
		frame.setLayout(null);
		ChangeColorPanel pan = new ChangeColorPanel();
		pan.setBounds(new Rectangle(20,50,pan.getWidth(),pan.getHeight()));
		frame.add(pan);
		/*int x=0;
		int  y=0;
		Deck deck = new Deck();
		for(Card c: deck.deck){
			c.setBounds(x, y, 95, 152);
			if(x<700){
				x = x+20;
			}
			else{
				x=0;
				y=y+150;
			}
			frame.add(c);
			System.out.println(c.getCardName()+ " " + c.getColor());
		}*/
	}
	
}
