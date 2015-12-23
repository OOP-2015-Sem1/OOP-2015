package io.deployment.shipListeners;

import java.awt.event.ActionEvent;

import prototypes.ButtonListener;
import resources.MapModel;

public class DestroyerListener extends ButtonListener {

	public DestroyerListener(MapModel model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		mapModel.selectDestroyer();
	}
	
}
