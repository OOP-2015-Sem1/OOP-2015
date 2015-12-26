package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import source.User;

public class ErrorNumberIntroducedFrame implements ActionListener {
	private JFrame noNumber;
	private JLabel errorMessage;
	private JButton OK;
	private User user;

	public ErrorNumberIntroducedFrame(User user) {
		this.noNumber = new JFrame();
		this.errorMessage = new JLabel();
		this.OK = new JButton("OK");
		this.user = user;
		
		this.noNumber.setLayout(new GridLayout(2, 1, 4, 5));
		this.noNumber.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.noNumber.setSize(500, 100);
		this.noNumber.setVisible(true);
		this.noNumber.setLocationRelativeTo(null);
		
		this.errorMessage
				.setText("Aah..There can't be more than 12 Players.So,please pick another number XD");
		
		this.noNumber.add(errorMessage);
		this.noNumber.add(OK);

		addActionListeners();
	}

	private void addActionListeners() {
		this.OK.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == this.OK) {
			this.noNumber.setVisible(false);
			new SelectNumberOfPlayersFrame(this.user);
		}

	}
}
