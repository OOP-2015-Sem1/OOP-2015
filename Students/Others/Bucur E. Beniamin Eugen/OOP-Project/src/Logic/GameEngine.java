package Logic;

import Data.*;
import Gui.PopUps.MessageBox;
import Gui.PopUps.ResourceChooser;
import javafx.scene.image.ImageView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameEngine implements Serializable {

    Game game;
    int turnCount = 0;
    boolean devCardPlayed;

    public GameEngine(Game game) {
        this.game = game;
    }

    public void playTurn() {
        devCardPlayed = false;
        game.setActivePlayer(game.getPlayersInTheGame().get(turnCount % game.getPlayersInTheGame().size()));
        game.getStats().update("Roll the dice");
        game.getRollDice().setEnable();
        if (game.getActivePlayer().getVictoryPoints() >= 10)
            gameWon();
    }

    private void gameWon() {
        MessageBox.display("Congratulations", "Player " + game.getActivePlayer().getPlayerName() + " has won!");
    }

    public void startingSequence() {
        game.getRollDice().setDisable();
        if (turnCount < game.getStartingSequence().size()) {
            game.setActivePlayer(game.getStartingSequence().get(turnCount));
            placeFreeSettlement();
        } else {
            generateAllResources();
            playTurn();
        }
    }

    public void dispatchResources() {
        for (Tile tile : game.getBoard().getTiles()) {
            if (tile.getToken() == game.getDice().getValue())
                for (Node node : tile.getAdjacentNodes()) {
                    if (node.getOwner() != null) {
                        if (node.getBuilding() == BuildingType.CITY)
                            node.getOwner().getResources().incrementResourceCounter(tile.getResourceType(), 2);
                        else if (node.getBuilding() == BuildingType.SETTLEMENT)
                            node.getOwner().getResources().incrementResourceCounter(tile.getResourceType(), 1);
                    }
                }
        }
    }

    private void generateAllResources() {
        for (Tile tile : game.getBoard().getTiles())
            for (Node node : tile.getAdjacentNodes())
                if (node.getOwner() != null)
                    node.getOwner().getResources().incrementResourceCounter(tile.getResourceType(), 1);
    }

    private void placeFreeSettlement() {
        game.getStats().update("Choose a node to place the settlement");
        for (ImageView node : game.getGameBoard().nodes) {
            node.setOnMouseClicked(e -> {
                Node buildingNode = game.getBoard().nodes.get(game.getGameBoard().nodes.indexOf(node));
                if (legalInitialSettlement(buildingNode)) {
                    buildingNode.setBuilding(BuildingType.SETTLEMENT);
                    buildingNode.setOwner(game.getActivePlayer());
                    game.getGameBoard().refreshNode(node, game.getActivePlayer().getSettlementPath());
                    game.getActivePlayer().increaseVictoryPoints(1);
                    placeFreeRoad();
                } else {
                    MessageBox.display("Error", "You cannot place a settlement here");
                    placeFreeSettlement();
                }
            });
        }
    }

    public void placeSettlement() {
        game.getStats().update("Choose a node to place the settlement");
        for (ImageView node : game.getGameBoard().nodes) {
            node.setOnMouseClicked(e -> {
                Node buildingNode = game.getBoard().nodes.get(game.getGameBoard().nodes.indexOf(node));
                if (legalSettlement(buildingNode)) {
                    game.getActivePlayer().getResources().spendResources(1, 1, 1, 1, 0);
                    buildingNode.setBuilding(BuildingType.SETTLEMENT);
                    buildingNode.setOwner(game.getActivePlayer());
                    game.getGameBoard().refreshNode(node, game.getActivePlayer().getSettlementPath());
                    game.getActivePlayer().increaseVictoryPoints(1);
                } else {
                    MessageBox.display("Error", "You cannot place a settlement here");
                    placeSettlement();
                }
            });
        }
    }

    public void placeCity() {
        game.getStats().update("Choose a node to place the city");
        for (ImageView node : game.getGameBoard().nodes) {
            node.setOnMouseClicked(e -> {
                Node buildingNode = game.getBoard().nodes.get(game.getGameBoard().nodes.indexOf(node));
                if (legalCity(buildingNode)) {
                    game.getActivePlayer().getResources().spendResources(0, 0, 0, 2, 3);
                    buildingNode.setBuilding(BuildingType.SETTLEMENT);
                    buildingNode.setOwner(game.getActivePlayer());
                    game.getGameBoard().refreshNode(node, game.getActivePlayer().getCityPath());
                    game.getActivePlayer().increaseVictoryPoints(2);
                } else {
                    MessageBox.display("Error", "You cannot place a city here");
                    placeCity();
                }
            });
        }
    }

    private void placeFreeRoad() {
        game.getStats().update("Choose the starting node for your road");
        for (ImageView start : game.getGameBoard().nodes) {
            start.setOnMouseClicked(e -> {
                game.getStats().update("Choose the ending node for your road");
                for (ImageView end : game.getGameBoard().nodes) {
                    end.setOnMouseClicked(evt -> {
                        Node startingNode = game.getBoard().nodes.get(game.getGameBoard().nodes.indexOf(start));
                        Node endingNode = game.getBoard().nodes.get(game.getGameBoard().nodes.indexOf(end));
                        if (game.gameEngine.legalRoad(startingNode, endingNode)) {
                            Road road = new Road(game.getActivePlayer(), startingNode, endingNode);
                            game.getActivePlayer().addRoad(road);
                            game.getBoard().addRoad(road);
                            ImageView newRoad = new ImageView(road.getPathToImage());
                            newRoad.relocate(road.getX(), road.getY());
                            game.getGameBoard().getChildren().add(newRoad);
                            startingNode.addNeighbouringRoad(road);
                            endingNode.addNeighbouringRoad(road);
                            turnCount++;
                            startingSequence();
                        } else {
                            MessageBox.display("Error", "You cannot place a road here");
                            placeFreeRoad();
                        }
                    });
                }
            });
        }
    }

    public void placeRoad() {
        game.getStats().update("Choose the starting node for your road");
        for (ImageView start : game.getGameBoard().nodes) {
            start.setOnMouseClicked(e -> {
                game.getStats().update("Choose the ending node for your road");
                for (ImageView end : game.getGameBoard().nodes) {
                    end.setOnMouseClicked(evt -> {
                        Node startingNode = game.getBoard().nodes.get(game.getGameBoard().nodes.indexOf(start));
                        Node endingNode = game.getBoard().nodes.get(game.getGameBoard().nodes.indexOf(end));
                        if (game.gameEngine.legalRoad(startingNode, endingNode)) {
                            Road road = new Road(game.getActivePlayer(), startingNode, endingNode);
                            game.getActivePlayer().addRoad(road);
                            game.getBoard().addRoad(road);
                            ImageView newRoad = new ImageView(road.getPathToImage());
                            newRoad.relocate(road.getX(), road.getY());
                            game.getGameBoard().getChildren().add(newRoad);
                            startingNode.addNeighbouringRoad(road);
                            endingNode.addNeighbouringRoad(road);
                        } else {
                            MessageBox.display("Error", "You cannot place a road here");
                            placeRoad();
                        }
                    });
                }
            });
        }
    }

    private boolean legalCity(Node node) {
        return ((node.getOwner() == game.getActivePlayer()) && (node.getBuilding() == BuildingType.SETTLEMENT));
    }


    private boolean legalSettlement(Node node) {
        return playerConnectedToSettlement(node) && noNeighbouringSettlements(node) && (node.getOwner() == null);
    }

    private boolean legalInitialSettlement(Node node) {
        return noNeighbouringSettlements(node) && (node.getOwner() == null);
    }


    private boolean playerConnectedToSettlement(Node node) {
        for (Road road : node.getNeighbouringRoads())
            if (road.getOwner() == game.getActivePlayer())
                return true;
        return false;
    }

    private boolean noNeighbouringSettlements(Node node) {
        for (Tile tile : node.getNeighbouringTiles()) {
            int pos = tile.getAdjacentNodes().indexOf(node);
            if (pos == 5) {
                if ((tile.getAdjacentNodes().get(4).getOwner() != null) || (tile.getAdjacentNodes().get(0).getOwner() != null))
                    return false;
            } else if (pos == 0) {
                if ((tile.getAdjacentNodes().get(5).getOwner() != null) || (tile.getAdjacentNodes().get(1).getOwner() != null))
                    return false;
            } else {
                if ((tile.getAdjacentNodes().get(pos + 1).getOwner() != null) || (tile.getAdjacentNodes().get(pos - 1).getOwner() != null))
                    return false;
            }
        }
        return true;
    }

    private boolean legalRoad(Node start, Node end) {
        return roadExists(start, end) && roadFree(start, end) && playerConnectedToRoad(start, end);
    }

    private boolean playerConnectedToRoad(Node start, Node end) {
        for (Road road : start.getNeighbouringRoads())
            if (road.getOwner() == game.getActivePlayer())
                return true;
        for (Road road : end.getNeighbouringRoads())
            if (road.getOwner() == game.getActivePlayer())
                return true;
        return (start.getOwner() == game.getActivePlayer()) || (end.getOwner() == game.getActivePlayer());
    }

    private boolean roadExists(Node start, Node end) {
        List<Tile> common = new ArrayList<>(start.getNeighbouringTiles());
        common.retainAll(end.getNeighbouringTiles());
        if (common.size() == 0) {
            return false;
        }

        int distance = Math.abs(common.get(0).getAdjacentNodes().indexOf(start) -
                common.get(0).getAdjacentNodes().indexOf(end));
        return ((distance == 1) || (distance == 5));
    }

    private boolean roadFree(Node start, Node end) {
        for (Road road : game.getBoard().getRoads()) {
            if (((road.getStart() == start) && (road.getEnd() == end)) ||
                    (road.getEnd() == start) && (road.getStart() == end))
                return false;
        }
        return true;
    }

    public void incrementTurnCount() {
        turnCount++;
    }

    public void sevenActions() {
        halfResourcesOverSeven();

        game.getStats().update("You can now play your turn");
        game.getControls().setEnable();
        game.getEndTurn().setEnable();
        game.getRollDice().setDisable();
    }

    public void moveRobber() {
        game.getStats().update("Move the robber");
        for (ImageView tile : game.getGameBoard().tiles) {
            tile.setOnMouseClicked(e -> {
                Tile tileClicked = game.getBoard().getTile(game.getGameBoard().tiles.indexOf(tile));
                int indexOfTile = game.getBoard().getTiles().indexOf(tileClicked);
                tileClicked.setHasRobber(true);
                game.getBoard().getTile(game.getBoard().getRobberTile()).setHasRobber(false);
                game.getBoard().setRobberTile(indexOfTile);
                game.getGameBoard().robber.relocate(BoardProperties.TOKEN_X[indexOfTile], BoardProperties.TOKEN_Y[indexOfTile]);
            });
        }
    }

    public void halfResourcesOverSeven() {
    }

    public void playDevelopmentCard(DevelopmentType card) {
        if (card == DevelopmentType.VICTORY_POINT) {
            game.getActivePlayer().increaseVictoryPoints(1);
            game.getActivePlayer().getDevelopmentCards().spendCard(DevelopmentType.VICTORY_POINT);
            return;
        }
        if (devCardPlayed)
            MessageBox.display("Illegal move", "You already played a development card this turn");
        else {
            switch (card) {
                case SOLDIER:
                    moveRobber();
                    game.getActivePlayer().getDevelopmentCards().spendCard(DevelopmentType.SOLDIER);
                    break;
                case MONOPOLY:
                    monopolizeResource();
                    game.getActivePlayer().getDevelopmentCards().spendCard(DevelopmentType.MONOPOLY);
                    break;
                case ROAD_BUILDING:
                    buildDevCardRoads();
                    game.getActivePlayer().getDevelopmentCards().spendCard(DevelopmentType.ROAD_BUILDING);
                    break;
                case YEAR_OF_PLENTY:
                    getTwoFreeResources();
                    game.getActivePlayer().getDevelopmentCards().spendCard(DevelopmentType.YEAR_OF_PLENTY);
                    break;
            }
        }
        devCardPlayed = true;
    }

    private void getTwoFreeResources() {
        ResourceType resource = ResourceChooser.get("Select the first free resource");
        game.getActivePlayer().getResources().incrementResourceCounter(resource, 1);
        resource = ResourceChooser.get("Select the second free resource");
        game.getActivePlayer().getResources().incrementResourceCounter(resource, 1);
    }

    private void buildDevCardRoads() {
    }

    private void monopolizeResource() {
        ResourceType resource = ResourceChooser.get("Select the resource you want to monopolize");
        for (Player player : game.getPlayersInTheGame()) {
            int nrOfResource = player.getResources().getCountForResource(resource);
            game.getActivePlayer().getResources().incrementResourceCounter(resource, nrOfResource);
            player.getResources().decrementResourceCounter(resource, nrOfResource);
        }
    }


}
