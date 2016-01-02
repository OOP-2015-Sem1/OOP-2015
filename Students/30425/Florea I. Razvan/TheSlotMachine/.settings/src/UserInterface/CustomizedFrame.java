package UserInterface;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import static Main.Constants.FRAME_WIDTH;
import static Main.Constants.FRAME_HEIGHT;

public class CustomizedFrame extends JFrame {

	private static final long serialVersionUID = -2596512545482309233L;

	public static TilesPanel display = new TilesPanel();
	public static ButtonsPanel controlPanel = new ButtonsPanel();

	public CustomizedFrame(String title) {

		super(title);

		setDefaultCloseOperation(CustomizedFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);

		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		add(controlPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setVisible(true);
	}

}