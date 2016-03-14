package com.example.corina.ginrummy.Welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.corina.ginrummy.Activities.Instructions;
import com.example.corina.ginrummy.Activities.Play;
import com.example.corina.ginrummy.R;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        final TextView instruction = (TextView) findViewById(R.id.instructions);
        TextView play = (TextView) findViewById(R.id.play);

        instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructionsIntent = new Intent(Welcome.this, Instructions.class);
                Welcome.this.startActivity(instructionsIntent);
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent playIntent = new Intent(Welcome.this, Play.class);
                Welcome.this.startActivity(playIntent);
            }
        });
    }
}