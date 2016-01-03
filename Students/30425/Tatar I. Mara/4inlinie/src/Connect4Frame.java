import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Connect4Frame extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;// to remove warning
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7;
	private Label lblSpacer;
	MenuItem newMI, exitMI,pvpMI,easyMI,mediumMI,hardMI;
	private boolean gameRunning;
	public static final String SPACE = "                  "; // 18 spaces

	private Board board;
	private AI ai;

	public Connect4Frame() {
		board = new Board(6, 7);
		gameRunning = true;
		setTitle("Connect4");
		// This creates the menu.
		MenuBar mbar = new MenuBar();
		Menu fileMenu = new Menu("File");
		mbar.add(fileMenu);
		newMI = new MenuItem("New");
		newMI.addActionListener(this);
		fileMenu.add(newMI);
		exitMI = new MenuItem("Exit");
		exitMI.addActionListener(this);
		fileMenu.add(exitMI);
		Menu fileMode=new Menu("Mode");
		mbar.add(fileMode);
		pvpMI=new MenuItem("Player vs Player");
		pvpMI.addActionListener(this);
		fileMode.add(pvpMI);
		Menu pvcMI=new Menu("Player vs Computer");
		pvcMI.addActionListener(this);
		fileMode.add(pvcMI);
		easyMI=new MenuItem("Easy");
		easyMI.addActionListener(this);
		pvcMI.add(easyMI);
		mediumMI=new MenuItem("Medium");
		mediumMI.addActionListener(this);
		pvcMI.add(mediumMI);
		hardMI=new MenuItem("Hard");
		hardMI.addActionListener(this);
		pvcMI.add(hardMI);

		setMenuBar(mbar);
		

		// Build control panel.
		Panel panel = new Panel();

		btn1 = new Button("1");
		btn1.addActionListener(this);
		panel.add(btn1);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn2 = new Button("2");
		btn2.addActionListener(this);
		panel.add(btn2);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn3 = new Button("3");
		btn3.addActionListener(this);
		panel.add(btn3);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn4 = new Button("4");
		btn4.addActionListener(this);
		panel.add(btn4);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn5 = new Button("5");
		btn5.addActionListener(this);
		panel.add(btn5);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn6 = new Button("6");
		btn6.addActionListener(this);
		panel.add(btn6);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn7 = new Button("7");
		btn7.addActionListener(this);
		panel.add(btn7);

		add(panel, BorderLayout.NORTH);

		// Set to a reasonable size.
		setSize(1024, 768);
	} // Connect4

	public void paint(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(110, 50, 100 + 100 * board.getColumns(), 100 + 100 * board.getRows());
		for (int row = 0; row < board.getRows(); row++)
			for (int col = 0; col < board.getColumns(); col++) {
				if (board.getArray()[row][col] == Chip.BLANK)
					g.setColor(Color.WHITE);
				if (board.getArray()[row][col] == Chip.RED)
					g.setColor(Color.RED);
				if (board.getArray()[row][col] == Chip.YELLOW)
					g.setColor(Color.YELLOW);
				g.fillOval(160 + 100 * col, 100 + 100 * row, 100, 100);
			}
		Chip winner = board.check4();
		if (winner != null) {
			displayWinner(g, winner);
			gameRunning = false;
		}
	} // paint

	public void displayWinner(Graphics g, Chip n) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier", Font.BOLD, 100));
		if (n == Chip.RED)
			g.drawString("Red wins!", 100, 400);
		else
			g.drawString("Yellow wins!", 100, 400);
	}

	public void actionPerformed(ActionEvent e) {
		if (gameRunning) {
			boolean moved=false;
			if (e.getSource() == btn1){
				board.putDisk(0);
				moved=true;
			}
			else if (e.getSource() == btn2){
				board.putDisk(1);
				moved=true;
			}
			else if (e.getSource() == btn3){
				board.putDisk(2);
			    moved=true;
		}
			else if (e.getSource() == btn4){
				board.putDisk(3);
				moved=true;
			}
			else if (e.getSource() == btn5){
				board.putDisk(4);
				moved=true;
			}
			else if (e.getSource() == btn6){
				board.putDisk(5);
				moved=true;
			}
			else if (e.getSource() == btn7){
				board.putDisk(6);
				moved=true;
			}
			
			if (moved) {
				if (ai != null) {
					board.putDisk(ai.nextMove(board));
				}
			}
		}
		if (e.getSource() == newMI) {
			board.initialize();
			gameRunning = true;

		} else if (e.getSource() == exitMI) {
			System.exit(0);
		}else if (e.getSource()==pvpMI){
			ai=null;			
		}else if (e.getSource()==easyMI){
			ai=new AI(4);			
		}else if (e.getSource()==mediumMI){
			ai=new AI(5);			
		}else if (e.getSource()==hardMI){
			ai=new AI(6);			
		}
		repaint();
	} // end ActionPerformed

}
