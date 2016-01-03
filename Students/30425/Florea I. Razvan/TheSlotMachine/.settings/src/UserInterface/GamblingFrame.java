package UserInterface;

import static Main.ValuesToWorkWith.winning;
import static Main.Constants.MAX_ALLOWED_GAMBLES;
import static Main.Constants.NUMBER_OF_GAMBLE_CARDS;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamblingFrame extends JFrame {

	private static final long serialVersionUID = -8009777093110390448L;

	private Random random = new Random();
	private JLabel generatedCard;
	private JButton redCard = new JButton();
	private JButton blackCard = new JButton();
	private JPanel cardPanel = new JPanel();
	private int code;
	private int gambleIterator = 0;

	public GamblingFrame(String title) {

		super(title);
		setLocation(500, 50);
		setSize(400, 400);
		setDefaultCloseOperation(GamblingFrame.HIDE_ON_CLOSE);
		setVisible(true);
		setLayout(new GridLayout(2, 3));

		generatedCard = new JLabel(new ImageIcon(ClassLoader.getSystemResource("back.jpg")));
		cardPanel.add(generatedCard);

		redCard.setBackground(Color.RED);
		blackCard.setBackground(Color.BLACK);

		redCard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				code = random.nextInt(NUMBER_OF_GAMBLE_CARDS);
				redOrBlack(code);

				if (gambleIterator == MAX_ALLOWED_GAMBLES) {
					setVisible(false);
				}

				if (code >= 6 && gambleIterator < MAX_ALLOWED_GAMBLES) {
					winning *= 2;
					ButtonsPanel.newWinning();
					gambleIterator++;
				} else if (code < 6) {
					winning = 0;
					ButtonsPanel.newWinning();
					setVisible(false);
				}

			}

		});

		blackCard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				code = random.nextInt(NUMBER_OF_GAMBLE_CARDS);
				redOrBlack(code);

				if (gambleIterator == MAX_ALLOWED_GAMBLES) {
					setVisible(false);
				}

				if (code < 6 && gambleIterator < MAX_ALLOWED_GAMBLES) {
					winning *= 2;
					ButtonsPanel.newWinning();
					gambleIterator++;
				} else if (code >= 6) {
					winning = 0;
					ButtonsPanel.newWinning();
					setVisible(false);
				}

			}

		});

		add(new JLabel());
		add(cardPanel);
		add(new JLabel());
		add(redCard);
		add(new JLabel());
		add(blackCard);

	}

	public void redOrBlack(int value) {

		cardPanel.setVisible(false);
		generatedCard = new JLabel(new ImageIcon(ClassLoader.getSystemResource("card" + value + ".jpg")));
		cardPanel.removeAll();
		cardPanel.add(generatedCard);
		cardPanel.setVisible(true);

	}

	public JFrame getGamblingFrame() {
		return this;
	}

}
