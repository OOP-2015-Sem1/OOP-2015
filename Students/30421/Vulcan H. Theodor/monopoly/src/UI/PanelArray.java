package UI;
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;



public class PanelArray {
	
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
				setFinalSize(mainGame.TILE_ARRAY.array[i], cornerPanelSize);
				southPanel.add(mainGame.TILE_ARRAY.array[i]);
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].tokenArea, BorderLayout.SOUTH);
			}
			else if (i<10) {
				setFinalSize(mainGame.TILE_ARRAY.array[i], xAxisPanelSize);
				southPanel.add(mainGame.TILE_ARRAY.array[i]);			
				mainGame.TILE_ARRAY.array[i].tokenArea.setPreferredSize(new Dimension(82, 94));
				mainGame.TILE_ARRAY.array[i].houseArea.setPreferredSize(new Dimension(82, 37));
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].houseArea, BorderLayout.NORTH);
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].tokenArea, BorderLayout.SOUTH);
				
			}
			else if (i==10) {
				setFinalSize(mainGame.TILE_ARRAY.array[i], cornerPanelSize);
				southPanel.add(mainGame.TILE_ARRAY.array[i]);
			}
			else if (i<20) {
				setFinalSize(mainGame.TILE_ARRAY.array[i], yAxisPanelSize);
				westPanel.add(mainGame.TILE_ARRAY.array[i]);
				mainGame.TILE_ARRAY.array[i].tokenArea.setPreferredSize(new Dimension(94, 82));
				mainGame.TILE_ARRAY.array[i].houseArea.setPreferredSize(new Dimension(37, 82));
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].houseArea, BorderLayout.EAST);
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].tokenArea, BorderLayout.WEST);
			}
			else if (i==20) {
				setFinalSize(mainGame.TILE_ARRAY.array[i], cornerPanelSize);
				northPanel.add(mainGame.TILE_ARRAY.array[i]);
			}
			else if (i<30) {
				setFinalSize(mainGame.TILE_ARRAY.array[i], xAxisPanelSize);
				northPanel.add(mainGame.TILE_ARRAY.array[i]);
				mainGame.TILE_ARRAY.array[i].tokenArea.setPreferredSize(new Dimension(82, 94));
				mainGame.TILE_ARRAY.array[i].houseArea.setPreferredSize(new Dimension(82, 37));
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].houseArea, BorderLayout.SOUTH);
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].tokenArea, BorderLayout.NORTH);
			}
			else if (i==30) {
				setFinalSize(mainGame.TILE_ARRAY.array[i], cornerPanelSize);
				northPanel.add(mainGame.TILE_ARRAY.array[i]);
			}
			else if (i<40) {
				setFinalSize(mainGame.TILE_ARRAY.array[i], yAxisPanelSize);
				eastPanel.add(mainGame.TILE_ARRAY.array[i]);
				mainGame.TILE_ARRAY.array[i].tokenArea.setPreferredSize(new Dimension(94, 82));
				mainGame.TILE_ARRAY.array[i].houseArea.setPreferredSize(new Dimension(37, 82));
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].houseArea, BorderLayout.WEST);
				mainGame.TILE_ARRAY.array[i].add(mainGame.TILE_ARRAY.array[i].tokenArea, BorderLayout.EAST);
			}
			mainGame.TILE_ARRAY.array[i].setBackground(Board.UIColor);
		}
	}
	
	private void setFinalSize(JPanel targetPanel, Dimension size){
		targetPanel.setPreferredSize(size);
		targetPanel.setMinimumSize(size);
		targetPanel.setMaximumSize(size);
	}
}
