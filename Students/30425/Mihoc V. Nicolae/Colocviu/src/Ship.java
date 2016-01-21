import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

public class Ship extends Compartment {
	String x = UUID.randomUUID().toString();
	int r = 0;
	boolean test;
	int profit = 0;

	public Ship() {
		Set<Integer> compartments = new LinkedHashSet<Integer>();
		r = new Random().nextInt(20);
		Math.abs(r);
		compartments.add(r);

	}
}