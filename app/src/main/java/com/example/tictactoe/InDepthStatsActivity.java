package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class InDepthStatsActivity extends AppCompatActivity {
    ListView stats;
    Button b_back2;
    TextView title;
    ArrayList<String> statsAL = new ArrayList<>();
    String player, lastOpponent, percentageFormatted, time, averageMovesFormatted;
    int wins, totalGames, totalMoves, MAX_DIGITS=26;
    double percentage, averageMoves;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_depth_stats);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        stats = findViewById(R.id.lv_stats);
        b_back2 = findViewById(R.id.b_back2);
        title = findViewById(R.id.title_inDepth);

        // get player name from intent
        Bundle bundle = getIntent().getExtras();
        player = bundle.getString("playerName");

        getData();

        String out = "Statistics for \n" + player.toUpperCase();
        title.setText(out);

        statsAL.add(getFormattedString("Games Played", String.valueOf(totalGames)));
        statsAL.add(getFormattedString("Games Won", String.valueOf(wins)));
        statsAL.add(getFormattedString("Win Rate", percentageFormatted + "%"));
        statsAL.add(getFormattedString("Total # Moves", String.valueOf(totalMoves)));
        statsAL.add(getFormattedString("Average # Moves", averageMovesFormatted));

        statsAL.add("Last played " + lastOpponent.toUpperCase() + " at\n" + time.substring(0,10) + " on " + time.substring(11));

        // Creating listview adapter (connects the listview to the string array)
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, statsAL);
        stats.setAdapter(adapter);

        // add on click listener to back button (closes activity when done looking at scores)
        b_back2.setOnClickListener(view -> finish());

    }

    // Retrieve data from shared preferences file
    public void getData(){
        SharedPreferences sh = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        wins = sh.getInt(player, 0);
        totalGames = sh.getInt(player + "_totalGames", 1);
        time = sh.getString(player + "_time", "N/A");
        lastOpponent = sh.getString(player + "_recentOpponent", "none");
        totalMoves = sh.getInt(player + "_totalMoves", 0);

        percentage = (((double) wins)/totalGames) * 100;
        averageMoves = (((double)totalMoves)/totalGames);
        DecimalFormat fmt = new DecimalFormat("###.#");
        percentageFormatted = fmt.format(percentage);
        averageMovesFormatted = fmt.format(averageMoves);

    }

    public String getFormattedString(String title, String variable){
        int digitsFirst = title.length();
        int digitsAfter = variable.length();

        String output = "";

        int digitsMiddle = MAX_DIGITS - digitsFirst - digitsAfter;

        output += title;
        for (int i = 0; i < digitsMiddle; i+=2){
            output += " .";
        }
        output += (" " + variable);

        return output;
    }
}