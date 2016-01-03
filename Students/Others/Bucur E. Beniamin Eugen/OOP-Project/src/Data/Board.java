package Data;

import java.io.Serializable;
import java.util.*;
import java.lang.Math;

public class Board implements Serializable {
    List<Tile> tiles = new ArrayList<>(20);
    public List<Node> nodes = new LinkedList<>();
    List<Road> roads = new LinkedList<>();
    int[] tileNumbers = {5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11};
    int robberTile;

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
        int[] positions = {2, 1, 0, 3, 7, 12, 16, 17, 18, 15, 11, 6, 5, 4, 8, 13, 14, 10, 9};
        for (int position : positions) {
            if (!tiles.get(position).isHasRobber()) {
                tiles.get(position).placeNumber(tileNumbers[i]);
                i++;
            }
        }

    }

    private void startFromRightSide() {
        int i = 0;
        int[] positions = {11, 6, 2, 1, 0, 3, 7, 12, 16, 17, 18, 15, 10, 5, 4, 8, 13, 14, 9};
        for (int position : positions) {
            if (!tiles.get(position).isHasRobber()) {
                tiles.get(position).placeNumber(tileNumbers[i]);
                i++;
            }
        }
    }

    private void startFromBRightCorner() {
        int i = 0;
        int[] positions = {18, 15, 11, 6, 2, 1, 0, 3, 7, 12, 16, 17, 14, 10, 5, 4, 8, 13, 9};
        for (int position : positions) {
            if (!tiles.get(position).isHasRobber()) {
                tiles.get(position).placeNumber(tileNumbers[i]);
                i++;
            }
        }
    }

    private void statFromBLeftCorner() {
        int i = 0;
        int[] positions = {16, 17, 18, 15, 11, 6, 2, 1, 0, 3, 7, 12, 13, 14, 10, 5, 4, 8, 9};
        for (int position : positions) {
            if (!tiles.get(position).isHasRobber()) {
                tiles.get(position).placeNumber(tileNumbers[i]);
                i++;
            }
        }
    }

    private void startFromLeftSide() {
        int i = 0;
        int[] positions = {7, 12, 16, 17, 18, 15, 11, 6, 2, 1, 0, 3, 8, 13, 14, 10, 5, 4, 9};
        for (int position : positions) {
            if (!tiles.get(position).isHasRobber()) {
                tiles.get(position).placeNumber(tileNumbers[i]);
                i++;
            }
        }
    }

    private void startFromULeftCorner() {
        int i = 0;
        int[] positions = {0, 3, 7, 12, 16, 17, 18, 15, 11, 6, 2, 1, 4, 8, 13, 14, 10, 5, 9};
        for (int position : positions) {
            if (!tiles.get(position).isHasRobber()) {
                tiles.get(position).placeNumber(tileNumbers[i]);
                i++;
            }
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
                tile.addAdjacentNode(node);
                node.addNeighbouringTiles(tile);
            } else {
                tile.addAdjacentNode(nodeOnTheSamePosition(node));
                nodeOnTheSamePosition(node).addNeighbouringTiles(tile);
            }

            xCoordinate = tile.getTileX() - 20;
            yCoordinate = tile.getTileY() + 120 - 15;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.addAdjacentNode(node);
                node.addNeighbouringTiles(tile);
            } else {
                tile.addAdjacentNode(nodeOnTheSamePosition(node));
                nodeOnTheSamePosition(node).addNeighbouringTiles(tile);
            }

            xCoordinate = tile.getTileX() + 69 - 15;
            yCoordinate = tile.getTileY() + 160 - 15;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.addAdjacentNode(node);
                node.addNeighbouringTiles(tile);
            } else {
                tile.addAdjacentNode(nodeOnTheSamePosition(node));
                nodeOnTheSamePosition(node).addNeighbouringTiles(tile);
            }

            xCoordinate = tile.getTileX() + 139 - 10;
            yCoordinate = tile.getTileY() + 120 - 15;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.addAdjacentNode(node);
                node.addNeighbouringTiles(tile);
            } else {
                tile.addAdjacentNode(nodeOnTheSamePosition(node));
                nodeOnTheSamePosition(node).addNeighbouringTiles(tile);
            }

            xCoordinate = tile.getTileX() + 139 - 10;
            yCoordinate = tile.getTileY() + 40 - 20;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.addAdjacentNode(node);
                node.addNeighbouringTiles(tile);
            } else {
                tile.addAdjacentNode(nodeOnTheSamePosition(node));
                nodeOnTheSamePosition(node).addNeighbouringTiles(tile);
            }

            xCoordinate = tile.getTileX() + 69 - 17;
            yCoordinate = tile.getTileY() - 20;
            node = new Node(BoardProperties.NODE_HEIGHT, BoardProperties.NODE_WIDTH, xCoordinate, yCoordinate);
            if (nodeOnTheSamePosition(node) == null) {
                nodes.add(node);
                tile.addAdjacentNode(node);
                node.addNeighbouringTiles(tile);
            } else {
                tile.addAdjacentNode(nodeOnTheSamePosition(node));
                nodeOnTheSamePosition(node).addNeighbouringTiles(tile);
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

    public void addRoad(Road road) {
        roads.add(road);
    }

    public List<Road> getRoads(){
        return roads;
    }

    public int getRobberTile() {
        return robberTile;
    }

    public void setRobberTile(int robberTile) {
        this.robberTile = robberTile;
    }
}
