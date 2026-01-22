package aventura.domain;

public abstract class Objeto extends Entidad{

    private boolean visible;

    public Objeto(String nombre, String descripcion) {
        super(nombre, descripcion);
        setVisible(visible);
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
