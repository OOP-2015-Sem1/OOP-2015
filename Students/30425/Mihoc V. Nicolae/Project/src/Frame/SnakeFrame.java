package Frame;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Clock.Time;
import Constants.Direction;
import Game_Characters.Fruit;
import Game_Characters.Snake;
import Panels.BoardPanel;
import Panels.SidePanel;
import Snake_Game.Game_Logic;

public class SnakeFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static long FRAME_TIME = 6000 / 50;

	private static final int MAX_DIRECTIONS = 3;

	private BoardPanel board;

	private SidePanel side;

	private Time logicTimer;

	private boolean isNewGame;

	private boolean isGameOver;

	private boolean isPaused;

	private LinkedList<Direction> directions;

	private int applesEaten;

	private boolean canGoThroughWalls;

	private Snake snake;
	private Fruit apple;
	private Game_Logic item;

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

	public SnakeFrame() {
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
		
		setSnake(new Snake());
		apple=new Fruit();

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
						item.resetGame();
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
			item.resetGame();
		}
	}

	public class DificMenu implements ActionListener {
		public void actionPerformed(ActionEvent easy) {
			setFRAME_TIME(10000 / 50);
		}
	}

	public class Dific1Menu implements ActionListener {
		public void actionPerformed(ActionEvent medium) {
			setFRAME_TIME(6000 / 50);
		}
	}

	public class Dific2Menu implements ActionListener {
		public void actionPerformed(ActionEvent hard) {
			setFRAME_TIME(1 / 50);
		}
	}

	public class WallsEvent implements ActionListener {
		public void actionPerformed(ActionEvent go) {
			setCanGoThroughWalls(true);
		}
	}

	public class WallsEvent2 implements ActionListener {
		public void actionPerformed(ActionEvent no) {
			setCanGoThroughWalls(false);
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

	public boolean isCanGoThroughWalls() {
		return canGoThroughWalls;
	}

	public void setCanGoThroughWalls(boolean canGoThroughWalls) {
		this.canGoThroughWalls = canGoThroughWalls;
	}

	public static long getFRAME_TIME() {
		return FRAME_TIME;
	}

	public static void setFRAME_TIME(long fRAME_TIME) {
		FRAME_TIME = fRAME_TIME;
	}

	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public Fruit getApple() {
		return apple;
	}

	public void setApple(Fruit apple) {
		this.apple = apple;
	}

}