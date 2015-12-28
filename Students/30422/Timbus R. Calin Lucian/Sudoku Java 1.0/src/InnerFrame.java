import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class InnerFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String s = "Sudoku,originally called Number Place,is a logic-based,combinatorial "
			+ "number-placement puzzle."
			+ " The objective is to fill a 9×9 grid with digits so that each column, each row, and each of the nine 3×3 sub-grids that compose the grid "
			+ "(also called boxes, blocks, regions, or sub-squares) contains all of the digits from 1 to 9. "
			+ "The puzzle setter provides a partially completed grid, which for a well-posed puzzle has a unique solution.";

	public InnerFrame() {
		super();
		JPanel jpl = new JPanel();
		Color c = new Color(104,104,104);
		jpl.setBackground(c);
		JTextArea jtx = new JTextArea(s,15,30);
		c = new Color(30,144,255);
		jtx.setForeground(c);
		jtx.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		jtx.setLineWrap(true);
		jpl.add(jtx);
		DefaultCaret caret = (DefaultCaret) jtx.getCaret();  
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);       
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(jtx);
		jpl.add(scrollPane);
		this.add(jpl);
		this.setTitle("Help instructions");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
		this.pack();
	}

	public static void main(String[] args) {
		
	}
}
