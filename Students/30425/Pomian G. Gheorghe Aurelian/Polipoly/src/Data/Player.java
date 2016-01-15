package Data;

public class Player {
	private int money, pos, owner, tries;
	private boolean jail, bankrupt;
	private String name;

	public Player(String name) {
		this.name = name;
		money = Rules.getInstance().getStartMoney();
		this.jail = false;
		this.pos = 0;
		this.setBankrupt(false);
		this.owner = 0;
		this.tries = 0;
	}


	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public boolean isJail() {
		return jail;
	}


	public void setJail(boolean jail) {
		this.jail = jail;
	}

	public int getPos() {
		return pos;
	}


	public void setPos(int pos) {
		this.pos = pos;
	}


	public boolean isBankrupt() {
		return bankrupt;
	}


	public void setBankrupt(boolean bankrupt) {
		this.bankrupt = bankrupt;
	}


	public int getOwner() {
		return owner;
	}


	public void setOwner(int owner) {
		this.owner = owner;
	}


	public int getTries() {
		return tries;
	}


	public void setTries(int tries) {
		this.tries = tries;
	}

}
