import java.util.Random;

public class Main {

	public static void main(String[] args) {
		TrainStation ts = new TrainStation();
	}
	
	public static Wagon generateWagon() {
		Random r = new Random();
		int i = r.nextInt(2);
		if (i == 0) {
			Passenger p = new Passenger("John");
			Wagon<Passenger> w = new Wagon<Passenger>("1");
			w.add(p);
			w.add(p);
			return w;
		} else {
			Passenger p = new Passenger("John");
			Wagon<Passenger> w = new Wagon<Passenger>("1");
			w.add(p);
			w.add(p);
			return w;
		}
	}
}
