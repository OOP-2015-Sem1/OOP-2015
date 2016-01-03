package battleship.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private PlayerDetailPanel player1Panel;
	private PlayerDetailPanel player2Panel;
	private JButton startButton;

	public MainFrame(String title) {
		super(title);

		setLayout(new BorderLayout());

		player1Panel = new PlayerDetailPanel("Player 1", true, null);
		player2Panel = new PlayerDetailPanel("Player 2", false, player1Panel);

		startButton = new JButton("Start");
		startButton.setBackground(new Color(100,100,100));
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player1Panel.getPlayer().getBoard().setGaneStart(true);
				player1Panel.getPlayer().getBoard().initBoardPositions(player1Panel.getPlayer().isMainPlayer());
				player2Panel.getPlayer().getBoard().setGaneStart(true);
				player2Panel.getPlayer().getBoard().initBoardPositions(player2Panel.getPlayer().isMainPlayer());
			}
		});

		Container container = getContentPane();
		container.add(player1Panel, BorderLayout.WEST);
		container.add(player2Panel, BorderLayout.EAST);
		container.add(startButton, BorderLayout.CENTER);
	}

	// getters and setters
	public PlayerDetailPanel getPlayer1Panel() {
		return player1Panel;
	}

	public void setPlayer1Panel(PlayerDetailPanel player1Panel) {
		this.player1Panel = player1Panel;
	}

	public PlayerDetailPanel getPlayer2Panel() {
		return player2Panel;
	}

	public void setPlayer2Panel(PlayerDetailPanel player2Panel) {
		this.player2Panel = player2Panel;
	}

}
