package com.example.roxanappop.chess.Controller;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.example.roxanappop.chess.MainActivity;
import com.example.roxanappop.chess.PlayButtonFragment;
import com.example.roxanappop.chess.PlayersTurnFragment;
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

    int count;
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
