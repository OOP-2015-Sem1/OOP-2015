package com.rodeapps.vladrusu.oopbattleships;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;

import Model.Board;
import Model.BoardDelegate;
import Model.Orientation;
import Model.Ship;

public class GameActivity extends AppCompatActivity implements BoardDelegate {

    private static final int FREE_COLOR = Color.parseColor("#80000000");
    private static final int OCCUPIED_COLOR = Color.parseColor("#40FF0000");

    private Board board;
    private GridView mEnemyGrid;
    private GridView mMyGrid;

    @Override
    public void onMissedShip(Board board, Point point) {
    }

    @Override
    public void onDestroyShip(Board board, Ship ship, Point point) {
    }

    @Override
    public void onHitShip(Board board, Ship ship, Point point) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mEnemyGrid = (GridView) findViewById(R.id.enamy_grid);
        mMyGrid = (GridView) findViewById(R.id.my_grid);
        mEnemyGrid.setAdapter(new CellAdapter(this, board, R.layout.small_cell_view));
        mMyGrid.setAdapter(new CellAdapter(this, board, R.layout.small_cell_view));
        Intent intent = getIntent();
        board = intent.getParcelableExtra(PlaceShipsActivity.BOARD);
//        drawBoard(mMyGrid, board);
    }

    private void drawBoard(GridView gridView, Board board) {
        for (Ship ship:board.getShips()) {
            drawShip(gridView, ship);
        }
    }

    public void drawShip(GridView gridView, Ship ship) {
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

    public void setCellOccupied(View view, Boolean occupied) {
        if (occupied) {
            view.setBackgroundColor(GameActivity.OCCUPIED_COLOR);
        } else {
            view.setBackgroundColor(GameActivity.FREE_COLOR);
        }
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
