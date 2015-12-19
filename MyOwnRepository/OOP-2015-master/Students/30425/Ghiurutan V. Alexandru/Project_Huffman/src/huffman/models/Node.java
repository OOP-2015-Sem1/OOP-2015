package huffman.models;
/**
 * 
 * @author Alexandru
 *
 */
public class Node implements Comparable<Node> {
	private char ch;
	private int freq;
	private Node left, right;
	private int x;
	private int y;

	public Node(int freq, Node left, Node right) {
		this.setFreq(freq);
		this.setLeft(left);
		this.setRight(right);
	}

	public Node(char ch, int freq, Node left, Node right) {
		this.setCh(ch);
		this.setFreq(freq);
		this.setLeft(left);
		this.setRight(right);
		this.setX(x);
		this.setY(y);
	}

	public boolean isLeaf() {
		return (left == null) && (right == null);
	}

	public int compareTo(Node other) {
		return (this.freq - other.freq);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setCh(char ch) {
		this.ch = ch;
	}

	public char getCh() {
		return this.ch;
	}

	public int getFreq() {
		return this.freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public Node getLeft() {
		return this.left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return this.right;
	}

	public void setRight(Node right) {
		this.right = right;
	}
}
