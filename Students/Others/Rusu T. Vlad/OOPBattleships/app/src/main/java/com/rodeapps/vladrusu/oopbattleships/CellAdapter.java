package com.rodeapps.vladrusu.oopbattleships;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.Objects;

import Model.Board;

/**
 * Created by vladrusu on 14/02/16.
 */
public class CellAdapter extends BaseAdapter {

    private static final int color1 = Color.parseColor("#AAAAAA");
    private static final int color2 = Color.parseColor("#CCCCCC");
    private Context context;
    private Board board;
    private int cellLayout;

    public CellAdapter(Context context, Board board, int layoyt) {
        this.context = context;
        this.board = board;
        if (layoyt == 0) {
            cellLayout = R.layout.cell_adapter_view;
        }
        this.cellLayout = layoyt;
    }

    @Override
    public int getCount() {
        return Board.size * Board.size;
    }

    @Override
    public Objects getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View gridView;
        if (convertView == null) {
            gridView = inflater.inflate(cellLayout, null);
            gridView.setBackgroundColor(colorForPosition(position));
        } else {
            gridView = convertView;
        }
        return gridView;
    }

    private int colorForPosition(int position) {
        int size = Board.size;
        int i = position / size;
        int j = position % size;
        if ((i % 2 == 0) ^ (j % 2 == 0)) {
            return color1;
        }
        return color2;
    }
}
