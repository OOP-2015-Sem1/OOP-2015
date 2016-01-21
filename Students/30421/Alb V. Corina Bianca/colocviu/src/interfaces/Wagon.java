package interfaces;

import java.util.UUID;

public interface Wagon {

	// profit, uuid, type caariable obj, profit
	public int getWagonProfit();

	public int getNrOfCariable();

	public void setNrOfCariable(int nrOfCariable);

	public String getID();

	public void setWagonProfit();
}