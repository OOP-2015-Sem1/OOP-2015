package UI;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import GameMechanics.Game;


public class PanelArray {
	
	public JPanel[] arrayOfPanel= new JPanel[40];
	public JPanel panelBoard= new JPanel();
	private JPanel northPanel= new JPanel();
	private JPanel southPanel= new JPanel();
	private JPanel eastPanel= new JPanel();
	private JPanel westPanel= new JPanel();
	Dimension xAxisPanelSize= new Dimension(82, 131);
	Dimension yAxisPanelSize= new Dimension(131, 82);
	Dimension cornerPanelSize= new Dimension(131, 131);
	


	Dimension size= new Dimension(1000, 1000);

	public PanelArray(){
		
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.LINE_AXIS));
		southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.LINE_AXIS));
		eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.PAGE_AXIS));
		westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.PAGE_AXIS));		
		
		northPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		southPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		eastPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		westPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		
		panelBoard.setLayout(new BorderLayout());
		panelBoard.setPreferredSize(size);
		panelBoard.setBackground(Board.UIColor);
		
		
		addPanelArray();
		panelBoard.add(northPanel, BorderLayout.NORTH);
		panelBoard.add(southPanel, BorderLayout.SOUTH);
		panelBoard.add(eastPanel, BorderLayout.EAST);
		panelBoard.add(westPanel, BorderLayout.WEST);
		
		}
	
	private void addPanelArray(){
				
		for(int i=0;i<40;i++){ 
			if (i==0) {
				setFinalSize(Game.TILE_ARRAY.array[i], cornerPanelSize);
				southPanel.add(Game.TILE_ARRAY.array[i]);
			}
			else if (i<10) {
				setFinalSize(Game.TILE_ARRAY.array[i], xAxisPanelSize);
				southPanel.add(Game.TILE_ARRAY.array[i]);			
			}
			else if (i==10) {
				setFinalSize(Game.TILE_ARRAY.array[i], cornerPanelSize);
				southPanel.add(Game.TILE_ARRAY.array[i]);
			}
			else if (i<20) {
				setFinalSize(Game.TILE_ARRAY.array[i], yAxisPanelSize);
				westPanel.add(Game.TILE_ARRAY.array[i]);
			}
			else if (i==20) {
				setFinalSize(Game.TILE_ARRAY.array[i], cornerPanelSize);
				northPanel.add(Game.TILE_ARRAY.array[i]);
			}
			else if (i<30) {
				setFinalSize(Game.TILE_ARRAY.array[i], xAxisPanelSize);
				northPanel.add(Game.TILE_ARRAY.array[i]);
			}
			else if (i==30) {
				setFinalSize(Game.TILE_ARRAY.array[i], cornerPanelSize);
				northPanel.add(Game.TILE_ARRAY.array[i]);
			}
			else if (i<40) {
				setFinalSize(Game.TILE_ARRAY.array[i], yAxisPanelSize);
				eastPanel.add(Game.TILE_ARRAY.array[i]);
			}
			Game.TILE_ARRAY.array[i].setBackground(Board.UIColor);
		}
	}
	
	private void setFinalSize(JPanel targetPanel, Dimension size){
		targetPanel.setPreferredSize(size);
		targetPanel.setMinimumSize(size);
		targetPanel.setMaximumSize(size);
	}
}
