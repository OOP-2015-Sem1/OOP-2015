package Solitaire;

import java.util.ArrayList;

public class LegalGameStates {
    private ArrayList<GameState> gameStates = new ArrayList<GameState>();
    int gsIdx=0;

    public LegalGameStates(ArrayList<GameState> gameStates) {
        super();
        this.gameStates = gameStates;
        this.gsIdx=0;
        
    }
    public LegalGameStates(){
        
    }
    public void addGameStates(GameState gameState){
        this.gameStates.add(gameState);
    }
    public ArrayList<GameState> getGameStates() {
        return gameStates;
    }
    public void setGameStates(ArrayList<GameState> gameStates) {
        this.gameStates = gameStates;
    }
    public int getGsIdx() {
        return gsIdx;
    }
    public void setGsIdx(int gsIdx) {
        this.gsIdx = gsIdx;
    }
}
