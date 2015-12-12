package first;
import java.awt.EventQueue;

public class Application {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameEngine game = new GameEngine();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
}
