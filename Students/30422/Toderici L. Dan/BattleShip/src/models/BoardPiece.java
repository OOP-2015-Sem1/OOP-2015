package models;

public class BoardPiece 
{
	
	private String piece;
	private boolean used;
	private int type;

	public BoardPiece()
	{
		setPiece("___|");
		setIsUsed(false);
		setType(0);
	}

	public String getPiece() 
	{
		return piece;
	}

	public void setPiece(String piece) 
	{
		this.piece = piece;
	}

	public boolean getIsUsed()
	{
		return used;
	}

	public void setIsUsed(boolean used) 
	{
		this.used = used;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}
}