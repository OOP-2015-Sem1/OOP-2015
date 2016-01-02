package controller;

import gui.Login;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ScoreFileReader {
	public static boolean alreadyExists = false;
	private Scanner scan;
	private static String content;
	private static String newHighScore;
	private static String oldHighScore;

	public void openFile() {
		try {
			scan = new Scanner(new File("users.txt"));

		} catch (Exception e) {
			System.out.println("ERROR");
		}
	}

	public void readFille() {
		content = "";
		while (scan.hasNext()) {
			String name = scan.next();
			String score = scan.next();
			content = content + name;
			content = content + " " + score;
			content = content + "\n";
			if (name.equals(Login.username)) {
				alreadyExists = true;
				Login.userScore = Integer.parseInt(score);
			}
		}
		System.out.println(content);
	}

	public static void updateFile() {
		if (Login.userScore < Main.score) {
			if (ScoreFileReader.alreadyExists == false) {
				content = content + Login.username + " " + Main.score + "\n";
			} else {
				System.out.println(Login.username);
				System.out.println(Login.userScore);
				System.out.println(Main.score);
				oldHighScore = Login.userScore + "";
				content.replaceAll(Login.username + " " + oldHighScore, Login.username + " " + newHighScore);
				System.out.println(content);
			}
		}
		try {

			File file = new File("C:/Users/Dan/workspace/Tetris/users.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

			System.out.println("Done");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
