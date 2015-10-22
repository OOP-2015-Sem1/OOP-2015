package chapter2.assignment1;

import javax.swing.JOptionPane;

//Class that deals with commands
public class Tasks {
	private InputOutput inOut;

	public Tasks() {
		super();
		String s = (String) JOptionPane.showInputDialog(null,
				"Give the way of reading the polynomial.Mathematically or normal?");
		if (!s.equalsIgnoreCase("normal") && !s.equalsIgnoreCase("mathematically")) {
			JOptionPane.showMessageDialog(null, "Incorrect input.");
			throw new IllegalArgumentException();
		} else if (s.equalsIgnoreCase("normal")) {
			inOut = new InputOutput("pol.txt", "out.txt");
		} else {
			inOut = new InputOutput("pol.txt", "out.txt", "mathematically");
		}
	}

	public void action(Functions f) {
		Polynomial p1 = new Polynomial(inOut.getP1());
		Polynomial p2 = new Polynomial(inOut.getP2());
		String command = inOut.getCommands();
		while (command != null) {
			String[] c = command.split(" ");
			switch (c[0]) {
			case "ADD":
				Polynomial rezult = f.add(p1, p2);
				inOut.print("out.txt", rezult.toString());
				break;
			case "SUBTRACT":
				Polynomial r1 = f.subtract(p1, p2);
				inOut.print("out.txt", r1.toString());
				break;
			case "MULTIPLY":
				Polynomial r2 = f.multiply(p1, p2);
				inOut.print("out.txt", r2.toString());
				break;
			case "MUL_SCAL":
				double d = Double.parseDouble(c[1]);
				Polynomial r3 = f.scalarMul(p1, d);
				Polynomial r4 = f.scalarMul(p2, d);
				inOut.print("out.txt", r3.toString());
				inOut.print("out.txt", r4.toString());
				break;
			case "EVAL":
				double x = Double.parseDouble(c[1]);
				double a = f.evaluate(p1, x);
				double b = f.evaluate(p2, x);
				inOut.print("out.txt", a);
				inOut.print("out.txt", b);
				break;
			case "DIVISION":
				Polynomial[] r = f.divide(p1, p2);
				inOut.print("out.txt", "Quotient :" + r[0].toString());
				inOut.print("out.txt", "Reminder :" + r[1].toString());
				break;
			case "ROOT_APROX":
				double number1 = f.bisection(p1);
				double number2 = f.bisection(p2);
				inOut.print("out.txt", number1);
				inOut.print("out.txt", number2);
				break;
			default:
				System.err.println("Incorrect command!");
				break;
			}
			command = inOut.getCommands();
		}
	}
}
