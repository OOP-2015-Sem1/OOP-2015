import java.util.List;

/**
 * Created by Paul on 13.01.2016.
 */
public class CargoWagon extends Wagon {

    private final CargoItem item;

    public CargoWagon(CargoItem item){
        super("Cargo");
        this.item = item;
    }

    public void addCargo(Carriable cargo){
        this.cargo.add(cargo);
    }

    @Override
    public int getProfit() {
        return cargo.size()*item.getProfit();
    }
}
