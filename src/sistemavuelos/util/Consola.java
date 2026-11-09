package sistemavuelos.util;

/**
 * Lo que tiene que ver con "como se ve" el menu principal y los mensajes sueltos del programa:
 * el banner del inicio, la caja del menu principal y los avisos/errores.
 *
 * Usamos solo caracteres ASCII (+, -, |) porque algunas consolas, sobre todo en Windows,
 * muestran raro los caracteres especiales.
 *
 * Los otros menus (vuelos, reservas y destinos) imprimen con System.out directamente,
 * asi que aqui dejamos solo lo que si se usa.
 */
public class Consola {

    // Ancho interior de las cajas de los menus.
    private static final int ANCHO = 52;

    // Clase de utilidades: no se crean objetos de ella.
    private Consola() { }

    // ---------------- Cajas ----------------

    // Cuadro grande con el nombre del sistema. Solo se usa al arrancar.
    public static void banner(String titulo, String subtitulo) {
        System.out.println();
        System.out.println("  +" + repetir('=', ANCHO) + "+");
        System.out.println("  " + centrar(titulo));
        System.out.println("  " + centrar(subtitulo));
        System.out.println("  +" + repetir('=', ANCHO) + "+");
    }

    // Caja de menu: titulo centrado y las opciones debajo, una por linea.
    public static void caja(String titulo, String... opciones) {
        System.out.println();
        System.out.println("  +" + repetir('-', ANCHO) + "+");
        System.out.println("  " + centrar(titulo));
        System.out.println("  +" + repetir('-', ANCHO) + "+");
        for (String opcion : opciones) {
            System.out.println("  |  " + rellenar(recortar(opcion, ANCHO - 2), ANCHO - 2) + "|");
        }
        System.out.println("  +" + repetir('-', ANCHO) + "+");
    }

    // ---------------- Mensajes ----------------

    public static void error(String mensaje) { System.out.println("  [!] " + mensaje); }

    public static void aviso(String mensaje) { System.out.println("  [i] " + mensaje); }

    // ---------------- Ayudas ----------------

    // Si el texto no cabe en la columna lo cortamos y le ponemos ".." al final.
    private static String recortar(String texto, int max) {
        if (texto.length() <= max) {
            return texto;
        }
        return texto.substring(0, max - 2) + "..";
    }

    private static String rellenar(String texto, int ancho) {
        return String.format("%-" + ancho + "s", texto);
    }

    private static String centrar(String texto) {
        String t = recortar(texto, ANCHO);
        int izquierda = (ANCHO - t.length()) / 2;
        int derecha = ANCHO - t.length() - izquierda;
        return "|" + repetir(' ', izquierda) + t + repetir(' ', derecha) + "|";
    }

    private static String repetir(char c, int veces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < veces; i++) {
            sb.append(c);
        }
        return sb.toString();
    }
}
