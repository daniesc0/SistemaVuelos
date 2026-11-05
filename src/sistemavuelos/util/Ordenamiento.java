package sistemavuelos.util;

import java.util.List;
import sistemavuelos.modelo.Vuelo;

/**
 * Ordenamientos 
 *
 * Usaremos ORDENAMIENTO POR INSERCION. La idea es la misma que cuando uno ordena
 * cartas en la mano: se toma una carta, la comparamos con las que ya tenemos ordenadas
 * y la metemos en el lugar que le toca, corriendo las demas un puesto.
 *
 * Ordenar IN PLACE la lista que reciben. GestorVuelos siempre les manda una COPIA,
 * asi la lista original (ordenada por codigo, necesaria para la busqueda binaria)
 * nunca se desordena.
 */
public class Ordenamiento {

    // Clase de utilidades: no se crean objetos de ella.
    private Ordenamiento() { }

    /** De menor a mayor precio. */
    public static void ordenarPorPrecio(List<Vuelo> vuelos) {
        // Empezamos en 1 porque el elemento 0, solo, ya se considera "ordenado".
        for (int i = 1; i < vuelos.size(); i++) {
            Vuelo actual = vuelos.get(i);   // la "carta" que vamos a acomodar
            int j = i - 1;

            // Mientras el de la izquierda sea MAS caro, lo corremos un puesto a la derecha.
            while (j >= 0 && vuelos.get(j).getPrecio() > actual.getPrecio()) {
                vuelos.set(j + 1, vuelos.get(j));
                j--;
            }
            // Cuando ya no hay nadie mas caro a la izquierda, ahi cae el vuelo actual.
            vuelos.set(j + 1, actual);
        }
    }

    /** Por fecha y, a igual fecha, por hora de salida (de la mas temprana a la mas tarde). */
    public static void ordenarPorHora(List<Vuelo> vuelos) {
        for (int i = 1; i < vuelos.size(); i++) {
            Vuelo actual = vuelos.get(i);
            int j = i - 1;

            // "viene despues" es true si el vuelo de la izquierda sale DESPUES del actual.
            while (j >= 0 && saleDespues(vuelos.get(j), actual)) {
                vuelos.set(j + 1, vuelos.get(j));
                j--;
            }
            vuelos.set(j + 1, actual);
        }
    }

    /**
     * true si el vuelo "a" sale despues que el vuelo "b".
     * Primero se compara la fecha; solo si es el mismo dia se desempata con la hora.
     */
    private static boolean saleDespues(Vuelo a, Vuelo b) {
        int porFecha = a.getFecha().compareTo(b.getFecha());
        if (porFecha != 0) {
            return porFecha > 0;
        }
        return a.getHoraSalida().compareTo(b.getHoraSalida()) > 0;
    }
}
