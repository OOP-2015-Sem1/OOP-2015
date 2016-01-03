package Gui.GameSceneComponents;

import Data.*;
import Gui.PopUps.MessageBox;
import Logic.GameEngine;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.LinkedList;
import java.util.List;

public class GameBoard extends Pane {

    Game game;

    public List<ImageView> tiles = new LinkedList<>();
    public List<ImageView> tokens = new LinkedList<>();
    public List<ImageView> nodes = new LinkedList<>();
    public ImageView robber;

    public GameBoard(Game game) {
        this.game = game;
        this.setPrefSize(BoardProperties.BOARD_WIDTH, BoardProperties.BOARD_HEIGHT);

        setBackground();
        placeRoads();
        placeTiles();
        placePorts();
        placeNodes();
        placeNumberTokens();
        placeRobber();
        placeExistingBuildings();
    }

    private void placeExistingBuildings() {
        for (Node node : game.getBoard().nodes)
            if (node.getOwner() != null)
                if (node.getBuilding() == BuildingType.SETTLEMENT)
                    refreshNode(nodes.get(game.getBoard().nodes.indexOf(node)), node.getOwner().getSettlementPath());
                else
                    refreshNode(nodes.get(game.getBoard().nodes.indexOf(node)), node.getOwner().getCityPath());
        for (Road road : game.getBoard().getRoads()) {
            ImageView newRoad = new ImageView(road.getPathToImage());
            newRoad.relocate(road.getX(), road.getY());
            this.getChildren().add(newRoad);
        }
    }

    private void setBackground() {
        this.setStyle("-fx-background-color: " + BoardProperties.WATER_COLOR);
    }

    private void placeRoads() {
        Image picRoads = new Image("Pics\\Roads.png");
        ImageView roads = new ImageView(picRoads);
        roads.setFitWidth(732);
        roads.setFitHeight(678);
        roads.relocate(51, 50);
        this.getChildren().add(roads);
    }

    private void placeTiles() {
        for (int i = 0; i < 19; i++) {
            tiles.add(new ImageView(game.getBoard().getTile(i).getTilePath()));
            tiles.get(i).setFitHeight(game.getBoard().getTile(i).getTileHeight());
            tiles.get(i).setFitWidth(game.getBoard().getTile(i).getTileWidth());
            tiles.get(i).relocate(game.getBoard().getTile(i).getTileX(), game.getBoard().getTile(i).getTileY());
            this.getChildren().add(tiles.get(i));
        }
    }

    private void placePorts() {
        //in case of BoardProperties ever being used for variable board sizes this will need to be changed

        Image picULeftAny = new Image("Pics\\Ports\\ULeftAny.png");
        Image picLeftOre = new Image("Pics\\Ports\\LeftOre.png");
        Image picLeftGrain = new Image("Pics\\Ports\\LeftGrain.png");
        Image picBLeftAny = new Image("Pics\\Ports\\BLeftAny.png");
        Image picBRightLumber = new Image("Pics\\Ports\\BRightLumber.png");
        Image picBRightBrick = new Image("Pics\\Ports\\BRightBrick.png");
        Image picRightAny = new Image("Pics\\Ports\\RightAny.png");
        Image picURightAny = new Image("Pics\\Ports\\URightAny.png");
        Image picURightSheep = new Image("Pics\\Ports\\URightSheep.png");

        ImageView ULeftAny = new ImageView(picULeftAny);
        ULeftAny.setFitWidth(75);
        ULeftAny.setFitHeight(78);

        ImageView LeftOre = new ImageView(picLeftOre);
        LeftOre.setFitWidth(63);
        LeftOre.setFitHeight(73);

        ImageView LeftGrain = new ImageView(picLeftGrain);
        LeftGrain.setFitWidth(63);
        LeftGrain.setFitHeight(73);

        ImageView BLeftAny = new ImageView(picBLeftAny);
        BLeftAny.setFitWidth(76);
        BLeftAny.setFitHeight(80);

        ImageView BRightLumber = new ImageView(picBRightLumber);
        BRightLumber.setFitWidth(78);
        BRightLumber.setFitHeight(80);

        ImageView BRightBrick = new ImageView(picBRightBrick);
        BRightBrick.setFitWidth(78);
        BRightBrick.setFitHeight(80);

        ImageView RightAny = new ImageView(picRightAny);
        RightAny.setFitWidth(63);
        RightAny.setFitHeight(73);

        ImageView URightAny = new ImageView(picURightAny);
        URightAny.setFitWidth(75);
        URightAny.setFitHeight(78);

        ImageView URightSheep = new ImageView(picURightSheep);
        URightSheep.setFitWidth(75);
        URightSheep.setFitHeight(78);

        ULeftAny.relocate(190, 10);
        LeftOre.relocate(61, 222);
        LeftGrain.relocate(61, 480);
        BLeftAny.relocate(190, 687);
        BRightLumber.relocate(417, 687);
        BRightBrick.relocate(642, 558);
        RightAny.relocate(785, 348);
        URightAny.relocate(645, 139);
        URightSheep.relocate(419, 9);

        this.getChildren().addAll(ULeftAny, LeftOre, LeftGrain, BLeftAny,
                BRightLumber, BRightBrick, RightAny, URightAny, URightSheep);
    }

    private void placeNumberTokens() {
        int robberOffset = 0;
        for (int i = 0; i < 19; i++) {
            if (!game.getBoard().getTile(i).isHasRobber()) {
                tokens.add(new ImageView(game.getBoard().getTile(i).getTokenPath()));
                tokens.get(i - robberOffset).setFitHeight(game.getBoard().getTile(i).getTokenHeight());
                tokens.get(i - robberOffset).setFitWidth(game.getBoard().getTile(i).getTokenWidth());
                tokens.get(i - robberOffset).relocate(game.getBoard().getTile(i).getTokenX(), game.getBoard().getTile(i).getTokenY());
                this.getChildren().add(tokens.get(i - robberOffset));
            } else {
                robberOffset = 1;
            }
        }
    }

    private void placeRobber() {
        robber = new ImageView("Pics\\Tokens\\Robber.png");
        robber.setFitHeight(BoardProperties.TOKEN_HEIGHT);
        robber.setFitWidth(BoardProperties.TOKEN_WIDTH);
        int i = 0;
        while (!game.getBoard().getTile(i).isHasRobber())
            i++;
        robber.relocate(BoardProperties.TOKEN_X[i], BoardProperties.TOKEN_Y[i]);
        game.getBoard().setRobberTile(i);
        this.getChildren().add(robber);
    }

    private void placeNodes() {
        int i = 0;
        for (Data.Node node : game.getBoard().nodes) {
            nodes.add(new ImageView("Pics\\Node.png"));
            nodes.get(i).setFitHeight(node.getNodeHeight());
            nodes.get(i).setFitWidth(node.getNodeWidth());
            nodes.get(i).relocate(node.getNodeX(), node.getNodeY());
            this.getChildren().add(nodes.get(i));
            i++;
        }
    }

    public void refreshNode(ImageView node, String path) {
        node.setImage(new Image(path));
    }
}
