package Data;

public class Property extends Purchasable {
	private int level, tier;
	private int pay[] = { 0, 0, 0, 0, 0, 0, 0 };

	public Property(int ID, int price, int level, int tier, int owner, int l0, int l1, int l2, int l3, int l4, int l5,
			int l6) {
		this.tier = tier;
		this.level = level;
		this.price = price;
		this.ID = ID;
		this.owner = owner;
		pay[0] = l0;
		pay[1] = l1;
		pay[2] = l2;
		pay[3] = l3;
		pay[4] = l4;
		pay[5] = l5;
		pay[6] = l6;
	}

	public int payment() {
		return pay[this.level];
	}

	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
