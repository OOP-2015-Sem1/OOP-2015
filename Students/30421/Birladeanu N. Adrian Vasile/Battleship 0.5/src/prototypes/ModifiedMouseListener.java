package prototypes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import resources.MapModel;

public abstract class ModifiedMouseListener implements MouseListener {

	protected int i;
	protected int j;
	protected MapModel mapModel;
	
	
	public ModifiedMouseListener(int i, int j, MapModel model){
		this.i=i;
		this.j=j;
		mapModel=model;
	}
	
	public ModifiedMouseListener(){
		i=0;
		j=0;
		mapModel=null;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
	
	public void setI(int i){
		this.i=i;
	}
	
	public void setJ(int j){
		this.j=j;
	}
}
