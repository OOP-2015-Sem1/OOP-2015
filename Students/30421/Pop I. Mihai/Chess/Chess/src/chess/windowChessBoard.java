package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;
import javax.swing.JDialog;

public class windowChessBoard extends objChessBoard implements MouseListener, MouseMotionListener
{
	
	private final int refreshRate = 5; //cati pixeli se muta inainte ca ecranul sa isi dea refresh
	
	private Image[][] imgPlayer = new Image[2][6];
	private String[] strPlayerName = {"", ""};
	private String strStatusMsg = "";
	private objCellMatrix cellMatrix = new objCellMatrix();
	private int currentPlayer = 1, startRow = 0, startColumn = 0, pieceBeingDragged = 0;
	private int startingX = 0, startingY = 0, currentX = 0, currentY = 0, refreshCounter = 0;
	private boolean firstTime = true, hasWon = false, isDragging = false;
	
	private objPawn pawnObject = new objPawn();
	private objRock rockObject = new objRock();
	private objKnight knightObject = new objKnight();
	private objBishop bishopObject = new objBishop();
	private objQueen queenObject = new objQueen();
	private objKing kingObject = new objKing();
	
	public windowChessBoard ()
	{
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
	}
	
	private String getPlayerMsg ()
	{
		
		if (hasWon)
		{
			return "Felicitari " + strPlayerName[currentPlayer - 1] + ",ai castigat!";
		}
		else if (firstTime)
		{
			return "" + strPlayerName[0] + " -piesele negre, " + strPlayerName[1] + " -piesele albe. Apasati butonul pentru un joc nou!";
		}
		else
		{
			return "" + strPlayerName[currentPlayer - 1] + " Este randul tau!";
		}
		
	}		 
	
	private void resetBoard ()
	{
		
		hasWon = false;
		currentPlayer = 1;
		strStatusMsg = getPlayerMsg();
		cellMatrix.resetMatrix();
		repaint();
		
	}
	
	public void setupImages (Image[] imgRed, Image[] imgBlue)
	{
		
		imgPlayer[0] = imgRed;
		imgPlayer[1] = imgBlue;
		resetBoard();
		
	}
	
	public void setNames (String strPlayer1Name, String strPlayer2Name)
	{
		
		strPlayerName[0] = strPlayer1Name;
		strPlayerName[1] = strPlayer2Name;
		strStatusMsg = getPlayerMsg();
		repaint();
		
	}
	
	protected void drawExtra (Graphics g)
	{
		
		for (int i = 0; i < vecPaintInstructions.size(); i++)
		{
			
			currentInstruction = (objPaintInstruction)vecPaintInstructions.elementAt(i);
			int paintStartRow = currentInstruction.getStartRow();
			int paintStartColumn = currentInstruction.getStartColumn();
			int rowCells = currentInstruction.getRowCells();
			int columnCells = currentInstruction.getColumnCells();
			
			for (int row = paintStartRow; row < (paintStartRow + rowCells); row++)
			{
				
				for (int column = paintStartColumn; column < (paintStartColumn + columnCells); column++)
				{
					
					int playerCell = cellMatrix.getPlayerCell(row, column);
					int pieceCell = cellMatrix.getPieceCell(row, column);
				
					if (playerCell != 0)
					{
						
						try
						{
							g.drawImage(imgPlayer[playerCell - 1][pieceCell], ((column + 1) * 50), ((row + 1) * 50), this);
						}
						catch (ArrayIndexOutOfBoundsException e)
						{
						}
						
					}
					
				}
				
			}
			
		}
		
		if (isDragging)
		{
			g.drawImage(imgPlayer[currentPlayer - 1][pieceBeingDragged], (currentX - 25), (currentY - 25), this);
		}		

		g.setColor(new Color(0,0,0));
		g.drawString(strStatusMsg, 50, 475);
		
		vecPaintInstructions.clear(); 
	}
	
	public void newGame ()
	{
		
		firstTime = false;
		resetBoard();
				
	}
	
	private void checkMove (int desRow, int desColumn)
	{
		
		boolean legalMove = false;
		
		if (cellMatrix.getPlayerCell(desRow,desColumn) == currentPlayer)
		{
			strStatusMsg = "ATENTIE! Nu se poate muta peste aceasta piesa!";
		}
		else
		{
			
			switch (pieceBeingDragged)
			{
				
				case 0: legalMove = pawnObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix(), currentPlayer);
						break;
				case 1: legalMove = rockObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
						break;
				case 2: legalMove = knightObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
						break;
				case 3: legalMove = bishopObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
						break;
				case 4: legalMove = queenObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
						break;
				case 5: legalMove = kingObject.legalMove(startRow, startColumn, desRow, desColumn, cellMatrix.getPlayerMatrix());
						break;
						
			}
			
		}				
		
