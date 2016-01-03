package models;

import java.awt.Point;
import java.util.ArrayList;

public class Ship 
{
	private int size;
	private boolean isPlaced;
	private char orientation;
	private ArrayList<ShipPart> parts;


	public Ship(int sizeOfShip)
	{
		setSize(sizeOfShip);
		setIsPlaced(false);
		setOrientation(' ');
		this.parts = new ArrayList<ShipPart>();
		initParts();
	}

	private void initParts() {
		for(int i = 0; i < getSize() ;i++)
		{
			ShipPart part = new ShipPart();
			this.parts.add(i, part);
		}

	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean getIsPlaced() {
		return isPlaced;
	}

	public void setIsPlaced(boolean isPlaced) {
		this.isPlaced = isPlaced;
	}

	public boolean getIsDestroyed() {
		for(ShipPart part : parts)
		{
			if(!part.getIsDestroyed())
			{
				return false;
			}

		}
		return true;
	}
	
	public char getOrientation() {
		
		return orientation;
	}

	public void setOrientation(char orientation) {
		this.orientation = orientation;
	}

	public void setLocationOfASpecifiedShipPart(int index,Point location)
	{
		ShipPart part = this.parts.get(index);
		part.setLocation(location);
	}
	
	
	public ShipPart getPartFromASpecifiedLocation(Point location)
	{
		for(int i = 0; i < size; i++)
		{
			if(parts.get(i).getLocation().equals(location))
			{
				return parts.get(i);
			}
		}

		System.out.println("Ship doesn't contain a part at that location");
		return null;

	}
	
	public ShipPart getPartAtIndex(int index)
	{
		return parts.get(index);
	}
	
	public void destroyAPartFromLocation(Point location)
	{
		ShipPart part = this.getPartFromASpecifiedLocation(location);
		part.setIsDestroyed(true);
	}
	

	public boolean getIsShipDestroyed ()
	{
		for(int i = 0; i < size; i++)
		{
			if(!parts.get(i).getIsDestroyed())
			{
				return false;
			}
		}
		return true;
	}
	
	public void printShip()
	{
		System.out.println("Ship of size :"+getSize());
		System.out.println("Made out of parts:");
		for(int i = 0; i < size; i++)
		{
			ShipPart part = new ShipPart();
				part = parts.get(i);
			System.out.println("Index"+i+":"+part.getLocation().getX()+" - "+part.getLocation().getY()+" is destroyed:"+part.getIsDestroyed());
		}
	}



}