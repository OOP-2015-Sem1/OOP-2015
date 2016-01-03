package io.deployment.shipListeners;

import java.awt.event.ActionEvent;

import prototypes.ButtonListener;
import resources.MapModel;

public class CruiserListener extends ButtonListener {

	public CruiserListener(MapModel model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mapModel.selectCruiser();
	}

}
