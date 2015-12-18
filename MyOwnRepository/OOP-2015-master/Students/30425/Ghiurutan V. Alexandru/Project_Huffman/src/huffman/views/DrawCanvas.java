package huffman.views;

import java.awt.*;

public class DrawCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	private Image imageBuffer;
	private Graphics imageGraphic;
	private Graphics visibleGraphic;

	@Override
	public void addNotify() {
		super.addNotify();
		this.setBackground(Color.WHITE);
		this.imageBuffer = this.createImage(1024, 768);
		this.imageGraphic = this.imageBuffer.getGraphics();
		this.imageGraphic.setPaintMode();
		this.imageGraphic.setColor(Color.BLACK);
		this.visibleGraphic = this.getGraphics();
		this.clear(false);
	}

	@Override
	public void print(Graphics graphics) {
		super.print(graphics);
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.drawImage(this.imageBuffer, 0, 0, null);
	}

	public void setFontSize(int n) {
		this.imageGraphic.setFont(new Font("Arial", 0, n));
	}

	@Override
	public void update(Graphics graphics) {
		this.paint(graphics);
	}

	public Dimension getPrefferedSize() {
		return new Dimension(640, 480);
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(1024, 768);
	}

	public void display() {
		Dimension dimension = this.getSize();
		this.display(0, 0, dimension.width, dimension.height);
	}

	public void display(int x, int y, int width, int height) {
		Shape shape = this.visibleGraphic.getClip();
		this.visibleGraphic.setClip(x, y, width + 1, height + 1);
		this.paint(visibleGraphic);
		this.visibleGraphic.setClip(shape);
		this.repaint();
	}

	public Graphics getBackingGraphic() {
		return this.imageGraphic;
	}

	public void clear() {
		this.clear(true);
	}

	public void clear(boolean isSet) {
		Color color = this.imageGraphic.getColor();
		this.imageGraphic.setColor(Color.WHITE);
		this.imageGraphic.fillRect(0, 0, 1024, 768);
		this.imageGraphic.setColor(color);
		if (isSet) {
			this.display();
		}
	}

	@Override
	public void setForeground(Color color) {
		if (this.imageGraphic != null) {
			this.imageGraphic.setColor(color);
		}
	}

	public void setColor(Color color) {
		if (this.imageGraphic != null) {
			this.imageGraphic.setColor(color);
		}
	}

	public void drawLine(int x1, int y1, int x2, int y2) {
		this.drawLine(x1, y1, x2, y2, true);
	}

	public void drawLine(int x1, int y1, int x2, int y2, boolean isSet) {
		this.imageGraphic.drawLine(x1, y1, x2, y2);
		if (isSet) {
			this.display(Math.min(x1, x2), Math.min(y1, y2), Math.abs(x2 - x1) + 1, Math.abs(y2 - y1) + 1);
		}
	}

	public void drawRect(int x1, int y1, int width, int height) {
		this.drawRect(x1, y1, width, height, true);
	}

	public void drawRect(int x1, int y1, int width, int height, boolean isSet) {
		this.imageGraphic.drawRect(x1, y1, width, height);
		if (isSet) {
			this.display(x1, y1, width, height);
		}
	}

	public void fillRect(int x1, int y1, int width, int height) {
		this.fillRect(x1, y1, width, height, true);
	}

	public void fillRect(int x1, int y1, int width, int height, boolean isSet) {
		this.imageGraphic.fillRect(x1, y1, width + 1, height + 1);
		if (isSet) {
			this.display(x1, y1, width, height);
		}
	}

	public void clearRect(int x1, int y1, int width, int height) {
		this.clearRect(x1, y1, width, height, true);
	}

	public void clearRect(int x1, int y1, int width, int height, boolean isSet) {
		Color color = this.imageGraphic.getColor();
		this.imageGraphic.setColor(Color.WHITE);
		this.imageGraphic.fillRect(x1, y1, width, height);
		this.imageGraphic.setColor(color);
		if (isSet) {
			this.display(x1, y1, width, height);
		}
	}

	public void drawString(String text, int x1, int y1) {
		this.drawString(text, x1, y1, true);
	}

	public void drawString(String text, int x1, int y1, boolean isSet) {
		this.imageGraphic.drawString(text, x1, y1);
		if (isSet) {
			FontMetrics fontMetrics = this.imageGraphic.getFontMetrics();
			this.display(x1, y1 - fontMetrics.getMaxAscent(),
					fontMetrics.stringWidth(text) + fontMetrics.getMaxAdvance(),
					fontMetrics.getMaxAscent() + fontMetrics.getMaxDescent());
		}
	}

	public void drawOval(int x1, int y1, int width, int height) {
		this.drawOval(x1, y1, width, height, true);
	}

	public void drawOval(int x1, int y1, int width, int height, boolean isSet) {
		this.imageGraphic.drawOval(x1, y1, width, height);
		if (isSet) {
			this.display(x1, y1, width, height);
		}
	}

	public void fillOval(int x1, int y1, int width, int height) {
		this.fillOval(x1, y1, width, height, true);
	}

	public void fillOval(int x1, int y1, int width, int height, boolean isSet) {
		this.imageGraphic.fillOval(x1, y1, width, height);
		this.imageGraphic.drawOval(x1, y1, width, height);
		if (isSet) {
			this.display(x1, y1, width, height);
		}
	}
}
