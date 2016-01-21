import java.util.ArrayList;

public class Ship {
	private String name;
	private boolean docked;
	private final boolean type;
	private int comp;
	static ArrayList<Compartment> cargo = new ArrayList<Compartment>();

	public Ship(String name, boolean docked, boolean type, int comp) {
		this.name = name;
		this.docked = docked;
		this.type = type;
		this.comp = comp;
		setComp();
	}

	private void setComp() {
		int i;
		for (i = 1; i <= comp; i++) {
			Compartment auxComp = new Compartment(type);
			cargo.add(auxComp);
		}
	}
	
	public int totalProfit() {
		int total = 0;
		for(Compartment cmp:cargo)
			total = total + cmp.getProfit();
		return total;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDocked() {
		return docked;
	}

	public void setDocked(boolean docked) {
		this.docked = docked;
	}

	public int getComp() {
		return comp;
	}

	public void setComp(int comp) {
		this.comp = comp;
	}

}
