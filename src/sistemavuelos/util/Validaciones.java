package sistemavuelos.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * PERSONA C: entregar primero, porque todos los menus dependen de esta clase.
 * Toda lectura del usuario pasa por aqui. Cada leerX pide el dato y repite hasta que sea valido
 * (internamente puede usar DatosInvalidosException).
 */
public class Validaciones {

    private Validaciones() { }

    public static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        throw new UnsupportedOperationException("Persona C: falta leerEntero");
    }

    public static double leerPrecio(Scanner sc, String mensaje) {
        throw new UnsupportedOperationException("Persona C: falta leerPrecio");
    }

    public static String leerTexto(Scanner sc, String mensaje) {
        throw new UnsupportedOperationException("Persona C: falta leerTexto");
    }

    public static String leerCodigoVuelo(Scanner sc, String mensaje) {
        throw new UnsupportedOperationException("Persona C: falta leerCodigoVuelo");
    }

    public static String leerDui(Scanner sc, String mensaje) {
        throw new UnsupportedOperationException("Persona C: falta leerDui");
    }

    public static String leerTelefono(Scanner sc, String mensaje) {
        throw new UnsupportedOperationException("Persona C: falta leerTelefono");
    }

    public static LocalDate leerFecha(Scanner sc, String mensaje) {
        throw new UnsupportedOperationException("Persona C: falta leerFecha");
    }

    public static LocalTime leerHora(Scanner sc, String mensaje) {
        throw new UnsupportedOperationException("Persona C: falta leerHora");
    }
}
