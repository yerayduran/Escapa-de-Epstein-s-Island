package Aventura.domain;

import Aventura.interfaces.Inventariable;

public class Jugador extends Entidad {

    private String nombre;
    private Objeto[] inventario;
    private int posicion;


    public Jugador(String nombre, String descripcion, String nombre1, Objeto[] inventario, int posicion) {
        super(nombre, descripcion);
        setNombre(nombre1);
        setInventario(inventario);
        setPosicion(posicion);
    }

    public Objeto[] getInventario() {
        return inventario;
    }

    public void setInventario(Objeto[] inventario) {
        this.inventario = inventario;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    private void coger(Objeto obj){

        if(obj instanceof Inventariable){

        }

    }
}
