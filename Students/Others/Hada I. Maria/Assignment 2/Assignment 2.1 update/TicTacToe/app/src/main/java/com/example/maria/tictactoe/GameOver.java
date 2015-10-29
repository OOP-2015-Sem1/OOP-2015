package com.example.maria.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver {

    private static int player1 = 0;
    private static int player2 = 0;

    public static boolean checkIfGameOver(int[] theBoard, Context context, Activity activity) {

        int p1Score = 0;
        int p2Score = 0;

        boolean isOver = false;

        if ((theBoard[0] == 0 && theBoard[1] == 0 && theBoard[2] == 0) ||       // -
                (theBoard[3] == 0 && theBoard[4] == 0 && theBoard[5] == 0) ||   // -
                (theBoard[6] == 0 && theBoard[7] == 0 && theBoard[8] == 0) ||   // -
                (theBoard[0] == 0 && theBoard[3] == 0 && theBoard[6] == 0) ||   // |
                (theBoard[1] == 0 && theBoard[4] == 0 && theBoard[7] == 0) ||   // |
                (theBoard[2] == 0 && theBoard[5] == 0 && theBoard[8] == 0) ||   // |
                (theBoard[0] == 0 && theBoard[4] == 0 && theBoard[8] == 0) ||   // \
                (theBoard[2] == 0 && theBoard[4] == 0 && theBoard[6] == 0)) {   // /

            Toast.makeText(context.getApplicationContext(), "O wins!", Toast.LENGTH_SHORT).show();

            p2Score++;
            setScore(0, p2Score, activity, context);

            isOver = true;

        } else if ((theBoard[0] == 1 && theBoard[1] == 1 && theBoard[2] == 1) ||    // -
                (theBoard[3] == 1 && theBoard[4] == 1 && theBoard[5] == 1) ||       // -
                (theBoard[6] == 1 && theBoard[7] == 1 && theBoard[8] == 1) ||       // -
                (theBoard[0] == 1 && theBoard[3] == 1 && theBoard[6] == 1) ||       // |
                (theBoard[1] == 1 && theBoard[4] == 1 && theBoard[7] == 1) ||       // |
                (theBoard[2] == 1 && theBoard[5] == 1 && theBoard[8] == 1) ||       // |
                (theBoard[0] == 1 && theBoard[4] == 1 && theBoard[8] == 1) ||       // \
                (theBoard[2] == 1 && theBoard[4] == 1 && theBoard[6] == 1)) {       // /

            Toast.makeText(context.getApplicationContext(), "X wins!", Toast.LENGTH_SHORT).show();

            p1Score++;
            setScore(p1Score, 0, activity, context);

            isOver = true;

        } else { //draw

            boolean emptyButton = false;
            for (int i = 0; i < 9; i++) {

                if (theBoard[i] == 2) {
                    emptyButton = true;
                }
            }

            if (!emptyButton) {
                Toast.makeText(context, "It's a draw!", Toast.LENGTH_SHORT).show();
                isOver = true;
            }
        }
        return isOver;
    }

    public static void setScore(int player1Score, int player2Score, Activity a, Context c) {

        TextView score1 = (TextView) a.findViewById(R.id.player1Score);
        TextView score2 = (TextView) a.findViewById(R.id.player2Score);

        if (player1Score == 0 && player2Score == 0) {
            player1 = 0;
            player2 = 0;
            saveScore(c, "scoreP1", 0);
            fetchScore(c, "scoreP1", score1);

            saveScore(c, "scoreP2", 0);
            fetchScore(c, "scoreP2", score2);
        } else if (player1Score != 0 && player2Score == 0) {
            player1++;
            saveScore(c, "scoreP1", player1);
            fetchScore(c, "scoreP1", score1);

        } else if (player1Score == 0 && player2Score != 0) {
            player2++;
            saveScore(c, "scoreP2", player2);
            fetchScore(c, "scoreP2", score2);
        }
    }

    public static void saveScore(Context context, String playerScore, int score) {
        SharedPreferences sharedPref = context.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(playerScore, String.valueOf(score));
        editor.apply();

        // Toast.makeText(context, "Score saved!", Toast.LENGTH_SHORT).show();
    }

    public static void fetchScore(Context context, String playerScore, TextView score) {
        SharedPreferences sharedPref = context.getSharedPreferences("scoreInfo", Context.MODE_PRIVATE);

        String s = sharedPref.getString(playerScore, "");
        score.setText(s);
    }
}
