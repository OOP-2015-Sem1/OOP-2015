package Gui.GameSceneComponents;

import Data.BoardProperties;
import Data.Game;
import Data.ResourceType;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.Serializable;


public class Stats extends VBox implements Serializable {

    HBox resources = new HBox();
    HBox player = new HBox();

    Label brickCount = new Label("Brick: ");
    Label lumberCount = new Label("Lumber: ");
    Label woolCount = new Label("Wool: ");
    Label grainCount = new Label("Grain: ");
    Label oreCount = new Label("Ore: ");

    Label victoryPoints = new Label("Victory Points: ");
    Label armySize = new Label("Army size: ");
    Label longestRoadLength = new Label("Length of longest Road: ");
    Label activePlayer = new Label("Active Player: ");

    Label instructions = new Label("Instructions: ");

    Game game;

    public Stats(Game game) {
        this.game = game;

        this.setPadding(new Insets(5, 40, 5, 3));
        this.setSpacing(7);

        setStyles();
        setResources();
        setPlayer();

        this.getChildren().addAll(resources, player, instructions);
        this.setMinSize(740, 0);
    }

    private void setStyles() {
        brickCount.getStyleClass().add("stats-label");
        lumberCount.getStyleClass().add("stats-label");
        woolCount.getStyleClass().add("stats-label");
        grainCount.getStyleClass().add("stats-label");
        oreCount.getStyleClass().add("stats-label");
        victoryPoints.getStyleClass().add("stats-label");
        armySize.getStyleClass().add("stats-label");
        longestRoadLength.getStyleClass().add("stats-label");
        activePlayer.getStyleClass().add("stats-label");
        instructions.getStyleClass().add("stats-label");
    }

    private void setResources() {
        resources.setSpacing(112);

        resources.getChildren().addAll(brickCount, lumberCount, woolCount, grainCount, oreCount);
    }

    private void setPlayer() {
        player.setSpacing(17);

        player.getChildren().addAll(activePlayer, victoryPoints, armySize, longestRoadLength);
    }

    public void update(String instructionText) {
        brickCount.setText(String.format("Brick: %d", game.getActivePlayer().getResources().getCountForResource(ResourceType.BRICK)));
        lumberCount.setText(String.format("Lumber: %d", game.getActivePlayer().getResources().getCountForResource(ResourceType.LUMBER)));
        woolCount.setText(String.format("Wool: %d", game.getActivePlayer().getResources().getCountForResource(ResourceType.WOOL)));
        grainCount.setText(String.format("Grain: %d", game.getActivePlayer().getResources().getCountForResource(ResourceType.GRAIN)));
        oreCount.setText(String.format("Ore: %d", game.getActivePlayer().getResources().getCountForResource(ResourceType.ORE)));

        victoryPoints.setText(String.format("Victory Points: %d", game.getActivePlayer().getVictoryPoints()));
        armySize.setText(String.format("Army size: %d", game.getActivePlayer().getArmySize()));
        longestRoadLength.setText(String.format("Length of longest road: %d", game.getActivePlayer().getLongestRoad()));
        activePlayer.setText("Active Player: " + game.getActivePlayer().getPlayerName());

        instructions.setText("Instructions: " + instructionText);
    }
}
