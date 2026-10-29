package sistemavuelos.servicio;

import java.util.ArrayList;
import java.util.List;
import sistemavuelos.estructuras.ArbolDestinos;
import sistemavuelos.excepciones.DatosInvalidosException;
import sistemavuelos.excepciones.VueloNoEncontradoException;
import sistemavuelos.modelo.Vuelo;

/**
 * Logica de los vuelos. PERSONA A.
 * Reglas: sin Scanner ni System.in; la lista "vuelos" siempre ordenada por codigo;
 * este gestor es el UNICO que llama a arbol.insertar / arbol.eliminarVuelo.
 */
public class GestorVuelos {

    private final ArrayList<Vuelo> vuelos = new ArrayList<>();
    private final ArbolDestinos arbol;

    public GestorVuelos(ArbolDestinos arbol) {
        this.arbol = arbol;
    }

    /** Agrega el vuelo (rechaza codigos repetidos) y lo registra tambien en el arbol. */
    public void agregarVuelo(Vuelo vuelo) throws DatosInvalidosException {
        // TODO (Persona A)
    }

    /** Quita el vuelo de la lista y del arbol. */
    public void eliminarVuelo(String codigo) throws VueloNoEncontradoException {
        // TODO (Persona A)
    }

    /** Busca por codigo (busqueda binaria). Lanza VueloNoEncontradoException si no existe. */
    public Vuelo buscarPorCodigo(String codigo) throws VueloNoEncontradoException {
        // TODO (Persona A)
        return null;
    }

    /** Copia de la lista de vuelos (ordenada por codigo). */
    public List<Vuelo> listarVuelos() {
        // TODO (Persona A)
        return new ArrayList<>();
    }

    /** Copia ordenada por precio. La lista original no se toca. */
    public List<Vuelo> ordenarPorPrecio() {
        // TODO (Persona A)
        return new ArrayList<>();
    }

    /** Copia ordenada por fecha/hora de salida. La lista original no se toca. */
    public List<Vuelo> ordenarPorHora() {
        // TODO (Persona A)
        return new ArrayList<>();
    }
}
