package UserInterface;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class CustomizedFrame {

	private TilesPanel display;
	private ButtonsPanel controlPanel;
	private JFrame mainFrame = new JFrame();
	
	public CustomizedFrame(String title) {
		
		mainFrame.setTitle(title);
		display = new TilesPanel();
		controlPanel = new ButtonsPanel();
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(700, 500);
		
		mainFrame.setLayout(new BorderLayout());
		mainFrame.add(display.getTilesPanel(), BorderLayout.CENTER);
		mainFrame.add(controlPanel.getButtonsPanel(), BorderLayout.SOUTH);

		mainFrame.setLocationRelativeTo(null);
		mainFrame.setVisible(true);
	}
	
	public JFrame getMainFrame() {
		return mainFrame;
	}


	public void setMainFrame(JFrame mainFrame) {
		this.mainFrame = mainFrame;
	}
	
	public TilesPanel getDisplay() {
		return display;
	}

	public void setDisplay(TilesPanel tilesPanel) {
		this.display = tilesPanel;
	}

	public ButtonsPanel getControlPanel() {
		return controlPanel;
	}

	public void setControlPanel(ButtonsPanel controlPanel) {
		this.controlPanel = controlPanel;
	}
}