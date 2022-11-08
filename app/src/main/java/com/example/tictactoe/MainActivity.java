package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // hide default action bar
        Objects.requireNonNull(getSupportActionBar()).hide();

        Resources res = getResources();
        homeMenu = res.getStringArray(R.array.homeMenu);
        ll_logo = findViewById(R.id.ll_logo);
        tv_home = findViewById(R.id.tv_home);

        // Creating listview adapter (connects the listview to the string array)
        listview = findViewById(R.id.lv_home);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, android.R.id.text1, homeMenu);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                switch (position){
                    case 0:
                        // launch enter name
                        Intent in = new Intent(MainActivity.this, EnterNamesActivity.class);
                        startActivity(in);
                        break;

                    case 1:
                        // launch choose opponent
                        FrameLayout fl = findViewById(R.id.frame_pickOpp);
                        FragmentManager fr = getSupportFragmentManager();
                        FragmentTransaction ft = fr.beginTransaction();
                        PickOpponentFragment opponents = new PickOpponentFragment();
                        ft.replace(R.id.frame_pickOpp, opponents);
                        ft.commit();

                        listview.setVisibility(View.GONE);
                        ll_logo.setVisibility(View.GONE);
                        tv_home.setVisibility(View.GONE);

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