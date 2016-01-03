package UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PropertyPanel extends JPanel{

	private static final long serialVersionUID = -8416732007521840834L;

	public PropertyPanel(){
		
		Color LEFT_COLOR_ARRAY[]= {
				new Color(149, 84, 54),
				new Color(170, 224, 250),
				new Color(217, 58, 150),
				new Color(247, 148, 29),
				new Color(237, 27, 36),
		};
		
		Color RIGHT_COLOR_ARRAY[]= {
				new Color(254, 242, 0),
				new Color(31, 178, 90),
				new Color(0, 114, 187),
				Color.WHITE,
				Color.DARK_GRAY
		};
		
		LabelArrayInPanel leftPanel= new LabelArrayInPanel();
		LabelArrayInPanel rightPanel= new LabelArrayInPanel();
		JPanel centerPositionLeftPanel= new JPanel();
		JPanel centerPositionRightPanel= new JPanel();
		Dimension SIDE_SPACE_SIZE= new Dimension(45,0);
		JPanel rightSpace= new JPanel();
		JPanel leftSpace= new JPanel();

		
		for(int i = 0; i < LabelArrayInPanel.i; i++) {
			Border leftBorder= BorderFactory.createLineBorder(LEFT_COLOR_ARRAY[i],3);
			Border rightBorder= BorderFactory.createLineBorder(RIGHT_COLOR_ARRAY[i],3);
			   for(int j = 0; j < LabelArrayInPanel.j; j++) {
				   if ((i==0 && j<2) || (j<3 && i!=0)){
				   leftPanel.labelArray[i][j].setBorder(leftBorder); 
				   leftPanel.labelArray[i][j].setBackground(Board.UIColor);
				   leftPanel.labelArray[i][j].setOpaque(true);
				   }
				   if ((i<2 && j<3) || (i>=2 && i<=3 && j<2) || (i==4)){
				   rightPanel.labelArray[i][j].setBorder(rightBorder); 
				   rightPanel.labelArray[i][j].setBackground(Board.UIColor);
				   rightPanel.labelArray[i][j].setOpaque(true);
				   }
			   }
		}
		
		setBackground(Board.UIColor);
		setLayout(new GridLayout(1,5,50,0));

		centerPositionLeftPanel.setLayout(new BorderLayout());	
		centerPositionRightPanel.setLayout(new BorderLayout());	
		leftSpace.setPreferredSize(SIDE_SPACE_SIZE);
		leftSpace.setBackground(Board.UIColor);
		rightSpace.setPreferredSize(SIDE_SPACE_SIZE);
		rightSpace.setBackground(Board.UIColor);

		
		centerPositionLeftPanel.add(leftSpace, BorderLayout.WEST);
		centerPositionLeftPanel.add(leftPanel.labelArrayContainer, BorderLayout.CENTER);
		centerPositionRightPanel.add(rightSpace, BorderLayout.EAST);
		centerPositionRightPanel.add(rightPanel.labelArrayContainer, BorderLayout.CENTER);
		
		add(centerPositionLeftPanel);
		add(centerPositionRightPanel);
	
		
		
	}
	
	static class LabelArrayInPanel{
		
		
		public JPanel labelArrayContainer= new JPanel();
		public JLabel labelArray[][]= new JLabel[5][4];
		static public int i=5;
		static public int j=4;
		
		 public LabelArrayInPanel(){
			
		
		labelArrayContainer.setLayout(new GridLayout(5, 4, 3, 3));
		labelArrayContainer.setBackground(Board.UIColor);
		
		
		for(int m = 0; m < i; m++) {
			   for(int n = 0; n < j; n++) {
				   labelArray[m][n] = new JLabel();
				   labelArrayContainer.add(labelArray[m][n]);
			   }
			}
		
		}
	
	}
}
