package io.deployment.shipListeners;

import java.awt.event.ActionEvent;

import prototypes.ButtonListener;
import resources.MapModel;

public class CarrierListener extends ButtonListener {
	
	public CarrierListener(MapModel model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent e){
		mapModel.selectCarrier();
	}

}
