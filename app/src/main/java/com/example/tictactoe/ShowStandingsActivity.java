package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.Objects;

public class ShowStandingsActivity extends AppCompatActivity {
    ListView scoreboard;
    String[] defLeaderbaord;
    Button b_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_standings);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        b_back = findViewById(R.id.b_back);

        Resources res = getResources();
        defLeaderbaord = res.getStringArray(R.array.leaderboard_default);

        // Creating listview adapter (connects the listview to the string array)
        scoreboard = findViewById(R.id.lv_leaderboard);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, defLeaderbaord);
        scoreboard.setAdapter(adapter);

        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}