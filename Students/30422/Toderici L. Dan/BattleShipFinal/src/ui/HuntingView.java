package ui;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import models.BoardPiece;

public class HuntingView extends JFrame 
{
	private JLabel playerBoardLabel;
	private JLabel enemyBoardLabel;
	private JLabel locationOfHitLabel;
	private JButton[][] playerBoard;
	private JButton[][] enemyBoard;
	private JButton hitButton;
	private ActionListener actionListener;

	public HuntingView(String player,ActionListener actionListener,BoardPiece[][] board)
	{
		this.actionListener = actionListener;
		setLocationRelativeTo(null);

		setBackground(Color.lightGray);
		setLayout(null);
		setSize (800,500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		playerBoardLabel = new JLabel(player+"'s Board");
		playerBoardLabel.setBounds(20,20,100,30);
		playerBoardLabel.setForeground(Color.blue);
		add(playerBoardLabel);

		playerBoard = new JButton[10][10];
		initPlayerBoard(board);

		enemyBoardLabel = new JLabel("Enemy Board");
		enemyBoardLabel.setForeground(Color.blue);
		enemyBoardLabel.setBounds(350, 20,150, 30);
		add(enemyBoardLabel);


		enemyBoard = new JButton[10][10];
		initEnemyBoard();

		locationOfHitLabel = new JLabel("Hit location: ");
		locationOfHitLabel.setForeground(Color.blue);
		locationOfHitLabel.setBounds(350,350,200,30);
		add(locationOfHitLabel);

		hitButton = new JButton("Hit");
		hitButton.setBounds(350, 380, 80, 30);
		hitButton.setActionCommand("N");
		hitButton.addActionListener(actionListener);
		add(hitButton);


	}

	public void initPlayerBoard(BoardPiece[][] board)
	{
		for(int i = 0; i<=9; i++)
		{
			for(int j=0; j<=9; j++)
			{
				playerBoard[i][j] = new JButton();
				if(board[j][i].getPiece().equals("_@_|"))
				{
					playerBoard[i][j].setBackground(Color.orange);
				}
				else
				{
					playerBoard[i][j].setBackground(Color.LIGHT_GRAY);
				}

				playerBoard[i][j].setBounds(20+((i)*30),50+(j*30), 30, 30);

				this.add(playerBoard[i][j]);
			}
		}

	}

	private void initEnemyBoard() {
		for(int i = 0; i<=9; i++)
		{
			for(int j=0; j<=9; j++)
			{
				enemyBoard[i][j] = new JButton();
				enemyBoard[i][j].setBackground(Color.LIGHT_GRAY);
				enemyBoard[i][j].setBounds(350+((i)*30),50+(j*30), 30, 30);
				enemyBoard[i][j].setActionCommand(i+"-"+j);
				enemyBoard[i][j].addActionListener(actionListener);

				this.add(enemyBoard[i][j]);
			}
		}

	}

	public void markHitOnEnemyBoard(BoardPiece[][] board)
	{
		for(int i = 0; i<=9; i++)
		{
			for(int j=0; j<=9; j++)
			{
				if(board[j][i].getPiece().equals("_X_|"))
				{
					enemyBoard[i][j].setBackground(Color.red);
				}
				if(board[j][i].getPiece().equals("_O_|"))
				{
					enemyBoard[i][j].setBackground(Color.black);
				}

			}
		}
	}

	public void markHitOnPlayerBoard(BoardPiece[][] board)
	{
		for(int i = 0; i<=9; i++)
		{
			for(int j=0; j<=9; j++)
			{
				if(board[i][j].getPiece().equals("_X_|"))
				{
					//System.out.println("C:"+i+"-"+j);
					playerBoard[j][i].setBackground(Color.red);
				}
				if(board[i][j].getPiece().equals("_O_|"))
				{
					playerBoard[j][i].setBackground(Color.black);
				}

			}
		}
	}


	public void changeHitButtonText(String text)
	{
		hitButton.setText(text);
		hitButton.setBounds(350, 380, 150, 30);
	}
	
	public void setLocationLabelText(String text)
	{
		locationOfHitLabel.setText(text);
	}
	

}
