package Gui.GameSceneComponents;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class BuildingCosts extends StackPane{

    public BuildingCosts(){
        ImageView costs = new ImageView("Pics\\BuildingCosts.png");
        this.getChildren().add(costs);
    }
}
