package go;

import go.GameBoard.State;

import java.util.ArrayList;

/**
 * A collection of adjacent Stone(s).
 *
 */
public class Chain {

public ArrayList<Stone> stones;
public State state;

public Chain(State state) {
    stones = new ArrayList<>();
}

public int getLiberties() {
    int total = 0;
    for (Stone stone : stones) {
        total += stone.liberties;
    }
    return total;
}

public void addStone(Stone stone) {
    stone.chain = this;
    stones.add(stone);
}

public void join(Chain chain) {
    for (Stone stone : chain.stones) {
        addStone(stone);
    }
}

}