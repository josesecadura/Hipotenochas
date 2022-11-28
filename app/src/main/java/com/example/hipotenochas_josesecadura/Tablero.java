package com.example.hipotenochas_josesecadura;

import java.util.LinkedList;
import java.util.List;

/* Esta clase tiene todos los metodos relacionados con crear las hipotenochas,
*  el tablero y el movimiento de las mismas*/
public class Tablero {

    Casilla[][] casillas;
    int filas, columnas;
    int hipotenochas;

    public Tablero(int filas, int columnas, int hipotenochas) {
        this.filas = filas;
        this.columnas = columnas;
        this.hipotenochas = hipotenochas;
    }

    public void crearTablero() {
        casillas = new Casilla[this.filas][this.columnas];
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                casillas[i][j] = new Casilla(i, j);
            }
        }
    }

    public void crearHipotenochas() {
        //Creamos las hipotenochas en el tablero para tener sus posiciones
        int hipotenochasCreadas = 0;
        while (hipotenochasCreadas < hipotenochas) {
            int fila = (int) (Math.random() * this.filas);
            int columna = (int) (Math.random() * this.columnas);
            if (!casillas[fila][columna].isHipotenocha()) {
                casillas[fila][columna].setHipotenocha(true);
                hipotenochasCreadas++;
            }
        }
        numeroHipotenochasCerca();
    }

    public int mostrarCasillasSinHipotenochas(int fila, int columna) {
        if (casillas[fila][columna].isHipotenocha()) {
            /*Aquí modificamos el estado de hipotenocha mostrada, porque al devolver -1
             * es que hay hipotenocha*/
            casillas[fila][columna].setHipotenochaMostrada(true);
            return -1;
        } else {
            casillas[fila][columna].setMostrada(true);
            return casillas[fila][columna].getHipotenochasAlrededor();
        }
    }

    private void numeroHipotenochasCerca() {
        //Recorremos el tablero para saber cuantas hipotenochas hay alrededor de cada casilla
        //y así poderlo indicar en el tablero
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isHipotenocha()) {
                    List<Casilla> casillasCerca = obtenerCasillasCerca(i, j);
                    for (Casilla casilla : casillasCerca) {
                        casilla.incrementarHipotenochasAlrededor();
                    }
                }
                System.out.println();
            }
        }
    }

    //Obtener las casillas que estan alrededor de la casilla que le pasamos tiene que ser recursivo para
    // que se repita hasta que no haya mas casillas alrededor
    private List<Casilla> obtenerCasillasCerca(int posFila, int posColumna) {
        List<Casilla> listaCasillas = new LinkedList<>();
        //Recorremos las filas que estan alrededor de la casilla para ver si hay minas
        for (int i = 0; i < 8; i++) {
            int fila = posFila;
            int columna = posColumna;
            switch (i) {
                case 0:
                    fila--;
                    break;
                case 1:
                    fila--;
                    columna++;
                    break;
                case 2:
                    columna++;
                    break;
                case 3:
                    columna++;
                    fila++;
                    break;
                case 4:
                    fila++;
                    break;
                case 5:
                    fila++;
                    columna--;
                    break;
                case 6:
                    columna--;
                    break;
                case 7:
                    fila--;
                    columna--;
                    break;
            }
            if (fila >= 0 && fila < this.casillas.length && columna >= 0 && columna < this.casillas[0].length) {
                listaCasillas.add(casillas[fila][columna]);
            }
        }
        return listaCasillas;
    }

    private int getNumCasillasMostradas() {
        //Obtenemos el número de casillas mostradas para después comprobar si hemos ganado
        int numCasillasMostradas = 0;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isMostrada()) {
                    numCasillasMostradas++;
                }
            }
        }
        return numCasillasMostradas;
    }

    public int getNumHipotenochasMostradas() {
        //Obtenemos el número de casillas mostradas para después comprobar si hemos ganado
        int numHipotenochasMostradas = 0;
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].isHipotenochaMostrada()) {
                    numHipotenochasMostradas++;
                }
            }
        }
        return numHipotenochasMostradas;
    }

    //boolean ha ganado
    public boolean haGanado() {
        //Si el numero de casillas mostradas es igual al numero de casillas que hay menos el numero de hipotenochas
        //también ganamos si hemos mostrado todas las hipotenochas
        if (getNumCasillasMostradas() == (this.filas * this.columnas) - hipotenochas || getNumHipotenochasMostradas() == hipotenochas) {
            return true;
        } else {
            return false;
        }
    }
}
