package demo.views.utilities;

import demo.views.MainFrame;

import java.util.ArrayList;

public class FrameStack {

	private static FrameStack instance;
	private ArrayList<MainFrame> stack;

	private FrameStack() {
		stack = new ArrayList<MainFrame>();
	}

	public static FrameStack getInstance() {
		if (instance == null) {
			instance = new FrameStack();
		}
		return instance;
	}

	public MainFrame peek() {
		return stack.get(0);
	}

	public void push(MainFrame frame) {
		if (!stack.isEmpty())
			peek().setVisible(false);
		frame.setVisible(true);
		stack.add(0, frame);
	}

	public void pop() {
		if (!stack.isEmpty()) {
			peek().dispose();
			stack.remove(0);
			if (!stack.isEmpty()) {
				peek().setVisible(true);
			}
		}
	}
}
