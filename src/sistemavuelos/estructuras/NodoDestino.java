package sistemavuelos.estructuras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import sistemavuelos.modelo.Vuelo;

/**
 * Nodo del BST. La clave es el destino; como un destino se repite en varios vuelos,
 * cada nodo guarda la lista de vuelos hacia ese destino.
 */
public class NodoDestino {

    private final String destino;
    private final List<Vuelo> vuelos = new ArrayList<>();
    private NodoDestino izquierdo;
    private NodoDestino derecho;

    public NodoDestino(String destino) {
        this.destino = destino;
    }

    public void agregarVuelo(Vuelo vuelo) {
        vuelos.add(vuelo);
    }

    /** Quita el vuelo del nodo. Devuelve true si estaba. */
    public boolean quitarVuelo(Vuelo vuelo) {
        return vuelos.remove(vuelo);
    }

    public boolean estaVacio() {
        return vuelos.isEmpty();
    }

    /** Vista de solo lectura de los vuelos de este destino. */
    public List<Vuelo> getVuelos() {
        return Collections.unmodifiableList(vuelos);
    }

    public String getDestino() { return destino; }
    public NodoDestino getIzquierdo() { return izquierdo; }
    public void setIzquierdo(NodoDestino izquierdo) { this.izquierdo = izquierdo; }
    public NodoDestino getDerecho() { return derecho; }
    public void setDerecho(NodoDestino derecho) { this.derecho = derecho; }
}
