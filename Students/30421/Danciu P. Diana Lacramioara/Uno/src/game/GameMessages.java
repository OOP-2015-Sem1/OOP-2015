package game;

public interface GameMessages {
	// -7 - Game's finished, you can't do anything else
	public static final int GAME_FINISHED = -7;
	// -6 - It's not players turn yet!!
	public static final int NOT_YOUR_TURN = -6;
	// -5 - The game need to determine the color of the next card
	public static final int SELECT_COLOR_FIRST = -5;
	// -4 - The player tried to draw a card, but deck's empty (pass turn)
	public static final int DECK_EMPTY = -4;
	// -3 - The player drew a card and has a valid play
	public static final int DREW_AND_VALID = -3;
	// -2 - The player drew a card and passed his turn
	public static final int DREW_AND_NO_VALID = -2;
	// -1 - The card selected was not valid and nothing happened
	public static final int CARD_NOT_VALID = -1;
	// 0 - Valid card played: A normal card
	public static final int PLAYED_NORMAL = 0;
	// 1 - Valid card played: Reverse card
	public static final int PLAYED_REVERSE = 1;
	// 2 - Valid card played: Skip card
	public static final int PLAYED_SKIP = 2;
	// 3 - Valid card played: Draw Two card
	public static final int PLAYED_DRAW_2 = 3;
	// 4 - Valid card played: Wild card
	public static final int PLAYED_WILD = 4;
	// 5 - Valid card played: Wild Draw Four card
	public static final int PLAYED_WILD_4 = 5;
	// 6 - Played a zero and the zero rule is on
	public static final int PLAYED_ZERO = 6;
	// 7 - Valid card played: Draw Two, and the next player has a D2 or W4
	public static final int PLAYED_DRAW_2N = 7;
	// 8 - Valid card played: Wild four, adn the next player has a W4
	public static final int PLAYED_WILD_4N = 8;
	// +20 - The previous player has an uno
	public static final int EXISTS_UNO = 20;
	// +35 - The previous player is a winner
	public static final int EXISTS_WINNER = 35;
	// 50+numPlayer - numPlayer has retired from the game
	public static final int PLAYER_RETIRED = 50;
}