import java.awt.Color;

public class Passenger implements Carriable{
private String name = new String();
public void setName(String brand){
	 this.name=name;
	}

	 public String getName(){
	 return this.name;
	 }
	 
	 public Passenger( String name){
			setName(name);
		}
@Override
public void type(String name, int profit) {
	Carriable passenger1= new Passenger("Vasile");
	
}


}
