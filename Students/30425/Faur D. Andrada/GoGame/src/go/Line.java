package go;
import java.awt.*;


public class Line {
	 Line(Point p1, Point p2) {
	        this.p1 = p1;
	        this.p2 = p2;
	    }
	    public void paint(Graphics g) {
	        g.drawLine(p1.x, p1.y, p2.x, p2.y);
	    }
	    final Point p1, p2;
}
