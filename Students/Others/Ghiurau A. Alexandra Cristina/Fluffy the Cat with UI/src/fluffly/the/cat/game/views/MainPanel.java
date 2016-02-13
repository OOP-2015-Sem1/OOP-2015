package fluffly.the.cat.game.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import fluffly.the.cat.game.Constants;
import fluffy.the.cat.models.Fluffy;

public class MainPanel extends JFrame {

	private static final long serialVersionUID = -8573649221379461824L;

	private static final int PANEL_BORDER_SIZE = 40;

	private GamePanel gamePanel;
	private ControlsPanel controlPanel;

	private Fluffy fluffy;

	public MainPanel() {
		super("Snake!");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(Constants.FRAME_HEIGHT, Constants.FRAME_WIDTH));

		gamePanel = new GamePanel();
		controlPanel = new ControlsPanel();

		
		fluffy  = new Fluffy();

		add(gamePanel, BorderLayout.WEST);
		add(new RightPanel(controlPanel), BorderLayout.CENTER);

		setFocusable(true);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}


	public JButton[][] getControlButtons() {
		return this.controlPanel.getControlButtons();
	}

	public void addActionListenerToButtons(ActionListener actionListener) {
		controlPanel.addActionListenerToButtons(actionListener);
	}

}
