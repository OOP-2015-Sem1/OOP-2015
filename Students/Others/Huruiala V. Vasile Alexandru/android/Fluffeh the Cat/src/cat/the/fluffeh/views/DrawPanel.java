package cat.the.fluffeh.views;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import cat.the.fluffeh.game.CellContent;

/**
 * 
 * @author alexh A JPanel that contains external resources (images) and
 *         interprets input to choose what to render.
 *
 */
public class DrawPanel extends JPanel {
	private static final long serialVersionUID = 2533894525174146131L;

	private static final String EMPTY_IMG_PATH = "res/empty.png";
	private static final String WALL_IMG_PATH = "res/wall.jpg";
	private static final String HATWALL_IMG_PATH = "res/hatwall.jpg";
	private static final String HAT_IMG_PATH = "res/hat.jpg";
	private static final String FLUFFEH_IMG_PATH = "res/fluffeh.png";

	private static Image emptyImg = null;
	private static Image wallImg = null;
	private static Image hatwallImg = null;
	private static Image hatImg = null;
	private static Image fluffehImg = null;
	private static boolean imagesInitialized = false;

	private int width;
	private int height;
	private CellContent content;

	public DrawPanel(int width, int height, CellContent content, LayoutManager lm) {
		super(lm);
		this.width = width;
		this.height = height;
		this.content = content;

		if (!DrawPanel.imagesInitialized) {
			DrawPanel.initImages();
			DrawPanel.imagesInitialized = true;
		}
	}

	public static void initImages() {
		try {
			DrawPanel.emptyImg = ImageIO.read(new File(EMPTY_IMG_PATH));
			DrawPanel.wallImg = ImageIO.read(new File(WALL_IMG_PATH));
			DrawPanel.hatwallImg = ImageIO.read(new File(HATWALL_IMG_PATH));
			DrawPanel.hatImg = ImageIO.read(new File(HAT_IMG_PATH));
			DrawPanel.fluffehImg = ImageIO.read(new File(FLUFFEH_IMG_PATH));
		} catch (Exception e) {
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		switch (content) {
		case FLUFFEH:
			g2d.drawImage(DrawPanel.emptyImg, 0, 0, this.width, this.height, null);
			g2d.drawImage(DrawPanel.fluffehImg, 0, 0, this.width, this.height, null);
			break;
		case EMPTY:
			g2d.drawImage(DrawPanel.emptyImg, 0, 0, this.width, this.height, null);
			break;
		case WALL:
			g2d.drawImage(DrawPanel.wallImg, 0, 0, this.width, this.height, null);
			break;
		case HATWALL:
			g2d.drawImage(DrawPanel.hatwallImg, 0, 0, this.width, this.height, null);
			break;
		case HAT:
			g2d.drawImage(DrawPanel.hatImg, 0, 0, this.width, this.height, null);
			break;

		case FOG:
		default:
			break;
		}
	}

	public void setContent(CellContent content) {
		this.content = content;
	}
}
