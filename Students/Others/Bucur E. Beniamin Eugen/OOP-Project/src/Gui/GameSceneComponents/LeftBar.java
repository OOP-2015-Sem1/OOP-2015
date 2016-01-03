package Gui.GameSceneComponents;

import Data.Game;
import javafx.scene.layout.VBox;

public class LeftBar extends VBox{

    public LeftBar(Game game){
        Controls controls = new Controls(game);
        game.setControls(controls);

        this.getChildren().addAll(new BuildingCosts(), controls);
    }
}