		if (legalMove)
		{
				
			int newDesRow = 0;
			int newDesColumn = 0;
			
			switch (pieceBeingDragged)
			{
				
				case 0: newDesRow = pawnObject.getDesRow();
						newDesColumn = pawnObject.getDesColumn();
						break;
				case 1: newDesRow = rockObject.getDesRow();
						newDesColumn = rockObject.getDesColumn();
						break;
				case 2: newDesRow = knightObject.getDesRow();
						newDesColumn = knightObject.getDesColumn();
						break;
				case 3: newDesRow = bishopObject.getDesRow();
						newDesColumn = bishopObject.getDesColumn();
						break;
				case 4: newDesRow = queenObject.getDesRow();
						newDesColumn = queenObject.getDesColumn();
						break;
				case 5: newDesRow = kingObject.getDesRow();
						newDesColumn = kingObject.getDesColumn();
						break;
						
			}
			
			cellMatrix.setPlayerCell(newDesRow, newDesColumn, currentPlayer);
			
			if (pieceBeingDragged == 0 && (newDesRow == 0 || newDesRow == 7)) //daca pionul nu a ajuns la sfarsitul tablei
			{
				
				boolean canPass = false;
				int newPiece = 2;
				String strNewPiece = "Rock";
				String[] strPieces = {"Rock","Knight","Bishop","Queen"};
				JOptionPane digBox = new JOptionPane("Alege piesa cu care vrei sa fie inlocuit pionul", JOptionPane.QUESTION_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, strPieces, "Rock");
				JDialog dig = digBox.createDialog(null, "pionul la capatul tablei");
				
				do
				{					
					
					dig.setVisible(true);					
					
					try
					{
						
						strNewPiece = digBox.getValue().toString();
						
						for (int i = 0; i < strPieces.length; i++)
						{
						
							if (strNewPiece.equalsIgnoreCase(strPieces[i]))
							{

								canPass = true;
								newPiece = i + 1;
								
							}
							
						}
						
					}
					catch (NullPointerException e)
					{
						canPass = false;							
					}												

				}
				while (canPass == false);

				cellMatrix.setPieceCell(newDesRow, newDesColumn, newPiece);
				
			}
			else
			{
				cellMatrix.setPieceCell(newDesRow, newDesColumn, pieceBeingDragged);
			}			
							
			if (cellMatrix.checkWinner(currentPlayer))
			{
				
				hasWon = true;
				strStatusMsg = getPlayerMsg();					
				
			}
			else
			{					
			
				if (currentPlayer == 1)
				{						
					currentPlayer = 2;						
				}
				else
				{						
					currentPlayer = 1;						
				}
				
				strStatusMsg = getPlayerMsg();
				
			}			
				
		}
		else
		{
			
			switch (pieceBeingDragged)
			{
				
				case 0: strStatusMsg = pawnObject.getErrorMsg();
						break;
				case 1: strStatusMsg = rockObject.getErrorMsg();
						break;
				case 2: strStatusMsg = knightObject.getErrorMsg();
						break;
				case 3: strStatusMsg = bishopObject.getErrorMsg();
						break;
				case 4: strStatusMsg = queenObject.getErrorMsg();
						break;
				case 5: strStatusMsg = kingObject.getErrorMsg();
						break;
						
			}
				
			unsucessfullDrag(desRow, desColumn);
			
		}
			
	}
	
	private void unsucessfullDrag (int desRow, int desColumn)
	{
		
		cellMatrix.setPieceCell(startRow, startColumn, pieceBeingDragged);
		cellMatrix.setPlayerCell(startRow, startColumn, currentPlayer);
		
	}
	
	private void updatePaintInstructions (int desRow, int desColumn)
	{
		
		currentInstruction = new objPaintInstruction(startRow, startColumn, 1);
		vecPaintInstructions.addElement(currentInstruction);
			
		currentInstruction = new objPaintInstruction(desRow, desColumn);
		vecPaintInstructions.addElement(currentInstruction);
		
	}
	
	public void mouseClicked (MouseEvent e)
	{
	}
	
	public void mouseEntered (MouseEvent e)
	{
	}
	
	public void mouseExited (MouseEvent e)
	{
		mouseReleased(e);
	}
	
	public void mousePressed (MouseEvent e)
	{
		
		if (!hasWon && !firstTime)
		{			
		
			int x = e.getX();
			int y = e.getY();
			
			if ((x > 60 && x < 430) && (y > 60 && y < 430)) //limitele corecte
			{
			
				startRow = findWhichTileSelected(y);
				startColumn = findWhichTileSelected(x);
						
				if (cellMatrix.getPlayerCell(startRow, startColumn) == currentPlayer)
				{
					
					pieceBeingDragged = cellMatrix.getPieceCell(startRow, startColumn);
					cellMatrix.setPieceCell(startRow, startColumn, 6);
					cellMatrix.setPlayerCell(startRow, startColumn, 0);
					isDragging = true;
					
				}
				else
				{
					isDragging = false;
				}
			
			}
			
		}
		
	}
	
	public void mouseReleased (MouseEvent e)
	{
		
		if (isDragging)
		{
			
			isDragging = false;
			
			int desRow = findWhichTileSelected(currentY);
			int desColumn = findWhichTileSelected(currentX);
			checkMove(desRow, desColumn);			
			repaint();
			
		}
		
	}
	
	public void mouseDragged (MouseEvent e)
	{
		
		if (isDragging)
		{
			
			int x = e.getX();
			int y = e.getY();
			
			if ((x > 60 && x < 430) && (y > 60 && y < 430)) //daca e in limitele corecte
			{
			
				if (refreshCounter >= refreshRate)
				{
								
					currentX = x;
					currentY = y;
					refreshCounter = 0;
					int desRow = findWhichTileSelected(currentY);
					int desColumn = findWhichTileSelected(currentX);
					
					updatePaintInstructions(desRow, desColumn);
					repaint();
					
				}
				else
				{
					refreshCounter++;
				}
			
			}
			
		}
		
	}
	
	public void mouseMoved (MouseEvent e)
	{
	}
	
	public void gotFocus ()
	{
		repaint();		
	}
				
}