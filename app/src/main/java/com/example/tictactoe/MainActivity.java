package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    String[] homeMenu;
    LinearLayout ll_logo;
    TextView tv_home;
    int test;

    // Feature 1: two player mode
    // Feature 2: Welcome's user/displays player 2s name when it is their turn
    // Feature 3: in depth stats page (when they click their name in the leaderboard, show more in depth info) |
    // Feature 4: (if feature 1 doesn't count) Function to reset stats

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        getData();

        // get initial vars
        Resources res = getResources();
        homeMenu = res.getStringArray(R.array.homeMenu);
        ll_logo = findViewById(R.id.ll_logo);
        tv_home = findViewById(R.id.tv_home);

        // Creating listview adapter
        listview = findViewById(R.id.lv_home);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, homeMenu);
        listview.setAdapter(adapter);

        // Determine which option the user selected, and open appropriate activity/fragment
        listview.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 0:
                    // launch enter name activity
                    Intent intent = new Intent(MainActivity.this, EnterNamesActivity.class);
                    // tells the activity which name is being chosen
                    intent.putExtra("playerChoosing", "Player 1");
                    startActivity(intent);
                    break;

                case 1:
                    // if shared preferences does not exist, open enter name activity, else open Choose Opponent fragment
                    if(test == 999999999){
                        // launch enter name activity
                        Intent intent1 = new Intent(MainActivity.this, EnterNamesActivity.class);
                        // tells the activity which name is being chosen
                        intent1.putExtra("playerChoosing", "Player 1");
                        intent1.putExtra("firstRun", true);
                        startActivity(intent1);
                    } else {
                        // launch choose opponent activity
                        Intent intent2 = new Intent(MainActivity.this, PIckOpponentActivity.class);
                        startActivity(intent2);
                    }
                    break;

                case 2:
                    // launch standings activity
                    Intent intent3 = new Intent(MainActivity.this, ShowStandingsActivity.class);
                    startActivity(intent3);
                    break;

                default:
                    // error
                    break;

            }

        });
    }

    // check for a computer log (basically to see if shared preferences exists)
    public void getData(){
        SharedPreferences sh = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        test = sh.getInt("computer", 999999999);
    }

}