package gui;

public class SnakeController {

	public GameFrame gameFrame;

	public void runControllers() {

		gameFrame.addActionListenerToButtons(new SnakeMouseControllsClickListener());
		// gameFrame.addKeyListener(new Controller());
	}
}
