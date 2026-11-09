package sistemavuelos.estructuras;

import java.util.ArrayList;
import java.util.List;
import sistemavuelos.modelo.Vuelo;

public class ArbolDestinos {

    private NodoDestino raiz;

    /** Inserta el vuelo en el nodo de su destino. Si el nodo no existe, lo crea. */
    public void insertar(Vuelo vuelo) {
        raiz = insertarRec(raiz, vuelo);
    }

    private NodoDestino insertarRec(NodoDestino nodo, Vuelo vuelo) {
        if (nodo == null) {                                   // lugar vacio: aqui va el nodo nuevo
            NodoDestino nuevo = new NodoDestino(vuelo.getDestino());
            nuevo.agregarVuelo(vuelo);
            return nuevo;
        }
        int cmp = vuelo.getDestino().compareToIgnoreCase(nodo.getDestino());
        if (cmp < 0) {                                        // destino menor: va a la izquierda
            nodo.setIzquierdo(insertarRec(nodo.getIzquierdo(), vuelo));
        } else if (cmp > 0) {                                 // destino mayor: va a la derecha
            nodo.setDerecho(insertarRec(nodo.getDerecho(), vuelo));
        } else {                                              // mismo destino: se suma a su lista
            nodo.agregarVuelo(vuelo);
        }
        return nodo;
    }

    /** Vuelos hacia ese destino. Lista vacia si no hay ninguno. */
    public List<Vuelo> buscarPorDestino(String destino) {
        NodoDestino nodo = buscarNodo(destino);
        if (nodo == null) {
            return new ArrayList<>();                         // llegamos a un hueco: no existe
        }
        return new ArrayList<>(nodo.getVuelos());             // copia de sus vuelos
    }

    /**
     * Quita el vuelo del nodo de su destino. Si el nodo se queda sin vuelos
     * no se borra del arbol: simplemente se omite al listar.
     */
    public void eliminarVuelo(Vuelo vuelo) {
        NodoDestino nodo = buscarNodo(vuelo.getDestino());
        if (nodo != null) {
            nodo.quitarVuelo(vuelo);
        }
    }

    // Baja por el arbol comparando destinos. Devuelve el nodo o null si no existe.
    private NodoDestino buscarNodo(String destino) {
        NodoDestino actual = raiz;
        while (actual != null) {
            int cmp = destino.trim().compareToIgnoreCase(actual.getDestino());
            if (cmp == 0) {
                return actual;
            } else if (cmp < 0) {
                actual = actual.getIzquierdo();               // es menor: seguir por la izquierda
            } else {
                actual = actual.getDerecho();                 // es mayor: seguir por la derecha
            }
        }
        return null;
    }

    /** Imprime los destinos en orden alfabetico (recorrido inorden recursivo). */
    public void listarDestinosInorden() {
        int mostrados = inorden(raiz);
        if (mostrados == 0) {
            System.out.println("  No hay destinos con vuelos registrados.");
        }
    }

    // izquierda -> nodo -> derecha. Devuelve cuantos destinos imprimio.
    private int inorden(NodoDestino nodo) {
        if (nodo == null) {
            return 0;                                         // caso base
        }
        int total = inorden(nodo.getIzquierdo());             // primero todo lo menor
        if (!nodo.estaVacio()) {                              // luego este nodo (si tiene vuelos)
            System.out.println("  " + nodo.getDestino() + " (" + nodo.getVuelos().size() + " vuelo(s))");
            for (Vuelo v : nodo.getVuelos()) {
                System.out.println("     " + v);
            }
            total++;
        }
        total += inorden(nodo.getDerecho());                  // al final todo lo mayor
        return total;
    }
}