import java.util.Comparator;

public class SortPlaneByProfit implements Comparator<Plane> {

	@Override
	public int compare(Plane p1, Plane p2) {
		return (p1.planeProfit - p2.planeProfit);
	}

}
