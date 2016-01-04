import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
	
		Level level = Levels.LevelData[0];
		Table table = TableCreator.CreateFromString(level.rows, level.columns, level.levelSource);
		MainWindow window=new MainWindow(table);
		
	}
}
