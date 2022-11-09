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

import java.util.Objects;
import java.util.Random;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    Button b_quit;
    TextView a1, a2, a3, b1, b2, b3, c1, c2, c3, t_title;
    char[][] currentGame;
    TextView[][] currentGameTVs;

    String playerName, secondPlayer;
    boolean player1Turn = true;

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

        currentGame = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        currentGameTVs = new TextView[][]{{a1, a2, a3}, {b1, b2, b3}, {c1, c2, c3}};

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.a1:
                addTurn(0, 0);
                break;

            case R.id.a2:
                addTurn(0, 1);
                break;

            case R.id.a3:
                addTurn(0, 2);
                break;

            case R.id.b1:
                addTurn(1, 0);
                break;

            case R.id.b2:
                addTurn(1, 1);
                break;

            case R.id.b3:
                addTurn(1, 2);
                break;

            case R.id.c1:
                addTurn(2, 0);
                break;

            case R.id.c2:
                addTurn(2, 1);
                break;

            case R.id.c3:
                addTurn(2, 2);
                break;

            default:
                //error
                break;
        }

        if(checkPlayer1Win()){
            openDialog(playerName);
            // add win to the persons name
        } else if (checkPlayer2Win()){
            openDialog(secondPlayer);
            // add win to second player's name
        }
    }

    public void openDialog(String winner){
        WinnerDialog wd = new WinnerDialog(winner);
        wd.show(getSupportFragmentManager(), "winner");
    }

    public void addTurn(int row, int column){
        // add turn to board
        String output;

        // if player X's turn, add turn to board
        if(player1Turn || secondPlayer.equals("Computer")){
            // add player X's turn to board
            currentGameTVs[row][column].setText(R.string.x);
            currentGame[row][column] = 'X';

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
            currentGameTVs[row][column].setText(R.string.o);
            currentGame[row][column] = 'O';
            output = playerName + "'s turn";
        }

        player1Turn = !player1Turn;
        t_title.setText(output);
    }

    public boolean checkPlayer1Win(){
        boolean userWon = false;

        // check horizontal wins
        if ((currentGame[0][0] == currentGame[0][1] && currentGame[0][1] == currentGame[0][2] && currentGame[0][2] == 'X') ||
                (currentGame[1][0] == currentGame[1][1] && currentGame[1][1] == currentGame[1][2] && currentGame[1][2] == 'X') ||
                (currentGame[2][0] == currentGame[2][1] && currentGame[2][1] == currentGame[2][2] && currentGame[2][2] == 'X')){
            userWon = true;
        // check for vertical wins
        } else if ((currentGame[0][0] == currentGame[1][0] && currentGame[1][0] == currentGame[2][0] && currentGame[2][0] == 'X') ||
                (currentGame[0][1] == currentGame[1][1] && currentGame[1][1] == currentGame[2][1] && currentGame[2][1] == 'X') ||
                (currentGame[0][2] == currentGame[1][2] && currentGame[1][2] == currentGame[2][2] && currentGame[2][2] == 'X')){
            userWon = true;
        // check for diagonal wins
        } else if ((currentGame[0][0] == currentGame[1][1] && currentGame[1][1] == currentGame[2][2] && currentGame[2][2] == 'X') ||
                (currentGame[0][2] == currentGame[1][1] && currentGame[1][1] == currentGame[2][0] && currentGame[2][0] == 'X')){
            userWon = true;
        }

        return userWon;
    }

    public boolean checkPlayer2Win(){
        boolean userWon = false;

        // check horizontal wins
        if ((currentGame[0][0] == currentGame[0][1] && currentGame[0][1] == currentGame[0][2] && currentGame[0][2] == 'O') ||
                (currentGame[1][0] == currentGame[1][1] && currentGame[1][1] == currentGame[1][2] && currentGame[1][2] == 'O') ||
                (currentGame[2][0] == currentGame[2][1] && currentGame[2][1] == currentGame[2][2] && currentGame[2][2] == 'O')){
            userWon = true;
            // check for vertical wins
        } else if ((currentGame[0][0] == currentGame[1][0] && currentGame[1][0] == currentGame[2][0] && currentGame[2][0] == 'O') ||
                (currentGame[0][1] == currentGame[1][1] && currentGame[1][1] == currentGame[2][1] && currentGame[2][1] == 'O') ||
                (currentGame[0][2] == currentGame[1][2] && currentGame[1][2] == currentGame[2][2] && currentGame[2][2] == 'O')){
            userWon = true;
            // check for diagonal wins
        } else if ((currentGame[0][0] == currentGame[1][1] && currentGame[1][1] == currentGame[2][2] && currentGame[2][2] == 'O') ||
                (currentGame[0][2] == currentGame[1][1] && currentGame[1][1] == currentGame[2][0] && currentGame[2][0] == 'O')){
            userWon = true;
        }

        return userWon;
    }

    public void getCompMove(){
        // random generator
        Random rand = new Random();

        // initial variables
        boolean valid = false;

        // checks if there are still spaces left for the computer to go, if so, comp randomly picks a move
        if(numSpacesLeft()>0) {
            do{
                // get row, col for comp move
                int compRow = rand.nextInt(3);
                int compCol = rand.nextInt(3);
                Log.d("TAG", compRow + " " + compCol);
                // check if spot isn't taken
                if (currentGame[compRow][compCol] != 'X' && currentGame[compRow][compCol] != 'O') {
                    currentGame[compRow][compCol] = 'O';
                    currentGameTVs[compRow][compCol].setText("O");
                    valid = true;
                }

            } while (!valid); // continue loop until valid move is selected
        }
    }

    public void resetBoard(){
        currentGame = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};

        for (int i = 0 ; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                currentGame[i][j] = ' ';
                currentGameTVs[i][j].setText(" ");
            }
        }

//        a1.setText(String.valueOf(currentGame[0][0]));
//        a2.setText(String.valueOf(currentGame[0][1]));
//        a3.setText(String.valueOf(currentGame[0][2]));
//        b1.setText(String.valueOf(currentGame[1][0]));
//        b2.setText(String.valueOf(currentGame[1][1]));
//        b3.setText(String.valueOf(currentGame[1][2]));
//        c1.setText(String.valueOf(currentGame[2][0]));
//        c2.setText(String.valueOf(currentGame[2][1]));
//        c3.setText(String.valueOf(currentGame[2][2]));

        String output = "Player " + playerName + "'s\nstarts";
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
        playerName = sh.getString("currentPlayerName", "Player 1");
        secondPlayer = sh.getString("secondPlayer", "Player 2");

    }

    public int numSpacesLeft(){
        // initial variables
        int numSpaces=0;

        // loops through the 2D array and checks how many contain spaces
        for (int i = 0 ; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentGame[i][j] == ' ') {
                    numSpaces++;
                }
            }
        }

        return numSpaces;
    }

    private class GameAlgorithm extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}