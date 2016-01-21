/**
 * Created by Paul on 13.01.2016.
 */
public class Passenger implements Carriable {
    private final String name;

    public Passenger(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
