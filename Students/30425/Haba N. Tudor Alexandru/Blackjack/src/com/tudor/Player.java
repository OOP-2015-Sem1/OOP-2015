package com.tudor;


public class Player {

    final static int MAX_CARDS = 52;
    public Card[] cards = new Card[MAX_CARDS];
    private int N = 0;
    @SuppressWarnings("unused")
	private String name;

    public Player(String name) {
        this.name = name;
    }

    public int inHand() {
        return N;
    }

    public Card dealTo(Card c) {
        cards[N++] = c;
        return c;
    }

    public void reset() {
        N = 0;
    }

    public int value() {
        int val = 0;
        boolean hasAce = false;
        for (int i = 0; i < N; i++) {
            val = val + cards[i].rank();
            if (cards[i].isAce()) {
                hasAce = true;
            }
        }
        if (hasAce && (val <= 11)) {
            val = val + 10;
        }
        return val;
    }
   
}