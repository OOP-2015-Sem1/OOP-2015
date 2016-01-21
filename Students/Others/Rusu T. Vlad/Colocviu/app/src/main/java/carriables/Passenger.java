package carriables;

/**
 * Created by vladrusu on 13/01/16.
 */
public class Passenger implements Carriable {
    private final String name;

    public Passenger(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getProfit() {
        return 0;
    }
}
