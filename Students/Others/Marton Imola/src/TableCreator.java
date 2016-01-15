
public class TableCreator {
	public static Table CreateFromString(int rowCount, int columnCount, String source)
	{
		Table table= new Table(rowCount, columnCount);
		int row=0; 
		int column=0;
		for(int i=0; i<source.length(); i++)
		{
			char character=source.charAt(i); //thank you stack overflow
			switch(character)
			{
			case '_':
				table.putSquare(column, row, SquareCreator.createEmpty());
				column++;
				break;
			case '#':
				table.putSquare(column, row, SquareCreator.createBrick());
				column++;
				break;
			case '*':
				table.putSquare(column,  row,  SquareCreator.createGoal());
				column++;
				break;
				
			}
			if(column>=columnCount)
			{
				column=0;
				row++;
			}
		}
			return table;
		
	}
}



/*  
    empty-> _
 	brick-> #
 	goal-> *
*/