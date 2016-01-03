package Gui.GameSceneComponents;

import Data.Game;
import javafx.scene.layout.BorderPane;

public class BottomBar extends BorderPane{

    public BottomBar(Game game){
        Stats stats = new Stats(game);
        game.setStats(stats);

        RollDice rollDice = new RollDice(game);
        game.setRollDice(rollDice);

        EndTurn endTurn = new EndTurn(game);
        game.setEndTurn(endTurn);

        this.setLeft(stats);
        this.setCenter(rollDice);
        this.setRight(endTurn);
    }
}
