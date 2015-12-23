package com.example.roxanappop.chess;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.roxanappop.chess.Controller.Controller;
import com.example.roxanappop.chess.Controller.PlayButtonActionListener;

/**
 * Created by roxanappop on 12/13/2015.
 */
public class PlayButtonFragment extends Fragment {

    public interface FragementActivity_I{
        Controller getControllerInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.play_button_fragment, container, false);
    }

    @Override
    public void onStart() {

        super.onStart();
        Button playButton = (Button) getActivity().findViewById(R.id.playButton);
        if(playButton!=null) {
            PlayButtonActionListener playButtonListener = new PlayButtonActionListener(getActivity());
            playButtonListener.addObserver(((FragementActivity_I)getActivity()).getControllerInstance());
            playButton.setOnClickListener(playButtonListener);
        }
    }
}
