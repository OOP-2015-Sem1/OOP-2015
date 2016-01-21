/**
 * Created by Paul on 13.01.2016.
 */
public class PassengerWagon extends Wagon {
    private final int TICKET_PRICE = 100;
    private final int MAX_PASSENGERS = 100;

    public PassengerWagon(){
        super("Passenger");
    }

    @Override
    public int getProfit() {
       return cargo.size()*100;
    }

    public void addPassenger(Passenger p){
        if (cargo.size()<=100){
            cargo.add(p);
        }
    }

}
