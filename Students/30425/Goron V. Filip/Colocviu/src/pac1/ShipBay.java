package pac1;

public class ShipBay {
	public Ship[] Ships = new Ship[100];
	private int shipsInBay = 0;

	public void receiveShip(String name) {
		Ships[shipsInBay] = new Ship(name, shipsInBay);
		shipsInBay++;
	}

	public void departShip(Ship leavingShip) {
		int leavingID = leavingShip.bayID;

		Ships[leavingID] = null;
		shipsInBay--;

		for (int i = leavingID; i < Ships.length; i++) {
			Ships[i].bayID--;
		}
	}

	public void sortOutput(boolean sortType) { // sortType == 1 will return profits sort
		String maxName = null;
		int maxProfit = 0;
		int max = 0;
		for (int i = 0; i < Ships.length; i++) {
			for (int j = 0; j < Ships.length; j++) {
				if (sortType) {
					if (Ships[j].computeProfits() > maxProfit) {
						maxProfit = Ships[j].computeProfits();
						max = j;
					}
				}else{
					if (Ships[j].name.compareToIgnoreCase(maxName) == 1) {
						maxName = Ships[j].name;
						max = j;
				}
			}
			System.out.println(Ships[max].name + "; " + Ships[max].getComLength() + "; " + Ships[max].computeProfits());
		}
	}
	}
}
