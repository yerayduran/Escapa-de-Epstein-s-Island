package aventura.domain;

import aventura.exceptions.AventuraException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Habitacion {
    private static final int MAX_OBJETOS = 10;

    private String id;
    private String descripcion;
    private List<Objeto> objetos;
    private Map<String, String> salidas;

    public Habitacion() {
        this.objetos = new ArrayList<>();
        this.salidas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public Habitacion(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
        this.objetos = new ArrayList<>();
        this.salidas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void agregarObjeto(Objeto obj) throws AventuraException {
        if (objetos.size() >= MAX_OBJETOS) {
            throw new AventuraException("No se puede agregar un objeto");
        }
        objetos.add(obj);
    }

    public boolean eliminarObjeto(Objeto obj) {
        return objetos.remove(obj);
    }

    public String mirar() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.descripcion).append("\n");

        for (Objeto obj : objetos) {
            if (obj != null && obj.isVisible()) {
                sb.append(" - ").append(obj.getNombre()).append("\n");
            }
        }

        if (!salidas.isEmpty()) {
            sb.append("Salidas: ").append(String.join(", ", salidas.keySet())).append("\n");
        }

        return sb.toString();
    }

    public List<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(List<Objeto> objetos) {
        this.objetos = (objetos != null) ? objetos : new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Map<String, String> getSalidas() {
        return salidas;
    }

    public void setSalidas(Map<String, String> salidas) {
        this.salidas = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if (salidas != null) {
            this.salidas.putAll(salidas);
        }
    }

    public void addSalida(String direccion, String destinoId) {
        if (direccion != null && destinoId != null) {
            salidas.put(direccion, destinoId);
        }
    }

    public String getDestino(String direccion) {
        if (salidas == null || direccion == null) {
            return null;
        }
        return salidas.get(direccion);
    }

    public Objeto buscar(String nombre) {
        for (Objeto obj : objetos) {
            if (obj != null && obj.getNombre().equalsIgnoreCase(nombre)) {
                return obj;
            }
        }
        return null;
    }
}