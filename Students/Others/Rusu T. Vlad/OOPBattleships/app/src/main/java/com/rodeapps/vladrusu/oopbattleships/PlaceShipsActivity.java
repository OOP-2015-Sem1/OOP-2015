package com.rodeapps.vladrusu.oopbattleships;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import Model.Board;
import Model.Orientation;
import Model.Ship;
import Model.ShipType;

public class PlaceShipsActivity extends AppCompatActivity {

    public static final String BOARD = "com.rodeapps.vladrusu.oopbattleships.BOARD";
    private static final int FREE_COLOR = Color.parseColor("#80000000");
    private static final int OCCUPIED_COLOR = Color.parseColor("#40FF0000");
    private Board board = new Board();
    private Ship selectedShip = null;
    private GridView gridView;

    public void placeShipAtPoint(Point point) {
        if (selectedShip != null) {
            board.placeShip(selectedShip, point);
            drawShip(selectedShip);
        }
    }

    public void rotateShip() {
        deleteShip(selectedShip);
        selectedShip.toggleOrientation();
        if (!board.shipAtValidPosition(selectedShip, selectedShip.getOrigin())) {
            selectedShip.toggleOrientation();
        }
        placeShipAtPoint(selectedShip.getOrigin());
    }

    public void confirmPlacement() {
        selectedShip = null;
    }

    public void drawShip(Ship ship) {
        if (ship.getOrientation() == Orientation.ORIENTATION_HORIZONTAL) {
            int startPosition = locationFromPoint(ship.getOrigin());
            for (int i = 0; i < ship.getSize(); i++) {
                View gridCell = gridView.getChildAt(i + startPosition);
                View view = gridCell.findViewById(R.id.cell_view);
                setCellOccupied(view, true);
            }
        } else {
            int startPosition = locationFromPoint(ship.getOrigin());
            for (int i = 0; i < ship.getSize(); i++) {
                View gridCell = gridView.getChildAt(startPosition + i * Board.size);
                View view = gridCell.findViewById(R.id.cell_view);
                setCellOccupied(view, true);
            }
        }
    }

    public void deleteShip(Ship ship) {
        if (ship.getOrientation() == Orientation.ORIENTATION_HORIZONTAL) {
            int startPosition = locationFromPoint(ship.getOrigin());
            for (int i = 0; i < ship.getSize(); i++) {
                View gridCell = gridView.getChildAt(i + startPosition);
                View view = gridCell.findViewById(R.id.cell_view);
                setCellOccupied(view, false);
            }
        } else {
            int startPosition = locationFromPoint(ship.getOrigin());
            for (int i = 0; i < ship.getSize(); i++) {
                View gridCell = gridView.getChildAt(startPosition + i * Board.size);
                View view = gridCell.findViewById(R.id.cell_view);
                setCellOccupied(view, false);
            }
        }
    }

    public void setCellOccupied(View view, Boolean occupied) {
        if (occupied) {
            view.setBackgroundColor(PlaceShipsActivity.OCCUPIED_COLOR);
        } else {
            view.setBackgroundColor(PlaceShipsActivity.FREE_COLOR);
        }
    }

    public void shipSelected(View view) {
        Button button = (Button) view;
        switch (button.getId()) {
            case R.id.aircraft_button:
                selectedShip = new Ship(ShipType.AIRCRAFT_CARRIER, board);
                break;
            case R.id.battleship_button:
                selectedShip = new Ship(ShipType.BATTLESHIP, board);
                break;
            case R.id.submarine_button:
                selectedShip = new Ship(ShipType.SUBMARINE, board);
                break;
            case R.id.cruiser_button:
                selectedShip = new Ship(ShipType.CRUISER, board);
                break;
            default:
                break;
        }
        String message = null;
        if (board.canPlaceShipOfType(selectedShip.getShipType())) {
            message = "Place the " + selectedShip.getName() + " on map";
        } else {
            message = "You can't place another " + selectedShip.getName() + " on map";
            selectedShip = null;
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
             .show();
    }

    public void goToGame() {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(BOARD, board);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_place_ships);
        gridView = (GridView) findViewById(R.id.place_ships_gridview);
        gridView.setAdapter(new CellAdapter(this, new Board(), R.layout.cell_adapter_view));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Point pointOnMap = pointFromLocation(position);
                if (selectedShip != null) {
                    if (board.containsShip(selectedShip)) {
                        if (selectedShip.containsPoint(pointOnMap)) {
                            rotateShip();
                        } else {
                            deleteShip(selectedShip);
                            placeShipAtPoint(pointOnMap);
                        }
                    } else if (board.shipAtValidPosition(selectedShip, pointOnMap)) {
                        placeShipAtPoint(pointOnMap);
                    }
                }
                if (board.boardIsReady()) {
                    goToGame();
                }
            }
        });
    }

    private Point pointFromLocation(int location) {
        int size = Board.size;
        int x = location / size;
        int y = location % size;
        return new Point(x, y);
    }

    private int locationFromPoint(Point point) {
        int size = Board.size;
        int x = point.x;
        int y = point.y;
        return ((x * size) + y);
    }
}
