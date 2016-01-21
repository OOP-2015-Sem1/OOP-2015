package col;

import java.util.ArrayList;
import java.util.List;

public class Wagon {


	public int profit=0;
	public int id;
	public final int TYPE_PASSANGER = 1;
	public final int TYPE_CARGO = 2;
	public int pasCounter = 0;
	public int cargNo;
	public int type=5;
	public final static int MAX_SIZE = 100;   //passanger capacity
	public boolean isCargo = true;
	public boolean hasBeenAdded=false;   //can't add a wagon to more than one train
	public List<Passanger> p1 = new ArrayList<Passanger>();
	public List<CargoItem> p2 = new ArrayList<CargoItem>();

	public void addPassangers(Passanger c) {
		if (type != TYPE_CARGO) {
			if (pasCounter < MAX_SIZE) {
				this.p1.add(c);
				pasCounter++;
				type = TYPE_PASSANGER;
				profit+=100;
			}
		}

	}

	public void addCargoItems(CargoItem c, int cnr) {
		if (type != TYPE_PASSANGER) {
			if (isCargo) {
				this.p2.add(c);
				cargNo = cnr; // also add the number of specific items in the
								// wagon
				type = TYPE_CARGO;
				profit=c.profit*cnr;  //item value times number of items
			}
			isCargo = false; // can't have more than one cargo item/ wagon
		}
	}
	public Wagon(int id)
	{
		this.id=id;
	}
	

}
