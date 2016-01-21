import java.util.LinkedList;
import java.util.UUID;

public abstract class Compartment {

    private static final String uuid = UUID.randomUUID().toString();
    LinkedList<? extends Carriable> carriables; // object should implement carriable
    protected int profit;

    public abstract <T extends Carriable> void addCarriable(T carriable);

    public abstract void computeProfit();

    public int getProfit(){
        computeProfit();
        return profit;
    }
}
