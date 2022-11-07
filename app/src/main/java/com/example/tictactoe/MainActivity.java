package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    String[] homeMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        Resources res = getResources();
        homeMenu = res.getStringArray(R.array.homeMenu);

        // Creating listview adapter (connects the listview to the string array)
        listview = findViewById(R.id.lv_home);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, homeMenu);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("TAG", "clicked " + position);
                switch (position){
                    case 0:
                        // launch enter names
                        Intent in = new Intent(MainActivity.this, EnterNamesActivity.class);
                        startActivity(in);
                        break;

                    case 1:
                        // launch play game
                        Intent inte = new Intent(MainActivity.this, PlayGameActivity.class);
                        startActivity(inte);
                        break;

                    case 2:
                        // launch standings
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