package fluffy.the.cat.models;

import java.awt.Point;
import java.io.Serializable;

public class Fluffy implements Serializable {

	private static final long serialVersionUID = -2733076686185224879L;

	private Point position;

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

}
