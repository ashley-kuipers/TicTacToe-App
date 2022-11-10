package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Objects;

public class PIckOpponentActivity extends AppCompatActivity {
    ListView listview;
    String[] opponentMenu;
    String player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_opponent);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        // resources can only be accessed from onCreate
        Resources res = getResources();
        opponentMenu = res.getStringArray(R.array.opponentMenu);

        // Creating listview adapter (connects the listview to the string array)
        listview = findViewById(R.id.lv_opponent);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, opponentMenu);
        listview.setAdapter(adapter);

        // add on click listener to the listview
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("TAG", "clicked " + position);
                switch (position){
                    case 0:
                        // Computer is chosen as player 2
                        player = "Computer";
                        saveData();

                        // open game activity
                        Intent intent = new Intent(PIckOpponentActivity.this, PlayGameActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        // Another Player is chosen as Player 2
                        player = "Player 2";

                        // open choose name activity so Player 2's name can be entered
                        Intent i = new Intent(PIckOpponentActivity.this, EnterNamesActivity.class);
                        i.putExtra("playerChoosing", "Player 2");
                        startActivity(i);
                        break;

                    default:
                        // error
                        break;

                }

            }
        });
    }

    // Save data to shared preferences (only called if computer is chosen as second player)
    public void saveData(){
        SharedPreferences sp = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if(player.equals("Computer")){
            // enter that second player was chosen to be computer
            editor.putString("secondPlayer", "Computer");
        }

        // commit sharedPreferences
        editor.apply();
    }
}