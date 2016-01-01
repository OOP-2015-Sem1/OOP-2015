package Gui.GameSceneComponents;

import Data.Game;
import javafx.scene.layout.VBox;

public class LeftBar extends VBox{

    public LeftBar(Game game){
        this.getChildren().addAll(new BuildingCosts(), new Controls(game));
    }
}
