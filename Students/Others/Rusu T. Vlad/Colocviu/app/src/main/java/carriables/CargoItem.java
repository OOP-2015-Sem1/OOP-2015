package carriables;

/**
 * Created by vladrusu on 13/01/16.
 */
public class CargoItem implements Carriable {
    private final String name;
    private final int profit;

    public CargoItem(String name, int profit) {
        this.name = name;
        this.profit = profit;
    }

    public String getName() {
        return this.name;
    }

    public int getProfit() {
        return this.profit;
    }
}
