package sistemavuelos.util;

import java.util.List;
import sistemavuelos.modelo.Vuelo;

/**
 * Isela Quintanilla: busqueda binaria por codigo de vuelo.
 *
 * Funcionamiento: en vez de revisar vuelo por vuelo, miramos el de en medio.
 * Si el codigo que buscamos es menor, descartamos la mitad de la derecha;
 * si es mayor, descartamos la mitad de la izquierda. Repetimos hasta encontrarlo
 * o quedarnos sin lista. Por eso la lista TIENE que estar ordenada por codigo.
 */
public class BusquedaBinaria {

    private BusquedaBinaria() { }

    /**
     * @param ordenadaPorCodigo lista de vuelos YA ordenada por codigo (GestorVuelos la mantiene asi)
     * @param codigo codigo a buscar, en mayusculas
     * @return la posicion del vuelo en la lista, o -1 si no existe
     */
    public static int buscarPorCodigo(List<Vuelo> ordenadaPorCodigo, String codigo) {
        if (codigo == null) {
            return -1;
        }

        int bajo = 0;                               // extremo izquierdo de la zona donde buscamos
        int alto = ordenadaPorCodigo.size() - 1;    // extremo derecho

        while (bajo <= alto) {
            int medio = (bajo + alto) / 2;
            int comparacion = ordenadaPorCodigo.get(medio).getCodigo().compareTo(codigo);

            if (comparacion == 0) {
                return medio;           // lo encontramos
            } else if (comparacion < 0) {
                bajo = medio + 1;       // el del medio es menor: buscamos a la derecha
            } else {
                alto = medio - 1;       // el del medio es mayor: buscamos a la izquierda
            }
        }
        return -1;  // se acabo la zona de busqueda: no esta
    }
}
