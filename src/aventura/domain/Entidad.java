package aventura.domain;

public abstract class Entidad {

    private String nombre;
    private String descripcion;

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
