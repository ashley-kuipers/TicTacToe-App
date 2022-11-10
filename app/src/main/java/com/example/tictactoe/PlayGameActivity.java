package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class PlayGameActivity extends AppCompatActivity implements View.OnClickListener {
    Button b_quit;
    TextView a1, a2, a3, b1, b2, b3, c1, c2, c3, t_title;
    char[][] currentGame;
    TextView[][] currentGameTVs;
    int p1Wins, p2Wins, compWins, p1TotalGames, p2TotalGames, compTotalGames, p1TotalMoves, p2TotalMoves, compTotalMoves;
    boolean gameWon = false, player1Turn = true;
    String playerName, secondPlayer, p1RecentOpponent, p2RecentOpponent, compRecentOpponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // get data from shared preferences
        getData();

        // initial variables
        b_quit = findViewById(R.id.b_quit);
        t_title = findViewById(R.id.t_title);
        String output = "Welcome " + playerName + ",\ntap a spot to start";
        t_title.setText(output);

        // bind all TextViews to variables
        a1 = findViewById(R.id.a1);
        a2 = findViewById(R.id.a2);
        a3 = findViewById(R.id.a3);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);
        c3 = findViewById(R.id.c3);

        // add on click listener to every TextView
        a1.setOnClickListener(this);
        a2.setOnClickListener(this);
        a3.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);

        // create game board
        currentGame = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};

        // create analogous game board of TextViews
        currentGameTVs = new TextView[][]{{a1, a2, a3}, {b1, b2, b3}, {c1, c2, c3}};

        // Adds function to the quit button (quits game and opens main activity)
        b_quit.setOnClickListener(view -> {
            finish();
            Intent i = new Intent(PlayGameActivity.this, MainActivity.class);
            startActivity(i);
        });

    }

    // deals with the click listener for each game board text view
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

    }

    private class GameAlgorithm extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

    // Adds a corresponding move to the board
    public void addTurn(int row, int column){
        String output;

        // if player X's turn (or playing against the computer), add a move
        if(player1Turn || secondPlayer.equals("Computer")){
            // add player X's turn to board
            currentGameTVs[row][column].setText(R.string.x);
            currentGame[row][column] = 'X';
            p1TotalMoves++;

            // check win conditions
            if(checkPlayer1Win()){
                openDialog(playerName + " wins!");
                // add win to the player 1's data
                p1Wins++;
                saveData();
            }

            // if playing against the computer (and user hasn't won already), get the computers move
            if(secondPlayer.equals("Computer") && !checkPlayer1Win()){
                // get computer move and add it to board
                getCompMove();
                compTotalMoves++;
                output = "Tap to play\nyour turn";

                // check if the computer won (and prevent computer AND player from winning at the same time)
                if(!checkPlayer1Win() && checkPlayer2Win()){
                    openDialog(secondPlayer + " wins!");
                    compWins++;
                    saveData();
                }

            // else switch to player 2's turn
            } else {
                output = secondPlayer + "'s\nturn";
            }

        // else get second player's move
        } else {
            // Add second player's move to board
            currentGameTVs[row][column].setText(R.string.o);
            currentGame[row][column] = 'O';
            p2TotalMoves++;
            output = playerName + "'s\nturn";

            // check if player 2 wins
            if(checkPlayer2Win()){
                openDialog(secondPlayer + " wins!");
                p2Wins++;
                saveData();
            }
        }

        // check if the game is a draw
        if(numSpacesLeft() == 0){
            openDialog("It's a draw!");
            saveData();
        }

        // if the game hasn't been won, switch the turns
        if(!gameWon){
            player1Turn = !player1Turn;
        } else {
            gameWon = false;
        }

        t_title.setText(output);

    }

    // Check if player X has a win
    public boolean checkPlayer1Win(){
        boolean userWon = false;

        // check for horizontal wins
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

    // check if Player O has a win
    public boolean checkPlayer2Win(){
        boolean userWon = false;

        // check for horizontal wins
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

    // gets the computers random move
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
                // check if spot isn't taken
                if (currentGame[compRow][compCol] != 'X' && currentGame[compRow][compCol] != 'O') {
                    currentGame[compRow][compCol] = 'O';
                    currentGameTVs[compRow][compCol].setText("O");
                    valid = true;
                }

            } while (!valid); // continue loop until valid move is selected
        }
    }

    // resets the game board
    public void resetBoard(){
        // turns each TextView and array value back to empty space
        for (int i = 0 ; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                currentGame[i][j] = ' ';
                currentGameTVs[i][j].setText(" ");
            }
        }

        // Lets users know that it's player 1's turn again
        String output = playerName + "\nstarts";
        t_title.setText(output);
        player1Turn = true;
    }

    // checks the number of empty spaces on the board
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

    // opens an alert dialog when someone wins
    public void openDialog(String text){
        WinnerDialog wd = new WinnerDialog(text);
        wd.show(getSupportFragmentManager(), "dialog");
        gameWon = true;
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
        outState.putCharArray("row1", currentGame[0]);
        outState.putCharArray("row2", currentGame[1]);
        outState.putCharArray("row3", currentGame[2]);

        // save other values
        outState.putString("title", t_title.getText().toString());
        outState.putBoolean("gameWon", gameWon);
        outState.putBoolean("player1Turn", player1Turn);

        super.onSaveInstanceState(outState);
    }

    // when phone is done turning, this function is called to restore any of that data you saved
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle saved) {
        // get values from saved state
        a1.setText(saved.getString("a1"));
        a2.setText(saved.getString("a2"));
        a3.setText(saved.getString("a3"));
        b1.setText(saved.getString("b1"));
        b2.setText(saved.getString("b2"));
        b3.setText(saved.getString("b3"));
        c1.setText(saved.getString("c1"));
        c2.setText(saved.getString("c2"));
        c3.setText(saved.getString("c3"));

        t_title.setText(saved.getString("title"));
        gameWon = saved.getBoolean("gameWon");
        player1Turn = saved.getBoolean("player1Turn");

        currentGame = new char[][]{saved.getCharArray("row1"), saved.getCharArray("row2"), saved.getCharArray("row3")};

        super.onRestoreInstanceState(saved);
    }

    // Retrieve data from shared preferences file
    public void getData(){
        SharedPreferences sh = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // Get current player names
        playerName = sh.getString("currentPlayerName", "Player 1");
        secondPlayer = sh.getString("secondPlayer", "Player 2");

        // get data for player 1
        p1Wins = sh.getInt(playerName.toLowerCase(), 0);
        p1TotalGames = sh.getInt(playerName.toLowerCase() + "_totalGames", 0);
        p1TotalMoves = sh.getInt(playerName.toLowerCase() + "_totalMoves", 0);

        // get data for player 2
        p2Wins = sh.getInt(secondPlayer.toLowerCase(), 0);
        p2TotalGames = sh.getInt(playerName.toLowerCase() + "_totalGames", 0);
        p2TotalMoves = sh.getInt(playerName.toLowerCase() + "_totalMoves", 0);

        // get data for computer
        compWins = sh.getInt("computer", 0);
        compTotalGames = sh.getInt("computer_totalGames", 0);
        compTotalMoves = sh.getInt("computer_totalMoves", 0);

    }

    // Save data to shared preferences and update player's stats
    public void saveData(){
        String time = getTime();

        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        // save game data depending on who was being played (computer or second player)
        if (secondPlayer.equals("Computer")){
            compTotalGames++;
            compRecentOpponent = playerName.toLowerCase();
            p1RecentOpponent = "computer";

            editor.putInt("computer", compWins);
            editor.putInt("computer_totalGames", compTotalGames);
            editor.putString("computer_recentOpponent", compRecentOpponent);
            editor.putString("computer_time", time);
            editor.putInt("computer_totalMoves", compTotalMoves);
        } else {
            p2TotalGames++;
            p2RecentOpponent = playerName.toLowerCase();
            p1RecentOpponent = secondPlayer.toLowerCase();

            editor.putInt(secondPlayer.toLowerCase(), p2Wins);
            editor.putInt(secondPlayer.toLowerCase() + "_totalGames", p2TotalGames);
            editor.putString(secondPlayer.toLowerCase() + "_recentOpponent", p2RecentOpponent);
            editor.putString(secondPlayer.toLowerCase() + "_time", time);
            editor.putInt(secondPlayer.toLowerCase() + "_totalMoves", p2TotalMoves);
        }

        // add to player 1's total games
        p1TotalGames++;

        // saves player 1's data to shared preferences
        editor.putInt(playerName.toLowerCase(), p1Wins);
        editor.putInt(playerName.toLowerCase() + "_totalGames", p1TotalGames);
        editor.putString(playerName.toLowerCase() + "_recentOpponent", p1RecentOpponent);
        editor.putString(playerName.toLowerCase() + "_time", time);
        editor.putInt(playerName.toLowerCase() + "_totalMoves", p1TotalMoves);

        editor.apply();
    }


    // Returns a formatted string of the current system time
    public String getTime(){
        // get the time when the new code was created
        long time = System.currentTimeMillis();
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm aa dd/MM/yyyy", Locale.CANADA);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return formatter.format(calendar.getTime());
    }

}