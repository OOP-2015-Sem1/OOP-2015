package chess;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;


public class Chess extends JFrame
{
		
	public static void main(String[] args) 
	{
		
	JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("CHESS GAME"); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        chessGUI chessWindow = new chessGUI();
        frame.setContentPane(chessWindow.createGUI(frame));
        frame.addWindowFocusListener(chessWindow);
        
        frame.setSize(850,550);
        frame.setResizable(false);
        frame.setVisible(true);  
        frame.pack();       
        
    }	
	
}