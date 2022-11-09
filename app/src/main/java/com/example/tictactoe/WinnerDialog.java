package com.example.tictactoe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class WinnerDialog  extends AppCompatDialogFragment {
    String currentWinner;
    public WinnerDialog(String winner){
        currentWinner = winner;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.dialog);
        builder.setTitle(currentWinner + " wins!");
        builder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                PlayGameActivity pga = (PlayGameActivity) getActivity();
                if (pga != null) {
                    pga.resetBoard();
                }
            }
        });

        return builder.create();
    }

}
