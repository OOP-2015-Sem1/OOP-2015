package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import source.User;

public class BetMoneyFrame implements ActionListener {

	private JFrame playerName;
	private int nbPlayers;
	private ArrayList<String> namePlayers;
	// private JPanel moneyPanel;
	private JButton saveMoney;
	private JPanel saveMoneyPanel;
	private JTextField myBetMoney;
	private User user;

	public BetMoneyFrame(int nbPlayers, User user) {
		this.user = user;
		this.nbPlayers = nbPlayers;
		
		this.saveMoneyPanel = new JPanel();
		this.namePlayers = new ArrayList<>();
		this.myBetMoney = new JTextField();
		this.playerName = new JFrame();
		this.saveMoney = new JButton("Save");
		
		this.playerName.setLayout(new GridLayout(2, 1));
		playerName.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		playerName.setSize(800, 500);
		playerName.setVisible(true);
		this.playerName.setLocationRelativeTo(null);

		setPlace();
		
		this.saveMoneyPanel.add(saveMoney);
		this.playerName.add(this.saveMoneyPanel);
		
		addActionListeners();
	}

	private void setPlace() {
		this.saveMoneyPanel.setLayout(new GridLayout(3, 1));
		JLabel myMoney = new JLabel(
				"How much money you want to bet?You still have "
						+ this.user.getMoney());
		this.saveMoneyPanel.add(myMoney);
		this.saveMoneyPanel.add(myBetMoney);
	}

	private void addActionListeners() {
		this.saveMoney.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.saveMoney) {
			this.namePlayers.add(this.user.getUserName());
			for (int i = 1; i < this.nbPlayers; i++) {
				String name = "Player " + i;
				this.namePlayers.add(name);
			}
			this.namePlayers.add("Dealer");
			
			int betValue = Integer.parseInt(this.myBetMoney.getText());
			if (betValue > this.user.getMoney() || betValue <= 0) {
				JOptionPane.showMessageDialog(null, "You only have "
						+ this.user.getMoney() + ".");
			} else {
				this.playerName.setVisible(false);
				new GameApplication(this.nbPlayers, this.namePlayers,
						this.user, betValue);
			}
		}
	}

}
