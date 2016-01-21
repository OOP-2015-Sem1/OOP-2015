package col;

import java.util.ArrayList;
import java.util.List;

public class Train {
	public List<Wagon> t = new ArrayList<Wagon>();
	public boolean isDeparting=false;  // currently in teh train station
	public String name;
	public int totalProfit=0;
	public int noOfWagons=0;
	public int type=5;
	public void addWagon(Wagon w)
	{
		if(!w.hasBeenAdded)
		{
		if(type==5 || type==w.type)           //can't have both types of wagons
		{
			type=w.type;
			t.add(w);
			noOfWagons++;
			totalProfit=totalProfit+w.profit;
		}
		w.hasBeenAdded=true;     //wagon has been added
		}
		
	}
	public Train( String name)
	{
		this.name=name;
	}
	
}
