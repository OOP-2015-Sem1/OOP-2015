
public class Level {
	public String levelSource;
	public String levelName;
	public Level(String levelName, int rows, int columns,String levelSource) {
		super();
		this.levelSource = levelSource;
		this.levelName = levelName;
		this.rows = rows;
		this.columns = columns;
	}
	public int rows, columns;
}
