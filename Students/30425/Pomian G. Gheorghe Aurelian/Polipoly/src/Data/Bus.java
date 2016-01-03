package Data;

public class Bus extends Purchasable {
	private int level;
	private int pay[] = { 25, 50, 100, 200 };

	public Bus(int ID, int price, int level, int owner) {
		this.level = level;
		this.price = price;
		this.ID = ID;
		this.owner = owner;
	}

	public int payment() {
		return pay[this.level];
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
