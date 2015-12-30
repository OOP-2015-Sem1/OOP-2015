package controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import models.Ai;
import ui.ShipPlacementView;

public class PlacementController implements ActionListener
{
	private ShipPlacementView shipPlacementView;
	private Ai player;
	private String inputStringForPlacementBoard;
	private int typeOfShipToBePlaced;
	private String orientationOfShip;
	private int rowOnWhichToBePlaced;
	private int columnOnWhichToBePlaced;

	public PlacementController(Ai player) 
	{

		shipPlacementView = new ShipPlacementView(this,player.getName());
		this.player = player;
		shipPlacementView.setVisible(true);

	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton buttonSource = (JButton) e.getSource();
		if(buttonSource.getText().equals("Random Placement"))
		{
			player.placementMadeByAI();
			shipPlacementView.putShipOnBoard(player.getPlacementBoard().getBoard());
			
			if(player.areAllShipsPlaced())
			{
				//System.out.println("intra");
				player.getPlacementBoard().setIsBoardReady(true);
				JOptionPane.showMessageDialog(shipPlacementView,player.getName()+" finished placing ships");
				shipPlacementView.setVisible(false);
			}
		}
		else if(buttonSource.getText().equals("Done"))
		{
			player.getPlacementBoard().setErrorMessage("N");
			
			String location = "";
			location =""+(char)(rowOnWhichToBePlaced+65);
			location = location+(columnOnWhichToBePlaced+1);
			
			inputStringForPlacementBoard =typeOfShipToBePlaced+" "+location+" "+orientationOfShip;
			//
			System.out.println("verify:"+inputStringForPlacementBoard);
			//
			shipPlacementView.setPlacementStringLabelText("Placement String:"+inputStringForPlacementBoard);
			
			player.placementMadeByPlayer(inputStringForPlacementBoard);
			shipPlacementView.putShipOnBoard(player.getPlacementBoard().getBoard());
			
			if(!player.getPlacementBoard().getErrorMessage().equals("N"))
			{
				JOptionPane.showMessageDialog(shipPlacementView,player.getPlacementBoard().getErrorMessage());
			}
			
			if(player.areAllShipsPlaced())
			{
				//System.out.println("intra");
				player.getPlacementBoard().setIsBoardReady(true);
				JOptionPane.showMessageDialog(shipPlacementView,player.getName()+" finished placing ships");
				shipPlacementView.setVisible(false);
			}
			
		}
		else if(buttonSource.getActionCommand().equals("N"))
		{
			if(buttonSource.getText().equals("H")||buttonSource.getText().equals("V"))
			{
				orientationOfShip = buttonSource.getText();
				//
				System.out.println("orientation:"+orientationOfShip);
				//
			
			}
			else
			{
				typeOfShipToBePlaced = Integer.parseInt(buttonSource.getText());
				//
				System.out.println("type :"+typeOfShipToBePlaced);
				//
			}

		}
		else
		{
			String[] parts = buttonSource.getActionCommand().split("-");
			
			rowOnWhichToBePlaced = Integer.parseInt(parts[1]);
			columnOnWhichToBePlaced = Integer.parseInt(parts[0]);
			//
			System.out.println("Location: "+rowOnWhichToBePlaced+"-"+columnOnWhichToBePlaced);
			//

			
		}


	}


	public String getInputStringForPlacementBoard() {
		return inputStringForPlacementBoard;
	}


	public void setInputStringForPlacementBoard(String inputStringForPlacementBoard) {
		this.inputStringForPlacementBoard = inputStringForPlacementBoard;
	}
	
	public void setVisibilityOfView(boolean choice)
	{
		shipPlacementView.setVisible(choice);
	}


}
