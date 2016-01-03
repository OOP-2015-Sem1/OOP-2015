package Data;

import java.io.Serializable;

public class BoardProperties implements Serializable{
    public static final int[] TILE_X = {199, 348, 497, 125, 274, 423, 572, 50, 199, 348, 497, 646, 125, 274, 423, 572, 199, 348, 497};
    public static final int[] TILE_Y = {50, 50, 50, 179, 179, 179, 179, 308, 308, 308, 308, 308, 437, 437, 437, 437, 566, 566, 566};
    public static final int TILE_HEIGHT = 160;
    public static final int TILE_WIDTH = 139;

    public static final int TOKEN_HEIGHT = 70;
    public static final int TOKEN_WIDTH = 70;
    public static final int[] TOKEN_X = {233, 382, 531, 159, 308, 457, 606, 84, 233, 382, 531, 680, 159, 308, 457, 606, 233, 382, 531};
    public static final int[] TOKEN_Y = {95, 95, 95, 224, 224, 224, 224, 353, 353, 353, 353, 353, 482, 482, 482, 482, 611, 611, 611};

    public static final int NODE_HEIGHT = 30;
    public static final int NODE_WIDTH = 30;

    public static final String WATER_COLOR = "#5abcd8";
    public static final int BOARD_HEIGHT = 776;
    public static final int BOARD_WIDTH = 790;


}
