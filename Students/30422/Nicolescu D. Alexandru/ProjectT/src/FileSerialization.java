import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileSerialization {

	public static void Serial(int choice) {

		try {
			Player mb = new Player();

			if (choice == 0) {
				// write object to file
				FileOutputStream fos = new FileOutputStream("mybean.ser");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(mb);
				oos.close();
			} else if (choice == 1) {
				// read object from file
				FileInputStream fis = new FileInputStream("mybean.ser");
				ObjectInputStream ois = new ObjectInputStream(fis);
				Player result = (Player) ois.readObject();
				ois.close();
			}
			// System.out.println("One:" + result.getOne() + ", Two:" +
			// result.getTwo());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}