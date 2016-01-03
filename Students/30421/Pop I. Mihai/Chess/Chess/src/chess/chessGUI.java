package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JOptionPane;

public class chessGUI implements ActionListener, KeyListener, WindowFocusListener
{
	
	private windowChessBoard mainChessBoard;
	private objCreateAppletImage createImage;
	private JButton cmdNewGame, cmdSetNames;
	private JTextField txtPlayerOne, txtPlayerTwo;
	private JLabel lblPlayerOne, lblPlayerTwo;
	private String[] strBlackPieces = {"bp.gif","br.gif","bn.gif","bb.gif","bq.gif","bk.gif"};
	private String[] strWhitePieces = {"wp.gif","wr.gif","wn.gif","wb.gif","wq.gif","wk.gif"};
	private Color clrBlue = new Color(23,45,110);
	private MediaTracker mt;
	
	public void chessGUI ()
	{
	}
	
	public Container createGUI (JFrame mainApp)
	{
		
		JPanel panRoot = new JPanel(new BorderLayout());
		panRoot.setOpaque(true);
                panRoot.setPreferredSize(new Dimension(850,650));
		
            
		mainChessBoard = new windowChessBoard();
		createImage = new objCreateAppletImage();
		
		mainChessBoard.setSize(new Dimension(500, 500));


		cmdNewGame = new JButton("JOC NOU");
		cmdSetNames = new JButton("SETATI NUMELE");
		
		cmdNewGame.addActionListener(this);
		cmdSetNames.addActionListener(this);
		
		txtPlayerOne = new JTextField("", 10);
		txtPlayerTwo = new JTextField("", 10);
		
		txtPlayerOne.addKeyListener(this);
		txtPlayerTwo.addKeyListener(this);
		
		lblPlayerOne = new JLabel("    ", JLabel.RIGHT);
		lblPlayerTwo = new JLabel("    ", JLabel.RIGHT);
		
		try
		{
			
			Image[] imgBlack = new Image[6];
			Image[] imgWhite = new Image[6];
			mt = new MediaTracker(mainApp);
			
			for (int i = 0; i < 6; i++)
			{				
			
				imgBlack[i] = createImage.getImage(this, "graphics/" + strBlackPieces[i],5000 );
				imgWhite[i] = createImage.getImage(this, "graphics/" + strWhitePieces[i],5000 );
                                
				mt.addImage(imgBlack[i], 0);
				mt.addImage(imgWhite[i], 0);
				
			}
			
			try
			{
				mt.waitForID(0);
			}
			catch (InterruptedException e)
			{
			}
			
			mainChessBoard.setupImages(imgBlack, imgWhite);
			
		}
		catch (NullPointerException e)
		{
			
			JOptionPane.showMessageDialog(null, "Pozele nu au putut fi incarcate. Trebuie sa existe un fisier cu toate piesele in el. Try downloading this programme again", "Pozele nu au putut fi incarcate!", JOptionPane.WARNING_MESSAGE);
			cmdNewGame.setEnabled(false);
			cmdSetNames.setEnabled(false);
			
		}
		
                
                
          
                JPanel panNameOne = new JPanel();
		JPanel panNameTwo = new JPanel();
		JPanel panPlayerOne = new JPanel();
		JPanel panPlayerTwo = new JPanel();
		JPanel panControl = new JPanel();
		JPanel panelmargine = new JPanel();
                
                panelmargine.setSize(100,500);       
                panRoot.add(panelmargine,BorderLayout.WEST);
                 panelmargine.setSize(100,500); 
		panRoot.add(mainChessBoard, BorderLayout.CENTER);
                
               panRoot.add(panNameTwo,BorderLayout.NORTH);
                JPanel panelnume2=new JPanel();
                panNameTwo.add(panelnume2,BorderLayout.CENTER);
                panelnume2.add(lblPlayerTwo);
		panelnume2.add(txtPlayerTwo);
                
                panRoot.add(panNameOne,BorderLayout.SOUTH);
                JPanel panelnume1=new JPanel();
                panNameOne.add(panelnume1,BorderLayout.CENTER);
                panelnume1.add(lblPlayerOne);
		panelnume1.add(txtPlayerOne);
                
                panRoot.add(panControl,BorderLayout.EAST);
                JPanel panel3=new JPanel(new GridLayout(2,1));
                panControl.add(panel3);
                panel3.add(cmdSetNames);
                panel3.add(cmdNewGame);
		
	
                
		panRoot.setBackground(clrBlue);
                panNameOne.setBackground(clrBlue); 
                panControl.setBackground(clrBlue); 
                panel3.setBackground(clrBlue); 
		panNameTwo.setBackground(clrBlue);
                panelnume1.setBackground(clrBlue);
                panelnume2.setBackground(clrBlue);
		panelmargine.setBackground(clrBlue);
		lblPlayerOne.setBackground(Color.GRAY); 
		lblPlayerTwo.setBackground(Color.WHITE);
		
		
		
		return panRoot;
		
	}
   
	
	public void actionPerformed(ActionEvent e)
	{
		
		if (e.getSource() == cmdSetNames)
		{
			
			if (txtPlayerOne.getText().equals(""))
			{
				txtPlayerOne.setText("Maria");
			}
			
			if (txtPlayerTwo.getText().equals(""))
			{
				txtPlayerTwo.setText("Andrei");
			}
			
			mainChessBoard.setNames(txtPlayerOne.getText(), txtPlayerTwo.getText());
			
		}
		else if (e.getSource() == cmdNewGame)
		{
			mainChessBoard.newGame();
		}
		
	}
	
	public void keyTyped(KeyEvent e)
	{
		
		String strBuffer = "";
		char c = e.getKeyChar();
		
		if (e.getSource() == txtPlayerOne)
		{
			strBuffer = txtPlayerOne.getText();
		}
		else
		{
			strBuffer = txtPlayerTwo.getText();
		}
		
		if (strBuffer.length() > 10 && !((c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))
		{
			e.consume();
		}
		
	}
	
	public void keyPressed(KeyEvent e)
	{
	}
	
	public void keyReleased(KeyEvent e)
	{
	}
	
	public void windowGainedFocus (WindowEvent e)
	{
		mainChessBoard.gotFocus();
	}
	
	public void windowLostFocus (WindowEvent e)
	{
	}
	
}