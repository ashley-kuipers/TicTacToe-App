package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    Button b_quit;
    TextView a1, a2, a3, b1, b2, b3, c1, c2, c3, t_title;
    char va1 = ' ', va2 = ' ', va3 = ' ', vb1 = ' ', vb2 = ' ', vb3 = ' ', vc1 = ' ', vc2 = ' ', vc3 = ' ';
    String playerName, secondPlayer;
    boolean turn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        getData();

        b_quit = findViewById(R.id.b_quit);
        t_title = findViewById(R.id.t_title);

        String output = "Welcome " + playerName + ",\ntap a spot to start";
        t_title.setText(output);

        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);

        a1.setOnClickListener(this);
        a2.setOnClickListener(this);
        a3.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);

        b_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.a1:
                va1 = addTurn(a1);
                break;

            case R.id.a2:
                va2 = addTurn(a2);
                break;

            case R.id.a3:
                va3 = addTurn(a3);
                break;

            case R.id.b1:
                vb1 = addTurn(b1);
                break;

            case R.id.b2:
                vb2 = addTurn(b2);
                break;

            case R.id.b3:
                vb3 = addTurn(b3);
                break;

            case R.id.c1:
                vc1 = addTurn(c1);
                break;

            case R.id.c2:
                vc2 = addTurn(c2);
                break;

            case R.id.c3:
                vc3 = addTurn(c3);
                break;

            default:
                //error
                break;
        }

        if(checkPlayer1Win()){
            openDialog('X');
        } else if (checkPlayer2Win()){
            openDialog('O');
        }
    }

    public void openDialog(char winner){
        WinnerDialog wd = new WinnerDialog(winner);
        wd.show(getSupportFragmentManager(), "winner");
    }

    public char addTurn(TextView t){
        // add turn to board
        char turnPlayed;
        String output;

        // if player X's turn, add turn to board
        if(turn){
            // add player X's turn to board
            t.setText(R.string.x);
            turnPlayed = 'X';

            // if playing against the computer, get the computers move
            if(secondPlayer.equals("Computer")){
                // get computer move and add it to board
                getCompMove();

                output = "Tap to play\nyour turn";
            } else {
                output = secondPlayer + "'s turn";
            }

        } else {
            // else it was second players move so add their turn to the board
            t.setText(R.string.o);
            turnPlayed = 'O';

            output = playerName + "'s turn";
            turn = !turn;
        }

        t_title.setText(output);
        return turnPlayed;
    }

    public boolean checkPlayer1Win(){
        boolean userWon = false;

        // check horizontal wins
        if ((va1 == va2 && va2 == va3 && va1 == 'X') || (vb1 == vb2 && vb2 == vb3 && vb1 == 'X') || (vc1 == vc2 && vc2 == vc3 && vc1 == 'X')){
            userWon = true;
        // check for vertical wins
        } else if ((va1 == vb1 && vb1 == vc1 && va1 == 'X') || (va2 == vb2 && vb2 == vc2 && va2 == 'X') || (va3 == vb3 && vb3 == vc3 && va3 == 'X')){
            userWon = true;
        // check for diagonal wins
        } else if ((va1 == vb2 && vb2 == vc3 && va1 == 'X') || (va3 == vb2 && vb2 == vc1 && va3 == 'X')){
            userWon = true;
        }

        return userWon;
    }

    public boolean checkPlayer2Win(){
        boolean userWon = false;

        // check horizontal wins
        if ((va1 == va2 && va2 == va3 && va1 == 'O') || (vb1 == vb2 && vb2 == vb3 && vb1 == 'O') || (vc1 == vc2 && vc2 == vc3 && vc1 == 'O')){
            userWon = true;
            // check for vertical wins
        } else if ((va1 == vb1 && vb1 == vc1 && va1 == 'O') || (va2 == vb2 && vb2 == vc2 && va2 == 'O') || (va3 == vb3 && vb3 == vc3 && va3 == 'O')){
            userWon = true;
            // check for diagonal wins
        } else if ((va1 == vb2 && vb2 == vc3 && va1 == 'O') || (va3 == vb2 && vb2 == vc1 && va3 == 'O')){
            userWon = true;
        }

        return userWon;
    }

    public void getCompMove(){
        // TODO: start here, figure out how to do random move
        // get random move

        // check if that move has been taken already
    }

    public void resetBoard(){
        va1 = ' ';
        va2 = ' ';
        va3 = ' ';
        vb1 = ' ';
        vb2 = ' ';
        vb3 = ' ';
        vc1 = ' ';
        vc2 = ' ';
        vc3 = ' ';

        a1.setText(String.valueOf(va1));
        a2.setText(String.valueOf(va2));
        a3.setText(String.valueOf(va3));
        b1.setText(String.valueOf(vb1));
        b2.setText(String.valueOf(vb2));
        b3.setText(String.valueOf(vb3));
        c1.setText(String.valueOf(vc1));
        c2.setText(String.valueOf(vc2));
        c3.setText(String.valueOf(vc3));

        String output = "Tap a spot\nto start";
        t_title.setText(output);
    }

    // when you turn the phone, this function is called to save any data you wish to save
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // save values in GameBoard
        outState.putString("a1", a1.getText().toString());
        outState.putString("a2", a2.getText().toString());
        outState.putString("a3", a3.getText().toString());
        outState.putString("b1", b1.getText().toString());
        outState.putString("b2", b2.getText().toString());
        outState.putString("b3", b3.getText().toString());
        outState.putString("c1", c1.getText().toString());
        outState.putString("c2", c2.getText().toString());
        outState.putString("c3", c3.getText().toString());
        super.onSaveInstanceState(outState);
    }

    // when phone is done turning, this function is called to restore any of that data you saved
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // get values from saved state
        a1.setText(savedInstanceState.getString("a1"));
        a2.setText(savedInstanceState.getString("a2"));
        a3.setText(savedInstanceState.getString("a3"));
        b1.setText(savedInstanceState.getString("b1"));
        b2.setText(savedInstanceState.getString("b2"));
        b3.setText(savedInstanceState.getString("b3"));
        c1.setText(savedInstanceState.getString("c1"));
        c2.setText(savedInstanceState.getString("c2"));
        c3.setText(savedInstanceState.getString("c3"));
        super.onRestoreInstanceState(savedInstanceState);
    }

    // Retrieve shared preferences file
    public void getData(){
        // get values from shared preferences
        SharedPreferences sh = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // retrieve variables from file
        playerName = sh.getString("currentPlayerName", "player");
        secondPlayer = sh.getString("secondPlayer", "Computer");

    }

    private class GameAlgorithm extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}