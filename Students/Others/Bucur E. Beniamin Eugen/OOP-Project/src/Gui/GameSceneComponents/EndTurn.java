package Gui.GameSceneComponents;

import Data.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class EndTurn extends StackPane {

    public EndTurn(Game game) {
        Button endTurn = new Button("END TURN\n\t       ->");
        endTurn.setOnAction(e -> System.out.println("Turn ended"));
        endTurn.getStyleClass().add("end-turn-button-mustard");

        this.getChildren().add(endTurn);
        endTurn.setAlignment(Pos.CENTER);
    }
}
