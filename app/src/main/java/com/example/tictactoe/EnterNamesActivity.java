package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class EnterNamesActivity extends AppCompatActivity {
    Button b_done;
    EditText et_playerName;
    String playerChoosing;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_names);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        b_done = findViewById(R.id.b_done);
        et_playerName = findViewById(R.id.et_playerName);
        title = findViewById(R.id.t_enterNames);

        Bundle bundle = getIntent().getExtras();
        playerChoosing = bundle.getString("playerChoosing");

        if (!playerChoosing.equals("Player 1")){
            title.setText(R.string.enterNameTitle2);
        }

        b_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                finish();
                if(playerChoosing.equals("Player 2")){
                    // open game activity
                    Intent intent = new Intent(EnterNamesActivity.this, PlayGameActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Save current Data when app is closed
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save values to sharedPreferences
        if(playerChoosing.equals("Player 1")){
            editor.putString("currentPlayerName", et_playerName.getText().toString());
        } else if (playerChoosing.equals("Player 2")){
            editor.putString("secondPlayer", et_playerName.getText().toString());
        }

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