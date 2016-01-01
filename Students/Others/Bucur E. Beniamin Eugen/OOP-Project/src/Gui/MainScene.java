package Gui;

import Logic.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.Random;

public class MainScene {

    Scene launchMenu;

    public MainScene() {
        VBox mainLayout = new VBox();
        BorderPane buttonLayout = new BorderPane();
        mainLayout.setPadding(new Insets(10, 10, 10, 10));
        buttonLayout.setPadding(new Insets(0, 10, 0, 10));

        Image neverSettle = new Image("Pics\\MainMenu.png");
        ImageView mainMenuImage = new ImageView(neverSettle);

        Button newGameButton = new Button("Start new game");
        newGameButton.getStyleClass().add("main-menu-buttons");
        newGameButton.setOnAction(e -> {
            NewGameScene scene = new NewGameScene();
            Main.mainWindow.setScene(scene.newGameScene);
            Main.mainWindow.centerOnScreen();
        });

        Button loadGameButton = new Button("Load saved game");
        loadGameButton.getStyleClass().add("main-menu-buttons");
        loadGameButton.setOnAction(e -> {
            LoadGameScene scene = new LoadGameScene();
            Main.mainWindow.setScene(scene.loadGameScene);
            Main.mainWindow.centerOnScreen();
        });

        buttonLayout.setLeft(newGameButton);
        buttonLayout.setRight(loadGameButton);

        mainLayout.getChildren().addAll(mainMenuImage, buttonLayout);
        launchMenu = new Scene(mainLayout, 630, 520);
        launchMenu.getStylesheets().add(MainScene.class.getResource("Theme.css").toExternalForm());
        Main.mainWindow.setScene(launchMenu);
    }


}