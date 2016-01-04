package Data;

public class Rules {

	private static Rules instance;

	private boolean LuckyTile, DoubleInc;
	private int StartMoney, NrPlayers, Order;

	public static Rules getInstance() {
		if (instance == null) {
			instance = new Rules();
		}
		return instance;
	}

	private Rules() {
		this.StartMoney = 1000;
		this.Order = 50;
		this.LuckyTile = false;
		this.DoubleInc = true;
		this.setNrPlayers(2);
	}

	public boolean isDoubleInc() {
		return DoubleInc;
	}

	public void setDoubleInc(boolean doubleInc) {
		DoubleInc = doubleInc;
	}

	public boolean isLuckyTile() {
		return LuckyTile;
	}

	public void setLuckyTile(boolean luckyTile) {
		LuckyTile = luckyTile;
	}

	public int getOrder() {
		return Order;
	}

	public void setOrder(int order) {
		Order = order;
	}

	public int getStartMoney() {
		return StartMoney;
	}

	public void setStartMoney(int startMoney) {
		StartMoney = startMoney;
	}

	public int getNrPlayers() {
		return NrPlayers;
	}

	public void setNrPlayers(int nrPlayers) {
		NrPlayers = nrPlayers;
	}

}
