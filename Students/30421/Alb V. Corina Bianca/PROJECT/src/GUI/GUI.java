package GUI;

import java.awt.Color;
import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.JFrame;

public class GUI {
	
	JFrame c = new JFrame();
	GroupLayout layout;
	
	public GUI(GUITableauPile sTableauOne, GUITableauPile sTableauTwo, GUITableauPile sTableauThree, GUITableauPile sTableauFour, 
			GUITableauPile sTableauFive, GUITableauPile sTableauSix, GUITableauPile sTableauSeven, GUIStockPile sStockPile, 
			GUIWastePile sWastePile, GUIFoundadtionClubs sFoundadtionClubs, GUIFoundationDiamonds sFoundationDiamonds, 
			GUIFoundationHearts sFoundationHearts, GUIFoundationSpades sFoundationSpades) {
		
		Container container = c.getContentPane();
		container.setBackground(Color.GREEN);
		
		layout = new GroupLayout(container);
		
		c.setLayout(layout);
		c.setVisible(true);
		c.setSize(800, 600);
		c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		layout.setHorizontalGroup(
				   layout.createSequentialGroup()
				   	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   			.addComponent(sStockPile, 60, 90, 200)
				   			.addGap(30)
				   			.addComponent(sTableauOne, 60, 90, 200))
				   	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   			.addComponent(sWastePile, 60, 90, 200)
				   			.addGap(30)
				   			.addComponent(sTableauTwo, 70, 100, 200))
				   	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   			.addGap(60)
				   			.addComponent(sTableauThree, 60, 90, 200))
				   	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   			.addComponent(sFoundadtionClubs, 60, 90, 200)
				   			.addGap(30)
				   			.addComponent(sTableauFour, 60, 90, 200))
				   	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   			.addComponent(sFoundationDiamonds, 60, 90, 200)
				   			.addGap(30)
				   			.addComponent(sTableauFive, 60, 90, 200))
				   	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   			.addComponent(sFoundationHearts, 60, 90, 200)
				   			.addGap(30)
				   			.addComponent(sTableauSix, 60, 90, 200))
				   	.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				   			.addComponent(sFoundationSpades, 60, 90, 200)
				   			.addGap(30)
				   			.addComponent(sTableauSeven, 60, 90, 200))
				);
		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(sStockPile, 60, 90, 200)
								.addComponent(sWastePile, 60, 90, 200)
								.addGap(30)
								.addComponent(sFoundadtionClubs, 60, 90, 200)
								.addComponent(sFoundationDiamonds, 60, 90, 200)
								.addComponent(sFoundationHearts, 60, 90, 200)
								.addComponent(sFoundationSpades, 60, 90, 200))
						.addGap(30)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(sTableauOne, 60, 90, 200)
								.addComponent(sTableauTwo, 70, 100, 200)
								.addComponent(sTableauThree, 60, 90, 200)
								.addComponent(sTableauFour, 60, 90, 200)
								.addComponent(sTableauFive, 60, 90, 200)
								.addComponent(sTableauSix, 60, 90, 200)
								.addComponent(sTableauSeven, 60, 90, 200))
				);
	}
}