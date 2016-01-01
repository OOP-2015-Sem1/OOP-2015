package Gui.GameSceneComponents;

import Data.Dice;
import Data.Game;
import Data.ResourceType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class RollDice extends HBox {

    Button rollDiceButton = new Button("Roll Dice");

    public RollDice(Game game){
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(5);

        VBox diceContainer = new VBox();
        diceContainer.setSpacing(5);

        ImageView redDieView = new ImageView(game.getDice().getRedDieImage(6));

        ImageView yellowDieView = new ImageView(game.getDice().getYellowDieImage(6));

        diceContainer.getChildren().addAll(redDieView, yellowDieView);

        rollDiceButton.getStyleClass().add("roll-button");
        rollDiceButton.setMinHeight(85);
        rollDiceButton.setOnAction(e -> {
            game.getDice().rollDice();
            redDieView.setImage(new Image(game.getDice().getRedDieImage(game.getDice().getRedDie())));
            yellowDieView.setImage(new Image(game.getDice().getYellowDieImage(game.getDice().getYellowDie())));
        });

        this.getChildren().addAll(diceContainer, rollDiceButton);
        this.setMinWidth(206);
    }

    public void setEnable(){
        rollDiceButton.setDisable(false);
    }

    public void setDisable(){
        rollDiceButton.setDisable(true);
    }
}
