package model;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class TimerClass extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int secondsPassed = 0;
	private int minutesPassed = 0;
	private int hoursPassed = 0;
	private Timer gameTimer;
	private JLabel timeLabel;
	private static final int LABEL_DIMENSION = 30;
	private JButton returnTime;

	public TimerClass() {
		super("Time Frame");
		returnTime = new JButton("Stop");
		returnTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(getHours() + " " + getMinutes() + " " + getSeconds());
				gameTimer.stop();
			}
		});
		this.setLayout(new FlowLayout());
		this.add(returnTime);
		gameTimer = new Timer(1000, this);
		timeLabel = new JLabel();
		timeLabel.setSize(new Dimension(LABEL_DIMENSION, LABEL_DIMENSION));
		gameTimer.start();
		this.add(timeLabel);
		this.setSize(150, 100);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (secondsPassed == 59) {
			if (minutesPassed == 59) {
				secondsPassed = 0;
				minutesPassed = 0;
				hoursPassed++;
			} else {
				minutesPassed++;
				secondsPassed = 0;
			}
		} else {
			secondsPassed++;
		}
		timeLabel.setText("Hr:" + String.valueOf(hoursPassed) + " " + "Min:" + String.valueOf(minutesPassed) + " "
				+ "Sec: " + String.valueOf(secondsPassed));
	}

	public int getSeconds() {
		return secondsPassed;
	}

	public int getMinutes() {
		return minutesPassed;
	}

	public int getHours() {
		return hoursPassed;
	}

	public static void main(String[] args) {
		new TimerClass();
	}
}
