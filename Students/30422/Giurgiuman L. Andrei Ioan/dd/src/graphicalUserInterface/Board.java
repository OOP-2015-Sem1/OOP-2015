package graphicalUserInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JPanel;
import javax.swing.Timer;

import sprites.Baddie;
import sprites.ClearPowerUp;
import sprites.Hero;
import sprites.LifePowerUp;
import static graphicalUserInterface.Commons.BOARD_HEIGHT;
import static graphicalUserInterface.Commons.BOARD_WIDTH;
import static graphicalUserInterface.Commons.DELAY;

public class Board extends JPanel implements ActionListener {

	private static final long serialVersionUID = 8804900687433935933L;
	private Timer timer;
	private Hero hero;
	private LifePowerUp lifePowerUp;
	private ClearPowerUp clearPowerUp;
	private ArrayList<Baddie> baddies;
	private static boolean gameRunning = false;
	private static final int BADDIES_ADD_RATE = 200;// milliseconds
	private static final int LIFE_ADD_RATE = 4000;// milliseconds
	private static final int CLEAR_ADD_RATE = 1500;// milliseconds
	private boolean isChecking = false;
	private final int HERO_INITIAL_X = 500;
	private final int HERO_INITIAL_Y = 705;
	private int seconds = 0;

	public Board()

	{
		initializeBoard();
		spawnBaddies();
		spawnLifePowerUp();
		spawnClearPowerUp();
	}

	private void initializeBoard() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		gameRunning = true;

		setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));

		hero = new Hero(HERO_INITIAL_X, HERO_INITIAL_Y);

		initializeBaddies();
		computeLastedSeconds();
		timer = new Timer(DELAY, this);
		timer.start();

	}

	public static boolean getGameStatus() {
		return gameRunning;
	}

	private void spawnBaddies() {
		Runnable runnable = new Runnable() {
			public void run() {
				if (!isChecking) {

					addBaddies();
				}
			}
		};
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
		schedule.scheduleAtFixedRate(runnable, 0, BADDIES_ADD_RATE, TimeUnit.MILLISECONDS);

	}

	private void computeLastedSeconds() {// counts seconds
		Runnable runnable = new Runnable() {
			public void run() {
				seconds += 1;
			}
		};
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
		schedule.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.MILLISECONDS);

	}

	private void spawnLifePowerUp() {
		Runnable runnable = new Runnable() {
			public void run() {
				Random r = new Random();
				lifePowerUp = new LifePowerUp(r.nextInt(BOARD_WIDTH - 50), r.nextInt(BOARD_HEIGHT - 50));
			}
		};
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
		schedule.scheduleAtFixedRate(runnable, 0, LIFE_ADD_RATE, TimeUnit.MILLISECONDS);

	}

	private void spawnClearPowerUp() {
		Runnable runnable = new Runnable() {
			public void run() {
				Random r = new Random();
				clearPowerUp = new ClearPowerUp(r.nextInt(BOARD_WIDTH - 60), r.nextInt(BOARD_HEIGHT - 60));
			}
		};
		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
		schedule.scheduleAtFixedRate(runnable, 0, CLEAR_ADD_RATE, TimeUnit.MILLISECONDS);
	}

	public void initializeBaddies() {
		baddies = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Random r = new Random();
			int side = r.nextInt(3);
			if (side == 0) {

				baddies.add(new Baddie(r.nextInt(BOARD_HEIGHT / 3), r.nextInt(BOARD_WIDTH), side));
			}
			if (side == 1) {

				baddies.add(new Baddie(r.nextInt(BOARD_HEIGHT), r.nextInt(BOARD_WIDTH / 2), side));
			}
			if (side == 2) {

				baddies.add(new Baddie(BOARD_WIDTH - r.nextInt(BOARD_WIDTH / 3) - 50, r.nextInt(BOARD_WIDTH), side));
			}
		}

	}

	private void addBaddies() {
		Random r = new Random();
		int side = r.nextInt(3);
		if (side == 0) {
			baddies.add(new Baddie(0, r.nextInt(BOARD_WIDTH - 320), side));
		}
		if (side == 1) {
			baddies.add(new Baddie(r.nextInt(BOARD_HEIGHT), 0, side));
		}
		if (side == 2) {
			baddies.add(new Baddie(BOARD_HEIGHT + 250, r.nextInt(BOARD_WIDTH - 320), side));
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (gameRunning) {
			drawLifePoints(g);
			doDrawing(g);
			drawControls(g);

		} else {

			drawGameOver(g);
			drawExitScreen(g);
		}
		Toolkit.getDefaultToolkit().sync();
	}

	private void drawExitScreen(Graphics g) {

		g.setColor(new Color(0, 32, 48));
		g.fillRect(190, BOARD_WIDTH / 2 - 30, BOARD_HEIGHT - 100, 50);
		g.setColor(Color.white);
		g.drawRect(190, BOARD_WIDTH / 2 - 30, BOARD_HEIGHT - 100, 50);

		String s = "Press E to exit.";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = this.getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(s, (BOARD_HEIGHT - metr.stringWidth(s)) / 2 + 150, BOARD_WIDTH / 2);
	}

	private void drawLifePoints(Graphics g) {

		String s;
		Font myfont = new Font("Helvetica", Font.BOLD, 14);
		g.setFont(myfont);
		g.setColor(new Color(96, 128, 255));
		s = "Life: " + hero.getLife();
		g.drawString(s, BOARD_WIDTH / 2 + 440, BOARD_HEIGHT);
	}

	private void drawControls(Graphics g) {

		String s;
		Font myfont = new Font("Helvetica", Font.BOLD, 14);
		g.setFont(myfont);
		g.setColor(new Color(98, 150, 160));
		s = "Controls:Arrow keys";
		g.drawString(s, BOARD_WIDTH / 12 - 80, BOARD_HEIGHT);
	}

	private void doDrawing(Graphics g) {

		drawLifePoints(g);
		if (hero.isVisible()) {
			g.drawImage(hero.getImage(), hero.getX(), hero.getY(), this);
		}
		for (int i = 0; i < baddies.size(); i++) {
			if (baddies.get(i).isVisible()) {
				g.drawImage(baddies.get(i).getImage(), baddies.get(i).getX(), baddies.get(i).getY(), this);

			} else {
				baddies.remove(i);
			}

		}
		g.drawImage(lifePowerUp.getImage(), lifePowerUp.getX(), lifePowerUp.getY(), this);
		g.drawImage(clearPowerUp.getImage(), clearPowerUp.getX(), clearPowerUp.getY(), this);

	}

	private void drawGameOver(Graphics g) {

		String msg = "Game Over!";
		Font small = new Font("Helvetica", Font.BOLD, 18);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg + "  You survived for : " + seconds + " seconds", (BOARD_WIDTH - fm.stringWidth(msg)) / 2 - 100,
				BOARD_HEIGHT / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();

		updateHero();
		updateBaddie();

		checkCollisions();

		repaint();
	}

	private void updateHero() {
		if (hero.isVisible()) {
			hero.moveHero();
		}

	}

	private void updateBaddie() {

		for (int i = 0; i < baddies.size(); i++) {

			Baddie b = baddies.get(i);
			if (b.isVisible()) {
				b.move();
			} else {
				baddies.remove(i);
			}
		}
	}

	public void checkCollisions() {

		Rectangle r3 = hero.getBounds();

		isChecking = true;
		ArrayList<Baddie> baddiesCopy = new ArrayList<Baddie>();
		for (int j = 0; j < baddies.size(); j++) {
			baddiesCopy.add(new Baddie(baddies.get(j).getX(), baddies.get(j).getY(), baddies.get(j).getSide()));
		}
		Iterator<Baddie> i = baddiesCopy.iterator();
		int pos = 0;
		while (i.hasNext()) {
			Baddie baddie = i.next();
			Rectangle r2 = baddie.getBounds();
			if (r3.intersects(r2)) {

				baddies.remove(pos);
				pos--;

				hero.setLife(hero.getLife() - 10);
			}
			pos++;
		}

		isChecking = false;
		Rectangle r4 = lifePowerUp.getBounds();
		if (r3.intersects(r4)) {
			hero.setLife(hero.getLife() + 10);
			Random r = new Random();
			lifePowerUp = new LifePowerUp(r.nextInt(BOARD_WIDTH), r.nextInt(BOARD_HEIGHT));

		}
		Rectangle r5 = clearPowerUp.getBounds();
		if (r3.intersects(r5)) {
			baddies = new ArrayList<Baddie>();
			Random r = new Random();
			clearPowerUp = new ClearPowerUp(r.nextInt(BOARD_WIDTH), r.nextInt(BOARD_HEIGHT));
		}
		if (hero.getLife() == 0) {
			hero.setVisible(false);
			for (Baddie baddie : baddies) {

				baddie.setVisible(false);

			}
			gameRunning = false;
		}
	}

	private void inGame() {
		if (!gameRunning) {
			timer.stop();

		}
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			int key = e.getKeyCode();
			hero.keyReleased(e);

			if ((!gameRunning) && ((key == 'e') || (key == 'E'))) {

				System.exit(0);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			hero.keyPressed(e);

		}
	}
}
