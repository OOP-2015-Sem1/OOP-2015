import java.awt.Font;

public class Constants {

	public static final int BORDER_WIDTH = 5;// cat de groasa ii linia
	public static final int G_w=700;
	public static final int G_h = 450;

	public static final int COUNT_COL = 10;// numar coloane desenate
	public static final int ROW_COUNT = 10;// numar total randuri

	public static final int TILE_SIZE = 40; // number of pixels for a tile

	public static final int CENTER_X = COUNT_COL * TILE_SIZE / 2;
	public static final int CENTER_Y = ROW_COUNT * TILE_SIZE / 2;

	// total pannel width
	public static final int PANEL_WIDTH = COUNT_COL * TILE_SIZE + BORDER_WIDTH * 2;

	// total hight of panel
	public static final int PANEL_HEIGHT = ROW_COUNT * TILE_SIZE + BORDER_WIDTH * 2;

	// font display
	public static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 16);
	public static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);
	public static final Font BIG_FONT = new Font("Tahoma", Font.BOLD, 50);
	
	public static final int NEWX = COUNT_COL * TILE_SIZE + TILE_SIZE;
	public static final int NEWY = (ROW_COUNT / 2 + 1) * TILE_SIZE;
	public static final int LENGHT=10;

}
