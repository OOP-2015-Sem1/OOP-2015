package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ErrorNumberIntroduced implements ActionListener {
	private JFrame noNumber;
	private JLabel errorMessage;
	private JButton OK;

	public ErrorNumberIntroduced() {
		this.noNumber = new JFrame();
		this.errorMessage = new JLabel();
		this.OK = new JButton("OK");
		this.noNumber.setLayout(new GridLayout(2, 1, 4, 5));
		this.errorMessage
				.setText("Aah..There can't be more than 12 Players.So,please pick another number XD");
		this.noNumber.add(errorMessage);
		this.noNumber.add(OK);
		this.noNumber.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.noNumber.setSize(500, 100);
		this.noNumber.setVisible(true);
		addActionListeners();
	}

	private void addActionListeners() {
		this.OK.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.OK) {
			this.noNumber.setVisible(false);
			new SelectNumberOfPlayers();
		}

	}
}
