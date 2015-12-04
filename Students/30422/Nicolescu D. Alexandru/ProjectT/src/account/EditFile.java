package account;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class EditFile {
	

	public static void replace(String oldscore,String newscore) {

		
			try {
				File file = new File("accounts.txt");
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = "", oldtext = "";
				while ((line = reader.readLine()) != null) 
					oldtext += line + "\r\n";

				reader.close();

				String replacedtext = oldtext.replaceAll(oldscore, "" + newscore);

				FileWriter writer = new FileWriter("accounts.txt");
				writer.write(replacedtext);

				writer.close();

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		
	}
}