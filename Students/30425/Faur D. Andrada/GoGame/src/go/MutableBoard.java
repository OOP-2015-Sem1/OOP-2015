package go;

public interface MutableBoard extends Board {
	int size();

	Stone at(int x, int y);

	void setAt(int x, int y, Stone color);
}
