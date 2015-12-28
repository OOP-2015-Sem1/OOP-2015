package UserInterface;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class CustomizedFrame extends JFrame {

	private static final long serialVersionUID = -2596512545482309233L;

	private TilesPanel display;
	private ButtonsPanel controlPanel;
	
	public CustomizedFrame(String title) {
		
		super(title);
		display = new TilesPanel();
		controlPanel = new ButtonsPanel();
		
		setDefaultCloseOperation(CustomizedFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setVisible(true);
	}

	public TilesPanel getDisplay() {
		return display;
	}

	public void setDisplay(TilesPanel display) {
		this.display = display;
	}

	public ButtonsPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(ButtonsPanel controlPanel) {
		this.controlPanel = controlPanel;
	}
	
}