package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class ShowStandingsActivity extends AppCompatActivity {
    ListView scoreboard;
    Button b_back, b_reset;
    ArrayList<String> leaderboard = new ArrayList<>();
    ArrayList<String> listNames = new ArrayList<>();
    HashMap<String, Integer> sortedBoard = new HashMap<>();

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
        b_reset = findViewById(R.id.b_reset);
        scoreboard = findViewById(R.id.lv_leaderboard);

        // Creating listview adapter (connects the listview to the string array)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, leaderboard);
        scoreboard.setAdapter(adapter);

        // get leaderboard values
        Object[] sortedScores = sortedBoard.values().toArray();
        Object[] sortedNames = sortedBoard.keySet().toArray();

        // add values to scoreboard
        for(int i = sortedNames.length; i > 0; i--){
            String name = String.valueOf(sortedNames[i-1]);
            int score = Integer.parseInt(String.valueOf(sortedScores[i-1]));
            leaderboard.add(name + " . . . . . . . . . . . " + score);
        }

        // add on click listener to back button (closes activity when done looking at scores)
        b_back.setOnClickListener(view -> finish());

        // add on click listener to back button (closes activity when done looking at scores)
        b_reset.setOnClickListener(view -> {
            // reset all standings for named entered
            getSharedPreferences("prefs", 0).edit().clear().apply();

            // Go back to Main Activity
            Intent in = new Intent(ShowStandingsActivity.this, MainActivity.class);
            // tells the activity which name is being chosen
            startActivity(in);

            finish();
        });

        // add onclick listener to listview
        scoreboard.setOnItemClickListener((parent, view, position, id) -> {
            // get text from listview clicked
            String selectedFromList = (String) (scoreboard.getItemAtPosition(position));
            // get name from text
            selectedFromList = selectedFromList.substring(0, selectedFromList.indexOf('.')-1).toLowerCase();

            // Open in depth stats activity
            Intent in = new Intent(ShowStandingsActivity.this, InDepthStatsActivity.class);
            // tells the activity which name is being chosen
            in.putExtra("playerName", selectedFromList);
            startActivity(in);
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

                // add log values to a hashmap so we can sort
                sortedBoard.put(name.toUpperCase(Locale.ROOT), wins);
            }
        }

        // sort values in hashmap
        List<Map.Entry<String, Integer> > list = new LinkedList<>(sortedBoard.entrySet());
        Collections.sort(list, (o1, o2) -> (o1.getValue()).compareTo(o2.getValue()));
        HashMap<String, Integer> temp = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        sortedBoard = temp;

    }

}