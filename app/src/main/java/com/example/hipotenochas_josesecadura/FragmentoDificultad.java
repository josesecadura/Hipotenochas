package com.example.hipotenochas_josesecadura;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class FragmentoDificultad extends DialogFragment {
    Dificultad dificultad;
    String[] nivel = {"Principiante", "Amateur", "Avanzado"};

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dificultad = (Dificultad) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        ventana.setTitle("Elige la dificultad");
        ventana.setSingleChoiceItems(nivel, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dificultad.dificultadSeleccionada(i);
            }
        });
        ventana.setPositiveButton("Voler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
            }
        });
        return ventana.create();
    }


public interface Dificultad {
    public void dificultadSeleccionada(int i);
}
}
