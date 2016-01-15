package Data;

public class Purchasable extends Tile {
	
	protected int price;
	protected int owner;
	
	public Purchasable () {
		this.owner = 0;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getOwner() {
		return owner;
	}

	public void setOwner(int owner) {
		this.owner = owner;
	}

}
