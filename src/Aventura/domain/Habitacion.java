package aventura.domain;

import aventura.domain.Objeto;

public class Habitacion extends Entidad {

    private static final int MAX_OBJETOS = 5;

    private Objeto[] objetosDeLaSala;

    private Habitacion norte, sur, este, oeste;

    public Habitacion(String nombre, String descripcion) {
        super(nombre, descripcion);
        this.objetosDeLaSala = new Objeto[MAX_OBJETOS];
    }


    public boolean agregarObjeto(Objeto objeto) {
        for (int i = 0; i < objetosDeLaSala.length; i++) {
            if (objetosDeLaSala[i] == null) {
                objetosDeLaSala[i] = objeto;
                return true;
            }
        }
        return false;
    }

    public void setNorte(Habitacion norte) { this.norte = norte; }
    public Habitacion getNorte() { return norte; }

    public void setSur(Habitacion sur) { this.sur = sur; }
    public Habitacion getSur() { return sur; }

    public void setEste(Habitacion este) { this.este = este; }
    public Habitacion getEste() { return este; }

    public void setOeste(Habitacion oeste) { this.oeste = oeste; }
    public Habitacion getOeste() { return oeste; }
}
