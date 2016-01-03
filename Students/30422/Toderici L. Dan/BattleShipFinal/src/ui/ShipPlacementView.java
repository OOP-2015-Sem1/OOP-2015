package ui;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


import models.BoardPiece;


public class ShipPlacementView extends JFrame  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 13L;

	private JLabel shipLabel;
	private ArrayList<JButton> shipTypes;
	private JLabel orientationLabel;
	private JButton verticalOrientationButton;
	private JButton horizontalOrientationButton;
	private JLabel placementBoardLabel;
	private JButton[][] placementBoard;
	private JLabel placementStringLabel;
	private JButton doneButton;
	private JButton randomButton;
	private ActionListener actionListener;
	private MouseListener mouseListener;


	public ShipPlacementView(MouseListener mouseListener,ActionListener actionListener,String player)
	{
		super(player+"'s Placement");
		setLocationRelativeTo(null);
		this.actionListener = actionListener;
		this.mouseListener = mouseListener;

		setBackground(Color.lightGray);
		setLayout(null);
		setSize (800,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		shipLabel = new JLabel("Ship Types");
		shipLabel.setBounds(20, 30, 100, 30);
		shipLabel.setForeground(Color.BLUE);

		add(shipLabel);

		shipTypes = new ArrayList<JButton>();

		initShipTypesLabels();

		orientationLabel = new JLabel("Orientation");
		orientationLabel.setBounds(20,220,70,30);
		orientationLabel.setForeground(Color.BLUE);
		add(orientationLabel);

		verticalOrientationButton = new JButton("V");
		verticalOrientationButton.setBounds(20,255,50,30);
		verticalOrientationButton.addActionListener(actionListener);
		//
		verticalOrientationButton.setActionCommand("N");
		//
		add(verticalOrientationButton);

		horizontalOrientationButton = new JButton("H");
		horizontalOrientationButton.setBounds(80,255,50,30);
		horizontalOrientationButton.addActionListener(actionListener);
		//
		horizontalOrientationButton.setActionCommand("N");
		//
		add(horizontalOrientationButton);

		randomButton = new JButton("Random Placement");
		randomButton.setBounds(20, 350,150,30);
		randomButton.addActionListener(actionListener);
		randomButton.setActionCommand("N");
		add(randomButton);



		placementBoardLabel = new JLabel(player+"'s Placement Board");
		placementBoardLabel.setForeground(Color.blue);
		placementBoardLabel.setBounds(350, 20,150, 30);
		add(placementBoardLabel);

		placementBoard = new JButton[10][10];
		initBoard();

		placementStringLabel = new JLabel("Placement String: ") ;
		placementStringLabel.setForeground(Color.blue);
		placementStringLabel.setBounds(350,350,200,30);
		add(placementStringLabel);

		doneButton = new JButton("Done");
		doneButton.setBounds(350, 380, 80, 30);
		doneButton.setActionCommand("N");
		doneButton.addActionListener(actionListener);
		add(doneButton);



	}

	private void initBoard() {
		for(int i = 0; i<=9; i++)
		{
			for(int j=0; j<=9; j++)
			{
				placementBoard[i][j] = new JButton();
				placementBoard[i][j].setBackground(Color.LIGHT_GRAY);
				placementBoard[i][j].setBounds(350+((i)*30),50+(j*30), 30, 30);

				placementBoard[i][j].setActionCommand(i+"-"+j);


				placementBoard[i][j].addActionListener(actionListener);
				placementBoard[i][j].addMouseListener(mouseListener);
				

				this.add(placementBoard[i][j]);
			}
		}

	}

	private void initShipTypesLabels() {
		for(int i = 1; i<=5;i++)
		{
			JButton shipButton = new JButton(String.valueOf(i));

			shipButton.setBounds(20, 30*(i+1)+5, 42*i, 20);
			shipButton.setBackground(Color.cyan);

			shipButton.addActionListener(actionListener);

			shipTypes.add(shipButton);
			//
			shipButton.setActionCommand("N");
			//
			this.add(shipTypes.get(i-1));


		}

	}


	public void setPlacementStringLabelText(String text)
	{
		placementStringLabel.setText(text);
	}


	public void putShipOnBoard(BoardPiece[][] board)
	{
		for(int i=0;i<=9;i++)
		{
			for(int j=0;j<=9;j++)
			{
				if(board[i][j].getPiece().equals("_@_|"))
				{
					placementBoard[j][i].setBackground(Color.orange);
				}
			}
		}
	}


	public void putPossibleShipOnBoard(BoardPiece[][] board)
	{
		for(int i=0;i<=9;i++)
		{
			for(int j=0;j<=9;j++)
			{

				if(board[i][j].getPiece().equals("_@_|"))
				{
					if(!placementBoard[j][i].getBackground().equals(Color.orange))
					{
						placementBoard[j][i].setBackground(Color.GREEN);
					}
				}
			}
		}
	}
	
	public void deleteGreenBackground()
	{
		for(int i=0;i<=9;i++)
		{
			for(int j=0;j<=9;j++)
			{
				if(placementBoard[j][i].getBackground().equals(Color.GREEN))
				{
					placementBoard[j][i].setBackground(Color.LIGHT_GRAY);
				}

			}
		}
	}

}
