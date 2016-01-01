package Gui.PopUps;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MessageBox {

    public static void display(String title, String message) {
        Stage popUpWindow = new Stage();

        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle(title);
        popUpWindow.setResizable(false);

        Label errorMessage = new Label();
        errorMessage.setText(message);

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("button-mustard");
        okButton.setOnAction(e -> popUpWindow.close());

        VBox layout = new VBox(30);
        layout.setPadding(new Insets(30, 10, 30, 10));
        layout.getChildren().addAll(errorMessage, okButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(MessageBox.class.getResource("..\\Theme.css").toExternalForm());
        popUpWindow.setScene(scene);
        popUpWindow.showAndWait();
    }

}