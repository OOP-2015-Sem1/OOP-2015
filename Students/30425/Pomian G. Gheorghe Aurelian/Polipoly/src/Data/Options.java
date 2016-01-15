package Data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.QuickLoad;

import org.newdawn.slick.opengl.Texture;

import UI.UI;

public class Options {
	
	private Texture background;
	private UI menuUI;
	
	public Options() {
		background = QuickLoad("MainBackground");
		menuUI =new UI();
	}
	
	public void update() {
		DrawQuadTex(background, 0, 0, 1024, 512);
		menuUI.draw();
	}

}
