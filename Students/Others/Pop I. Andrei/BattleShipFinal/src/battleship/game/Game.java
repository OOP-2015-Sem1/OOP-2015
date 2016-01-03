package battleship.game;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import battleship.game.io.BoardFrame;

public class Game implements ActionListener{
	
	private final BoardConfiguration boardConfiguration;
	private final BoardFrame boardFrame;
	private final String ME = "me";
	private final String COMPUTER = "computer";
	private String activePlayer = ME;
	private String otherPlayer = COMPUTER;
	private Timer timer;
	private int timeInterval;
	private Point hitPoint;
	
	public Game() {
		boardConfiguration = new BoardConfiguration(this);
		boardFrame = boardConfiguration.getTheBoardFrame();
		boardFrame.setStatusInfo("Status: Placing ships!");
		timer = new Timer(1000, this);
	}
	
	public void startTheGame() {
		//myBoard.enableMyBoard();
		//myBoard.clearComputerBoard();
		boardFrame.setStatusInfo("Status: Game started");
		activePlayer = COMPUTER;
		otherPlayer = ME;
		boardFrame.setPlayerInfo(activePlayer);
		nextRound();
		
	}
	
	private void nextRound() {
		
		boardFrame.setPlayerInfo(activePlayer);
		timeInterval = 2;
		if(activePlayer.equals(COMPUTER)) {
			//System.out.println(" computer");
			boardConfiguration.activateComputer();
			hitPoint = boardConfiguration.getLastComputerHit();
			startTimer();
		}
		else {
			//System.out.println(" player");
			boardConfiguration.enableComputerBoard();
		}
		
	}
	
	private void checkEndOfGame() {
		if(boardConfiguration.activePlayerDestroyedTheShips(otherPlayer)) {
			if(activePlayer.equals(COMPUTER))
				JOptionPane.showMessageDialog(null, "Computer Won");
			else
				JOptionPane.showMessageDialog(null, "You Won");
			System.exit(0);
		}
		
	}
	
	public void startTimer(){
		timer.start();
	}
	
	public void switchPlayers() {
		if(activePlayer.equals(COMPUTER)) {
			activePlayer = ME;
			otherPlayer = COMPUTER;
		}
		else {
			activePlayer = COMPUTER;
			otherPlayer = ME;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timeInterval -=1;
		if(timeInterval == 0) {
			checkEndOfGame();
			boardConfiguration.repaintHitCellAfterFire(hitPoint);
			timer.stop();
			switchPlayers();
			//boardFrame.setNextPlayer();
			nextRound();
		}
	}
}
