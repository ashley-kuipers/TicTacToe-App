package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ShowStandingsActivity extends AppCompatActivity {
    ListView scoreboard;
    Button b_back;
    ArrayList<String> leaderboard = new ArrayList<String>();
    ArrayList<String> listNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_standings);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // get data from shared preferences
        getData();

        // initial variables
        b_back = findViewById(R.id.b_back);
        scoreboard = findViewById(R.id.lv_leaderboard);

        // Creating listview adapter (connects the listview to the string array)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, leaderboard);
        scoreboard.setAdapter(adapter);

        // add on click listener to back button (closes activity when done looking at scores)
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    // gets data from the shared preferences
    public void getData(){
        SharedPreferences sh = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        // get stringArray from file
        Gson gson = new Gson();
        String json = sh.getString("listNames", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        listNames = gson.fromJson(json, type);

        // if listNames is not null, add each entry in shared preferences to leaderboard
        if(listNames != null){
            for(int i=0; i < listNames.size(); i++){
                String name = listNames.get(i);
                int wins = sh.getInt(name, 0);
                leaderboard.add(name.toUpperCase() + " . . . . . . . . . . . " + wins);
            }
        }

        // put each name and score in a treemap (key = score, value = name)


    }

}