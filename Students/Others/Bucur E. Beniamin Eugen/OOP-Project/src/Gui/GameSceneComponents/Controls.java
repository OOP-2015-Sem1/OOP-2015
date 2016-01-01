package Gui.GameSceneComponents;

import Data.Game;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controls extends VBox{

    HBox buyButtons = new HBox();
    HBox tradeButtons = new HBox();

    Game game;

    public Controls(Game game){
        this.setPadding(new Insets(10, 2, 0, 2));
        this.setSpacing(10);

        this.game = game;

        setBuyButtons();
        setTradeButtons();

        Button playDevelopmentCard = new Button("Play development card");
        playDevelopmentCard.getStyleClass().add("play-dev-card-button");
        playDevelopmentCard.setMinSize(408, 92);

        this.getChildren().addAll(buyButtons, tradeButtons, playDevelopmentCard);
        this.setMaxWidth(410);
    }

    private void setBuyButtons(){
        buyButtons.setSpacing(2);

        ImageView buyCity = new ImageView("Pics\\Buttons\\City.png");

        ImageView buySettlement = new ImageView("Pics\\Buttons\\Settlement.png");

        ImageView buyRoad = new ImageView("Pics\\Buttons\\Road.png");

        ImageView buyDevCard = new ImageView("Pics\\Buttons\\DevCard.png");

        buyButtons.getChildren().addAll(buyCity, buySettlement, buyRoad, buyDevCard);
    }

    private void setTradeButtons(){
        tradeButtons.setSpacing(5);

        Button trade = new Button("Trade");
        trade.getStyleClass().add("huge-buttons");
        trade.setMinSize(201, 75);

        Button buyResource = new Button("Buy resource");
        buyResource.getStyleClass().add("huge-buttons");
        buyResource.setMinSize(201, 75);

        tradeButtons.getChildren().addAll(trade, buyResource);
    }
}
