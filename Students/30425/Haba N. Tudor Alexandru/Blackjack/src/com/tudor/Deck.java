package com.tudor;


public class Deck {

    final static int DECK_SIZE = 52;
    private Card[] cards;
    private int N = 0;

    public Deck() {
        cards = new Card[DECK_SIZE];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 13; j++) {
                cards[N] = new Card(N, j, i + "" + j + ".png");
                N++;
            }
        }
    }

    public Card dealFrom() {
        return cards[--N];
    }

    public boolean isEmpty() {
        return (N == 0);
    }

    public int size() {
        return N;
    }

    public void shuffle() {
        for (int i = 0; i < N; i++) {
            int r = (int) (Math.random()*i);
            Card swap = cards[i];
            cards[i] = cards[r];
            cards[r] = swap;
        }
    }
}