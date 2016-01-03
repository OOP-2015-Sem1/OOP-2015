import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUI {
	
	private JButton upButton;
	private JButton downButton;
	private JButton leftButton;
	private JButton rightButton;
	
	private JPanel mainPanel;
	private JPanel buttonsPanel;
	private JPanel catPanel;
	private int nrC;
	private int nrR;

	private static final int ROWS = 20;
	private static final int COLS = 40;
	private final char[][] board = new char[ROWS][COLS];
	
	private JFrame myFrame; 
	
	public GUI() {
				
		mainPanel = new JPanel(new BorderLayout());
		
		upButton = new JButton("UP");
		downButton = new JButton("DOWN");
		leftButton = new JButton("LEFT");
		rightButton = new JButton("RIGHT");
		
		buttonsPanel = new JPanel(); 
		buttonsPanel.setSize(500,50);
		
		buttonsPanel.add(upButton);
		buttonsPanel.add(downButton);
		buttonsPanel.add(leftButton);
		buttonsPanel.add(rightButton);
		
		catPanel = new JPanel();
		catPanel.setLayout(new GridBagLayout());
		catPanel.setSize(500,450);
		catPanel.setBackground(Color.RED);
		
		 GridBagConstraints gbc = new GridBagConstraints();
         
		 try {
				String line;
				FileReader fileReader = new FileReader("FluffyWorld.txt");

				BufferedReader bufferedReader = new BufferedReader(fileReader);
				int rowC = 0;
				while ((line = bufferedReader.readLine()) != null) {
					int colC = 0;
					for (char c : line.toCharArray()) {
						board[rowC][colC++] = c;
					}
					rowC++;
					nrC = colC;
				}
				nrR = rowC;
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file ");
			} catch (IOException ex) {
				System.out.println("Error reading file ");
			}
		 
		for (int i=0; i<nrR; i++) {
			for (int j=0; j<nrC; j++) {
				JPanel or = new JPanel();
				gbc.gridx = j;
		        gbc.gridy = i;
		        
		        char x;
		        
		        x = board[i][j];
		        
		        System.out.println(x);
		        
				if ( x == '*') {
					or.setBackground(Color.BLUE);
				}
				if ( x == 'W') {
					or.setBackground(Color.BLACK);
				}
				if ( x == 'H') {
					or.setBackground(Color.CYAN);
				}
				catPanel.add(or, gbc);
			}
		}
		
		mainPanel.add(buttonsPanel);
		mainPanel.add(catPanel);
				
		myFrame = new JFrame();
		myFrame.add(mainPanel);
		myFrame.setSize(500,500);
		myFrame.setVisible(true);	
	}
}