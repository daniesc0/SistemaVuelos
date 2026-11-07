package sistemavuelos.util;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.IntConsumer;
import sistemavuelos.modelo.Operacion;
import sistemavuelos.modelo.Reserva;
import sistemavuelos.modelo.Vuelo;

/**
 * Todo lo que tiene que ver con "como se ve" el programa en la consola:
 * cajas para los menus, mensajes y la forma de mostrar vuelos, reservas e historial.
 *
 * La hicimos para que los menus no se llenen de System.out.println repetidos
 * y para que si algun dia queremos cambiar el estilo, lo cambiemos en un solo lugar.
 * Usamos solo caracteres ASCII (+, -, |) porque algunas consolas, sobre todo en Windows,
 * muestran raro los caracteres especiales.
 */
public class Consola {

    // Ancho interior de las cajas de los menus.
    private static final int ANCHO = 52;

    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

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

    // Titulito para separar cada operacion (por ejemplo "NUEVO VUELO").
    public static void seccion(String titulo) {
        System.out.println();
        System.out.println("  " + titulo.toUpperCase());
        System.out.println("  " + repetir('-', titulo.length()));
    }

    // ---------------- Mensajes ----------------

    public static void exito(String mensaje) { System.out.println("  [OK] " + mensaje); }

    public static void error(String mensaje) { System.out.println("  [!] " + mensaje); }

    public static void aviso(String mensaje) { System.out.println("  [i] " + mensaje); }

    // Para que el usuario alcance a leer lo que salio antes de que reaparezca el menu.
    public static void pausar(Scanner sc) {
        System.out.print("\n  Presiona ENTER para continuar...");
        sc.nextLine();
    }

    // ---------------- Vuelos, reservas e historial ----------------
    // Antes lo mostrabamos en tablas, pero con tantas columnas costaba leerlo.
    // Ahora cada elemento sale como una "tarjeta" corta, con las cosas dichas con palabras.

    // Un vuelo en tres lineas: ruta, cuando sale y como van los asientos.
    public static void mostrarVuelo(Vuelo v) {
        System.out.println("  " + v.getCodigo() + "  -  " + v.getOrigen() + " a " + v.getDestino());
        System.out.println("     Sale el " + v.getFecha().format(FECHA) + " a las " + v.getHoraSalida()
                + "   |   Precio: " + String.format(Locale.US, "$%,.2f", v.getPrecio()));

        String asientos;
        if (v.estaLleno()) {
            asientos = "LLENO (los " + v.getCapacidad() + " asientos estan ocupados)";
        } else {
            asientos = v.cantidadLibres() + " asientos libres de " + v.getCapacidad();
        }
        // La lista de espera solo la mencionamos si de verdad hay gente esperando.
        String espera = v.cantidadEnEspera() > 0
                ? "   |   " + v.cantidadEnEspera() + " persona(s) en lista de espera"
                : "";
        System.out.println("     " + asientos + espera);
    }

    // Varios vuelos, de 5 en 5 para que no se vaya toda la lista de golpe.
    public static void listaVuelos(List<Vuelo> vuelos, Scanner sc) {
        paginar(sc, vuelos.size(), 5, "vuelo(s)", i -> {
            mostrarVuelo(vuelos.get(i));
            System.out.println();
        });
    }

    // Una reserva en dos lineas: quien es y que reservo.
    public static void mostrarReserva(Reserva r) {
        System.out.println("  Reserva #" + r.getId() + "  -  " + r.getPasajero().getNombre()
                + " (DUI " + r.getPasajero().getDui() + ")");
        System.out.println("     Vuelo " + r.getVuelo().getCodigo() + " a " + r.getVuelo().getDestino()
                + "   |   Asiento " + r.getAsiento());
    }

    // Varias reservas, de 8 en 8.
    public static void listaReservas(List<Reserva> reservas, Scanner sc) {
        paginar(sc, reservas.size(), 8, "reserva(s)", i -> {
            mostrarReserva(reservas.get(i));
            System.out.println();
        });
    }

    // El historial se lee como frases ("Reservo el asiento 3 del vuelo AV101"), una por linea.
    // Ya viene con lo mas reciente primero, asi que solo marcamos la primera.
    public static void historial(List<Operacion> historial) {
        for (int i = 0; i < historial.size(); i++) {
            Operacion op = historial.get(i);
            String accion;
            switch (op.getTipo()) {
                case RESERVA:
                    accion = "Reservo el asiento " + op.getAsiento() + " del vuelo " + op.getCodigoVuelo();
                    break;
                case CANCELACION:
                    accion = "Cancelo el asiento " + op.getAsiento() + " del vuelo " + op.getCodigoVuelo();
                    break;
                default:
                    accion = "Entro a la lista de espera del vuelo " + op.getCodigoVuelo();
                    break;
            }
            String marca = (i == 0) ? "   <- la mas reciente" : "";
            System.out.println("  " + op.getFechaHora().format(FECHA_HORA) + "   " + accion + marca);
        }
    }

    // Imprime una lista por pedacitos. Cada vez que termina un pedazo pregunta si seguimos.
    // El que llama solo dice cuantos elementos hay y como se imprime el elemento numero i.
    private static void paginar(Scanner sc, int total, int porPagina, String nombre, IntConsumer imprimir) {
        for (int i = 0; i < total; i++) {
            imprimir.accept(i);
            boolean finDePagina = (i + 1) % porPagina == 0;
            if (finDePagina && i + 1 < total) {
                System.out.print("  --- Mostrando " + (i + 1) + " de " + total + " " + nombre
                        + " ---  ENTER para ver mas, 0 para parar: ");
                if (sc.nextLine().trim().equals("0")) {
                    System.out.println();
                    return;
                }
                System.out.println();
            }
        }
        System.out.println("  --- Fin de la lista: " + total + " " + nombre + " en total ---");
    }

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
