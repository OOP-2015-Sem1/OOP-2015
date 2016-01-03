package Gui.PopUps;

import Data.DevelopmentType;
import Data.ResourceType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ResourceChooser {

    public static ResourceType get(String message) {
        Stage window = new Stage(StageStyle.UNDECORATED);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        VBox layout = new VBox(30);
        layout.setPadding(new Insets(30, 10, 30, 10));

        Label nameLabel = new Label();
        nameLabel.setText(message);

        final ResourceType[] resource = new ResourceType[1];

        Button chooseBrick = new Button("Choose Brick");
        chooseBrick.setOnMouseClicked(event -> {
            resource[0] = ResourceType.BRICK;
            window.close();
        });

        Button chooseLumber = new Button("Choose Lumber");
        chooseLumber.setOnMouseClicked(event -> {
            resource[0] = ResourceType.LUMBER;
            window.close();
        });

        Button chooseWool = new Button("Choose Wool");
        chooseWool.setOnMouseClicked(event -> {
            resource[0] = ResourceType.WOOL;
            window.close();
        });

        Button chooseGrain = new Button("Choose Grain");
        chooseGrain.setOnMouseClicked(event -> {
            resource[0] = ResourceType.GRAIN;
            window.close();
        });

        Button chooseOre = new Button("Choose Ore");
        chooseOre.setOnMouseClicked(event -> {
            resource[0] = ResourceType.ORE;
            window.close();
        });

        layout.getChildren().addAll(nameLabel, chooseBrick, chooseLumber, chooseWool, chooseGrain, chooseOre);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(SaveFile.class.getResource("..\\Theme.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();

        return resource[0];
    }

}
