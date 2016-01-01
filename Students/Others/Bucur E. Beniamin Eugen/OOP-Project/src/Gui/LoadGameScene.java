package Gui;

import Data.Game;
import Gui.PopUps.MessageBox;
import Logic.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.List;

public class LoadGameScene {

    Scene loadGameScene;

    public LoadGameScene() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(40, 10, 40, 10));
        layout.setVgap(8);
        layout.setHgap(10);

        Label selectSavedGame = new Label("Select the game that you would like to load from the following list:");
        GridPane.setConstraints(selectSavedGame, 1, 1);

        File folder = new File("Saved Games");
        ListView<String> filesList = new ListView<>();
        filesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        ObservableList<File> rawFiles = FXCollections.observableArrayList(folder.listFiles());
        ObservableList<String> fileList = FXCollections.observableArrayList();
        for (File rawFile : rawFiles) {
            if (rawFile.getName().contains(".cat")) {
                fileList.add(rawFile.getName().replace(".cat", ""));
            }
        }
        filesList.setItems(fileList);
        GridPane.setConstraints(filesList, 1, 2);

        Button deleteSavedGame = new Button("Delete game");
        deleteSavedGame.setOnAction(e -> {
            try {
                File file = new File("Saved Games\\" +
                        filesList.getSelectionModel().getSelectedItem() + ".cat");
                if (file.delete()) {
                    MessageBox.display("Success", "\t\t\t Delete operation successful.\nChanges will be visible the " +
                            "next time you open the game.");
                } else {
                    MessageBox.display("Error", "Delete operation failed.");
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        });
        GridPane.setConstraints(deleteSavedGame, 1, 3);

        Button switchToNewGameScene = new Button("New Game");
        switchToNewGameScene.getStyleClass().add("button-mustard");
        switchToNewGameScene.setOnAction(e -> {
            NewGameScene scene = new NewGameScene();
            Main.mainWindow.setScene(scene.newGameScene);
            Main.mainWindow.centerOnScreen();
        });
        GridPane.setConstraints(switchToNewGameScene, 2, 3);

        Button startGame = new Button("Load Game");
        startGame.setOnAction(e -> {
            try {
                FileInputStream loadFile = new FileInputStream("Saved Games\\" +
                        filesList.getSelectionModel().getSelectedItem() + ".cat");
                ObjectInputStream load = new ObjectInputStream(loadFile);
                Game loadedGame = (Game) load.readObject();
                load.close();

                GameScene scene = new GameScene(loadedGame);
                Main.mainWindow.setScene(scene.gameScene);
                Main.mainWindow.centerOnScreen();
            } catch (Exception exc) {
                MessageBox.display("Nothing selected", "You have to select something, genius.");
            }
        });
        GridPane.setConstraints(startGame, 3, 3);


        layout.getChildren().addAll(selectSavedGame, filesList, deleteSavedGame, switchToNewGameScene, startGame);
        BorderPane center = new BorderPane();
        center.setCenter(layout);
        loadGameScene = new Scene(center, 730, 290);
        loadGameScene.getStylesheets().add(LoadGameScene.class.getResource("Theme.css").toExternalForm());

    }
}
