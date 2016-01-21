import java.util.HashSet;
import java.util.Set;

public class ShipBay {

    Set<Ship> ships = new HashSet<>();

    public void receiveShip(Ship ship) {
        ships.add(ship);
    }

    public void departShip(Ship ship) {
        ships.remove(ship);
    }

    public boolean checkShipsExistence(Ship ship) {
        return ships.contains(ship);
    }

    public void checkShipsByName() {
        Set<Ship> shipsInOrder = ships; // .sort?
        for (Ship ship : shipsInOrder){
            printSummary(ship);
        }
    }

    public void checkShipsByProfit() {
        Set<Ship> shipsInOrder = ships; // .sort?
        for (Ship ship : shipsInOrder) {
            printSummary(ship);
        }
    }

    public void printSummary(Ship ship) {
        System.out.printf("Name: %s\n", ship.name);
        System.out.printf("Number of Compartments: %d\n", ship.compartments.size());
        System.out.printf("Profit: %d\n\n", ship.getProfit());
    }

}
