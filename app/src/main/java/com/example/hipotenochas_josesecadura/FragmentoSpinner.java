package com.example.hipotenochas_josesecadura;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class FragmentoSpinner extends DialogFragment {
    /* Podriamos añadir más tipos de hipotenochas fijandonos en los objetos drawables.
    * Importante cambiarlo en el main también cuando recogemos el valor del spinner*/

    private String[] hipotenocha = {"Normal","Abstracta", "Afortunada", "Deportista","Camuflada","Che","Colchonero","Cule",
    "De incógnito","Española"};
    private int[] fotoHipotenocha = {R.drawable.hipotenocha,R.drawable.abstracta, R.drawable.afortunada, R.drawable.deportista, R.drawable.camuflada,R.drawable.che,
            R.drawable.colchonero,R.drawable.cule, R.drawable.deincognito, R.drawable.espanola};
    private Personaje personje;

    //Este método se ejecuta cuando se crea el fragmento
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        personje = (Personaje) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder ventana = new AlertDialog.Builder(getActivity());
        View v = getLayoutInflater().inflate(R.layout.dialog_spinner,null);
        ventana.setTitle("Elige hipotenocha");
        Spinner spinner = v.findViewById(R.id.spinner);
        ventana.setView(v);
        HipotenochasAdapter adapter = new HipotenochasAdapter();
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                personje.personajeSeleccionado(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    //Creamos el adaptador de las hipotenochas para poder mostrarlas
    public class HipotenochasAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return hipotenocha.length;
        }

        @Override
        public Object getItem(int i) {
            return hipotenocha[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            //Crearemos el inflador para el spinner personalizado
            LayoutInflater inflador = LayoutInflater.from(viewGroup.getContext());
            View v = inflador.inflate(R.layout.item_spinner, viewGroup, false);
            ImageView iv = v.findViewById(R.id.imageView);
            TextView tv = v.findViewById(R.id.nombreHip);
            iv.setImageResource(fotoHipotenocha[i]);
            tv.setText(hipotenocha[i]);
            return v;
        }
    }

    //interfaz para mandar el objeto
    public interface Personaje {
        public void personajeSeleccionado(int i);
    }

}
