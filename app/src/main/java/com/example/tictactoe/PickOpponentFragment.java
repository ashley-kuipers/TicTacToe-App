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

import java.util.Objects;


public class PickOpponentFragment extends Fragment {
    ListView listview;
    String[] opponentMenu;

    public PickOpponentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Log.d("TAG", "clicked " + position);
                switch (position){
                    case 0:
                        // Computer
                        saveData("Computer");

                        // open game activity
                        Intent intent = new Intent(getActivity(), PlayGameActivity.class);
                        startActivity(intent);
                        break;

                    case 1:
                        // Another Player
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

    // Save current Data when app is closed
    public void saveData(String player){
        SharedPreferences sharedPreferences = this.requireActivity().getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // save values to sharedPreferences
        editor.putString("secondPlayer", "Computer"); // TODO: CHANGE VAR HERE

        // commit sharedPreferences
        editor.apply();
    }

}
