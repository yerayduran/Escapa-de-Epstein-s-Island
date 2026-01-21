package Aventura.domain;

public abstract class Entidad {

    private String nombre;
    private String descripcion;
    private boolean hayObjetos;

    public Entidad(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
