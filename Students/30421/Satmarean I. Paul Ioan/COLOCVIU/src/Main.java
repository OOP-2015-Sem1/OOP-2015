import java.util.*;

/**
 * Created by Paul on 13.01.2016.
 */
public class Main {



    public static int randomNumber(){
        Random rand = new Random();
        int number = rand.nextInt();
        if (number<0) number = 0-number;
        number = number % 10000;
        return number;
    }

    public static Passenger randomPassenger(){
        UUID uid = UUID.randomUUID();
        Passenger p = new Passenger(uid.toString());
        return p;
    }

    public static CargoItem randomCargoItem(){
        UUID uid = UUID.randomUUID();
        CargoItem item = new CargoItem(uid.toString(),randomNumber());
        return item;
    }

    public static CargoWagon randomCargoWagon(){
        CargoWagon w = new CargoWagon(randomCargoItem());
        int items = randomNumber()%20;
        for(int i=0;i<items;i++){
            w.addCargo(randomCargoItem());
        }
        return w;
    }

    public static PassengerWagon randomPassengerWagon(){
        PassengerWagon pw = new PassengerWagon();
        int items = randomNumber()%20;
        for(int i=0;i<items;i++){
            pw.addPassenger(randomPassenger());
        }
        return pw;
    }

    public static Train randomPassengerTrain(){
        Train pt = new Train("Passenger");
        int items = randomNumber()%20;
        for(int i=0;i<items;i++){
            pt.addWagon(randomPassengerWagon());
        }
        return pt;
    }

    public static Train randomCargoTrain(){
        Train ct = new Train("Passenger");
        int items = randomNumber()%20;
        for(int i=0;i<items;i++){
            ct.addWagon(randomCargoWagon());
        }
        return ct;
    }

    public static void main(String[] args) {
        TrainStation station = new TrainStation();

        int items = randomNumber()%20;
        for(int i=0;i<items;i++){
            if(i%2==0) {
                station.receiveTrain(randomCargoTrain());
            }else {
                station.receiveTrain(randomPassengerTrain());
            }
        }
        ArrayList<Train> trains = new ArrayList<Train>();
        trains.addAll(station.getTrains());
        //HashSet<Train> trains = station.getTrains();

        for(Train t:trains){
            t.printSummary();
        }


    }
}
