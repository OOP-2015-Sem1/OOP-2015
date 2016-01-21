package models;

import java.util.*;
import java.util.UUID;

public abstract class Compartment {
private String id;
public List<Carriable> iop= new ArrayList<Carriable>();
public Compartment(String id){
	id=UUID.randomUUID().toString();

}

public String getId() {
	return id;
}
public  int computeProfit(){
int profit=0;
return profit;
}

}
