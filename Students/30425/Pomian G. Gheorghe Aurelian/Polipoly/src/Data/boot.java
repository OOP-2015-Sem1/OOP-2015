package Data;

import static helpers.Artist.BeginSession;

import org.lwjgl.opengl.Display;

import helpers.StateManager;

public class boot {

	public boot() {

		BeginSession();

		while (!Display.isCloseRequested()) {
			
			StateManager.update();

			Display.update();
			Display.sync(60);
		}
		Display.destroy();
	}

	public static void main(String[] args) {
		new boot();

	}

}
