package sistemavuelos.util;

import java.util.List;
import sistemavuelos.modelo.Vuelo;

/**
 * PERSONA A: implementar los dos ordenamientos (burbuja, insercion, seleccion... el que prefieran,
 * pero escrito a mano, sin Collections.sort).
 *
 * Ordenan IN PLACE la lista que reciben. GestorVuelos les pasa una COPIA, asi la lista
 * original (ordenada por codigo, necesaria para la busqueda binaria) nunca se desordena.
 */
public class Ordenamiento {

    private Ordenamiento() { }

    /** De menor a mayor precio. */
    public static void ordenarPorPrecio(List<Vuelo> vuelos) {
        throw new UnsupportedOperationException("Persona A: falta implementar ordenarPorPrecio");
    }

    /** Por fecha y, a igual fecha, por hora de salida (de la mas temprana a la mas tarde). */
    public static void ordenarPorHora(List<Vuelo> vuelos) {
        throw new UnsupportedOperationException("Persona A: falta implementar ordenarPorHora");
    }
}
