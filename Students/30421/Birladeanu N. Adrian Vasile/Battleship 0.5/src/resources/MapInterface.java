package resources;

import javax.swing.*;

import io.deployment.MapDeploymentListener;
import prototypes.ModifiedButton;
import java.awt.*;
import java.awt.event.MouseListener;
import resources.Resources;

public class MapInterface extends JPanel {
	
	private static final long serialVersionUID = -4962839051476896747L;
	
	private ModifiedButton[][] region=new ModifiedButton[Resources.MAP_DIMENSION]
			[Resources.MAP_DIMENSION];
	private MapDeploymentListener[][] deploymentListener=new MapDeploymentListener
			[Resources.MAP_DIMENSION][Resources.MAP_DIMENSION];
	private MapModel superiorMapModel;
	
	
	
	public MapInterface(MapModel model){
		this.setLayout(new GridLayout
				(Resources.MAP_DIMENSION, Resources.MAP_DIMENSION));
		superiorMapModel=model;
		this.setButtons();		
	}
	
	public void changeButtonColor(int i, int j, Color colour){
		region[i][j].changeColour(colour);
	}
	
	public void setButtons(){
		for(int i=0; i<10; i++)
			for(int j=0; j<10; j++){
				region[i][j]=new ModifiedButton();
				changeButtonColor(i, j, Color.BLUE);
				this.add(region[i][j]);
				}
	}
	
	public void addSpecificListener(MouseListener listener, int i, int j){
		region[i][j].addMouseListener(listener);
	}
	
	public void addDeploymentListener(){
		
		int i, j;
		for(i=0; i<Resources.MAP_DIMENSION; i++){
			for(j=0; j<Resources.MAP_DIMENSION; j++){
				deploymentListener[i][j]=new MapDeploymentListener
						(i, j, superiorMapModel);
				MouseListener mouseListener=deploymentListener[i][j];
				region[i][j].addMouseListener(mouseListener);
			}
		}
	}
	
	
	public void revertColour(int i, int j){
		region[i][j].revertColour();
	}
	
	public void removeDeploymentListener(int i, int j){
		MouseListener[] mouseListener;
		mouseListener=region[i][j].getMouseListeners();
		if(mouseListener!=null){
			if(mouseListener[0]!=null){
				region[i][j].removeMouseListener(mouseListener[0]);
			}
			if(mouseListener[1]!=null){
				region[i][j].removeMouseListener(mouseListener[1]);
			}
		}
	}
	
	public void recolourMap(){
		for(int i=0; i<Resources.MAP_DIMENSION; i++)
			for(int j=0; j<Resources.MAP_DIMENSION; j++){
				this.changeButtonColor(i, j, Color.BLUE);
			}
	}
}
