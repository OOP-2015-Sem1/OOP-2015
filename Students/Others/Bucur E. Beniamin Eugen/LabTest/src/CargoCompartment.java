import java.util.List;

public class CargoCompartment extends Compartment {

    CargoItem item;

    public CargoCompartment(CargoItem item){
        this.item = item;
    }

    @Override
    public void computeProfit(){
        super.profit = item.profit * carriables.size();
    }

    @Override
    public <T extends Carriable> void addCarriable(T carriable) {
//        super.carriables.add(carriable);
    }
}
