package demo.controllers;

import demo.views.FirstPanelFrame;
import demo.views.GameFrame;

public class MainClass {
	public static void main(String[] args) {
		new MainMenuController(new FirstPanelFrame("Main Frame"), false);
	}
}
