package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import models.Ai;
import ui.SetPlayerNameView;


public class GameController implements ActionListener
{
	private GameModeChoiceController gameModeChoice;
	private PlacementController placementController;
	private HuntingController huntingControllerForPlayer1;
	private HuntingController huntingControllerForPlayer2;
	private SetPlayerNameView setPlayerNameView;


	private Ai player1;
	private Ai player2;
	private String playerName = "N";



	public GameController() 
	{
		gameModeChoice = new GameModeChoiceController();
		chooseGameMode();
		playerSettup();
		placeShips();
		initHuntingBoards();
		startHunt();


	}



	private void playerSettup() 
	{
		
		switch (gameModeChoice.getGameMode()) 
		{
		case 1:
			setPlayerNameView = new SetPlayerNameView(this);
			while(playerName.equals("N"))
			{
				setPlayerNameView.setVisible(true);
			}
			
			setPlayerNameView.setVisible(false);
			
			player1 = new Ai(playerName);
			
			playerName = "N";

			setPlayerNameView = new SetPlayerNameView(this);
			
			while(playerName.equals("N"))
			{
				setPlayerNameView.setVisible(true);
			}
			setPlayerNameView.setVisible(false);
			
			player2 = new Ai(playerName);

			playerName = "N";


			break;
		case 2:
			setPlayerNameView = new SetPlayerNameView(this);
			while(playerName.equals("N"))
			{
				setPlayerNameView.setVisible(true);
			}
			setPlayerNameView.setVisible(false);
			
			player1 = new Ai(playerName);
			
			player1.setName(playerName);

			break;
		case 3:

			break;

		}



	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonSource = (JButton) e.getSource();
		if(buttonSource.getText().equals("Submit"))
		{
			if(!setPlayerNameView.getPlayerName().equals(""))
			{
				playerName = setPlayerNameView.getPlayerName();
				System.out.println(playerName);
			}
			else
			{
				JOptionPane.showMessageDialog(setPlayerNameView,"Null input! Try again");
			}
		}
	}



	private void initHuntingBoards() 
	{
		switch (gameModeChoice.getGameMode()) {
		case 1:

			player1.initHuntingBoard(player2.getPlacementBoard().getBoard());
			player2.initHuntingBoard(player1.getPlacementBoard().getBoard());


			huntingControllerForPlayer1 = new HuntingController(player1,player2,false);
			huntingControllerForPlayer1.setVisibilityOfView(false);


			huntingControllerForPlayer2 = new HuntingController(player2,player1,false);
			huntingControllerForPlayer2.setVisibilityOfView(false);


			break;
		case 2:
			player1.initHuntingBoard(player2.getPlacementBoard().getBoard());
			player2.initHuntingBoard(player1.getPlacementBoard().getBoard());

			huntingControllerForPlayer1 = new HuntingController(player1,player2,false);
			huntingControllerForPlayer1.setVisibilityOfView(false);

			huntingControllerForPlayer2 = new HuntingController(player2,player1,true);
			huntingControllerForPlayer2.setVisibilityOfView(false);

			break;
		case 3:
			player1.initHuntingBoard(player2.getPlacementBoard().getBoard());
			player2.initHuntingBoard(player1.getPlacementBoard().getBoard());

			huntingControllerForPlayer1 = new HuntingController(player1,player2,true);
			huntingControllerForPlayer1.setVisibilityOfView(false);

			huntingControllerForPlayer2 = new HuntingController(player2,player1,true);
			huntingControllerForPlayer2.setVisibilityOfView(false);

			break;

		default:
			break;
		}

	}


	public void chooseGameMode()
	{

		while(gameModeChoice.getGameMode()==-1)
		{
			gameModeChoice.setVisibilityOfView(true);
		}
		gameModeChoice.setVisibilityOfView(false);
	}

	public void placeShips()
	{
		switch (gameModeChoice.getGameMode()) {
		case 1:
			//player1 = new Ai("Marcel");
			placementController = new PlacementController(player1);

			while(!player1.getPlacementBoard().getIsBoardReady())
			{

				placementController.setVisibilityOfView(true);
			}

			//player2 = new Ai("Maria");
			placementController = new PlacementController(player2);
			while(!player2.getPlacementBoard().getIsBoardReady())
			{
				placementController.setVisibilityOfView(true);
			}

			break;
		case 2:

			player2 = new Ai("Ai1");
			player2.placementMadeByAI();
			while(!player2.getPlacementBoard().getIsBoardReady())
			{
				System.out.print("");
			}


			//player1 = new Ai("Marcel");
			placementController = new PlacementController(player1);

			while(!player1.getPlacementBoard().getIsBoardReady())
			{
				placementController.setVisibilityOfView(true);
			}

			break;
		case 3:
			player1 = new Ai("Ai1");
			player1.placementMadeByAI();
			player2 = new Ai("Ai2");
			player2.placementMadeByAI();
			while(!player1.getPlacementBoard().getIsBoardReady() && !player2.getPlacementBoard().getIsBoardReady())
			{
				System.out.println("");
			}
			break;

		default:
			break;
		}
	}

	private void startHunt() 
	{
		int turn = 1;

		while(!checkForWinner(turn))
		{


			if(turn%2 == 1)
			{
				huntingControllerForPlayer1.updateBoard();

				huntingControllerForPlayer1.setIsPlayerTurn(true);

				huntingControllerForPlayer2.setVisibilityOfView(false);
				huntingControllerForPlayer1.setVisibilityOfView(true);

				while(huntingControllerForPlayer1.getIsPlayerTurn())
				{
					if(huntingControllerForPlayer1.getHitWasSubmitted())
					{
						huntingControllerForPlayer1.takeAHit();
					}
					if(checkForWinner(turn))
					{
						break;
					}
				}
			}
			else
			{
				huntingControllerForPlayer2.updateBoard();

				huntingControllerForPlayer2.setIsPlayerTurn(true);

				huntingControllerForPlayer1.setVisibilityOfView(false);
				huntingControllerForPlayer2.setVisibilityOfView(true);

				while(huntingControllerForPlayer2.getIsPlayerTurn())
				{	
					if(huntingControllerForPlayer2.getHitWasSubmitted())
					{
						huntingControllerForPlayer2.takeAHit();
					}
					if(checkForWinner(turn))
					{
						break;
					}
				}
			}
			turn ++;
		}



	}

	private boolean checkForWinner(int turn) 
	{
		if(turn %2 ==0)
		{

			if(player2.areAllShipsDestroyed())
			{


				System.out.println("Player"+player1.getName()+" wins!");

				huntingControllerForPlayer1.showMessageInView(player1.getName()+ "wins!");

				huntingControllerForPlayer1.setVisibilityOfView(false);
				huntingControllerForPlayer2.setVisibilityOfView(false);

				return true;
			}

		}
		else
		{
			if( player1.areAllShipsDestroyed())
			{	


				System.out.println("Player"+player2.getName()+" wins!");

				huntingControllerForPlayer1.showMessageInView(player2.getName()+" wins!");
				huntingControllerForPlayer1.setVisibilityOfView(false);
				huntingControllerForPlayer2.setVisibilityOfView(false);
				return true;
			}
		}
		return false;

	}





}
