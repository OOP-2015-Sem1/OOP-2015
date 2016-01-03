package uno.java.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WinnerFrame extends JFrame {
	public JLabel winnerPlayer;
	public JButton okBtn;

	public WinnerFrame() {
		super.setTitle("Winner");
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(200, 200);
		this.setVisible(true);
		this.setLayout(null);
	}

	public WinnerFrame(String player) {
		this();
		this.winnerPlayer = new JLabel(player);
		this.winnerPlayer.setBounds(0, 0, 50, 20);
		this.add(this.winnerPlayer);
		this.okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}

		});
	}
}
