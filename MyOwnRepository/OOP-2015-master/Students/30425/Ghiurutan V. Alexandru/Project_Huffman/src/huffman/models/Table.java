package huffman.models;

public class Table {
	private char ch;
	private String code;
	private int freq;

	public Table(char ch, int freq, String code) {
		this.setCh(ch);
		this.setCode(code);
		this.setFreq(freq);
	}

	public void setCh(char ch) {
		this.ch = ch;
	}

	public char getCh() {
		return this.ch;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public int getFreq() {
		return freq;
	}
}
