package Gui.GameSceneComponents;

import Data.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class EndTurn extends StackPane {

    Button endTurn;

    public EndTurn(Game game) {
        endTurn = new Button("END TURN\n\t       ->");
        endTurn.setOnAction(e -> {
            game.gameEngine.incrementTurnCount();
            game.gameEngine.playTurn();
            game.getControls().setDisable();
            this.setDisable();
        });
        endTurn.getStyleClass().add("end-turn-button-mustard");

        this.getChildren().add(endTurn);
        endTurn.setAlignment(Pos.CENTER);
    }

    public void setDisable(){
        endTurn.setDisable(true);
    }

    public void setEnable(){
        endTurn.setDisable(false);
    }
}
