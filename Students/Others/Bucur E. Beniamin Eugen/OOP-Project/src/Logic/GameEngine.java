package Logic;

import Data.*;
import Gui.PopUps.MessageBox;

public class GameEngine {

    public static void playTurn(Game game) {
        game.getRollDice().setEnable();
        //something in between. again, I have to wait for the user to press the button..
        game.getRollDice().setDisable();
        dispatchResources(game, game.getDice().getValue());
        //enablePlayerActions;
        //verifyForWinner
    }

    public static void startingSequence(Game game) {
        for (Player player : game.getStartingSequence()) {
            game.setActivePlayer(player);
            game.getStats().update("Place a settlement on the map");
            while (!placeSettlement(game, game.getGameBoard().getNodeClicked())) {
                MessageBox.display("Error", "You cannot place a settlement here");
            }
            /*game.getStats().update("Place a road on the map");
            while(!placeRoad(game, game.getGameBoard().getNodeClicked(), game.getGameBoard().getNodeClicked())){
                MessageBox.display("Error", "You cannot place a road here");
            }*/
        }
    }

    private static boolean placeSettlement(Game game, Node nodeClicked) {
        if (nodeClicked.getOwner() == null) {
            nodeClicked.setBuilding(BuildingType.SETTLEMENT);
            nodeClicked.setOwner(game.getActivePlayer());
            game.getGameBoard().refreshNode(game.getBoard().nodes.indexOf(nodeClicked), game.getActivePlayer().getSettlement());
            game.getActivePlayer().increaseVictoryPoints(1);
            return true;
        } else {
            return false;
        }
    }

    private static void dispatchResources(Game game, int diceValue) {
        for (Tile tile : game.getBoard().getTiles()) {
            if (tile.getToken() == diceValue)
                for (Node node : tile.getNodes()) {
                    if (node.getOwner() != null) {
                        if (node.getBuilding() == BuildingType.CITY)
                            node.getOwner().getResources().incrementResourceCounter(tile.getResourceType(), 2);
                        else if (node.getBuilding() == BuildingType.SETTLEMENT)
                            node.getOwner().getResources().incrementResourceCounter(tile.getResourceType(), 1);
                    }
                }
        }
    }

}
