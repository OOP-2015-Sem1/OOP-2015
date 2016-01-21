import java.util.LinkedHashSet;
import java.util.Set;

public class Ship {

    Set<Compartment> compartments = new LinkedHashSet<>();
    String name;
    int profit;

    public Ship(String name) {
        this.name = name;
    }

    public void addCompartment(Compartment compartment) {
        if (compartments.size() == 0)
            compartments.add(compartment);
        else if (compartments.getClass() == compartment.getClass())
            compartments.add(compartment);
        else
            System.out.println("A ship cannot have both passenger and cargo compartments!");
    }

    public void computeProfit(){
        int profit = 0;
        for (Compartment compartment : compartments)
            profit += compartment.getProfit();
        this.profit = profit;
    }

    public int getProfit(){
        computeProfit();
        return profit;
    }
}
