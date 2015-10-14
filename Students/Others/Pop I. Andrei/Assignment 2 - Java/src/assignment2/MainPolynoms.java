package assignment2;

import java.io.File;

public class MainPolynoms {

	public static void main(String[] args) {
		
		InputOutput control = new InputOutput();
		
		File filename = new File("polynom.txt");
		
		control.readWriteFile(filename);
		
		//System.out.println(control.p1);
		//System.out.println(control.p2);
	}

}
