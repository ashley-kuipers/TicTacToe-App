package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class EnterNamesActivity extends AppCompatActivity {
    Button b_done;
    EditText et_playerName;
    String playerChoosing;
    TextView title;
    ArrayList<String> listNames = new ArrayList<>();
    boolean firstRun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        b_done = findViewById(R.id.b_done);
        et_playerName = findViewById(R.id.et_playerName);
        title = findViewById(R.id.t_enterNames);

        // get data from intent
        Bundle bundle = getIntent().getExtras();
        // figure out which player is the name being chosen for
        playerChoosing = bundle.getString("playerChoosing");
        // figure out if this is the first run of the app
        firstRun = bundle.getBoolean("firstRun");

        // Change title if choosing name for player 2
        if (playerChoosing.equals("Player 2")){
            title.setText(R.string.enterNameTitle2);
        }

        // choose what happens when done button is pressed
        b_done.setOnClickListener(view -> {
            saveData();

            if(playerChoosing.equals("Player 2")){
                // open game activity
                Intent intent = new Intent(EnterNamesActivity.this, PlayGameActivity.class);
                startActivity(intent);
            } else if(firstRun){
                // launch choose opponent fragment
                Intent intent1 = new Intent(EnterNamesActivity.this, PIckOpponentActivity.class);
                startActivity(intent1);
            }
        });
    }

    // Save current Data
    public void saveData(){
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        // get name from editText
        String enteredName = et_playerName.getText().toString();

        // access list of names
        Gson g = new Gson();
        String j = sp.getString("listNames", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        listNames = g.fromJson(j, type);

        // if no listNames has been created, create one
        if(listNames == null){
            listNames = new ArrayList<>();
            listNames.add("computer");

            // create blank log value for computer
            editor.putInt("computer", 0);
            editor.putInt("computer_totalGames", 0);
            editor.putString("computer_recentOpponent", "name");
            editor.putString("computer_time", "N/A");
            editor.putInt("computer_totalMoves", 0);
        }

        // check for name in the current list of names, if not, create a blank log value for that name and add them to the list
        int storedName = sp.getInt(enteredName.toLowerCase(), 10000000);
        if(storedName == 10000000){
            String lowerCaseName = enteredName.toLowerCase();

            // create blank log values
            editor.putInt(lowerCaseName, 0);
            editor.putInt(lowerCaseName + "_totalGames", 0);
            editor.putString(lowerCaseName + "_recentOpponent", "name");
            editor.putString(lowerCaseName + "_time", "N/A");
            editor.putInt(lowerCaseName + "_totalMoves", 0);

            // add the name to the list of player names
            listNames.add(enteredName.toLowerCase());
        }

        // save names of current players for PlayGameActivity
        if(playerChoosing.equals("Player 1")){
            editor.putString("currentPlayerName", enteredName);
        } else if (playerChoosing.equals("Player 2")){
            editor.putString("secondPlayer", enteredName);
        }

        // convert list to json and save
        Gson gson = new Gson();
        String json = gson.toJson(listNames);
        editor.putString("listNames", json);

        // commit sharedPreferences
        editor.apply();
    }

    // when you turn the phone, this function is called to save any data you wish to save
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // saving the index to the bundle in the method argument
        outState.putString("currentText", et_playerName.getText().toString());
        super.onSaveInstanceState(outState);
    }

    // when phone is done turning, this function is called to restore any of that data you saved
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        // get values from saved state
        et_playerName.setText(savedInstanceState.getString("currentText"));
        super.onRestoreInstanceState(savedInstanceState);
    }

}