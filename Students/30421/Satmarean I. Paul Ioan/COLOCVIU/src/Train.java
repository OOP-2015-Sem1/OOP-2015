import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;

/**
 * Created by Paul on 13.01.2016.
 */
public class Train implements Comparable {

    private final String name;
    private String type;
    private LinkedList<Wagon> wagons;

    public Train(){
        UUID uid = UUID.randomUUID();
        name = uid.toString();
        wagons = new LinkedList<Wagon>();
    }

    public Train(String type){
        this.type = type;

        UUID uid = UUID.randomUUID();
        name = uid.toString();
        wagons = new LinkedList<Wagon>();
    }

    public int getProfit(){
        int total =0;
        for(Wagon w:wagons){
            total += w.getProfit();
        }
        return total;
    }

    public void removeWagon(Wagon w){
        if(wagons.contains(w)) {
            Iterator<Wagon> it = wagons.iterator();
            while(it.next()!=null){
                if(it.equals(w)){
                    it.remove();
                    return;
                }
            }
        }

    }

    public void addWagon(Wagon w){
        if(type==null){
            this.type= w.getType();
            wagons.add(w);
            return;
        }else if(w.getType().equals(type)) {
            if (!wagons.contains(w)) {
                wagons.add(w);
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        Train t = (Train)o;
        return name.compareTo(t.getName());
    }

    public String getName(){
        return name;
    }

    public void printSummary(){
        System.out.println("["+ name + "] " + "[" + Integer.toString(wagons.size()) + "] " + "[" +Integer.toString(getProfit()) + "]");
    }
}
