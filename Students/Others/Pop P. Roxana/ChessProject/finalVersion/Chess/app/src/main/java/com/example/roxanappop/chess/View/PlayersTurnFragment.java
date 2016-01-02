package com.example.roxanappop.chess.View;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.roxanappop.chess.R;

/**
 * Created by roxanappop on 12/13/2015.
 */
public class PlayersTurnFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.players_turn, container, false);
    }
}
