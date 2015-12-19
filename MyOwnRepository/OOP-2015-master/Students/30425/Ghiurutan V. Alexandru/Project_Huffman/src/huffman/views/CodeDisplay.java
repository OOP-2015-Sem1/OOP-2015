package huffman.views;
import javax.swing.JTextArea;

public class CodeDisplay extends JTextArea {
	private static final long serialVersionUID = 9170516702125062175L;
	private String[] frequency = { "public void getSymbolsFrequency() {", "frequency = new int[MAX_NR_OF_SYMBOLS];",
			"char[] characters = this.getInputText().toCharArray();", "for (int i = 0; i < characters.length; i++) {",
			"frequency[characters[i]]++;", "}" };
	private String[] huffmanTree = { "public void buildHuffmanTree() {", "for (int i = 0; i < frequency.length; i++) {",
			"if (frequency[i] != 0) {", "Node n = new Node((char) i, frequency[i], null, null);", "forest.add(n);", "}",
			"}", "while (forest.size() != 1) {", "Node n1 = forest.poll();", "Node n2 = forest.poll();",
			"int newFrequency = n1.getFreq() + n2.getFreq();", "Node n = new Node(newFrequency, n1, n2);",
			"forest.add(n);", "}", "this.setRootOfHuffmanTree(forest.poll());", "}" };
	private String[] traverseTree = { "public void traverseHuffmanTree(Node rootNode, String code) {",
			"if (!rootNode.isLeaf()) {", "traverseHuffmanTree(rootNode.getLeft(), code + '0');",
			"traverseHuffmanTree(rootNode.getRight(), code + '1');", "} else {",
			"Table table = new Table(rootNode.getCh(), rootNode.getFreq(), code);", "arrayTable.add(table);", "}",
			"}" };
	private String[] encode = { "public void encodeText() {", "String text = getInputText();",
			"char[] characters = text.toCharArray();", "for (char ch : characters) {", "symbolCode(ch);", "}", "}",

			"private void symbolCode(char ch) {", "for (int i = 0; i < arrayTable.size(); i++) {",
			"if (arrayTable.get(i).getCh() == ch) {", "outputText += arrayTable.get(i).getCode();", "}", "}", "}" };
	private String[] decode = { "public void decode(String text) {", "while (!text.equals(EMPTY_STRING)) {",
			"for (int i = 0; i < arrayTable.size(); i++) {", "if (text.startsWith(arrayTable.get(i).getCode())) {",
			"text = text.substring(arrayTable.get(i).getCode().length());", "decodedText += arrayTable.get(i).getCh();",
			"break;", "}", "}", "}", "}" };

	public CodeDisplay() {
	}

	public void getFunction(String function) {
		this.setText("");
		switch (function) {
		case "frequency":
			printFunction(frequency);
			break;
		case "huffmanTree":
			printFunction(huffmanTree);
			break;
		case "traverseTree":
			printFunction(traverseTree);
			break;
		case "encode":
			printFunction(encode);
			break;
		case "decode":
			printFunction(decode);
			break;
		default:
			throw new RuntimeException("Invalid function!");
		}
	}

	private void printFunction(String[] function) {
		for (String x : function) {
			this.append(x);
			this.append("\n");
		}
		try {
			Thread.sleep(20);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
}
