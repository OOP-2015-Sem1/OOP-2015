public class PassagerCompartment extends Compartment {

    final int TICKET_PRICE = 100;
    final int MAX_NR_OF_PASSENGERS = 100;

    @Override
    public void computeProfit() {
        super.profit = TICKET_PRICE * carriables.size();
    }

    @Override
    public <T extends Carriable> void addCarriable(T carriable) {
        if (super.carriables.size() < MAX_NR_OF_PASSENGERS) {
            //        super.carriables.add(carriable);
        } else
            System.out.println("This compartment is full");
    }
}
