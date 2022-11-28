package com.example.hipotenochas_josesecadura;

public class Casilla {

    private int fila, columna;
    boolean isHipotenocha;
    boolean isMostrada;
    boolean hipotenochaMostrada;
    int hipotenochasAlrededor;

    public Casilla(int filas, int columnas) {
        this.fila = filas;
        this.columna = columnas;
    }

    //El get y set de fila y columna no se usan, pero los dejo por si en un futuro se le saca utilidad
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean isHipotenocha() {
        return isHipotenocha;
    }

    public void setHipotenocha(boolean hipotenocha) {
        isHipotenocha = hipotenocha;
    }

    public void incrementarHipotenochasAlrededor(){
        this.hipotenochasAlrededor++;
    }

    public int getHipotenochasAlrededor() {
        return hipotenochasAlrededor;
    }

    public void setHipotenochasAlrededor(int hipotenochasAlrededor) {
        this.hipotenochasAlrededor = hipotenochasAlrededor;
    }

    public boolean isHipotenochaMostrada() {
        return hipotenochaMostrada;
    }

    public void setHipotenochaMostrada(boolean hipotenochaMostrada) {
        this.hipotenochaMostrada = hipotenochaMostrada;
    }

    public boolean isMostrada() {
        return isMostrada;
    }

    public void setMostrada(boolean mostrada) {
        isMostrada = mostrada;
    }
}
