package io.deployment;

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import prototypes.ButtonListener;
import resources.Main;
import resources.MapModel;

public class NextButton extends ButtonListener {

	public NextButton(MapModel model) {
		super(model);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(mapModel.shipPoints==21){
			Main.game.setBattlePanel();
		}
		else JOptionPane.showMessageDialog
		(null, "You have not placed all the ships");
	}

}
