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
	private static final String EMPTY_STRING = "";
	private int[] frequency;
	private PriorityQueue<Node> forest;
	private Node root;
	private ArrayList<Table> arrayTable;
	private String inputText;
	private String outputText;
	private int totalNodes;
	private int maximumHeight;
	private String decodedText;

	public Huffman(String text) {
		forest = new PriorityQueue<Node>();
		arrayTable = new ArrayList<Table>();
		this.setInputText(text);
		this.setOutputText("");
		this.setDecodedText("");
		this.getSymbolsFrequency();
		buildHuffmanTree();
		traverseHuffmanTree(getRootOfHuffmanTree(), "");
		encodeText();
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
		while (!text.equals(EMPTY_STRING)) {
			for (int i = 0; i < arrayTable.size(); i++) {
				if (text.startsWith(arrayTable.get(i).getCode())) {
					text = text.substring(arrayTable.get(i).getCode().length());
					decodedText += arrayTable.get(i).getCh();
					break;
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

	public void determineNodePositions() {
		inorderTraversal(root, 0);
	}

	public void inorderTraversal(Node root, int depth) {
		if (root != null) {
			inorderTraversal(root.getLeft(), depth + 1);
			root.setX(totalNodes++);
			root.setY(depth);
			inorderTraversal(root.getRight(), depth + 1);
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
}
