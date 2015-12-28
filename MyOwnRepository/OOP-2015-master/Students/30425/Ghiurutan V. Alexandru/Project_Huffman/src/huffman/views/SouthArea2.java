package huffman.views;

import java.util.ArrayList;
import javax.swing.JTextArea;
import huffman.models.Huffman;
import huffman.models.Table;

public class SouthArea2 extends JTextArea {
	private static final long serialVersionUID = -2437106010901489849L;
	private ArrayList<Table> arrayTable;

	public SouthArea2(int x, int y) {
		super(x, y);
		this.setOpaque(false);
		this.setEditable(false);
	}

	public void clearArea() {
		this.setText("");
	}

	public void displayEncodingCharacteristics(Huffman huffman) {
		this.arrayTable = huffman.getArrayListOfNodes();
		this.setText("Character\tEncoding\t\t\tFrequency\n");
		for (Table table : arrayTable) {
			this.append(table.getCh() + "\t" + table.getCode() + "\t\t\t" + table.getFreq() + "\n");
		}
	}

}
