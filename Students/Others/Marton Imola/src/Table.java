
public class Table {
	
	private int numberOfRows;
	private int numberOfColumns;
	private Square[][] table;
	public Table(int numberOfRows, int numberOfColumns) {
		super();
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		table=new Square[numberOfRows][numberOfColumns];
		
	}
	
	public int getNumberOfRows() {
		return numberOfRows;
	}

	public int getNumberOfColumns() {
		return numberOfColumns;
	}

	public void putSquare(int column, int row, Square createdSquare) {
		// TODO Auto-generated method stub YAAAAAAAAY
		table[row][column]=createdSquare;
		
	}
	public Square getSquare(int column, int row){
		return table[row][column];
	}
	
	public String getRowRule(int row){
		int goalCounter=0;
		String rule="";
		for(int column=0; column<numberOfColumns; column++)
		{
			Square currentSquare=getSquare(column, row);
			if(currentSquare.isGoal())
			{
				goalCounter++;
			}
			
			else 
			{
				if(goalCounter>0)
					rule=rule+' '+goalCounter;
				goalCounter=0;
			}
			
		}
		if(goalCounter>0)
			rule=rule+' '+goalCounter;
		return rule;
	}
	
	public String getColumnRule(int column){
		int goalCounter=0;
		String rule="";
		for(int row=0; row<numberOfRows; row++)
		{
			Square currentSquare=getSquare(column, row);
			if(currentSquare.isGoal())
			{
				goalCounter++;
			}
			
			else 
			{
				if(goalCounter>0)
					rule=rule+' '+goalCounter;
				goalCounter=0;
			}
			
		}
		if(goalCounter>0)
			rule=rule+' '+goalCounter;
		return rule;
	}

	
}
