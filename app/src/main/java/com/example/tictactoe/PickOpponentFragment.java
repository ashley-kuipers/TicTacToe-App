package com.example.tictactoe;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class PickOpponentFragment extends Fragment {
    ListView listview;
    String[] opponentMenu;
    String player;

    public PickOpponentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // resources can only be accessed from onCreate
        Resources res = getResources();
        opponentMenu = res.getStringArray(R.array.opponentMenu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pick_opponent, container, false);

        // Creating listview adapter (connects the listview to the string array)
        listview = view.findViewById(R.id.lv_opponent);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(), R.layout.custom_listview, android.R.id.text1, opponentMenu);
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
                        Intent intent = new Intent(getActivity(), PlayGameActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        // Another Player is chosen as Player 2
                        player = "Player 2";

                        // open choose name activity so Player 2's name can be entered
                        Intent i = new Intent(getActivity(), EnterNamesActivity.class);
                        i.putExtra("playerChoosing", "Player 2");
                        startActivity(i);
                        break;

                    default:
                        // error
                        break;

                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    // Save data to shared preferences (only called if computer is chosen as second player)
    public void saveData(){
        SharedPreferences sp = this.requireActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if(player.equals("Computer")){
            // enter that second player was chosen to be computer
            editor.putString("secondPlayer", "Computer");

            // double check win log value for computer
            int compWins = sp.getInt("computer", 900000000);
            if(compWins == 900000000){
                editor.putInt("computer", 0);
            }
        }

        // commit sharedPreferences
        editor.apply();
    }

}
