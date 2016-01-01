package Gui;


import Data.Game;
import Gui.GameSceneComponents.*;
import Logic.GameEngine;
import Logic.Main;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class GameScene {

    public Scene gameScene;

    public GameScene(Game game){
        BorderPane layout = new BorderPane();

        TopMenu topMenu = new TopMenu(game);
        layout.setTop(topMenu);

        GameBoard gameBoard = new GameBoard(game);
        layout.setCenter(gameBoard);
        game.setGameBoard(gameBoard);

        LeftBar leftBar = new LeftBar(game);
        layout.setRight(leftBar);

        BottomBar bottomBar = new BottomBar(game);
        layout.setBottom(bottomBar);

        gameScene = new Scene(layout, 1300, 948);
        gameScene.getStylesheets().add(LoadGameScene.class.getResource("Theme.css").toExternalForm());

        Main.mainWindow.setScene(gameScene);
        Main.mainWindow.centerOnScreen();
    }
}
