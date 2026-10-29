package sistemavuelos.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import sistemavuelos.excepciones.DatosInvalidosException;

/**
 * Pasajero con su propio historial (PILA): la operacion mas reciente sale primero.
 * Dos pasajeros son el mismo si tienen el mismo DUI.
 */
public class Pasajero {

    private final String dui;
    private final String nombre;
    private final String telefono;
    private final Stack<Operacion> historial = new Stack<>();

    public Pasajero(String dui, String nombre, String telefono) throws DatosInvalidosException {
        if (dui == null || dui.trim().isEmpty()) {
            throw new DatosInvalidosException("El DUI no puede estar vacio");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatosInvalidosException("El nombre no puede estar vacio");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new DatosInvalidosException("El telefono no puede estar vacio");
        }
        this.dui = dui.trim();
        this.nombre = nombre.trim();
        this.telefono = telefono.trim();
    }

    /** Apila una operacion en el historial. */
    public void registrarOperacion(Operacion op) {
        historial.push(op);
    }

    /**
     * Devuelve una COPIA del historial con la operacion mas reciente primero.
     * Se desapila una copia de la pila, asi la pila real nunca se modifica desde afuera.
     */
    public List<Operacion> verHistorial() {
        Stack<Operacion> copia = new Stack<>();
        copia.addAll(historial);
        List<Operacion> resultado = new ArrayList<>();
        while (!copia.isEmpty()) {
            resultado.add(copia.pop());
        }
        return resultado;
    }

    /** Ultima operacion registrada, o null si el historial esta vacio. */
    public Operacion ultimaOperacion() {
        return historial.isEmpty() ? null : historial.peek();
    }

    public String getDui() { return dui; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pasajero)) return false;
        return dui.equals(((Pasajero) o).dui);
    }

    @Override
    public int hashCode() {
        return dui.hashCode();
    }

    @Override
    public String toString() {
        return nombre + " (DUI " + dui + ", tel " + telefono + ")";
    }
}
