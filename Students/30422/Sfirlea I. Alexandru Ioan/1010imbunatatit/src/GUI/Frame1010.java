package GUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import Main.Constants;

public class Frame1010 extends JFrame {
	private static final long serialVersionUID = -8573649221379461824L;
	
	public static String playerName = Frame1010.queryForPlayer();

	public Frame1010() {

		super("1010");

		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Constants.G_w, Constants.G_h);
		setResizable(true);

		BoardPanel board = new BoardPanel();
		add(board, BorderLayout.CENTER);
		ButtonPanel buton = new ButtonPanel();
		add(buton, BorderLayout.EAST);

		setVisible(true);

	}

	public static boolean queryBackgroundColorat() {
		int reply = JOptionPane.showConfirmDialog(null, "Sa fie colorat background?", "Optiune",
				JOptionPane.YES_NO_OPTION);
		return (reply == JOptionPane.YES_OPTION);
	}

	public static String queryForPlayer() {
		String text = JOptionPane.showInputDialog("Numele jucatorului: ");
		return text;
	}
}
