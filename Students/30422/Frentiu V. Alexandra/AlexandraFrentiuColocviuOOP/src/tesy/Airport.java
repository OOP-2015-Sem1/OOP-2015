package tesy;

import java.util.*;

public class Airport {
	Plane p;
	public boolean departing,arriving,inStation;
	public Airport()
	{
		int i;
	int n = 9;//for now
	HashSet l = new HashSet();
		for(i=1;i<=n;i++)//n unique planes
			{
		p = new Plane();
			
		l.add(p);
	}
	}
	public boolean departingPlanes()
	{
		return departing;
	}
	public boolean arrivingPlanes()
	{
		return arriving;
	}
	public boolean planeIsInStation()
	{
		return inStation;
	}
	public int  getNrPlane(int n)
	{
		return n;
	}
}