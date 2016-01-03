package io.deployment;

import java.awt.event.MouseEvent;
import prototypes.ModifiedMouseListener;
import resources.MapModel;

public class MapDeploymentListener extends ModifiedMouseListener {
	
	private static boolean active=true;
	
	public MapDeploymentListener(int i, int j, MapModel model){
		super(i, j, model);
	}
	
	public MapDeploymentListener(){
		this.i=0;
		this.j=0;
	}
	
	public static void disable(){
		active=false;
	}
	
	public static void enable(){
		active=true;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(active==true){
			if(mapModel.correctPosition==true){
				mapModel.setShip(i, j);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		if(active==true){
			mapModel.checkShip(i, j);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if(active==true){
			mapModel.revertTemporaryColours();
		}
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

}
