package UI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class Board extends JFrame {
	
	private static final long serialVersionUID = -202995241589559897L;
	static public Color UIColor= Color.LIGHT_GRAY;
	public static PanelArray overlapingPanelBoard= new PanelArray();
	//public static mainGame game =new Game();
	
	
	public Board(String name){
		super(name); 
		
		JLabel boardImage= new JLabel();
		JPanel controlPanel= new ControlPanel();
		JPanel spacedOverlapingButtonBoard= new JPanel();
		JPanel leftSpacing= new JPanel();
		
		
		leftSpacing.setPreferredSize(new Dimension(3,1));
		spacedOverlapingButtonBoard.setLayout(new BorderLayout());
		spacedOverlapingButtonBoard.setBackground(UIColor);
		spacedOverlapingButtonBoard.add(leftSpacing, BorderLayout.WEST);
		spacedOverlapingButtonBoard.add(overlapingPanelBoard.panelBoard, BorderLayout.CENTER);
		addImageToLabel(boardImage);
		setLayout(new BorderLayout());	
		setBackground(UIColor);
		add(boardImage, BorderLayout.WEST);
		add(spacedOverlapingButtonBoard, BorderLayout.WEST);
		add(controlPanel , BorderLayout.CENTER);
		

	
		
	
	}
	
	private void addImageToLabel(JLabel targetLabel){
		
		Border imagePanelBorder= BorderFactory.createLineBorder(Color.BLACK,3);
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\Board.png"));
		} catch (IOException e) {
		    e.printStackTrace();
		}
		Image dimensionedImg = img.getScaledInstance(1000, 1000, Image.SCALE_SMOOTH);
		ImageIcon picLabel= new ImageIcon(dimensionedImg);
		
		targetLabel.setIcon(picLabel);
		targetLabel.setSize(1000, 1000);
		targetLabel.setBorder(imagePanelBorder);
	}
	
	public static Integer askNumberOfPlayers(){
		
		Object[] possibleValues = {"2", "3", "4", "5" };
		ImageIcon icon = new ImageIcon("E:\\Programming Software\\eclipse\\workspace\\Monopoly\\NumberOfPlayersDialogIcon.png");
				
		 Object selectedValue = JOptionPane.showInputDialog(null,
		             "Choose number of players: ", "Monopoly",
		             JOptionPane.INFORMATION_MESSAGE, icon,
		             possibleValues, possibleValues[0]);
		 
		 String stringSelection= selectedValue.toString();
		 Integer integerSelection= Integer.parseInt(stringSelection);
		 return integerSelection;
		 
	}
	
	public static void notEnoughMoneyPrompt(){
		 String message = "Not enough money pal!";
			    JOptionPane.showMessageDialog(new JFrame(), message, "Dialog",
			        JOptionPane.ERROR_MESSAGE);
	}

}
