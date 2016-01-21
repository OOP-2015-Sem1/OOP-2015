import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Paul on 13.01.2016.
 */
public abstract class Wagon {
    private final String ID;
    private final String Type;

    private int profit;
    protected ArrayList<Carriable> cargo;

    public abstract int getProfit();

    public Wagon(String type){

        //sper ca am facut bine ca habar nu am ce e UUID
        UUID uid = UUID.randomUUID();
        ID = uid.toString();
        this.Type = type;
        cargo = new ArrayList<Carriable>();
    }

    public String getType(){
        return this.Type;
    }
}
