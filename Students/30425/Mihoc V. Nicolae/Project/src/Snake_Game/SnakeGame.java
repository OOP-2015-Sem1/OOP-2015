package Snake_Game;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SnakeGame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static long FRAME_TIME = 6000 / 50;

	private static final int MIN_SNAKE_LENGTH = 3;

	private static final int MAX_DIRECTIONS = 3;

	private BoardPanel board;

	private SidePanel side;

	private Random random;

	private Time logicTimer;

	private boolean isNewGame;

	private boolean isGameOver;

	private boolean isPaused;

	private LinkedList<Point> snake;

	private LinkedList<Direction> directions;

	private int applesEaten;

	private boolean canGoThroughWalls;

	private JMenuBar menubar;

	private JMenuItem exit;
	private JMenuItem newGame;
	private JMenuItem Easy;
	private JMenuItem Medium;
	private JMenuItem Hard;
	private JMenuItem YES;
	private JMenuItem NO;

	private JMenu MainMenu;
	private JMenu Difficulty;
	private JMenu GoThroughWalls;

	EventMenu exitgame = new EventMenu();
	EventsMenu startgame = new EventsMenu();
	DificMenu easygame = new DificMenu();
	Dific1Menu mediumgame = new Dific1Menu();
	Dific2Menu hardgame = new Dific2Menu();
	WallsEvent YESopt = new WallsEvent();
	WallsEvent2 NOopt = new WallsEvent2();

	private SnakeGame() {
		super("Snake Game");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		menubar = new JMenuBar();
		MainMenu = new JMenu("MainMenu");
		Difficulty = new JMenu("Difficulty");
		GoThroughWalls = new JMenu("Go Through Walls");

		menubar.add(MainMenu);
		menubar.add(Difficulty);
		menubar.add(GoThroughWalls);

		this.board = new BoardPanel(this);
		this.side = new SidePanel(this);

		newGame = new JMenuItem("New Game");
		MainMenu.add(newGame);

		exit = new JMenuItem("Exit");
		MainMenu.add(exit);

		Easy = new JMenuItem("Easy");
		Difficulty.add(Easy);

		Medium = new JMenuItem("Medium");
		Difficulty.add(Medium);

		Hard = new JMenuItem("Hard");
		Difficulty.add(Hard);

		YES = new JMenuItem("YES");
		GoThroughWalls.add(YES);

		NO = new JMenuItem("NO");
		GoThroughWalls.add(NO);

		exit.addActionListener(exitgame);
		newGame.addActionListener(startgame);
		Easy.addActionListener(easygame);
		Medium.addActionListener(mediumgame);
		Hard.addActionListener(hardgame);
		YES.addActionListener(YESopt);
		NO.addActionListener(NOopt);

		setFocusable(true);

		add(menubar, BorderLayout.NORTH);
		add(board, BorderLayout.CENTER);
		add(side, BorderLayout.EAST);

		addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {

				case KeyEvent.VK_UP:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.DOWN && last != Direction.UP) {
								directions.addLast(Direction.UP);
							}
						}
					}
					break;

				case KeyEvent.VK_DOWN:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.UP && last != Direction.DOWN) {
								directions.addLast(Direction.DOWN);
							}
						}
					}
					break;

				case KeyEvent.VK_LEFT:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.RIGHT && last != Direction.LEFT) {
								directions.addLast(Direction.LEFT);
							}
						}
					}
					break;

				case KeyEvent.VK_RIGHT:
					if (!isPaused && !isGameOver) {
						if (directions.size() < MAX_DIRECTIONS) {
							Direction last = directions.peekLast();
							if (last != Direction.LEFT && last != Direction.RIGHT) {
								directions.addLast(Direction.RIGHT);
							}
						}
					}
					break;

				case KeyEvent.VK_P:
					if (!isGameOver) {
						isPaused = !isPaused;
						logicTimer.setPaused(isPaused);
					}
					break;

				case KeyEvent.VK_ENTER:
					if (isGameOver) {
						resetGame();
					}
					break;
				}
			}

		});

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public class EventMenu implements ActionListener {
		public void actionPerformed(ActionEvent exit) {

			System.exit(0);
		}
	}

	public class EventsMenu implements ActionListener {
		public void actionPerformed(ActionEvent start) {
			resetGame();
		}
	}

	public class DificMenu implements ActionListener {
		public void actionPerformed(ActionEvent easy) {
			FRAME_TIME = 10000 / 50;
		}
	}

	public class Dific1Menu implements ActionListener {
		public void actionPerformed(ActionEvent medium) {
			FRAME_TIME = 6000 / 50;
		}
	}

	public class Dific2Menu implements ActionListener {
		public void actionPerformed(ActionEvent hard) {
			FRAME_TIME = 1 / 50;
		}
	}

	public class WallsEvent implements ActionListener {
		public void actionPerformed(ActionEvent go) {
			canGoThroughWalls = true;
		}
	}

	public class WallsEvent2 implements ActionListener {
		public void actionPerformed(ActionEvent no) {
			canGoThroughWalls = false;
		}
	}

	public void startGame() {

		this.random = new Random();
		this.snake = new LinkedList<>();
		this.directions = new LinkedList<>();
		this.logicTimer = new Time(9.0f);
		this.isNewGame = true;

		logicTimer.setPaused(true);

		while (true) {
			long start = System.nanoTime();
			logicTimer.update();

			if (logicTimer.hasElapsedCycle()) {
				updateGame();
			}

			board.repaint();
			side.repaint();

			long delta = (System.nanoTime() - start) / 1000000L;
			if (delta < FRAME_TIME) {
				try {
					Thread.sleep(FRAME_TIME - delta);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void updateGame() {

		TileType collision = updateSnake();

		if (collision == TileType.Apple) {
			applesEaten++;
			spawnFruit();
		} else if (collision == TileType.SnakeBody) {
			isGameOver = true;
			logicTimer.setPaused(true);
		}
	}

	private TileType updateSnake() {

		Direction direction = directions.peekFirst();
		Point head = new Point(snake.peekFirst());
		switch (direction) {
		case UP:
			head.y--;
			break;

		case DOWN:
			head.y++;
			break;

		case LEFT:
			head.x--;
			break;

		case RIGHT:
			head.x++;
			break;
		}
		if (canGoThroughWalls == false) {
			if (head.x < 0 || head.x >= BoardPanel.ROW || head.y < 0 || head.y >= BoardPanel.COL) {
				return TileType.SnakeBody;
			}
		} else {
			if (head.x < 0) {
				head.x = BoardPanel.ROW - 1;
			}
			if (head.y < 0) {
				head.y = BoardPanel.COL - 1;
			}
			if (head.x > BoardPanel.ROW - 1) {
				head.x = 0;
			}
			if (head.y > BoardPanel.COL - 1) {
				head.y = 0;
			}
		}

		TileType old = board.getTile(head.x, head.y);
		if (old != TileType.Apple && snake.size() > MIN_SNAKE_LENGTH) {
			Point tail = snake.removeLast();
			board.setTile(tail, null);
			old = board.getTile(head.x, head.y);
		}

		if (old != TileType.SnakeBody) {
			board.setTile(snake.peekFirst(), TileType.SnakeBody);
			snake.push(head);
			board.setTile(head, TileType.SnakeHead);
			if (directions.size() > 1) {
				directions.poll();
			}
		}

		return old;
	}

	private void resetGame() {

		this.applesEaten = 0;

		this.isNewGame = false;
		this.isGameOver = false;

		Point head = new Point(BoardPanel.COL / 2, BoardPanel.ROW / 2);

		snake.clear();
		snake.add(head);

		board.clearBoard();
		board.setTile(head, TileType.SnakeHead);

		directions.clear();
		directions.add(Direction.UP);

		logicTimer.reset();

		spawnFruit();
	}

	private void spawnFruit() {

		int index = random.nextInt(BoardPanel.COL * BoardPanel.ROW - snake.size());

		int freeFound = -1;
		for (int x = 0; x < BoardPanel.COL; x++) {
			for (int y = 0; y < BoardPanel.ROW; y++) {
				TileType type = board.getTile(x, y);
				if (type == null || type == TileType.Apple) {
					if (++freeFound == index) {
						board.setTile(x, y, TileType.Apple);
						break;
					}
				}
			}
		}
	}
	public boolean isNewGame() {
		return isNewGame;
	}

	public boolean isGameOver() {
		return isGameOver;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public int getApplesEaten() {
		return applesEaten;
	}

	public Direction getDirection() {
		return directions.peek();
	}

	public static void main(String[] args) {
		SnakeGame snake = new SnakeGame();
		snake.startGame();
	}

}