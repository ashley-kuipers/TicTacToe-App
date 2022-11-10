package com.example.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class WinnerDialog  extends AppCompatDialogFragment {
    String text;
    public WinnerDialog(String textInput){
        text = textInput;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.dialog);
        builder.setTitle(text);
        builder.setPositiveButton("OKAY", (dialogInterface, i) -> {
            PlayGameActivity pga = (PlayGameActivity) getActivity();
            if (pga != null) {
                pga.resetBoard();
            }
        });

        return builder.create();
    }

}
