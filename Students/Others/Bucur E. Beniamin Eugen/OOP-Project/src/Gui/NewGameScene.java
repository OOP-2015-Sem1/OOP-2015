package Gui;

import Data.Game;
import Data.PlayerColor;
import Gui.PopUps.MessageBox;
import Logic.GameEngine;
import Logic.Main;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;
import java.util.List;

public class NewGameScene {

    Scene newGameScene;
    TextField redPlayer = new TextField();
    TextField bluePlayer = new TextField();
    TextField orangePlayer = new TextField();
    TextField greyPlayer = new TextField();

    public NewGameScene() {
        GridPane layout = new GridPane();
        layout.setPadding(new Insets(40, 10, 40, 10));
        layout.setVgap(8);
        layout.setHgap(10);

        Label insertNames = new Label("Please insert the names of the players:   ");
        GridPane.setConstraints(insertNames, 1, 0);


        redPlayer.setPromptText("Red player");
        GridPane.setConstraints(redPlayer, 0, 1);

        GridPane redContainer = redPlayerIcons();
        GridPane.setConstraints(redContainer, 1, 1);

        bluePlayer.setPromptText("Blue player");
        GridPane.setConstraints(bluePlayer, 0, 2);

        GridPane blueContainer = bluePlayerIcons();
        GridPane.setConstraints(blueContainer, 1, 2);

        orangePlayer.setPromptText("Orange player");
        GridPane.setConstraints(orangePlayer, 0, 3);

        GridPane orangeContainer = orangePlayerIcons();
        GridPane.setConstraints(orangeContainer, 1, 3);

        greyPlayer.setPromptText("Grey player");
        GridPane.setConstraints(greyPlayer, 0, 4);

        GridPane greyContainer = greyPlayerIcons();
        GridPane.setConstraints(greyContainer, 1, 4);

        Button switchToLoadGameScene = new Button("Load game");
        switchToLoadGameScene.getStyleClass().add("button-mustard");
        switchToLoadGameScene.setOnAction(e -> {
            LoadGameScene scene = new LoadGameScene();
            Main.mainWindow.setScene(scene.loadGameScene);
        });
        GridPane.setConstraints(switchToLoadGameScene, 2, 5);

        Button startGame = new Button("Start Game");
        startGame.setOnAction(e -> {
            if (colorsUsed().size() > 2) {
                if (namesUnique()) {
                    if (namesRespectLength()) {
                        Game newGame = new Game(playerList(), colorsUsed());
                        GameScene scene = new GameScene(newGame);
                        newGame.gameEngine.startingSequence();
                    } else {
                        MessageBox.display("Error", "Names must have at most 10 characters");
                    }
                } else {
                    MessageBox.display("Error", "Two or more players cannot have the same name");
                }
            } else {
                MessageBox.display("Error", "A game requires three or four players");
            }
        });
        GridPane.setConstraints(startGame, 3, 5);

        layout.getChildren().addAll(redPlayer, bluePlayer, orangePlayer, greyPlayer);
        layout.getChildren().addAll(insertNames, switchToLoadGameScene, startGame);
        layout.getChildren().addAll(redContainer, blueContainer, orangeContainer, greyContainer);
        BorderPane center = new BorderPane();
        center.setCenter(layout);
        newGameScene = new Scene(center, 730, 290);
        newGameScene.getStylesheets().add(NewGameScene.class.getResource("Theme.css").toExternalForm());
        Main.mainWindow.centerOnScreen();
    }

    private GridPane redPlayerIcons() {
        GridPane redContainer = new GridPane();
        redContainer.setHgap(20);
        redContainer.setPadding(new Insets(0, 0, 0, 10));
        ImageView redSettlement = new ImageView("Pics\\Buildings\\RedSettlement.png");
        ImageView redCity = new ImageView("Pics\\Buildings\\RedCity.png");

        redCity.setFitHeight(25);
        redCity.setFitWidth(25);
        GridPane.setConstraints(redCity, 0, 0);

        redSettlement.setFitHeight(25);
        redSettlement.setFitWidth(25);
        GridPane.setConstraints(redSettlement, 1, 0);

        redContainer.getChildren().addAll(redCity, redSettlement);

        return redContainer;
    }

