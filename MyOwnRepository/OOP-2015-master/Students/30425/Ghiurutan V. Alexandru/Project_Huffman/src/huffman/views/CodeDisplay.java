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
	private String[] decode = { "public void decode(String text) {", "while (!text.equals(\"\")) {",
			"for (int i = 0; i < arrayTable.size(); i++) {", "if (text.startsWith(arrayTable.get(i).getCode())) {",
			"text = text.substring(arrayTable.get(i).getCode().length());", "decodedText += arrayTable.get(i).getCh();",
			"break;", "}", "}", "}", "}" };
	private String[] drawTree = { "private Object drawTree(Node root, int depth, int index) {", "if (root == null) {",
			"return null;", "}", "int xCoord = (int) ((PANEL_WIDTH * (index)) / (Math.pow(2, depth) + 1));",
			"int yCoord = (int) (depth * PANEL_HEIGHT) / treeDepth;",
			"Object rootVertex = graph.insertVertex(parent, null, root.getCh() + \":\" + root.getFreq(), xCoord, yCoord,",
			"NODE_SIZE,NODE_SIZE,\"shape=ellipse;perimeter=30;fillColor=green\");",
			"Object leftChildVertex = drawTree(root.getLeft(), depth + 1, (index * 2) - 1);",
			"if (leftChildVertex != null) {",
			"graph.insertEdge(parent, null, \"0\", rootVertex, leftChildVertex,\"startArrow=none;endArrow=none;strokeWidth=1;strokeColor=yellow\");",
			"}", "Object rightChildVertex = drawTree(root.getRight(), depth + 1, index * 2);",
			"if (rightChildVertex != null) {",
			"graph.insertEdge(parent, null, \"1\", rootVertex, rightChildVertex,\"startArrow=none;endArrow=none;strokeWidth=1;strokeColor=yellow\");",
			"}", "return rootVertex;", "}", };
	private String[] determineMaximumHeight = { "public void determineMaximumHeight() {",
			"this.setMaximumHeight(maxDepth(root));", "}",

			"public void setMaximumHeight(int maximumHeight) {", "this.maximumHeight = maximumHeight;", "}",

			"public int maxDepth(Node root) {", "if (root == null) {", "return 0;", "} else {",
			"int leftDepth = maxDepth(root.getLeft());", "int rightDepth = maxDepth(root.getRight());",
			"if (leftDepth > rightDepth) {", "return leftDepth + 1;", "} else {", "return rightDepth + 1;", "}", "}",
			"}" };
	private String[] determineNumberOfNodes = { "public void determineNumberOfNodes() {", "inorderTraversal(root);",
			"}",

			"public void inorderTraversal(Node root) {", "if (root != null) {", "inorderTraversal(root.getLeft());",
			"totalNodes++;", "inorderTraversal(root.getRight());", "}", "}", };

	public CodeDisplay() {
		this.setOpaque(false);
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
		case "drawTree":
			printFunction(drawTree);
			break;
		case "determineMaximumHeight":
			printFunction(determineMaximumHeight);
			break;
		case "determineNumberOfNodes":
			printFunction(determineNumberOfNodes);
			break;
		default:
			throw new RuntimeException("Invalid function!");
		}
	}

	private void printFunction(String[] function) {
		this.setText("");
		for (String x : function) {
			this.append(x);
			this.append("\n");
		}
	}
}
