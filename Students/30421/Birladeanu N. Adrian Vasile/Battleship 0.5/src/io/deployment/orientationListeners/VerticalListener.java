package io.deployment.orientationListeners;

import java.awt.event.ActionEvent;
import prototypes.ButtonListener;
import resources.MapModel;

public class VerticalListener extends ButtonListener{

	public VerticalListener(MapModel model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		mapModel.setVerticalOrientation();
	}

}
