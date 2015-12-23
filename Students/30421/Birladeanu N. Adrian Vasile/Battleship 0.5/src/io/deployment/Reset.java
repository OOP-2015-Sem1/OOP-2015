package io.deployment;

import java.awt.event.ActionEvent;

import prototypes.ButtonListener;
import resources.MapModel;

public class Reset extends ButtonListener {
	public Reset(MapModel model) {
		super(model);
	}

	public void actionPerformed(ActionEvent e){
		mapModel.reset();
	}

}
