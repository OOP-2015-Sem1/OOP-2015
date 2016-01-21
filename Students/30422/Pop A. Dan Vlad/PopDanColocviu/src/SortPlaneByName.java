import java.util.Comparator;

public class SortPlaneByName implements Comparator<Plane> {

	@Override
	public int compare(Plane p1, Plane p2) {
		return p1.name.compareTo(p2.name);
	}

}
