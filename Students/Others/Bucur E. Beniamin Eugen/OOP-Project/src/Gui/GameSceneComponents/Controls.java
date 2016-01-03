package Gui.GameSceneComponents;

import Data.DevelopmentType;
import Data.Game;
import Data.ResourceCards;
import Data.ResourceType;
import Gui.PopUps.DevCardChooser;
import Gui.PopUps.MessageBox;
import Gui.PopUps.ResourceChooser;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controls extends VBox {

    Game game;

    HBox buyButtons = new HBox();
    HBox tradeButtons = new HBox();

    Button playDevelopmentCard;
    ImageView buyCity;
    ImageView buySettlement;
    ImageView buyRoad;
    ImageView buyDevCard;
    Button trade;
    Button buyResource;

    public Controls(Game game) {
        this.setPadding(new Insets(10, 2, 0, 2));
        this.setSpacing(10);

        this.game = game;

        setBuyButtons();
        setTradeButtons();

        playDevelopmentCard = new Button("Play development card");
        playDevelopmentCard.getStyleClass().add("play-dev-card-button");
        playDevelopmentCard.setMinSize(408, 92);
        playDevelopmentCard.setOnMouseClicked(e -> {
            DevelopmentType development = DevCardChooser.get();
            if (game.getActivePlayer().getDevelopmentCards().getNrOfCards(development) > 0){
                game.gameEngine.playDevelopmentCard(development);
            } else {
                MessageBox.display("Error", "You do not own this development card");
            }
        });

        this.getChildren().addAll(buyButtons, tradeButtons, playDevelopmentCard);
        this.setMaxWidth(410);
    }

    private void setBuyButtons() {
        buyButtons.setSpacing(2);

        buyCity = new ImageView("Pics\\Buttons\\City.png");
        buyCity.setOnMouseClicked(e -> {
            if (game.getActivePlayer().getResources().verifyResourceAvailability(0, 0, 0, 2, 3)) {
                game.gameEngine.placeCity();
                game.getActivePlayer().increaseVictoryPoints(1);
            } else {
                MessageBox.display("Error", "You do not have enough resources to build a city");
            }
        });

        buySettlement = new ImageView("Pics\\Buttons\\Settlement.png");
        buySettlement.setOnMouseClicked(e -> {
            if (game.getActivePlayer().getResources().verifyResourceAvailability(1, 1, 1, 1, 0)) {
                game.gameEngine.placeSettlement();
                game.getActivePlayer().increaseVictoryPoints(2);
            } else {
                MessageBox.display("Error", "You do not have enough resources to build a settlement");
            }
        });

        buyRoad = new ImageView("Pics\\Buttons\\Road.png");
        buyRoad.setOnMouseClicked(e -> {
            if (game.getActivePlayer().getResources().verifyResourceAvailability(1, 1, 0, 0, 0)) {
                game.gameEngine.placeRoad();
            } else {
                MessageBox.display("Error", "You do not have enough resources to buy a road");
            }
        });

        buyDevCard = new ImageView("Pics\\Buttons\\DevCard.png");
        buyDevCard.setOnMouseClicked(e -> {
            ResourceCards resources = game.getActivePlayer().getResources();
            if (resources.verifyResourceAvailability(0, 0, 1, 1, 1)) {
                resources.spendResources(0, 0, 1, 1, 1);
                String cardType = game.getActivePlayer().getDevelopmentCards().buyCard();
                MessageBox.display("Success", "You received a " + cardType + " development card");
            } else {
                MessageBox.display("Error", "You do not have enough resources to buy a development card");
            }
        });

        buyButtons.getChildren().addAll(buyCity, buySettlement, buyRoad, buyDevCard);
    }

    private void setTradeButtons() {
        tradeButtons.setSpacing(5);

        trade = new Button("Trade");
        trade.getStyleClass().add("huge-buttons");
        trade.setMinSize(201, 75);
        trade.setOnMouseClicked(e -> {
            ResourceType tradedResource = ResourceChooser.get("Select the resource you want to trade");
            if (game.getActivePlayer().getResources().getCountForResource(tradedResource) >= 4){
                ResourceType desiredResource = ResourceChooser.get("Select the resource you want to get");
                game.getActivePlayer().getResources().incrementResourceCounter(desiredResource, 1);
                game.getActivePlayer().getResources().decrementResourceCounter(tradedResource, 2);
            } else {
                MessageBox.display("Error", "You need to have less than 4 resources of this type");
            }
        });

        buyResource = new Button("Buy resource");
        buyResource.getStyleClass().add("huge-buttons");
        buyResource.setMinSize(201, 75);

        tradeButtons.getChildren().addAll(trade, buyResource);
    }

    public void setDisable() {
        buyCity.setDisable(true);
        buySettlement.setDisable(true);
        buyRoad.setDisable(true);
        buyDevCard.setDisable(true);
        trade.setDisable(true);
        buyResource.setDisable(true);
    }

    public void setEnable() {
        buyCity.setDisable(false);
        buySettlement.setDisable(false);
        buyRoad.setDisable(false);
        buyDevCard.setDisable(false);
        trade.setDisable(false);
        buyResource.setDisable(false);
    }
}
