import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameDisplay extends JFrame implements ActionListener {

	GameDisplay() {
		JFrame Window = new JFrame();
		setLayout(new BorderLayout());

		JPanel Player2 = new JPanel();
		JPanel Player4 = new JPanel();
		JPanel Player3 = new JPanel();
		JPanel Player1 = new JPanel();
		JPanel Center = new JPanel();

		// Set background

		Player2.setBackground(Color.GREEN);
		Player4.setBackground(Color.GREEN);
		Player3.setBackground(Color.GREEN);
		Player1.setBackground(Color.GREEN);
		Center.setBackground(Color.GREEN);

		// CENTER REGION
		Center.setLayout(new GridLayout(3, 3));
		JLabel components[];
		int CardsPlayedIndex=0;
		components = new JLabel[9];
		for (int i = 0; i < 8; i++) {
			if ((i % 2) != 0) {
				components[i] = GameRunner.CardsPlayed[CardsPlayedIndex].CardPicture;
				CardsPlayedIndex++;
			} else
				components[i] = new JLabel();
			Center.add(components[i]);
		}
		// SCORE DISPLAY
		components[8] = new JLabel();
		Center.add(components[8]);
		components[8].setLayout(new BoxLayout(components[8], BoxLayout.Y_AXIS));
		JLabel score[];
		score = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			score[i] = new JLabel("Player" + (i + 1) + " : " + Integer.toString(Player.Score));
			components[8].add(score[i]);
		}

		// SOUTH REGION
		Player4.setLayout(new FlowLayout());
		JLabel Player4Cards[];
		Player4Cards = new JLabel[13];
		for (int i = 0; i < GameRunner.Players[3].handlength; i++) { 
			Player4Cards[i] = GameRunner.Players[3].hand[i].CardPicture;
			Player4Cards[i].addMouseListener(new ListenerMouse(i)); 
			Player4.add(Player4Cards[i]);
		}

		// NORTH REGION
		Player2.setLayout(new FlowLayout());
		JLabel Player2Cards[];
		Player2Cards = new JLabel[13];
		ImageIcon picture = new ImageIcon("cardback.jpg");
		for (int i = 0; i < GameRunner.Players[1].handlength; i++) {
			Player2Cards[i] = new JLabel(picture);
			Player2.add(Player2Cards[i]);

		}

		// EAST REGION
		Player3.setLayout(new BoxLayout(Player3, BoxLayout.Y_AXIS));
		JLabel Player3Cards[];
		Player3Cards = new JLabel[13];
		ImageIcon pic = new ImageIcon("cardbackrotated.jpg");
		for (int i = 0; i <GameRunner.Players[2].handlength; i++) {
			Player3Cards[i] = new JLabel(pic);
			Player3.add(Box.createVerticalStrut(6));
			Player3.add(Box.createHorizontalStrut(150));
			Player3.add(Player3Cards[i]);

		}

		// WEST REGION
		Player1.setLayout(new BoxLayout(Player1, BoxLayout.Y_AXIS));
		JLabel e[];
		e = new JLabel[13];
		ImageIcon pict = new ImageIcon("cardbackrotated.jpg");
		for (int i = 0; i < GameRunner.Players[0].handlength; i++) {
			e[i] = new JLabel(pict);
			Player1.add(Box.createVerticalStrut(6));
			Player1.add(Box.createHorizontalStrut(50));
			Player1.add(e[i]);

		}

		// window
		Window.add(Player2, BorderLayout.NORTH);
		Window.add(Player4, BorderLayout.SOUTH);
		Window.add(Player3, BorderLayout.EAST);
		Window.add(Player1, BorderLayout.WEST);
		Window.add(Center, BorderLayout.CENTER);

		Window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Window.setSize(900, 900);
		Window.setVisible(true);

		// GameEnded Show Winner
		if (GameRunner.GameEnded() == true) {
			JOptionPane.showMessageDialog(null, "Player " + GameRunner.MinimumScorePlayer() + " won the game!");
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
