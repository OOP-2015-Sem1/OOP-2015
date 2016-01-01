package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.awt.Component;
import javax.swing.JComponent;

public interface ComponentSizer {
	public default void sizeComponents(ArrayList<JComponent> components) {
		for (JComponent jComponent : components) {
			jComponent.setAlignmentX(Component.CENTER_ALIGNMENT);
			jComponent.setMaximumSize(new Dimension(150,20));
		}
	}
}
