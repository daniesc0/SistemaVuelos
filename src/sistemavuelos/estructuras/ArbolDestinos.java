package sistemavuelos.estructuras;

import java.util.ArrayList;
import java.util.List;
import sistemavuelos.modelo.Vuelo;

//no tocar
public class ArbolDestinos {

    private NodoDestino raiz;

    //Inserta el vuelo en nodo. Si el nodo no existe, lo crea.
    public void insertar(Vuelo vuelo) {
        // TODO 
    }

    //Quita el vuelo del nodo si el nodo se queda sin vuelos NO se borra del arbol; simplemente se omite al listar (nodo.estaVacio()).
    
    public void eliminarVuelo(Vuelo vuelo) {
        // TODO 
    }

    //Vuelos hacia ese destino. Lista vacia si no hay ninguno.
    public List<Vuelo> buscarPorDestino(String destino) {
        // TODO 
        return new ArrayList<>();
    }

    //Imprime los destinos en orden alfabetico con sus vuelos. Debe ser RECURSIVO (requisito del PDF): crear un metodo privado inorden(NodoDestino nodo).
    public void listarDestinosInorden() {
        
    }
}
