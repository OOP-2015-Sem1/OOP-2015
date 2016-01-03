package game;

import java.awt.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class UnoPanelWindow extends JWindow {

	private Image screen;

	public UnoPanelWindow() {
		super();

		MediaTracker t = new MediaTracker(this);
		Toolkit tk = Toolkit.getDefaultToolkit();

		screen = tk.getImage("resources/splash.png");
		t.addImage(screen, 0);
		try {
			t.waitForAll();
		} catch (Exception e) {
			System.out.println("Ooops! Loading splash image");
			System.exit(1);
		}

		Dimension screenSize = tk.getScreenSize();
		setSize(new Dimension(screen.getWidth(this), screen.getHeight(this)));
		setLocation((screenSize.width - 320) / 2, (screenSize.height - 100) / 2);

		setVisible(true);
	}

	@SuppressWarnings("unused")
	private class SplashPane extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			g.drawImage(screen, 0, 0, this);
		}
	}
}