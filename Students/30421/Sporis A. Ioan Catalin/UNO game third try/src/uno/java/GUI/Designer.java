package uno.java.GUI;

import java.awt.Component;
import java.awt.Rectangle;

public interface Designer {
	public void componentSetBounds(Component component, Rectangle bounds);

	public void arrangeItems();
}
