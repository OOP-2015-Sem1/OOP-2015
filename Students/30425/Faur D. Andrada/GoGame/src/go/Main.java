package go;
//import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class Main {
	
	 public static final String TITLE = "Simple Go";
	 private StartDialog startDialog;
	 
	/*private static void build(Container c) {
        c.add(new TopPanel(new Game(19)));
    }
    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        build(frame.getContentPane());
        frame.setPreferredSize(new Dimension(520, 600));
        frame.pack();
        frame.setVisible(true);
    }
    */
    public static void main(String[] args) {
    	new Main().init();
        
    	
    	/*
    	 * javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    	 */
        
        
        
    }
    
    private void init() {
        startDialog = new StartDialog(this);
        startDialog.pack();
        startDialog.setLocationByPlatform(true);
        startDialog.setVisible(true);
    }
	public void startGame(int size) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
        f.setTitle(TITLE);

        f.add(new TopPanel(new Game(size)));

        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                startDialog.setVisible(true);
            }
        });
        f.pack();
        f.setResizable(false);
        f.setLocationByPlatform(true);
        f.setVisible(true);
	}

	
}
