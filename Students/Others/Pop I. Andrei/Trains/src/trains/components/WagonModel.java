package trains.components;

import java.util.ArrayList;
import java.util.UUID;

public abstract class WagonModel implements WagonInterface{
	
	public final String ID;
	
	public WagonModel() {
		ID=UUID.randomUUID().toString();
	}
	
	
}
