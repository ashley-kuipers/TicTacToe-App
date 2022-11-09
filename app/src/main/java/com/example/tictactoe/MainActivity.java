package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    String[] homeMenu;
    LinearLayout ll_logo;
    TextView tv_home;

    // Feature 1: two player mode - DONE
    // Feature 2: Welcome's user/displays player 2s name when it is their turn - DONE
    // Feature 3: settings? could have dark mode? | or in depth stats page? (when they click their name in the leaderboard, show more in depth info | add function to reset leaderboard
    // TODO: move gameplay into async task
    // TODO: sort leaderboard
    // TODO: some feedback on game buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // get initial vars
        Resources res = getResources();
        homeMenu = res.getStringArray(R.array.homeMenu);
        ll_logo = findViewById(R.id.ll_logo);
        tv_home = findViewById(R.id.tv_home);

        // Creating listview adapter
        listview = findViewById(R.id.lv_home);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, homeMenu);
        listview.setAdapter(adapter);

        // Determine which option the user selected, and open appropriate activity/fragment
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                switch (position){
                    case 0:
                        // launch enter name activity
                        Intent in = new Intent(MainActivity.this, EnterNamesActivity.class);
                        // tells the activity which name is being chosen
                        in.putExtra("playerChoosing", "Player 1");
                        startActivity(in);
                        break;

                    case 1:
                        // launch choose opponent fragment
                        FrameLayout fl = findViewById(R.id.frame_pickOpp);
                        FragmentManager fr = getSupportFragmentManager();
                        FragmentTransaction ft = fr.beginTransaction();
                        PickOpponentFragment opponents = new PickOpponentFragment();
                        ft.replace(R.id.frame_pickOpp, opponents);
                        ft.commit();

                        // move other items off the screen so fragment is full screen
                        listview.setVisibility(View.GONE);
                        ll_logo.setVisibility(View.GONE);
                        tv_home.setVisibility(View.GONE);
                        break;

                    case 2:
                        // launch standings activity
                        Intent intent = new Intent(MainActivity.this, ShowStandingsActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        // error
                        break;

                }

            }
        });
    }

}