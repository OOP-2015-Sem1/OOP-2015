package org.game.cards;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class PokerGui extends JFrame implements ActionListener {
	private JButton betButton , dealButton , playAgain;
	private JButton[] buttonArray= new JButton[5];
	private JPanel middlePanel , topPanel , bottomPanel , buttonPanel , optPanel , logoPanel;
	protected static JLabel blank1 , blank2 , bankLabel , nameLabel , betLabel , logoLabel;
	private JLabel[] board;
	protected static JRadioButton[] radioButton ;
	private ButtonGroup radioButtons=new ButtonGroup();
	private JLabel textLabel ;
	private Player player = new Player();
	private Deck deck;
	private boolean[] isDiscard = new boolean[5];
	private ButtonGroup group = new ButtonGroup();
	private int betAmount , win;
	private Card[] card;
	
	public void setName(String name) {
		if(name == null || name.equals(""))
			nameLabel.setText("Name : Player");
		else {
			nameLabel.setText("Name: "+ name);
			player.playerName = name;
		}
	}
	
	public void setGame(String game) {
		try{
			player.loadGame(game);
		}
		catch(java.util.NoSuchElementException e){
			JOptionPane.showMessageDialog(null, "The name you provided doesn't exist","Uh oh!", JOptionPane.WARNING_MESSAGE);
			//nameLabel.setText("Name: "+ name);
			bankLabel.setText("Bank : $1000");
		}
	}
	
	public void Game() {
		deck =new Deck();
		player= new Player();
		logoPanel= new JPanel(new GridLayout(1,1));
		topPanel= new JPanel(new GridLayout(1,3));
		middlePanel=new JPanel(new GridLayout(2,2));
		bottomPanel= new JPanel(new GridLayout(2,1));
		optPanel=new JPanel(new GridLayout(1,2));
		
		logoLabel= new JLabel(new ImageIcon("logo.jpg"));
		logoPanel.add(logoLabel);
		
		bankLabel=new JLabel("Bank: $"+player.bank, JLabel.CENTER);
		bankLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		bankLabel.setFont(new Font("Arial", Font.BOLD , 12));
		betLabel= new JLabel("Bet Amount: $"+player.betAmount,JLabel.CENTER);
		betLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		betLabel.setFont(new Font("Arial", Font.BOLD , 12));
		nameLabel=new JLabel("Name:",JLabel.CENTER);
		nameLabel.setBorder(BorderFactory.createLineBorder(Color.black));
		nameLabel.setFont(new Font("Arial", Font.BOLD, 12));
		
		topPanel.add(bankLabel);
		topPanel.add(betLabel);
		topPanel.add(nameLabel);
		
		dealButton=new JButton("Deal");
		dealButton.setEnabled(false);
		dealButton.addActionListener(this);
		
		textLabel=new JLabel("Place bet",JLabel.CENTER);
		textLabel.setBorder(BorderFactory.createLineBorder(Color.green,2));
		textLabel.setOpaque(true);
		textLabel.setBackground(Color.green);
		textLabel.setFont(new Font("Arial",Font.BOLD,12));
		playAgain=new JButton("Play Again");
		playAgain.setEnabled(false);
		playAgain.addActionListener(this);
		bottomPanel.add(dealButton);
		optPanel.add(textLabel);
		optPanel.add(playAgain);
		bottomPanel.add(optPanel);
		
		board=new JLabel[5];
		
		for(int i=0; i<5;i++){
			JLabel label=new JLabel();
			board[i]=label;
			middlePanel.add(board[i]);
			isDiscard[i]=false;
		}
		
		radioButton=new JRadioButton[6];
		bottomPanel=new JPanel(new GridLayout(8,1));
		
		JRadioButton bet1=new JRadioButton("$10");
		bet1.addActionListener(this);
		group.add(bet1);
		radioButton[0]=bet1;
		
		JRadioButton bet2=new JRadioButton("$25");
		bet2.addActionListener(this);
		group.add(bet2);
		radioButton[0]=bet2;
		
		JRadioButton bet3=new JRadioButton("$50");
		bet3.addActionListener(this);
		group.add(bet3);
		radioButton[0]=bet3;
		
		JRadioButton bet4=new JRadioButton("$100");
		bet4.addActionListener(this);
		group.add(bet4);
		radioButton[0]=bet4;
		
		JRadioButton bet5=new JRadioButton("$250");
		bet5.addActionListener(this);
		group.add(bet5);
		radioButton[0]=bet5;
		
		JRadioButton bet6=new JRadioButton("$500");
		bet6.addActionListener(this);
		group.add(bet6);
		radioButton[0]=bet6;
		
		
		for(int i=0;i<6;i++)
		{
			buttonPanel.add(radioButton[i]);
			radioButtons.add(radioButton[i]);
		}
		middlePanel.add(buttonPanel);
		
		for(int i=0;i<5;i++){
			buttonArray[i]=new JButton("Discard");
			buttonArray[i].setEnabled(false);
			buttonArray[i].addActionListener(this);
			middlePanel.add(buttonArray[i]);
			
		}
		
		betButton=new JButton("Place Bet");
		betButton.addActionListener(this);
		betButton.setEnabled(false);
		middlePanel.add(betButton);
		
		setLayout(new GridLayout(4,1));
		add(logoPanel);
		add(topPanel);
		add(middlePanel);
		add(bottomPanel);
}
	
	public void actionPerformed(ActionEvent event){
		for(int i=0;i<6;i++){
			if(event.getSource()==radioButton[i]){
				betButton.setEnabled(true);
				betAmount=i;
			}
		}
		
		if(event.getSource()==betButton){
			Card[] hand = player.getCards();
			Card tempCard;
			for(int i=0;i<5;i++){
				tempCard=player.getCardAtIndex(i);
				String string=tempCard.toString();
				ImageIcon icon=new ImageIcon(string );
				board[i].setIcon(icon);
				buttonArray[i].setEnabled(true);
			}
			
		betButton.setEnabled(false);
		dealButton.setEnabled(true);
		playAgain.setEnabled(false);
		textLabel.setText("Choose which cards to discard , then press DEAL.");
		textLabel.setOpaque(true);
		textLabel.setBackground(Color.green);
		textLabel.setBorder(BorderFactory.createLineBorder(Color.green,1));
		
		if(betAmount==0)
			betAmount=10;
		else if(betAmount==1)
			betAmount=25;
		else if(betAmount==2)
			betAmount=50;
		else if(betAmount==3)
			betAmount=100;
		else if(betAmount==4)
			betAmount=250;
		else if(betAmount==5)
			betAmount=500;
		
		betLabel.setText("Bet Amount: $" + betAmount);
		player.bank=player.bank-betAmount;
		bankLabel.setText("Bank: $" + player.bank);
		for(int i=0;i<6;i++)
			radioButton[i].setEnabled(false);
		radioButtons.clearSelection();
	}
		for(int i=0;i<5;i++){
			if(event.getSource()==buttonArray[i] && buttonArray[i].getText()=="Discard")
			{
				board[i].setBorder(BorderFactory.createLineBorder(Color.red ,2));
				buttonArray[i].setText("Hold");
				isDiscard[i]=true;
			}
			else if (event.getSource()==buttonArray[i] && buttonArray[i].getText()=="Hold")
			{
				board[i].setBorder(null);
				buttonArray[i].setText("Discard");
				isDiscard[i]=false;
			}
		}
		
		if(event.getSource()==dealButton){
			Card[] newCard;
			for(int i=0;i<5;i++){
				if(isDiscard[i]){
					newCard=player.getCards();
					Card[][] hand = null;
					hand[i]=newCard;
					String string=newCard.toString();
					ImageIcon icon=new ImageIcon(string);
					board[i].setIcon(icon);
					board[i].setBorder(null);
					buttonArray[i].setText("Discard");
					isDiscard[i]=false;
				}
			}
			String winString;
			switch(win) {
			case 1:
				winString="a flush";
				player.bank = player.bank + (9*betAmount);
				bankLabel.setText("Bank: $" + player.bank);
				break;
			case 2:
				winString = " a straight";
				player.bank = player.bank + (25*betAmount);
				bankLabel.setText("Bank: $" + player.bank);
				break;
			case 3 :
				winString="full house";
				player.bank = player.bank + (6*betAmount);
				bankLabel.setText("Bank: $" + player.bank);
				break;
			case 4:
				winString = " four of a kind";
			    player.bank = player.bank + (4*betAmount);
				bankLabel.setText("Bank: $" + player.bank);
				break;
			 case 5:
				winString = " three of a kind";
				player.bank = player.bank + (3*betAmount);
				bankLabel.setText("Bank: $" + player.bank);
				break;
			 case 6:
				winString = " two pair";
				player.bank = player.bank + (2*betAmount);
				bankLabel.setText("Bank: $" + player.bank);
				break;
			case 7:
				winString = " a pair";
				player.bank = player.bank + (1*betAmount);
				bankLabel.setText("Bank: $" + player.bank);
				break;
			default:
				winString = "";
			}
			if(win == 0) {
				textLabel.setText("You Lose");
			}
			else {
				textLabel.setText("You won. Your winning hand was" + winString);
			}
			dealButton.setEnabled(false);
			playAgain.setEnabled(true);
			for(int i = 0; i < 5; i++)
				buttonArray[i].setEnabled(false);
			}
		
	}
	
	

}
