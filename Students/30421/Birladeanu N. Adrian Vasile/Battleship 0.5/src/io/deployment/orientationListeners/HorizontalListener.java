package io.deployment.orientationListeners;

import java.awt.event.ActionEvent;
import prototypes.ButtonListener;
import resources.MapModel;

public class HorizontalListener extends ButtonListener {

	public HorizontalListener(MapModel model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mapModel.setHorizontalOrientation();
	}

}
