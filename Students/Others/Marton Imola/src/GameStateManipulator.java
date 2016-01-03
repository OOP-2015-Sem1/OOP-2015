
public interface GameStateManipulator {
	void decrementLife(int amount);
	int getCurrentLife();
	void userActionPerformed();
}
