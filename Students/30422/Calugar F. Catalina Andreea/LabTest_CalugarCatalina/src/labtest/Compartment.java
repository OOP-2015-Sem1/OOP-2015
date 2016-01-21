package labtest;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Compartment implements Carriable {

	private String ID;
	private Carriable c;
	private List<Carriable> carriable;

	public Compartment() {
		ID = UUID.randomUUID().toString();
		setCarriable(new ArrayList<Carriable>());
	}

	public List<Carriable> getCarriable() {
		return carriable;
	}

	public void setCarriable(List<Carriable> carriable) {
		this.carriable = carriable;
	}

	

}
