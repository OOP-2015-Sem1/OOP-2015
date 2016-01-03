package chess;
public class objPaintInstruction
{
	
	private int startRow = 0, startColumn = 0, rowCells = 0, columnCells = 0;
	
	public objPaintInstruction ()
	{
	}
	
	public objPaintInstruction (int firstRow, int firstColumn)
	{
		calculateRedrawCells (firstRow, firstColumn);
	}
	
	public objPaintInstruction (int startRow, int startColumn, int numCells)
	{
		
		this.startRow = startRow;
		this.startColumn = startColumn;
		rowCells = numCells;
		columnCells = numCells;
		
	}
	
	private void calculateRedrawCells (int firstRow, int firstColumn)
	{
		
		if (firstRow == 0)
		{
			this.startRow = firstRow;
		}
		else
		{
			this.startRow = firstRow - 1;
		}
		
		if (firstColumn == 0)
		{
			this.startColumn = firstColumn;
		}
		else
		{
			this.startColumn = firstColumn - 1;
		}
		
		if (firstRow <= 5)
		{
			rowCells = 3;
		}
		else
		{
			rowCells = 8 - startRow;
		}
		
		if (firstColumn <= 5)
		{
			columnCells = 3;
		}
		else
		{
			columnCells = 8 - startColumn;
		}
		
	}
	
	public void setMatrix (int firstRow, int firstColumn)
	{
		calculateRedrawCells(firstRow, firstColumn);
	}
	
	public void setMatrix (int startRow, int startColumn, int numCells)
	{
		
		this.startRow = startRow;
		this.startColumn = startColumn;
		rowCells = numCells;
		columnCells = numCells;
		
	}
	
	public int getStartRow ()
	{
		return startRow;
	}
	
	public int getStartColumn ()
	{
		return startColumn;
	}
	
	public int getRowCells ()
	{
		return rowCells;
	}
	
	public int getColumnCells ()
	{
		return columnCells;
	}
	
}