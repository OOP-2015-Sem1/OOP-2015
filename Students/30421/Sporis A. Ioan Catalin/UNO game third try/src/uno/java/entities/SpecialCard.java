package uno.java.entities;

public class SpecialCard extends Card {
	private int draw;
	private boolean wild;
	private boolean skip;
	private boolean reverse;

	public SpecialCard(String cardName) {
		super.setValue(0);
		super.setSpecial(true);
		super.setCardName(cardName);

	}

	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public boolean isWild() {
		return wild;
	}

	public void setWild(boolean wild) {
		this.wild = wild;
	}

	public boolean isSkip() {
		return skip;
	}

	public void setSkip(boolean skip) {
		this.skip = skip;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

}
