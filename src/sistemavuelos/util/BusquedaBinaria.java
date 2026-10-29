package sistemavuelos.util;

import java.util.List;
import sistemavuelos.modelo.Vuelo;

/** PERSONA A: busqueda binaria (iterativa o recursiva) por codigo de vuelo. */
public class BusquedaBinaria {

    private BusquedaBinaria() { }

    /**
     * @param ordenadaPorCodigo lista de vuelos YA ordenada por codigo (GestorVuelos la mantiene asi)
     * @param codigo codigo a buscar, en mayusculas
     * @return la posicion del vuelo en la lista, o -1 si no existe
     */
    public static int buscarPorCodigo(List<Vuelo> ordenadaPorCodigo, String codigo) {
        throw new UnsupportedOperationException("Persona A: falta implementar la busqueda binaria");
    }
}
