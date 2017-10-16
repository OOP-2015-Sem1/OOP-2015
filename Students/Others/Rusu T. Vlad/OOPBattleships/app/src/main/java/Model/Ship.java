package Model;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by vladrusu on 14/02/16.
 */
public class Ship implements Parcelable {

    public static final Creator<Ship> CREATOR = new Creator<Ship>() {
        public Ship createFromParcel(Parcel source) {
            return new Ship(source);
        }

        public Ship[] newArray(int size) {
            return new Ship[size];
        }
    };
    private static final int UNDAMAGED_POINT = 0;
    private static final int DAMAGED_POINT = 1;
    private String name = "";
    private ShipType shipType;
    private int size;
    private Point origin;
    private Orientation orientation = Orientation.ORIENTATION_HORIZONTAL;
    //    private Board board;
    private int[] hitPoints;

    public Ship(ShipType shipType, Board board) {
        this.shipType = shipType;
        this.origin = new Point();
        switch (shipType) {
            case AIRCRAFT_CARRIER:
                this.name = "Aircraft Carrier";
                this.size = 5;
                break;
            case BATTLESHIP:
                this.name = "Battleship";
                this.size = 4;
                break;
            case SUBMARINE:
                this.name = "Submarine";
                this.size = 3;
                break;
            case CRUISER:
                this.name = "Cruiser";
                this.size = 2;
                break;
        }
        hitPoints = new int[this.size];
        for (int i = 0; i < hitPoints.length; i++) {
            hitPoints[i] = UNDAMAGED_POINT;
        }
    }

    protected Ship(Parcel in) {
        this.name = in.readString();
        int tmpShipType = in.readInt();
        this.shipType = tmpShipType == -1 ? null : ShipType.values()[tmpShipType];
        this.size = in.readInt();
        this.origin = in.readParcelable(Point.class.getClassLoader());
        int tmpOrientation = in.readInt();
        this.orientation = tmpOrientation == -1 ? null : Orientation.values()[tmpOrientation];
        this.hitPoints = in.createIntArray();
    }

    public String getName() {
        return this.name;
    }

    public ShipType getShipType() {
        return this.shipType;
    }

    public int getSize() {
        return this.size;
    }

    public int getPositionX() {
        return this.origin.x;
    }

    public void setPositionX(int x) {
        int maxX = Board.size;
        if (this.orientation == Orientation.ORIENTATION_VERTICAL) {
            maxX = maxX - this.size - 1;
        }
        if (x < 0) {
            this.origin.x = 0;
        } else if (x >= maxX) {
            this.origin.x = maxX - 1;
        } else {
            this.origin.x = x;
        }
    }

    public int getPositionY() {
        return this.origin.y;
    }

    public void setPositionY(int y) {
        int maxY = Board.size;
        if (this.orientation == Orientation.ORIENTATION_HORIZONTAL) {
            maxY = maxY - this.size + 1;
        }
        if (y < 0) {
            this.origin.y = 0;
        } else if (y >= maxY) {
            this.origin.y = maxY - 1;
        } else {
            this.origin.y = y;
        }
    }

    public Point getOrigin() {
        return this.origin;
    }

    public void setOrigin(Point point) {
        setPositionX(point.x);
        setPositionY(point.y);
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    /**
     * @param point
     * @return 2D point on map transformed to 1D point on ship
     */
    public int transformPoint(Point point) {
        if (this.orientation == Orientation.ORIENTATION_HORIZONTAL) {
            return point.y - this.origin.y;
        } else {
            return point.x - this.origin.x;
        }
    }

    public void hitPoint(int point) {
        hitPoints[point] = DAMAGED_POINT;
    }

    public Boolean isDestroyed() {
        for (int i = 0; i < hitPoints.length; i++) {
            if (hitPoints[i] == UNDAMAGED_POINT) {
                return false;
            }
        }
        return true;
    }
    //    private String name = "";
    //    private ShipType shipType;
    //    private int size;
    //    private Point origin;
    //    private Orientation orientation = Orientation.ORIENTATION_HORIZONTAL;
    //    private Board board;
    //    private int[] hitPoints;

    public Boolean containsPoint(Point point) {
        if (this.orientation == Orientation.ORIENTATION_HORIZONTAL) {
            if ((this.origin.x == point.x) && (point.y > this.origin.y) && (point.y - this.origin.y < this.size)) {
                return true;
            }
        } else {
            if ((point.x - this.origin.x < this.size) && (point.x > this.origin.x) && (this.origin.y == point.y)) {
                return true;
            }
        }
        return false;
    }

    public void toggleOrientation() {
        if (this.orientation == Orientation.ORIENTATION_HORIZONTAL) {
            this.orientation = Orientation.ORIENTATION_VERTICAL;
        } else {
            this.orientation = Orientation.ORIENTATION_HORIZONTAL;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeInt(this.shipType == null ? -1 : this.shipType.ordinal());
        dest.writeInt(this.size);
        dest.writeParcelable(this.origin, 0);
        dest.writeInt(this.orientation == null ? -1 : this.orientation.ordinal());
        dest.writeIntArray(this.hitPoints);
    }
}
