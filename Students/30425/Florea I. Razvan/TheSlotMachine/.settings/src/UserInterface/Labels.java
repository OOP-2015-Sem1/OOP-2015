package UserInterface;

import javax.swing.JLabel;

public class Labels {

	public static JLabel creditLabel = new JLabel();
	public static JLabel winningLabel = new JLabel();

	private JLabel betLabel = new JLabel("Select Bet:");
	private JLabel linesLabel = new JLabel("Select number of  winning lines:");
	private JLabel totalCredit = new JLabel("Total:");
	private JLabel currentWinning = new JLabel("Winning:");

	public JLabel getTotalCredit() {
		return totalCredit;
	}

	public JLabel getCurrentWinning() {
		return currentWinning;
	}

	public JLabel getBetLabel() {
		return betLabel;
	}

	public void setBetLabel(JLabel betLabel) {
		this.betLabel = betLabel;
	}

	public JLabel getLinesLabel() {
		return linesLabel;
	}

	public void setLinesLabel(JLabel linesLabel) {
		this.linesLabel = linesLabel;
	}

}
