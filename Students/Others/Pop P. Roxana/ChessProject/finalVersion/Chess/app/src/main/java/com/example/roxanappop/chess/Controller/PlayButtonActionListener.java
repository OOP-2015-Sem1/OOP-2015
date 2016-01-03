package com.example.roxanappop.chess.Controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;

import com.example.roxanappop.chess.View.PlayersTurnFragment;
import com.example.roxanappop.chess.R;

import java.util.Observable;

/**
 * Created by roxanappop on 12/9/2015.
 */
public class PlayButtonActionListener extends Observable implements View.OnClickListener {
    Activity activity;

    public PlayButtonActionListener(Activity activity){
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {

        FragmentManager fragmentManager = activity.getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        PlayersTurnFragment playersTurnFragment = new PlayersTurnFragment();
        fragmentTransaction.replace(R.id.fragment, playersTurnFragment);
        fragmentTransaction.commit();
        this.setChanged();
        notifyObservers();

    }
}