    private GridPane bluePlayerIcons() {
        GridPane blueContainer = new GridPane();
        blueContainer.setHgap(20);
        blueContainer.setPadding(new Insets(0, 0, 0, 10));
        ImageView blueSettlement = new ImageView("Pics\\Buildings\\BlueSettlement.png");
        ImageView blueCity = new ImageView("Pics\\Buildings\\BlueCity.png");
        blueCity.setFitHeight(25);
        blueCity.setFitWidth(25);
        GridPane.setConstraints(blueCity, 0, 0);

        blueSettlement.setFitHeight(25);
        blueSettlement.setFitWidth(25);
        GridPane.setConstraints(blueSettlement, 1, 0);

        blueContainer.getChildren().addAll(blueCity, blueSettlement);

        return blueContainer;
    }

    private GridPane orangePlayerIcons() {
        GridPane orangeContainer = new GridPane();
        orangeContainer.setHgap(20);
        orangeContainer.setPadding(new Insets(0, 0, 0, 10));
        ImageView orangeSettlement = new ImageView("Pics\\Buildings\\OrangeSettlement.png");
        ImageView orangeCity = new ImageView("Pics\\Buildings\\OrangeCity.png");

        orangeCity.setFitHeight(25);
        orangeCity.setFitWidth(25);
        GridPane.setConstraints(orangeCity, 0, 0);

        orangeSettlement.setFitHeight(25);
        orangeSettlement.setFitWidth(25);
        GridPane.setConstraints(orangeSettlement, 1, 0);

        orangeContainer.getChildren().addAll(orangeCity, orangeSettlement);

        return orangeContainer;
    }

    private GridPane greyPlayerIcons() {
        GridPane greyContainer = new GridPane();
        greyContainer.setHgap(20);
        greyContainer.setPadding(new Insets(0, 0, 0, 10));
        ImageView greySettlement = new ImageView("Pics\\Buildings\\GreySettlement.png");
        ImageView greyCity = new ImageView("Pics\\Buildings\\GreyCity.png");

        greyCity.setFitHeight(25);
        greyCity.setFitWidth(25);
        GridPane.setConstraints(greyCity, 0, 0);

        greySettlement.setFitHeight(25);
        greySettlement.setFitWidth(25);
        GridPane.setConstraints(greySettlement, 1, 0);

        greyContainer.getChildren().addAll(greyCity, greySettlement);

        return greyContainer;
    }

    private boolean namesUnique() {
        return !(redPlayer.getText().equals(bluePlayer.getText()) || redPlayer.getText().equals(orangePlayer.getText()) ||
                redPlayer.getText().equals(greyPlayer.getText()) || bluePlayer.getText().equals(orangePlayer.getText()) ||
                bluePlayer.getText().equals(greyPlayer.getText()) || orangePlayer.getText().equals(greyPlayer.getText()));
    }

    private boolean namesRespectLength() {
        return redPlayer.getText().length() <= 10 && bluePlayer.getText().length() <= 10 &&
                orangePlayer.getText().length() <= 10 && greyPlayer.getText().length() <= 10;
    }

    private List<PlayerColor> colorsUsed() {
        List<PlayerColor> colorsUsed = new LinkedList<>();
        if (!redPlayer.getText().equals("")) {
            colorsUsed.add(PlayerColor.RED);
        }
        if (!bluePlayer.getText().equals("")) {
            colorsUsed.add(PlayerColor.BLUE);
        }
        if (!orangePlayer.getText().equals("")) {
            colorsUsed.add(PlayerColor.ORANGE);
        }
        if (!greyPlayer.getText().equals("")) {
            colorsUsed.add(PlayerColor.GREY);
        }
        return colorsUsed;
    }

    private List<String> playerList() {
        List<String> playerList = new LinkedList<>();
        if (!redPlayer.getText().equals("")) {
            playerList.add(redPlayer.getText());
        }
        if (!bluePlayer.getText().equals("")) {
            playerList.add(bluePlayer.getText());
        }
        if (!orangePlayer.getText().equals("")) {
            playerList.add(orangePlayer.getText());
        }
        if (!greyPlayer.getText().equals("")) {
            playerList.add(greyPlayer.getText());
        }
        return playerList;
    }
}
