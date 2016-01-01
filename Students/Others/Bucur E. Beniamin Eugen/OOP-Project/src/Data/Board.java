package Data;

import java.io.Serializable;
import java.util.*;
import java.lang.Math;

public class Board implements Serializable{
    List<Tile> tiles = new ArrayList<>(20);
    public List<Node> nodes = new LinkedList<>();
    List<Road> roads = new LinkedList<>();
    int[] tileNumbers = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};

    public Board() {
        populateTileArray();
        shuffleTileArray();
        matchTokensToTiles();
        setDimensionsAndCoordinates();
        generateNodes();
    }

    public Tile getTile(int pos) {
        return tiles.get(pos);
    }

    private void populateTileArray() {
        for (int i = 1; i <= 19; i++) {
            if (i < 5) {
                tiles.add(new Tile(TileType.PLAIN));
            } else if (i < 9) {
                tiles.add(new Tile(TileType.FOREST));
            } else if (i < 13) {
                tiles.add(new Tile(TileType.PASTURE));
            } else if (i < 16) {
                tiles.add(new Tile(TileType.HILL));
            } else if (i < 19) {
                tiles.add(new Tile(TileType.MOUNTAIN));
            } else if (i == 19) {
                tiles.add(new Tile(TileType.DESERT));
            }
        }
    }

    private void shuffleTileArray() {
        Collections.shuffle(tiles);
    }

    private void setDimensionsAndCoordinates() {
        for (int i = 0; i < 19; i++) {
            tiles.get(i).setTileHeight(BoardProperties.TILE_HEIGHT);
            tiles.get(i).setTileWidth(BoardProperties.TILE_WIDTH);
            tiles.get(i).setTileX(BoardProperties.TILE_X[i]);
            tiles.get(i).setTileY(BoardProperties.TILE_Y[i]);
            tiles.get(i).setTokenHeight(BoardProperties.TOKEN_HEIGHT);
            tiles.get(i).setTokenWidth(BoardProperties.TOKEN_WIDTH);
            tiles.get(i).setTokenX(BoardProperties.TOKEN_X[i]);
            tiles.get(i).setTokenY(BoardProperties.TOKEN_Y[i]);
        }
    }

    private void matchTokensToTiles() {
        Random random = new Random();
        switch (random.nextInt(5)) {
            case 0: {
                if (!tiles.get(0).isHasRobber()) {
                    startFromULeftCorner();
                } else {
                    startFromLeftSide();
                }
                break;
            }
            case 1:
                if (!tiles.get(7).isHasRobber()) {
                    startFromLeftSide();
                } else {
                    statFromBLeftCorner();
                }
                break;
            case 2:
                if (!tiles.get(16).isHasRobber()) {
                    statFromBLeftCorner();
                } else {
                    startFromBRightCorner();
                }
                break;
            case 3:
                if (!tiles.get(18).isHasRobber()) {
                    startFromBRightCorner();
                } else {
                    startFromRightSide();
                }
                break;
            case 4:
                if (!tiles.get(11).isHasRobber()) {
                    startFromRightSide();
                } else {
                    startFromURightCorner();
                }
                break;
            case 5:
                if (!tiles.get(2).isHasRobber()) {
                    startFromURightCorner();
                } else {
                    startFromLeftSide();
                }
                break;
        }
    }

    private void startFromURightCorner() {
        int i = 0;
        if (!tiles.get(2).isHasRobber()) {
            tiles.get(2).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(1).isHasRobber()) {
            tiles.get(1).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(0).isHasRobber()) {
            tiles.get(0).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(3).isHasRobber()) {
            tiles.get(3).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(7).isHasRobber()) {
            tiles.get(7).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(12).isHasRobber()) {
            tiles.get(12).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(16).isHasRobber()) {
            tiles.get(16).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(17).isHasRobber()) {
            tiles.get(17).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(18).isHasRobber()) {
            tiles.get(18).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(15).isHasRobber()) {
            tiles.get(15).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(11).isHasRobber()) {
            tiles.get(11).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(6).isHasRobber()) {
            tiles.get(6).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(5).isHasRobber()) {
            tiles.get(5).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(4).isHasRobber()) {
            tiles.get(4).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(8).isHasRobber()) {
            tiles.get(8).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(13).isHasRobber()) {
            tiles.get(13).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(14).isHasRobber()) {
            tiles.get(14).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(10).isHasRobber()) {
            tiles.get(10).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(9).isHasRobber()) {
            tiles.get(9).placeNumber(tileNumbers[i]);
        }

    }

    private void startFromRightSide() {
        int i = 0;
        if (!tiles.get(11).isHasRobber()) {
            tiles.get(11).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(6).isHasRobber()) {
            tiles.get(6).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(2).isHasRobber()) {
            tiles.get(2).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(1).isHasRobber()) {
            tiles.get(1).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(0).isHasRobber()) {
            tiles.get(0).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(3).isHasRobber()) {
            tiles.get(3).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(7).isHasRobber()) {
            tiles.get(7).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(12).isHasRobber()) {
            tiles.get(12).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(16).isHasRobber()) {
            tiles.get(16).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(17).isHasRobber()) {
            tiles.get(17).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(18).isHasRobber()) {
            tiles.get(18).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(15).isHasRobber()) {
            tiles.get(15).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(10).isHasRobber()) {
            tiles.get(10).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(5).isHasRobber()) {
            tiles.get(5).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(4).isHasRobber()) {
            tiles.get(4).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(8).isHasRobber()) {
            tiles.get(8).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(13).isHasRobber()) {
            tiles.get(13).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(14).isHasRobber()) {
            tiles.get(14).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(9).isHasRobber()) {
            tiles.get(9).placeNumber(tileNumbers[i]);
        }
    }

    private void startFromBRightCorner() {
        int i = 0;
        if (!tiles.get(18).isHasRobber()) {
            tiles.get(18).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(15).isHasRobber()) {
            tiles.get(15).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(11).isHasRobber()) {
            tiles.get(11).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(6).isHasRobber()) {
            tiles.get(6).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(2).isHasRobber()) {
            tiles.get(2).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(1).isHasRobber()) {
            tiles.get(1).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(0).isHasRobber()) {
            tiles.get(0).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(3).isHasRobber()) {
            tiles.get(3).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(7).isHasRobber()) {
            tiles.get(7).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(12).isHasRobber()) {
            tiles.get(12).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(16).isHasRobber()) {
            tiles.get(16).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(17).isHasRobber()) {
            tiles.get(17).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(14).isHasRobber()) {
            tiles.get(14).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(10).isHasRobber()) {
            tiles.get(10).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(5).isHasRobber()) {
            tiles.get(5).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(4).isHasRobber()) {
            tiles.get(4).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(8).isHasRobber()) {
            tiles.get(8).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(13).isHasRobber()) {
            tiles.get(13).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(9).isHasRobber()) {
            tiles.get(9).placeNumber(tileNumbers[i]);
        }
    }

    private void statFromBLeftCorner() {
        int i = 0;
        if (!tiles.get(16).isHasRobber()) {
            tiles.get(16).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(17).isHasRobber()) {
            tiles.get(17).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(18).isHasRobber()) {
            tiles.get(18).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(15).isHasRobber()) {
            tiles.get(15).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(11).isHasRobber()) {
            tiles.get(11).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(6).isHasRobber()) {
            tiles.get(6).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(2).isHasRobber()) {
            tiles.get(2).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(1).isHasRobber()) {
            tiles.get(1).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(0).isHasRobber()) {
            tiles.get(0).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(3).isHasRobber()) {
            tiles.get(3).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(7).isHasRobber()) {
            tiles.get(7).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(12).isHasRobber()) {
            tiles.get(12).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(13).isHasRobber()) {
            tiles.get(13).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(14).isHasRobber()) {
            tiles.get(14).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(10).isHasRobber()) {
            tiles.get(10).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(5).isHasRobber()) {
            tiles.get(5).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(4).isHasRobber()) {
            tiles.get(4).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(8).isHasRobber()) {
            tiles.get(8).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(9).isHasRobber()) {
            tiles.get(9).placeNumber(tileNumbers[i]);
        }
    }

    private void startFromLeftSide() {
        int i = 0;
        if (!tiles.get(7).isHasRobber()) {
            tiles.get(7).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(12).isHasRobber()) {
            tiles.get(12).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(16).isHasRobber()) {
            tiles.get(16).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(17).isHasRobber()) {
            tiles.get(17).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(18).isHasRobber()) {
            tiles.get(18).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(15).isHasRobber()) {
            tiles.get(15).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(11).isHasRobber()) {
            tiles.get(11).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(6).isHasRobber()) {
            tiles.get(6).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(2).isHasRobber()) {
            tiles.get(2).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(1).isHasRobber()) {
            tiles.get(1).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(0).isHasRobber()) {
            tiles.get(0).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(3).isHasRobber()) {
            tiles.get(3).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(8).isHasRobber()) {
            tiles.get(8).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(13).isHasRobber()) {
            tiles.get(13).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(14).isHasRobber()) {
            tiles.get(14).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(10).isHasRobber()) {
            tiles.get(10).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(5).isHasRobber()) {
            tiles.get(5).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(4).isHasRobber()) {
            tiles.get(4).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(9).isHasRobber()) {
            tiles.get(9).placeNumber(tileNumbers[i]);
        }
    }

    private void startFromULeftCorner() {
        int i = 0;
        if (!tiles.get(0).isHasRobber()) {
            tiles.get(0).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(3).isHasRobber()) {
            tiles.get(3).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(7).isHasRobber()) {
            tiles.get(7).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(12).isHasRobber()) {
            tiles.get(12).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(16).isHasRobber()) {
            tiles.get(16).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(17).isHasRobber()) {
            tiles.get(17).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(18).isHasRobber()) {
            tiles.get(18).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(15).isHasRobber()) {
            tiles.get(15).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(11).isHasRobber()) {
            tiles.get(11).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(6).isHasRobber()) {
            tiles.get(6).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(2).isHasRobber()) {
            tiles.get(2).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(1).isHasRobber()) {
            tiles.get(1).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(4).isHasRobber()) {
            tiles.get(4).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(8).isHasRobber()) {
            tiles.get(8).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(13).isHasRobber()) {
            tiles.get(13).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(14).isHasRobber()) {
            tiles.get(14).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(10).isHasRobber()) {
            tiles.get(10).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(5).isHasRobber()) {
            tiles.get(5).placeNumber(tileNumbers[i]);
            i++;
        }
        if (!tiles.get(9).isHasRobber()) {
            tiles.get(9).placeNumber(tileNumbers[i]);
        }
    }

    private void generateNodes() {
        for (Tile tile : tiles) {
            int xCoordinate;
            int yCoordinate;

            xCoordinate = tile.getTileX() - 20;
            yCoordinate = tile.getTileY() + 40 - 20;
            Node node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.adjacentNodes[0] = node;
            } else {
                tile.adjacentNodes[0] = nodeOnTheSamePosition(node);
            }

            xCoordinate = tile.getTileX() - 20;
            yCoordinate = tile.getTileY() + 120 - 15;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.adjacentNodes[1] = node;
            } else {
                tile.adjacentNodes[1] = nodeOnTheSamePosition(node);
            }

            xCoordinate = tile.getTileX() + 69 - 15;
            yCoordinate = tile.getTileY() + 160 - 15;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.adjacentNodes[2] = node;
            } else {
                tile.adjacentNodes[2] = nodeOnTheSamePosition(node);
            }

            xCoordinate = tile.getTileX() + 139 - 10;
            yCoordinate = tile.getTileY() + 120 - 15;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.adjacentNodes[3] = node;
            } else {
                tile.adjacentNodes[3] = nodeOnTheSamePosition(node);
            }

            xCoordinate = tile.getTileX() + 139 - 10;
            yCoordinate = tile.getTileY() + 40 - 20;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.adjacentNodes[4] = node;
            } else {
                tile.adjacentNodes[4] = nodeOnTheSamePosition(node);
            }

            xCoordinate = tile.getTileX() + 69 - 17;
            yCoordinate = tile.getTileY() - 20;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.adjacentNodes[5] = node;
            } else {
                tile.adjacentNodes[5] = nodeOnTheSamePosition(node);
            }
        }
    }

    private Node nodeOnTheSamePosition(Node newNode) {
        for (Node existingNode : nodes)
            if (((Math.abs((existingNode.nodeX - newNode.nodeX))) < 15) &&
                    ((Math.abs((existingNode.nodeY - newNode.nodeY))) < 15))
                return existingNode;
        return null;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void addRoad(Road road){
        roads.add(road);
    }
}
