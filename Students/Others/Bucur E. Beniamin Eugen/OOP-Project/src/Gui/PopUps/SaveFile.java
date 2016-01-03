package Gui.PopUps;

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

public class SaveFile {

    public static String getFileName() {
        Stage window = new Stage(StageStyle.UNDECORATED);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Save game");
        window.setResizable(false);

        Label nameLabel = new Label();
        nameLabel.setText("Please choose a name for your file");

        TextField name = new TextField();
        name.setPromptText("File name");

        Label warningMessage = new Label();
        warningMessage.setText("Note that choosing a name that already exist will overwrite the previous saved game");
        warningMessage.getStyleClass().add("warning-label");

        Button closeButton = new Button("Save game");
        closeButton.getStyleClass().add("button-mustard");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(30);
        layout.setPadding(new Insets(30, 10, 30, 10));
        layout.getChildren().addAll(nameLabel, name, warningMessage, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(SaveFile.class.getResource("..\\Theme.css").toExternalForm());
        window.setScene(scene);
        window.showAndWait();

        return name.getText();
    }
}
