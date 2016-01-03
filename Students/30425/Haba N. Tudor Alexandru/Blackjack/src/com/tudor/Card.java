package com.tudor;


public class Card {

    @SuppressWarnings("unused")
	private int cardNumber;
    private int rank;
    private String front;

    Card(int cardNumber, int rank, String front) {
        this.cardNumber = cardNumber;
        this.rank = rank;
        this.front = front;
    }

    public boolean isAce() {
        return rank == 0;
    }

    public int rank() {
        if (rank == 0) {
            return 1;
        }
        if (rank >= 9) {
            return 10;
        }
        return rank + 1;
    }

    public String toString() {
        return this.front;
    }
}
