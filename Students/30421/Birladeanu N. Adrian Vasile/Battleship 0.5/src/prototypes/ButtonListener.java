package prototypes;

import java.awt.event.ActionListener;

import resources.MapModel;

public abstract class ButtonListener implements ActionListener {

	protected final MapModel mapModel;

	public ButtonListener(MapModel model) {
		this.mapModel = model;
	}

}
