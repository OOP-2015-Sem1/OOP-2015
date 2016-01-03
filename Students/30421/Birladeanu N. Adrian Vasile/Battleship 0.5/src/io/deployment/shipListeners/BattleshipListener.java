package io.deployment.shipListeners;

import java.awt.event.ActionEvent;

import prototypes.ButtonListener;
import resources.MapModel;

public class BattleshipListener extends ButtonListener {

	public BattleshipListener(MapModel model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		mapModel.selectBattleship();
	}

}
