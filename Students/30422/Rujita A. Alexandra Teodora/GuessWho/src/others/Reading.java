package others;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reading {

	public void CitireIntrebari(String[] questions, int nrIntrebari) throws Exception {

		FileReader file = new FileReader("Intrebari.txt");
		BufferedReader reader = new BufferedReader(file);
		String line = reader.readLine();

		for (int i = 0; i < nrIntrebari; i++) {
			questions[i] = line;
			line = reader.readLine();
		}
		file.close();
	}

	public int noOfQuestions() throws IOException {

		FileReader file = new FileReader("Intrebari.txt");
		BufferedReader reader = new BufferedReader(file);
		String line = reader.readLine();
		int c = 0;

		while (line != null) {
			line = reader.readLine();
			c++;
		}
		file.close();
		return c;
	}

}