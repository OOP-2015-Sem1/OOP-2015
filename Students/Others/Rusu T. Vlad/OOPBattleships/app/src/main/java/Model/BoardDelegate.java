package Model;

import android.graphics.Point;

/**
 * Created by vladrusu on 14/02/16.
 */
public interface BoardDelegate {

    void onMissedShip(Board board, Point point);

    void onHitShip(Board board, Ship ship, Point point);

    void onDestroyShip(Board board, Ship ship, Point point);
}
