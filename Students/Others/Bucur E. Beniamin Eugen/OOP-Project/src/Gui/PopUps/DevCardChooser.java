package Gui.PopUps;

import Data.DevelopmentType;
import Data.ResourceType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DevCardChooser {

    public static DevelopmentType get() {
        Stage window = new Stage(StageStyle.UNDECORATED);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setResizable(false);

        VBox layout = new VBox(30);
        layout.setPadding(new Insets(30, 10, 30, 10));

        Label nameLabel = new Label();
        nameLabel.setText("Choose the development card you want to play");

        final DevelopmentType[] development = new DevelopmentType[1];

        Button soldier = new Button("Soldier");
        soldier.setOnMouseClicked(event -> {
            development[0] = DevelopmentType.SOLDIER;
            window.close();
        });

        Button victoryPoint = new Button("Victory point");
        victoryPoint.setOnMouseClicked(event -> {
            development[0] = DevelopmentType.VICTORY_POINT;
            window.close();
        });

        Button chooseWool = new Button("Monopoly");
        chooseWool.setOnMouseClicked(event -> {
            development[0] = DevelopmentType.MONOPOLY;
            window.close();
        });

        Button roadBuilding = new Button("Road building");
        roadBuilding.setOnMouseClicked(event -> {
            development[0] = DevelopmentType.ROAD_BUILDING;
            window.close();
        });

        Button yearOfPlenty = new Button("Year of plenty");
        yearOfPlenty.setOnMouseClicked(event -> {
            development[0] = DevelopmentType.YEAR_OF_PLENTY;
            window.close();
        });

        layout.getChildren().addAll(nameLabel, soldier, victoryPoint, chooseWool, roadBuilding, yearOfPlenty);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(SaveFile.class.getResource("..\\Theme.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();

        return development[0];
    }
}
