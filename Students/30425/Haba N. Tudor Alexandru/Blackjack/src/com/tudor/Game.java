package com.tudor;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Deck deck;
    public Player player = new Player("player");
    public Player dealer = new Player("dealer");

    private JButton jbuttonhit = new JButton("Hit");
    private JButton jbuttonStay = new JButton("Stay");
    private JButton jbuttonDeal = new JButton("Deal");

    private JLabel jlblStatus = new JLabel(" ", JLabel.CENTER);
    private JLabel jScoreStatus = new JLabel(" ");

    JPanel playerPanel = new JPanel();
    JPanel dealerPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    JPanel statusPanel = new JPanel();
    
    JPanel scorePanel = new JPanel();
    
    

    Game() {
        JFrame gameFrame = new JFrame("Project - BlackJack");
        //gameFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("cards/10.png"));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        buttonsPanel.add(jbuttonhit);
        buttonsPanel.add(jbuttonStay);
        buttonsPanel.add(jbuttonDeal);
        statusPanel.add(jlblStatus);
        
        scorePanel.add(jScoreStatus); 
        
        

        jbuttonhit.addActionListener(this);
        jbuttonStay.addActionListener(this);
        jbuttonDeal.addActionListener(this);

        jScoreStatus.setBackground(Color.RED);
        jbuttonhit.setEnabled(false);
        jbuttonStay.setEnabled(false);

        dealerPanel.setBackground(Color.GREEN);
        playerPanel.setBackground(Color.GREEN);
        buttonsPanel.setBackground(Color.GREEN);
        statusPanel.setBackground(Color.GREEN);
        scorePanel.setBackground(Color.GREEN);
        gameFrame.setLayout(new BorderLayout());
        gameFrame.add(dealerPanel, BorderLayout.NORTH);
        gameFrame.add(playerPanel, BorderLayout.CENTER);
        gameFrame.add(buttonsPanel, BorderLayout.SOUTH);
        gameFrame.add(statusPanel, BorderLayout.WEST);
        gameFrame.add(scorePanel, BorderLayout.EAST);
        gameFrame.repaint();
        gameFrame.setSize(450, 350);
        gameFrame.setVisible(true);
    }

    private void hitPlayer() {
        Card newCard = player.dealTo(deck.dealFrom());
        playerPanel.add(new JLabel(new ImageIcon("cards/" + newCard.toString())));
        scorePanel.add(new JLabel(""+player.value()));
        playerPanel.updateUI();
    }

    private void hitDealerDown() {
        @SuppressWarnings("unused")
		Card newCard = dealer.dealTo(deck.dealFrom());
        dealerPanel.add(new JLabel(new ImageIcon("cards/b2fv.png")));
        dealerPanel.updateUI();
    }

    private void hitDealer() {
        Card newCard = dealer.dealTo(deck.dealFrom());
        dealerPanel.add(new JLabel(new ImageIcon("cards/" + newCard.toString())));
        dealerPanel.updateUI();
    }

    private void deal() {
        playerPanel.removeAll();
        dealerPanel.removeAll();
        scorePanel.removeAll();
        playerPanel.updateUI();
        dealerPanel.updateUI();
        player.reset();
        dealer.reset();
        if (deck == null || deck.size() < 15) {
            deck = new Deck();
            deck.shuffle();
            jlblStatus.setText("Shuffling");
        }
        hitPlayer();
        hitDealerDown();
        hitPlayer();
        hitDealer();
    }

    private void checkWinner() {
        dealerPanel.removeAll();
        for (int i = 0; i < dealer.inHand(); i++) {
            dealerPanel.add(new JLabel(new ImageIcon("cards/" + dealer.cards[i].toString())));
        }
         if (player.value() > 21){
        	jlblStatus.setText("Player busts");
        } else if (dealer.value() > 21) {
            jlblStatus.setText("Dealer Busts");
        } else if (dealer.value() == player.value()) {
            jlblStatus.setText("Push");
        } else if (dealer.value() < player.value()) {
            jlblStatus.setText("Player Wins");
        } else {
            jlblStatus.setText("Dealer Wins");
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbuttonhit) {
            hitPlayer();
            if (player.value() > 21) {
                checkWinner();
                jbuttonhit.setEnabled(false);
                jbuttonStay.setEnabled(false);
                jbuttonDeal.setEnabled(true);
            }
        }

        if (e.getSource() == jbuttonStay) {
            while (dealer.value() < 17 || player.value() > dealer.value()) {
                hitDealer();
            }
            checkWinner();
            jbuttonhit.setEnabled(false);
            jbuttonStay.setEnabled(false);
            jbuttonDeal.setEnabled(true);
        }

        if (e.getSource() == jbuttonDeal) {
            deal();
            jlblStatus.setText(" ");
            jbuttonhit.setEnabled(true);
            jbuttonStay.setEnabled(true);
            jbuttonDeal.setEnabled(false);
        }
    
    }
    

   

    public static void main(String[] args) {
        new Game();
    }
}
