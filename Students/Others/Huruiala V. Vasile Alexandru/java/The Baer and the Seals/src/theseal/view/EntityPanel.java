package theseal.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JPanel;

import theseal.game.Game;
import theseal.model.Drawable;
import theseal.model.Hole;
import theseal.model.Ice;

public class EntityPanel extends JPanel {
	private static final long serialVersionUID = -3814164961827198118L;

	private ArrayList<Drawable> submergedDrawables;
	private ArrayList<Drawable> surfacedDrawables;
	private Hole hole;

	private ArrayList<String> eventMessages;

	private boolean willDraw;

	private int width;
	private int height;

	public EntityPanel(Game game) {
		super();
		this.submergedDrawables = new ArrayList<>();
		this.surfacedDrawables = new ArrayList<>();
		this.hole = null;

		this.willDraw = true;

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

		this.width = (int) (d.getWidth() / game.getCols());
		this.height = (int) (d.getHeight() / game.getRows());

		this.eventMessages = new ArrayList<>();

		setFont(new Font(Font.SERIF, Font.PLAIN, 12));
	}

	public void addEventMessage(String eventMessage) {
		this.eventMessages.add(eventMessage);
	}

	public void resetDrawables() {
		this.submergedDrawables.clear();
		this.surfacedDrawables.clear();
		this.hole = null;
	}

	public void addSubmergedDrawable(Drawable drawable) {
		this.submergedDrawables.add(drawable);
	}

	public void addSurfacedDrawable(Drawable drawable) {
		this.surfacedDrawables.add(drawable);
	}

	public void setHole(Hole hole) {
		this.hole = hole;
	}

	public void setWillDraw(Boolean willDraw) {
		this.willDraw = willDraw;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// Submerged entities
		if (this.willDraw) {
			for (Drawable submerged : this.submergedDrawables) {
				g2d.drawImage(submerged.getImage(), 0, 0, width, height, null);
			}
			// Draws Ice or a hole depending on the case
			if (hole == null) {
				g2d.drawImage(Ice.getInstance().getImage(), 0, 0, width, height, null);
			} else {
				g2d.drawImage(hole.getImage(), 0, 0, width, height, null);
			}
			// Surfaced entities
			for (Drawable surfaced : this.surfacedDrawables) {
				g2d.drawImage(surfaced.getImage(), 0, 0, width, height, null);
			}

			g2d.setColor(Color.RED);

			int index = 0;
			for (String s : this.eventMessages) {
				g2d.drawString(s, 10, 10 + 16 * index);
				index++;
			}
		}
	}
}
