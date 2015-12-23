package huffman.views;

import javax.swing.JTextArea;
import huffman.models.Huffman;

public class SouthArea1 extends JTextArea {
	private static final long serialVersionUID = 1855511942299647093L;

	public SouthArea1(int x, int y) {
		super(x, y);
		this.setOpaque(false);
	}

	public void setEfficiencyResults(Huffman huffman) {
		huffman.computeAverageBitsPerLetter();
		this.setText("The average number of bits per encoding is: " + huffman.getAverageBitsPerLetter() + ".\n");
		this.append("The text has " + huffman.getArrayListOfNodes().size() + " different characters.\n");
		this.append("The total number of nodes in our Huffman tree is " + huffman.getTotalNodes() + ".\n");
		this.append("The depth of the Huffman Tree is " + huffman.getMaximumHeight() + ".");
	}
}
