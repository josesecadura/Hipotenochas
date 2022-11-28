package com.example.hipotenochas_josesecadura;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener, FragmentoDificultad.Dificultad, FragmentoSpinner.Personaje {
    private Point p;
    private GridLayout gridLayout;
    private Button[][] botones;
    private int anchura, altura,numHipotenochas,numFilas,
            numColumnas,anchuraPantalla,alturaPantalla;
    private Tablero tablero;

    //Haremos un objeto que guarde el fondo que quereos poner a los botones
    private Drawable hipoenochaSeleccionada;

    //Sirve para poner las líneas de separación entre los botones
    private GradientDrawable gd = new GradientDrawable();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obtenerAlturaAnchura();
        //Por normal genereal la hipotenocha seleccionada sera la normal
        hipoenochaSeleccionada = getResources().getDrawable(R.drawable.hipotenocha);
        anchuraPantalla=anchura/10;
        alturaPantalla=altura/10;
        gridLayout = findViewById(R.id.gridLayout);
        //Elegimos la dificultad 0 porque será la principal de la aplicación
        dificultadSeleccionada(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemInst:
                showDialog(1);
                return true;
            case R.id.itemComenzar:
                //Cuando le demos a este boton se reiniciara el juego
                reiniciarJuego();
                return true;

            case R.id.itemConfigura:
                FragmentoDificultad fragmentoDificultad = new FragmentoDificultad();
                FragmentManager fm = getSupportFragmentManager();
                fragmentoDificultad.show(fm, "fragmentoDificultad");
                return true;

            case R.id.elegir_hipotenocha:
                FragmentoSpinner fragHipotenocha = new FragmentoSpinner();
                fragHipotenocha.show(getSupportFragmentManager(), "fragmentoDificultad");
               /* FragmenoSpinnerHipotenocha fragHipotenocha = new FragmenoSpinnerHipotenocha();
                fragHipotenocha.show(getSupportFragmentManager(), "fragmentoDificultad");*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Recogemos lo que nos manda el spinner y cambiamos la hipotenocha seleccionada
    @Override
    public void personajeSeleccionado(int i) {
        switch (i){
            case 0:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.hipotenocha);
                break;
            case 1:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.abstracta);
                break;
            case 2:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.afortunada);
                break;
            case 3:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.deportista);
                break;
            case 4:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.camuflada);
                break;
            case 5:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.che);
                break;
            case 6:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.colchonero);
                break;
            case 7:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.cule);
                break;
            case 8:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.deincognito);
                break;
            case 9:
                hipoenochaSeleccionada = getResources().getDrawable(R.drawable.espanola);
                break;
        }
    }

    //Recogemos el fragmento de la dificultad para crear el tablero si la cambia
    public void dificultadSeleccionada(int i) {
        switch (i) {
            case 0:
                gridLayout.removeAllViews();
                numFilas = 8;
                numColumnas = 8;
                numHipotenochas = 10;
                anchuraPantalla = anchura / 10;
                alturaPantalla = altura / 10;
                crearTablero();
                ponerBotones();
                break;
            case 1:
                gridLayout.removeAllViews();
                numFilas = 12;
                numColumnas = 12;
                numHipotenochas = 30;
                anchuraPantalla = anchura / 15;
                alturaPantalla = altura / 15;
                crearTablero();
                ponerBotones();
                break;
            case 2:
                gridLayout.removeAllViews();
                numFilas=16;
                numColumnas=16;
                numHipotenochas=60;
                anchuraPantalla = anchura / 20;
                alturaPantalla = altura / 20;
                crearTablero();
                ponerBotones();
                break;
        }
    }

    //Pulsación corta del botón
    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        String[] posicion = b.getTag().toString().split(",");
        int x = Integer.parseInt(posicion[0]);
        int y = Integer.parseInt(posicion[1]);
        if (tablero.mostrarCasillasSinHipotenochas(x, y) == -1) {
            b.setBackground(hipoenochaSeleccionada);
            bloquearBotones();
            Toast.makeText(MainActivity.this, "Has perdido, había hipotenocha", Toast.LENGTH_SHORT).show();
        } else {
            //Si no hay mina en la casilla que hemos pulsado entonces tendremos que mostrar el numero de minas que hay alrededor
            int numHipotenochasAlrededor = tablero.mostrarCasillasSinHipotenochas(x,y);
            //Si el numero de hipotenochas alrededor es 0 entonces tendremos que mostrar las casillas alrededor
            if(numHipotenochasAlrededor==0) {
                mostrarCasillasAlrededor(x,y);
            }else{
                b.setTextColor(Color.BLACK);
                b.setText(String.valueOf(numHipotenochasAlrededor));
                b.setEnabled(false);
            }
            comprobarGanador();
        }
    }

    //Pulsación larga del botón
    @Override
    public boolean onLongClick(View view) {
        Button b = (Button) view;
        String[] posicion = b.getTag().toString().split("");
        int x = Integer.parseInt(posicion[0]);
        int y = Integer.parseInt(posicion[2]);
        if (tablero.mostrarCasillasSinHipotenochas(x, y) == -1) {
            b.setBackground(hipoenochaSeleccionada);
            b.setEnabled(false);
            Toast.makeText(this, "Quedan "+(numHipotenochas-tablero.getNumHipotenochasMostradas())+" hipotenochas", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Has perdido, no habia hipotenocha", Toast.LENGTH_SHORT).show();
            //Como han perdido tendremos que bloquear todos los botones
            bloquearBotones();
        }
        comprobarGanador();
        return true;
    }

    //Si gana el jugador se mostrara un mensaje y bloquearemos el tablero
    private void comprobarGanador() {
        if (tablero.haGanado()){
            Toast.makeText(this, "HAS GANADO", Toast.LENGTH_SHORT).show();
            bloquearBotones();
        }
    }

    //Tendremos que recorrer las casillas de alrededor, y si las de alrdedor tambien son 0 entonces
    // tendremos que mostrar las de alrededor de esas
    private void mostrarCasillasAlrededor(int x, int y) {
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (i >= 0 && i < botones.length && j >= 0 && j < botones[i].length) {
                    if (botones[i][j].isEnabled()) {
                        int numHipotenochasAlrededor = tablero.mostrarCasillasSinHipotenochas(i, j);
                        if (numHipotenochasAlrededor == 0) {
                            botones[i][j].setText("");
                            botones[i][j].setEnabled(false);
                            mostrarCasillasAlrededor(i, j);
                            botones[i][j].setBackgroundColor(Color.RED);
                        } else {
                            botones[i][j].setTextColor(Color.BLACK);
                            botones[i][j].setText(String.valueOf(numHipotenochasAlrededor));
                            botones[i][j].setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    //Creamos el tablero ficticio con las hipotenochas para obtener las posiciones de los botones y las hipotenochas
    private void crearTablero() {
        tablero = new Tablero(numFilas, numColumnas, numHipotenochas);
        tablero.crearTablero();
        tablero.crearHipotenochas();
    }

    //Ponemos los botones en el gridLayout y les asignamos una posición
    private void ponerBotones() {
        botones = new Button[numFilas][numColumnas];
        gridLayout.setColumnCount(numColumnas);
        gridLayout.setRowCount(numFilas);
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                gd.setStroke(1, Color.BLACK);
                gd.setColor(Color.WHITE);
                botones[i][j] = new Button(this);
                botones[i][j].setBackground(gd);
                botones[i][j].setTag(i + "," + j);
                botones[i][j].setId(View.generateViewId());
                botones[i][j].setLayoutParams(new ViewGroup.LayoutParams(anchuraPantalla, alturaPantalla));
                botones[i][j].setId(View.generateViewId());
                gridLayout.addView(botones[i][j]);
                botones[i][j].setOnClickListener(this);
                botones[i][j].setOnLongClickListener(this);
            }
        }
    }

    private void reiniciarJuego() {
        //Reiniciamos el juego
        gridLayout.removeAllViews();
        crearTablero();
        ponerBotones();
    }

    private void bloquearBotones() {
        for (int i = 0; i < botones.length; i++) {
            for (int j = 0; j < botones[i].length; j++) {
                botones[i][j].setEnabled(false);
            }
        }
    }

    private void obtenerAlturaAnchura() {
        p = new Point();
        Display pantallaDisplay = getWindowManager().getDefaultDisplay();
        pantallaDisplay.getSize(p);
        anchura = p.x;
        altura = p.y;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        id = 1;
        AlertDialog.Builder ventana = new AlertDialog.Builder(this);
        ventana = new AlertDialog.Builder(this);
        ventana.setTitle("Instrucciones");
        ventana.setMessage("El juego es tipo buscaminas:\nCuando pulsas en una casilla, sale" +
                " un número que identifica cuántas hipotenochas hay alrededor:\nTen cuidado porque si " +
                "pulsas en una casilla que tenga una hipotenocha escondida,perderás.\nSi crees o tienes" +
                " la certeza de que hay una hipotenocha, haz un click largo sobre la casilla para señalarla." +
                " No hagas click largo en una casilla que no tenga hipotenocha, porque perderás. Ganas una vez" +
                " hayas encontrado todas las hipotenochas");
        ventana.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        return ventana.create();
    }
}