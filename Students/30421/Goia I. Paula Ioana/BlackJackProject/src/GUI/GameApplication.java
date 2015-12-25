package GUI;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import source.Card;
import source.Deck;

public class GameApplication implements ActionListener {

	private int nbPlayers;
	private JFrame gameFrame;
	private ArrayList<JPanel> panelPlayers;
	private JPanel button;
	private JButton hit;
	private JButton stand;
	private JButton newGame;
	private Deck deck;
	private ArrayList<Deck> decks;
	private ArrayList<String> winners;
	private ArrayList<String> playersInformation;

	public GameApplication(int nbPlayers) {
		this.nbPlayers = nbPlayers;
		this.gameFrame = new JFrame();
		this.panelPlayers = new ArrayList<>();
		this.winners = new ArrayList<>();
		this.playersInformation = new ArrayList<>();
		this.hit = new JButton("Hit");
		this.stand = new JButton("Stand");
		this.newGame = new JButton("New Game");
		this.button = new JPanel();
		this.deck = new Deck();
		this.decks = new ArrayList<>();
		this.deck.createFullDeck(this.deck.getDeck());
		this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameFrame.setSize(1200, 780);
		this.gameFrame.setVisible(true);
		setGameLayout();
		initGame();
		addActionListeners();
	}

	private void setGameLayout() {
		int lines = this.nbPlayers;
		if (this.nbPlayers % 2 != 0)
			lines++;
		this.gameFrame.setLayout(new GridLayout((lines + 2) / 2, 2));
		for (int i = 0; i < this.nbPlayers + 1; i++) {
			JPanel jp = new JPanel();
			jp.setLayout(new GridLayout(1, 10));
			this.panelPlayers.add(jp);
			this.gameFrame.add(this.panelPlayers.get(i));
			this.decks.add(new Deck());
		}
		this.button.setLayout(new FlowLayout());
		this.button.add(this.hit);
		this.button.add(this.stand);
		this.button.add(this.newGame);
		this.gameFrame.add(this.button);
	}

	private void initGame() {
		this.deck.shuffledeck();
		for (int i = 0; i < this.nbPlayers + 1; i++) {
			this.decks.get(i).getDeck().add(this.deck.getDeck().get(0));
			this.deck.removeFirstCard();
			this.decks.get(i).getDeck().add(this.deck.getDeck().get(0));
			this.deck.removeFirstCard();
			display(this.decks.get(i), i);
			this.panelPlayers.get(i).repaint();
		}
	}

	private void keepPlayingWithMe() {
		// add to me
		this.decks.get(0).getDeck().add(this.deck.getDeck().get(0));
		this.deck.removeFirstCard();
		display(this.decks.get(0), 0);
		this.panelPlayers.get(0).repaint();
		// add to others
		keepPlayingWithoutMe();
	}

	private void keepPlayingWithoutMe() {
		for (int i = 1; i < this.nbPlayers + 1; i++) {
			if (this.decks.get(i).getSumValue() <= 16) {
				this.decks.get(i).getDeck().add(this.deck.getDeck().get(0));
				this.deck.removeFirstCard();
				display(this.decks.get(i), i);
				this.panelPlayers.get(i).repaint();
			}
		}
	}

	private void display(Deck deck1, int panel) {
		this.panelPlayers.get(panel).removeAll();
		JLabel name = new JLabel(PlayersName.getNamePlayers().get(panel));
		this.panelPlayers.get(panel).add(name);
		this.panelPlayers.get(panel).invalidate();
		if(PlayersName.getNamePlayers().get(panel).equals("Dealer")){
			this.panelPlayers.get(panel).add(
					new JLabel("Value:hidden"));
			this.panelPlayers.get(panel).setLayout(new FlowLayout());
			this.panelPlayers.get(panel).add(new JLabel(deck1.getDeck().get(0).getImg()));
			ImageIcon img = new ImageIcon("images/b.gif");
			deck1.getDeck().get(1).setImg(img);
			this.panelPlayers.get(panel).add(new JLabel(deck1.getDeck().get(1).getImg()));
		}
		else {
			this.panelPlayers.get(panel).add(
					new JLabel("Value:" + deck1.getSumValue()));
			this.panelPlayers.get(panel).setLayout(new FlowLayout());
			for (Card d : deck1.getDeck()) {
				this.panelPlayers.get(panel).add(new JLabel(d.getImg()));
			}
		}		
		this.panelPlayers.get(panel).validate();
		this.panelPlayers.get(panel).repaint();
	}
	
	private void win(){
		Boolean stopGame = false;
		int max21 = 0;
		for (int i = 1; i < this.nbPlayers + 1; i++) {
			if(this.decks.get(i).getSumValue() >= 17){
				stopGame = true;
			}	
		}
		if( stopGame == true){
			for (int i = 0; i < this.nbPlayers + 1; i++) {
				if ( this.decks.get(i).getSumValue() <= 21){
					max21 = this.decks.get(i).getSumValue();
					break;
				}			
			}
			for (int i = 0; i < this.nbPlayers + 1; i++) {
				this.playersInformation.add("   "+PlayersName.getNamePlayers().get(i)+" has the value " + this.decks.get(i).getSumValue());
					if( this.decks.get(i).getSumValue() < 21 && this.decks.get(i).getSumValue() > max21 ){
						max21 = this.decks.get(i).getSumValue();
				}		
			}
			for (int i = 0; i < this.nbPlayers + 1; i++) {
				if ( this.decks.get(i).getSumValue() == max21 ){
					winners.add(PlayersName.getNamePlayers().get(i));
				}
			}
			this.gameFrame.setVisible(false);
			new Winners(winners, this.playersInformation);
		}
		else keepPlayingWithoutMe();
	}

	private void addActionListeners() {
		this.hit.addActionListener(this);
		this.stand.addActionListener(this);
		this.newGame.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.hit) {
			keepPlayingWithMe();
		}
		if (arg0.getSource() == this.stand) {
			keepPlayingWithoutMe();
			win();
		}
		if (arg0.getSource() == this.newGame) {
			this.gameFrame.setVisible(false);
			new StartGame();
		}

	}
	
	public ArrayList<String> getWinners() {
		return winners;
	}
	
}
