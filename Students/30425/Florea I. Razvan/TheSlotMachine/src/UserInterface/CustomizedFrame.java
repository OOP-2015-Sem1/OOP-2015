package UserInterface;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class CustomizedFrame extends JFrame {

	private static final long serialVersionUID = -2596512545482309233L;

	public static TilesPanel display = new TilesPanel();
	public static ButtonsPanel controlPanel = new ButtonsPanel();
	
	public CustomizedFrame(String title) {
		
		super(title);
		
		setDefaultCloseOperation(CustomizedFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}