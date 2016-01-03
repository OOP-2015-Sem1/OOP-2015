package Data;

import Data.Board;
import Data.Player;
import Gui.GameSceneComponents.*;
import Logic.GameEngine;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Game implements Serializable{
    Player activePlayer;
    List<Player> playersInTheGame = new LinkedList<>();
    List<Player> startingSequence = new LinkedList<>();
    Board board;
    Dice dice;
    public GameEngine gameEngine;

    transient Stats stats;
    transient GameBoard gameBoard;
    transient RollDice rollDice;
    transient Controls controls;
    transient EndTurn endTurn;

    public Game(List<String> playerNames, List<PlayerColor> colorsUsed) {
        for (int i = 0; i < playerNames.size(); i++) {
            playersInTheGame.add(new Player(playerNames.get(i), colorsUsed.get(i)));
            startingSequence.add(new Player(playerNames.get(i), colorsUsed.get(i)));
        }
        board = new Board();
        dice = new Dice();
        generateStartingSequence();
        gameEngine = new GameEngine(this);
    }

    private void generateStartingSequence() {
        Collections.shuffle(startingSequence);
        for (int i = startingSequence.size() - 1; i >= 0; i--){
            startingSequence.add(startingSequence.get(i));
        }
    }

    public List<Player> getStartingSequence() {
        return startingSequence;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Stats getStats() {
        return stats;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<Player> getPlayersInTheGame() {
        return playersInTheGame;
    }

    public Board getBoard(){
        return board;
    }

    public Dice getDice() {
        return dice;
    }

    public RollDice getRollDice() {
        return rollDice;
    }

    public void setRollDice(RollDice rollDice) {
        this.rollDice = rollDice;
    }

    public EndTurn getEndTurn() {
        return endTurn;
    }

    public void setEndTurn(EndTurn endTurn) {
        this.endTurn = endTurn;
    }

    public Controls getControls() {
        return controls;
    }

    public void setControls(Controls controls) {
        this.controls = controls;
    }


}