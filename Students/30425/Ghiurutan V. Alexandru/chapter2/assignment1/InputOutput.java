package chapter2.assignment1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.System.out;
import java.text.DecimalFormat;

public class InputOutput {
	private static FileReader fileReader;
	private static BufferedReader bufferedReader;
	private static FileWriter fileWriter;
	private static BufferedWriter bufferedWriter;
	private String p1;
	private String p2;

	public InputOutput(String inputFileName, String outputFileName) {
		this(inputFileName, outputFileName, "Normal");
	}

	public InputOutput(String inputFileName, String outputFileName, String read) {
		if (read.equalsIgnoreCase("Mathematically")) {
			readPolynomialsMathematically(inputFileName, outputFileName);
		} else {
			try {
				fileReader = new FileReader(inputFileName);
				bufferedReader = new BufferedReader(fileReader);
				this.p1 = bufferedReader.readLine();
				this.p2 = bufferedReader.readLine();
				fileWriter = new FileWriter(outputFileName);
				bufferedWriter = new BufferedWriter(fileWriter);
			} catch (FileNotFoundException e) {
				out.println(e);
			} catch (IOException e) {
				out.println(e);
			} finally {

			}

		}
	}

	// Twist 3
	private String getNormalForm(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '^') {
				String a = "" + s.charAt(i) + s.charAt(i + 1);
				s = s.replace(a, "");
			}
			if (s.charAt(i) == '+') {
				String a = "" + s.charAt(i);
				s = s.replace(a, "");
			}
		}
		s = s.replaceAll("X", " ");
		return s;
	}

	private void readPolynomialsMathematically(String inputFileName, String outputFileName) {
		try {

			bufferedReader = new BufferedReader(new FileReader(inputFileName));
			this.p1 = getNormalForm(bufferedReader.readLine());
			this.p2 = getNormalForm(bufferedReader.readLine());
			bufferedWriter = new BufferedWriter(new FileWriter(outputFileName));
		} catch (FileNotFoundException e) {
			out.println(e);
		} catch (IOException e) {
			out.println(e);
		} finally {

		}

	}

	private void swap(double[] p, int a, int b) {
		double temp = p[a];
		p[a] = p[b];
		p[b] = temp;
	}

	private double[] reverseArray(double[] p) {
		for (int left = 0, right = p.length - 1; left < right; left++, right--) {
			swap(p, left, right);
		}
		return p;
	}

	public double[] getP1() {
		String[] aux = this.p1.split(" ");
		double[] p1 = new double[aux.length];
		for (int i = 0; i < aux.length; i++) {
			p1[i] = Double.parseDouble(aux[i]);
		}
		return reverseArray(p1);
	}

	public double[] getP2() {
		String[] aux = this.p2.split(" ");
		double[] p2 = new double[aux.length];
		for (int i = 0; i < aux.length; i++) {
			p2[i] = Double.parseDouble(aux[i]);
		}
		return reverseArray(p2);
	}

	public String getCommands() {
		String s = "";
		try {
			s = bufferedReader.readLine();
		} catch (IOException e) {
			System.out.println(e);
		} finally {

		}
		while (s != null && s.trim().length() == 0) {
			try {
				s = bufferedReader.readLine();
			} catch (IOException e) {
				System.out.println(e);
			} finally {

			}
		}
		return s;
	}

	public void print(String outputFileName, String s) {
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(outputFileName, true));
			bufferedWriter.write(s);
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println(e);
		} finally {

		}
	}

	public void print(String outputFileName, double x) {
		DecimalFormat f = new DecimalFormat();
		try {
			fileWriter = new FileWriter(outputFileName, true);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(f.format(x));
			bufferedWriter.newLine();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println(e);
		} finally {

		}
	}
}
