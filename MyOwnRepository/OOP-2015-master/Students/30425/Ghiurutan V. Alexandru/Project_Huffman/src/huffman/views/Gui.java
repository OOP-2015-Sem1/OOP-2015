package huffman.views;

import javax.swing.*;
import javax.swing.border.Border;
import huffman.views.CodeDisplay;
import java.awt.*;
import java.awt.event.*;
import huffman.models.Huffman;
import huffman.models.Node;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTextArea inputMessageArea1, inputMessageArea2, outputMessageArea1, outputMessageArea2;
	private JButton button1, button2, button3;
	private JPanel inputArea1, inputArea2, codePanel;
	private Huffman huffman;
	private static final int SCREEN_WIDTH = 450;
	private static final int SCREEN_HEIGHT = 300;
	private CodeDisplay codeDisplay;
	private String inputText;
	private DrawCanvas canvas;

	public Gui() {
		this.setLayout(new BorderLayout());
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// NORTH SECTION
		button1 = new JButton("Build Huffman Tree");
		codeDisplay = new CodeDisplay();
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button1) {
					inputText = inputMessageArea1.getText();
					if (inputText.equals("")) {
						JOptionPane.showMessageDialog(Gui.this, "Please enter some text.", "Error",
								JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					huffman = new Huffman(inputText);
					codeDisplay.getFunction("frequency");
					codeDisplay.getFunction("huffmanTree");
					codeDisplay.getFunction("traverseTree");
					codeDisplay.getFunction("encode");
					huffman.determineNodePositions();
					huffman.determineMaximumHeight();
					canvas.clear();
					drawTree(huffman.getRootOfHuffmanTree());
					canvas.display();
				}
			}
		});
		button2 = new JButton("Encode text");
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button2) {
					String text = huffman.getOutputText();
					if (text.equals("")||(text==null)) {
						JOptionPane.showMessageDialog(Gui.this,
								"Please enter first some text and build the Huffman Tree.", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						outputMessageArea1.setText(text);
						codeDisplay.getFunction("encode");
					}
				}
			}
		});
		inputMessageArea1 = new JTextArea(3, 30);
		inputMessageArea1.setToolTipText("Please enter here the text");
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		inputMessageArea1.setBorder(border);
		outputMessageArea1 = new JTextArea(3, 30);
		outputMessageArea1.setEditable(false);
		outputMessageArea1.setBorder(border);
		inputArea1 = new JPanel(new GridLayout(2, 2));
		inputArea1.add(inputMessageArea1);
		inputArea1.add(button1);
		inputArea1.add(outputMessageArea1);
		inputArea1.add(button2);
		this.add(inputArea1, BorderLayout.NORTH);
		// SOUTH SECTION
		button3 = new JButton("Decode");
		inputMessageArea2 = new JTextArea(3, 30);
		inputMessageArea2.setToolTipText("Please enter the encoding of the initial message.");
		outputMessageArea2 = new JTextArea(3, 30);
		outputMessageArea2.setEditable(false);
		inputArea2 = new JPanel(new GridLayout());
		inputArea2.add(button3);
		inputArea2.add(inputMessageArea2);
		inputArea2.add(outputMessageArea2);
		this.add(inputArea2, BorderLayout.SOUTH);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == button3) {
					String text = "";
					text = inputMessageArea2.getText();
					if (text.equals("") || (!text.equals(huffman.getOutputText()))) {
						JOptionPane.showMessageDialog(Gui.this, "Please enter some correct text.", "Error",
								JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
					huffman.decode(text);
					codeDisplay.getFunction("decode");
					outputMessageArea2.setText(huffman.getDecodedText());
				}
			}
		});

		canvas = new DrawCanvas();
		codePanel = new JPanel();
		codePanel.add(codeDisplay);
		this.add(codePanel, BorderLayout.EAST);
		this.getContentPane().add(canvas, BorderLayout.WEST);
		this.setVisible(true);

	}

	// WEST SECTION
	// Inorder traversal
	public void drawTree(Node root) {
		int xScale, yScale, dx, dy, dx1, dy1;
		xScale = (SCREEN_WIDTH / huffman.getTotalNodes());
		yScale = (SCREEN_HEIGHT / huffman.getMaximumHeight());
		if (root != null) {
			drawTree(root.getLeft());
			dx = root.getX() * xScale + 30;
			dy = root.getY() * yScale + 50;
			String text = root.getCh() + ":" + root.getFreq();
			drawNode(dx, dy);
			drawText(text, dx, dy, Color.GREEN);
			codeDisplay.getFunction("huffmanTree");
			if (root.getLeft() != null) {
				dx1 = root.getLeft().getX() * xScale + 30;
				dy1 = root.getLeft().getY() * yScale + 50;
				drawLine(dx, dy, dx1, dy1, Color.BLACK);
			}
			if (root.getRight() != null) {
				dx1 = root.getRight().getX() * xScale + 30;
				dy1 = root.getRight().getY() * yScale + 50;
				drawLine(dx, dy, dx1, dy1, Color.BLACK);
			}
			if (root.getRight() == null && root.getLeft() == null) {
				drawText("Algorithm ended.", dx - 5, dy + 15, Color.BLUE);
			}
			drawTree(root.getRight());
		}
	}

	private void drawText(String text, int dx, int dy, Color color) {
		Color col = color;
		canvas.setForeground(col);
		Font font = new Font("", Font.BOLD, 14);
		canvas.setFont(font);
		canvas.drawString(text, dx, dy, true);
	}

	private void drawNode(int dx, int dy) {
		canvas.setForeground(Color.WHITE);
		canvas.fillOval(dx - 10, dy - 15, 30, 20);
		canvas.setForeground(Color.BLACK);
		canvas.drawOval(dx - 10, dy - 15, 30, 20);
	}

	private void drawLine(int dx, int dy, int dx1, int dy1, Color color) {
		canvas.setForeground(color);
		canvas.drawLine(dx, dy, dx1, dy1);
	}
}