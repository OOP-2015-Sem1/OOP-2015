package controllers;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import models.Ai;
import models.Board;
import models.BoardPiece;
import ui.HuntingView;

public class HuntingController implements ActionListener{
	private HuntingView huntingView;
	private Ai player1;
	private Ai player2;
	private boolean isPlayerAi;
	private BoardPiece[][] huntingBoard;
	private int rowToHit;
	private int columnToHit;
	private boolean hitWasSubmitted;
	private boolean didPlayerHit;
	private boolean isValidHit;
	private boolean isPlayerTurn;



	public HuntingController(Ai player1,Ai player2,boolean isPlayerAi)
	{

		this.player1 = player1;
		this.player2 = player2;
		this.huntingBoard = player1.getHuntingBoard().getHuntingBoardMatrix();
		this.isPlayerAi = isPlayerAi;

		huntingView = new HuntingView(player1.getName(),this,player1.getPlacementBoard().getBoard());

		if(isPlayerAi)
		{
			huntingView.changeHitButtonText("Show Ai Hit");
		}

		huntingView.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton buttonSource = (JButton) e.getSource();
		if(buttonSource.getActionCommand().equals("N"))
		{

			
			
			if(isPlayerAi)
			{

				String location = player1.generateHitByAi();
				//System.out.println(location);
				

				
				rowToHit =((int)location.charAt(0))-65;
				if(location.length()==3)
				{
					columnToHit = 9;
				}
				else
				{
					columnToHit = Integer.parseInt(""+location.charAt(1));
				}


				setHitWasSubmitted(true);
				setIsValidHit(false);
				
				columnToHit = columnToHit-1;

			}
		
			takeAHit();

		}
		else
		{
			String[] parts = buttonSource.getActionCommand().split("-");

			rowToHit= Integer.parseInt(parts[1]);
			columnToHit = Integer.parseInt(parts[0]);

		}

		

	}

	public void takeAHit() 
	{

		String location = "";
		location =""+(char)(rowToHit+65);
		location = location+(columnToHit+1);

		huntingView.setLocationLabelText("Hit location: "+location);
		String coordinates = player1.getHuntingBoard().hitCell(location);

		int type = -1;

		if(!coordinates.equals("N"))
		{
			type = player2.getPlacementBoard().markAsHitOrMiss(rowToHit, columnToHit);
			JOptionPane.showMessageDialog(huntingView,player2.getPlacementBoard().getMessageToTheEnemy());
		}
		else
		{

		}


		if(type ==0 )
		{
			setDidPlayerHit(false);

			if(player1.getDidHitSomething() == 1)
			{
				player1.setCoordinatesOfLastHitPart(player1.getCoordinatesOfInitialHit());
			}

		}
		else if(type !=-1)
		{

			setDidPlayerHit(true);

			if(player1.getDidHitSomething() == 0)
			{

				player1.setCoordinatesOfInitialHit(new Point(rowToHit, columnToHit));

				System.out.println("initial:"+
						player1.getCoordinatesOfInitialHit().getX()+"-"+
						player1.getCoordinatesOfInitialHit().getY());

				player1.setCoordinatesOfLastHitPart(new Point(rowToHit, columnToHit));

				System.out.println("last:"+
						player1.getCoordinatesOfLastHitPart().getX()+"-"+
						player1.getCoordinatesOfLastHitPart().getY());
			}
			else 
			{
				player1.setCoordinatesOfLastHitPart(new Point(rowToHit, columnToHit));
				System.out.println("last:"+
						player1.getCoordinatesOfLastHitPart().getX()+"-"+
						player1.getCoordinatesOfLastHitPart().getY());
			}

			player1.setDidHitSomething(1);

			Point locationToDestroy = new Point(rowToHit, columnToHit);
			player2.getShipOfType(type)
			.destroyAPartFromLocation(locationToDestroy);

			if(player2.isShipOfTypeDestroyed(type))
			{


				JOptionPane.showMessageDialog(huntingView,
						"Ship of type "+
								type
								+ " is destroyed");

				player1.setDidHitSomething(0);
			}


		}
		else
		{
			setIsValidHit(false);
			if(!isPlayerAi)
			{
			JOptionPane.showMessageDialog(huntingView,"Already hit that cell!");
			}
		}


		System.out.println(coordinates);

		if(!coordinates.equals("N"))
		{

			setIsValidHit(true);
		}
		System.out.println("hit is valid :"+getIsValidHit());

		huntingView.markHitOnEnemyBoard(player1.getHuntingBoard().getHuntingBoardMatrix());


		setHitWasSubmitted(false);

		if(getIsValidHit()&& !getDidPlayerHit())
		{
			setIsPlayerTurn(false);

		}

	}

	public void updateBoard()
	{
		player1.getPlacementBoard().printBoard();
		huntingView.markHitOnPlayerBoard(player1.getPlacementBoard().getBoard());
	}

	public void setVisibilityOfView(boolean choice)
	{
		huntingView.setVisible(choice);
	}

	public boolean getIsValidHit() 
	{
		return isValidHit;
	}

	public void setIsValidHit(boolean isValidHit) 
	{
		this.isValidHit = isValidHit;
	}

	public boolean getDidPlayerHit() 
	{
		return didPlayerHit;
	}

	public void setDidPlayerHit(boolean wasHit)
	{
		this.didPlayerHit = wasHit;
	}

	public boolean getIsPlayerTurn() {
		return isPlayerTurn;
	}

	public void setIsPlayerTurn(boolean isMyTurn) {
		this.isPlayerTurn = isMyTurn;
	}

	public boolean getHitWasSubmitted() {
		return hitWasSubmitted;
	}

	public void setHitWasSubmitted(boolean hitWasSubmitted) {
		this.hitWasSubmitted = hitWasSubmitted;
	}

	public void showMessageInView(String message)
	{
		JOptionPane.showMessageDialog(huntingView,message);
	}

}
