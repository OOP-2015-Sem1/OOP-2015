package demo.views.utilities;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToolBar;

public class Clock extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Thread runner = null;
	JLabel clock = new JLabel();

	String dateToDisplay;

	public void setClock(JToolBar toolBar) {

		clock.setFont(new Font("Serif", Font.BOLD, 40));
		toolBar.add(clock, BorderLayout.LINE_END);
		clock.setText(timeNow());
		start();

	}

	// get current time
	public String timeNow() {

		Calendar now = Calendar.getInstance();
		int hrs = now.get(Calendar.HOUR_OF_DAY);
		int min = now.get(Calendar.MINUTE);
		int sec = now.get(Calendar.SECOND);

		String time = zero(hrs) + ":" + zero(min) + ":" + zero(sec);

		return time;
	}

	public String zero(int num) {
		String number = (num < 10) ? ("0" + num) : ("" + num);
		return number; // Add leading zero if needed

	}

	public void start() {
		if (runner == null)
			runner = new Thread(this);
		runner.start();
		// method to start thread
	}

	public void run() {
		while (runner != null) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Thread failed");
			}
			dateToDisplay = timeNow();
			clock.setText(dateToDisplay);
		}
		runner = null;
	}

}
