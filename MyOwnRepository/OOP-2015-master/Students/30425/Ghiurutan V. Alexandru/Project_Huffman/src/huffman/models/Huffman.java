package huffman.models;

import huffman.models.Node;
import java.util.PriorityQueue;
import java.util.ArrayList;

/**
 * 
 * @author Alexandru
 *
 */

public class Huffman {
	private static final int MAX_NR_OF_SYMBOLS = 256;
	private int[] frequency;
	private PriorityQueue<Node> forest;
	private Node root;
	private ArrayList<Table> arrayTable;
	private String inputText;
	private String outputText;
	private int totalNodes;
	private int maximumHeight;
	private String decodedText;
	private int averageBitsPerLetter;

	public Huffman(String text) {
		forest = new PriorityQueue<Node>();
		arrayTable = new ArrayList<Table>();
		this.setInputText(text);
		this.setOutputText("");
		this.setDecodedText("");
		this.setAverageBitsPerLetter(0);
	}

	public void getSymbolsFrequency() {
		frequency = new int[MAX_NR_OF_SYMBOLS];
		char[] characters = this.getInputText().toCharArray();
		for (int i = 0; i < characters.length; i++) {
			frequency[characters[i]]++;
		}
	}

	public void buildHuffmanTree() {
		for (int i = 0; i < frequency.length; i++) {
			if (frequency[i] != 0) {
				Node n = new Node((char) i, frequency[i], null, null);
				forest.add(n);
			}
		}
		while (forest.size() != 1) {
			Node n1 = forest.poll();
			Node n2 = forest.poll();
			int newFrequency = n1.getFreq() + n2.getFreq();
			Node n = new Node(newFrequency, n1, n2);
			forest.add(n);
		}
		this.setRootOfHuffmanTree(forest.poll());
	}

	public void traverseHuffmanTree(Node rootNode, String code) {
		if (!rootNode.isLeaf()) {
			traverseHuffmanTree(rootNode.getLeft(), code + '0');
			traverseHuffmanTree(rootNode.getRight(), code + '1');
		} else {
			Table table = new Table(rootNode.getCh(), rootNode.getFreq(), code);
			arrayTable.add(table);
		}
	}

	public void setRootOfHuffmanTree(Node root) {
		this.root = root;
	}

	public Node getRootOfHuffmanTree() {
		return root;
	}

	public void encodeText() {
		String text = getInputText();
		char[] characters = text.toCharArray();
		for (char ch : characters) {
			symbolCode(ch);
		}
	}

	private void symbolCode(char ch) {
		for (int i = 0; i < arrayTable.size(); i++) {
			if (arrayTable.get(i).getCh() == ch) {
				outputText += arrayTable.get(i).getCode();
			}
		}
	}

	public void decode(String text) {
		setDecodedText("");
		boolean ind;
		int i;
		loop: while (!text.equals("")) {
			ind = false;
			for (i = 0; i < arrayTable.size(); i++) {
				if (text.startsWith(arrayTable.get(i).getCode())) {
					text = text.substring(arrayTable.get(i).getCode().length());
					decodedText += arrayTable.get(i).getCh();
					ind = true;
					break;
				}
				if (!ind && (i == arrayTable.size() - 1)) {
					decodedText += "...";
					break loop;
				}
			}
		}
	}

	public String getDecodedText() {
		return decodedText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}

	public String getInputText() {
		return inputText;
	}

	public String getOutputText() {
		return outputText;
	}

	public void setOutputText(String outputText) {
		this.outputText = outputText;
	}

	// Methods that are used in printing the tree
	public void determineMaximumHeight() {
		this.setMaximumHeight(maxDepth(root));
	}

	public void setMaximumHeight(int maximumHeight) {
		this.maximumHeight = maximumHeight;
	}

	public int maxDepth(Node root) {
		if (root == null) {
			return 0;
		} else {
			int leftDepth = maxDepth(root.getLeft());
			int rightDepth = maxDepth(root.getRight());
			if (leftDepth > rightDepth) {
				return leftDepth + 1;
			} else {
				return rightDepth + 1;
			}
		}
	}

	public void determineNumberOfNodes() {
		inorderTraversal(root);
	}

	public void inorderTraversal(Node root) {
		if (root != null) {
			inorderTraversal(root.getLeft());
			totalNodes++;
			inorderTraversal(root.getRight());
		}
	}

	public int getTotalNodes() {
		return totalNodes;
	}

	public int getMaximumHeight() {
		return maximumHeight;
	}

	public void setDecodedText(String decodedText) {
		this.decodedText = decodedText;
	}

	public int getAverageBitsPerLetter() {
		return averageBitsPerLetter;
	}

	public void setAverageBitsPerLetter(int averageBitsPerLetter) {
		this.averageBitsPerLetter = averageBitsPerLetter;
	}

	public void computeAverageBitsPerLetter() {
		for (int i = 0; i < arrayTable.size(); i++) {
			averageBitsPerLetter += arrayTable.get(i).getCode().length() * arrayTable.get(i).getFreq();
		}
	}

	public ArrayList<Table> getArrayListOfNodes() {
		return arrayTable;
	}
}
