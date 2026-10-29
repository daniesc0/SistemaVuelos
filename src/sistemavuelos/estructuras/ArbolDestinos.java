package sistemavuelos.estructuras;

import java.util.ArrayList;
import java.util.List;
import sistemavuelos.modelo.Vuelo;

/**
 * BST ordenado por destino (comparar con compareToIgnoreCase).
 *
 * PERSONA C: implementar los 4 metodos. Las firmas ya estan acordadas en la guia
 * (seccion 9): no cambiarlas sin avisar.
 *
 * Mientras esten vacios, el resto del sistema compila y corre sin romperse.
 */
public class ArbolDestinos {

    private NodoDestino raiz;

    /** Inserta el vuelo en el nodo de su destino. Si el nodo no existe, lo crea. */
    public void insertar(Vuelo vuelo) {
        // TODO (Persona C)
    }

    /**
     * Quita el vuelo del nodo de su destino.
     * Decision del equipo: si el nodo se queda sin vuelos NO se borra del arbol;
     * simplemente se omite al listar (nodo.estaVacio()).
     */
    public void eliminarVuelo(Vuelo vuelo) {
        // TODO (Persona C)
    }

    /** Vuelos hacia ese destino. Lista vacia si no hay ninguno. */
    public List<Vuelo> buscarPorDestino(String destino) {
        // TODO (Persona C)
        return new ArrayList<>();
    }

    /**
     * Imprime los destinos en orden alfabetico con sus vuelos.
     * Debe ser RECURSIVO (requisito del PDF): crear un metodo privado inorden(NodoDestino nodo).
     */
    public void listarDestinosInorden() {
        // TODO (Persona C)
    }
}
