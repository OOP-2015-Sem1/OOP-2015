package Model;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by vladrusu on 14/02/16.
 */
public class Board implements Parcelable {

    public static final int size = 10;
    public static final int NUMBER_OF_AIRCRAFTS = 1;
    public static final int NUMBER_OF_BATTLESHIPS = 1;
    public static final int NUMBER_OF_SUBMARINES = 2;
    public static final int NUMBER_OF_CRUISERS = 3;
    public static final Creator<Board> CREATOR = new Creator<Board>() {
        public Board createFromParcel(Parcel source) {
            return new Board(source);
        }

        public Board[] newArray(int size) {
            return new Board[size];
        }
    };
    private ArrayList<Ship> ships = new ArrayList<>();
    private ArrayList<Point> hitPoints = new ArrayList<>();
    private BoardDelegate delegate = null;

    public Board(BoardDelegate delegate) {
        this.delegate = delegate;
    }

    public Board() {
    }

    protected Board(Parcel in) {
        this.ships = in.createTypedArrayList(Ship.CREATOR);
        this.hitPoints = in.createTypedArrayList(Point.CREATOR);
    }

    public void setDelegate(BoardDelegate delegate) {
        this.delegate = delegate;
    }

    public void hitPoint(Point point) {
        if (pointIsOnBoard(point)) {
            if (!hitPoints.contains(point)) {
                hitPoints.add(point);
                Ship hitShip = shipAtPoint(point);
                if (hitShip != null) {
                    hitShip.hitPoint(hitShip.transformPoint(point));
                    if (hitShip.isDestroyed()) {
                        if (this.delegate != null) {
                            this.delegate.onDestroyShip(this, hitShip, point);
                        }
                    } else {
                        if (this.delegate != null) {
                            this.delegate.onHitShip(this, hitShip, point);
                        }
                    }
                } else {
                    if (this.delegate != null) {
                        this.delegate.onMissedShip(this, point);
                    }
                }
            }
        }
    }

    public Boolean pointIsOnBoard(Point point) {
        if ((point.x < 0) || (point.x >= Board.size)) {
            return false;
        } else if ((point.y < 0) || (point.y >= Board.size)) {
            return false;
        }
        return true;
    }

    public Ship shipAtPoint(Point point) {
        for (Ship ship : this.ships) {
            if (ship.getOrientation() == Orientation.ORIENTATION_HORIZONTAL) {
                if (ship.getPositionX() == point.x) {
                    if ((point.y >= ship.getPositionY()) && (point.y < ship.getPositionY() + ship.getSize())) {
                        return ship;
                    }
                }
            } else {
                if (ship.getPositionY() == point.y) {
                    if ((point.x >= ship.getPositionX()) && (point.x < ship.getPositionX() + ship.getSize())) {
                        return ship;
                    }
                }
            }
        }
        return null;
    }

    public Boolean shipAtValidPosition(Ship ship, Point position) {
        if (ships.contains(ship)) {
            ships.remove(ship);
        }
        if (ship.getOrientation() == Orientation.ORIENTATION_HORIZONTAL) {
            for (int i = 0; i < ship.getSize(); i++) {
                if (shipAtPoint(new Point(position.x, position.y + i)) != null) {
                    ships.add(ship);
                    return false;
                }
            }
        } else {
            for (int i = 0; i < ship.getSize(); i++) {
                if (shipAtPoint(new Point(position.x + i, position.y)) != null) {
                    ships.add(ship);
                    return false;
                }
            }
        }
        ships.add(ship);
        return true;
    }

    public void placeShip(Ship ship, Point point) {
        ship.setOrigin(point);
        if (!ships.contains(ship)) {
            this.ships.add(ship);
        }
    }

    public void removeShip(Ship ship) {
        this.ships.remove(ship);
    }

    public void changeOrientationForShip(Ship ship) {
        if (ship.getOrientation() == Orientation.ORIENTATION_HORIZONTAL) {
            ship.setOrientation(Orientation.ORIENTATION_VERTICAL);
        } else {
            ship.setOrientation(Orientation.ORIENTATION_HORIZONTAL);
        }
    }

    public void moveShipToPoint(Ship ship, Point point) {
        ship.setOrigin(point);
    }

    public Boolean shipHasValidPosition(Ship ship) {
        Point shipOrigin = ship.getOrigin();
        if (ships.contains(ship)) {
            ships.remove(ship);
        }
        if (ship.getOrientation() == Orientation.ORIENTATION_HORIZONTAL) {
            for (int i = 0; i < ship.getSize(); i++) {
                if (shipAtPoint(new Point(shipOrigin.x, shipOrigin.y + i)) != null) {
                    ships.add(ship);
                    return false;
                }
            }
        } else {
            for (int i = 0; i < ship.getSize(); i++) {
                if (shipAtPoint(new Point(shipOrigin.x + i, shipOrigin.y)) != null) {
                    ships.add(ship);
                    return false;
                }
            }
        }
        ships.add(ship);
        return true;
    }

    public Boolean canPlaceShipOfType(ShipType type) {
        int count = 0;
        for (Ship ship : ships) {
            if (ship.getShipType() == type) {
                count++;
            }
        }
        int maxNumber;
        switch (type) {
            case AIRCRAFT_CARRIER:
                maxNumber = NUMBER_OF_AIRCRAFTS;
                break;
            case BATTLESHIP:
                maxNumber = NUMBER_OF_BATTLESHIPS;
                break;
            case SUBMARINE:
                maxNumber = NUMBER_OF_SUBMARINES;
                break;
            case CRUISER:
                maxNumber = NUMBER_OF_CRUISERS;
                break;
            default:
                maxNumber = 0;
                break;
        }
        if (count >= maxNumber) {
            return false;
        }
        return true;
    }

    public Boolean containsShip(Ship ship) {
        return ships.contains(ship);
    }

    public Boolean boardIsReady() {
        return (ships.size() == 7);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(ships);
        dest.writeTypedList(hitPoints);
    }

    public ArrayList<Ship> getShips() {
        return this.ships;
    }
}
