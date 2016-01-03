package Data;

public class Utility extends Purchasable {
	private int level;

	public Utility(int ID, int price, int level, int owner) {
		this.level = level;
		this.price = price;
		this.ID = ID;
		this.owner = owner;
	}

	public int payment(int dice) {
		if (this.level == 0)
			return dice * 4;
		else
			return dice * 10;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
