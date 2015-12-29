
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import GameShapes.Ball;
import GameShapes.Brick;
import GameShapes.Paddle;
import GameShapes.ScoreTable;

public class Screen extends JPanel implements Runnable {

	private int MENU_WIDTH = 1260;
	private int MENU_HEIGHT = 850;
	private volatile boolean running = false;
	private Thread thread;
	private Container gameScreen;
	private Ball ball;
	protected Paddle paddle;
	public Graphics g;
	private Brick[][] brick = new Brick[1000][1000];
	private Map<Integer, Color> colorMap = new HashMap<Integer, Color>();
	private ScoreTable scoretable;
	JFrame menu = new JFrame("Breakout");
	private JButton NewGameButton = new JButton("New Game");
	private JButton ChangeColorsButton = new JButton("Change Theme");
	private JButton PauseGameButton = new JButton("Pause Game");
	private JButton ResumeGameButton = new JButton("Resume Game");

	public Screen() {
		createGameComponents();
		colorMap.put(55, Color.red);
		colorMap.put(75, Color.red);
		colorMap.put(95, Color.orange);
		colorMap.put(115, Color.orange);
		colorMap.put(135, Color.yellow);
		colorMap.put(155, Color.yellow);
		colorMap.put(175, Color.green);
		colorMap.put(195, Color.green);
		colorMap.put(215, Color.cyan);
		colorMap.put(235, Color.cyan);
		start();
	}

	public void start() {

		thread = new Thread(this);
		thread.start();
	}

	public void run() {
		running = true;
		ball.setGameOver(false);
		while (running == true) {

			if (ball.getGameOver() != false) {
				running = false;
				popupWindow("You Lost", Color.red);
			}
			if (ball.getBrickCount() == 80) {
				running = false;
				popupWindow("You won", Color.green);

			}
			repaint();
			update();
		}
		NewGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (running == false) {
					running = true;
					ball = new Ball(200, 295, Color.gray, menu);
					createBricks();
					start();
					start();
				}
			}

		});
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		ball.Paint(g);
		for (int j = 55; j <= 235; j += 20) {
			setBricks(g, j, colorMap.get(j));
		}
		paddle.Paint(g);
		scoretable.paintScore(g, Color.darkGray, ball);
	}

	private void setBricks(Graphics g, int j, Color color) {
		for (int i = 15; i <= 785; i += 110) {
			if (!brick[i][j].getVisibility()) {
				brick[i][j] = new Brick(i, j);
				brick[i][j].Paint(g, color, i, j, ball);
			}
		}
	}

	private void update() {

		ball.move(paddle, brick);

	}

	private void createBricks() {
		for (int i = 0; i < 915; i++)
			for (int j = 0; j < 915; j++) {
				brick[i][j] = new Brick(i, j);
				brick[i][j].setVisiblity(false);
			}
	}

	private void createGameComponents() {

		setBackground(Color.white);
		JFrame menu = new JFrame("Breakout");
		GameCommands game = new GameCommands();
		menu.setSize(MENU_WIDTH, MENU_HEIGHT);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ball = new Ball(200, 295, Color.gray, menu);
		paddle = new Paddle(400, 780, menu);
		scoretable = new ScoreTable();

		NewGameButton.setBackground(Color.GREEN);
		NewGameButton.setIcon(new ImageIcon("E:\\UTCN\\JAVA\\workspace\\TestDemo3\\play.png"));
		ChangeColorsButton.setBackground(Color.YELLOW);
		ChangeColorsButton.setIcon(new ImageIcon("E:\\UTCN\\JAVA\\workspace\\TestDemo3\\theme.png"));
		ChangeColorsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setBackground(Color.black);
			}
		});
		game.add(NewGameButton);
		game.add(ChangeColorsButton);
		
		gameScreen = menu.getContentPane();
		gameScreen.setLayout(new BorderLayout());
		gameScreen.add(this, BorderLayout.CENTER);
		gameScreen.add(game, BorderLayout.EAST);
		paddle.movePaddle(menu);

		createBricks();
		menu.setVisible(true);
	}

	private void popupWindow(String s, Color statecolor) {
		JFrame popUp = new JFrame("Breakout");
		JPanel popUpPanel = new JPanel(new BorderLayout());
		JButton state = new JButton(s);
		state.setBackground(statecolor);
		popUpPanel.add(state);
		popUp.add(popUpPanel);
		popUp.setSize(200, 200);
		popUp.setVisible(true);

	}
}
