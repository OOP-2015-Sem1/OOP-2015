package theseal.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import theseal.game.Game;
import theseal.game.GameEvent;
import theseal.game.GameState;
import theseal.model.Bear;
import theseal.model.Hole;
import theseal.model.Seal;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 4256765294602106273L;

	private static final String BEAR_PATH = "bear.png";
	private static final String SEAL_PATH = "seal.png";
	private Image bearImage;
	private Image sealImage;

	private int width;
	private int height;

	private GameState gameState;
	private ArrayList<String> gameEvents;

	private EntityPanel[][] entityPanels;

	public GamePanel(Game game, int width, int height) {
		super.setLayout(new GridLayout(game.getRows(), game.getCols()));
		this.entityPanels = new EntityPanel[game.getRows()][game.getCols()];

		for (int row = 0; row < game.getRows(); row++) {
			for (int col = 0; col < game.getCols(); col++) {
				this.entityPanels[row][col] = new EntityPanel(game);
				add(this.entityPanels[row][col]);
			}
		}

		this.updateEntityPanels(game);

		try {
			this.bearImage = ImageIO.read(new File(BEAR_PATH));
			this.sealImage = ImageIO.read(new File(SEAL_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.width = width;
		this.height = height;
		this.gameState = GameState.StillRunning;
		setBackground(Color.BLUE);
	}

	public void setWillDraw(boolean willDraw) {
		for (int row = 0; row < this.entityPanels.length; row++) {
			for (int col = 0; col < this.entityPanels[0].length; col++) {
				this.entityPanels[row][col].setWillDraw(willDraw);
			}
		}
	}

	public void resetEntityPanelsDrawables() {
		for (int row = 0; row < this.entityPanels.length; row++) {
			for (int col = 0; col < this.entityPanels[0].length; col++) {
				this.entityPanels[row][col].resetDrawables();
			}
		}
	}

	public void updateEntityPanels(Game game) {
		this.resetEntityPanelsDrawables();
		for (Hole h : game.getHoles()) {
			Point p = h.getPosition();
			this.entityPanels[p.x][p.y].setHole(h);
		}
		for (Seal s : game.getSeals()) {
			Point p = s.getPosition();
			if (s.isSubmerged) {
				this.entityPanels[p.x][p.y].addSubmergedDrawable(s);
			} else {
				this.entityPanels[p.x][p.y].addSurfacedDrawable(s);
			}
		}

		Bear b = game.getBear();
		Point p = b.getPosition();
		this.entityPanels[p.x][p.y].addSurfacedDrawable(b);

		for (GameEvent ge : game.getGameEvents()) {
			Point pos = ge.getPosition();
			this.entityPanels[pos.x][pos.y].addEventMessage(ge.getMessage());
		}
	}

	public void setState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		switch (this.gameState) {
		case StillRunning:
			this.setWillDraw(true);
			break;
		case SealsDead:
			this.setWillDraw(false);
			g2d.drawImage(this.bearImage, 0, 0, this.width, this.height, null);
			g2d.setColor(Color.RED);
			setFont(new Font(Font.SERIF, Font.PLAIN, 48));
			g2d.drawString(Game.SEALS_DIED, 10, 50);
			break;
		case SealsLive:
			this.setWillDraw(false);
			g2d.drawImage(this.sealImage, 0, 0, this.width, this.height, null);
			g2d.setColor(Color.RED);
			setFont(new Font(Font.SERIF, Font.PLAIN, 48));
			g2d.drawString(Game.SEALS_LIVED, 10, 50);
			break;
		}
	}
}
