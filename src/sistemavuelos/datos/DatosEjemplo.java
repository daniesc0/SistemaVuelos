package sistemavuelos.datos;

import java.time.LocalDate;
import java.time.LocalTime;
import sistemavuelos.excepciones.AsientoOcupadoException;
import sistemavuelos.excepciones.DatosInvalidosException;
import sistemavuelos.excepciones.ReservaNoEncontradaException;
import sistemavuelos.excepciones.VueloNoEncontradoException;
import sistemavuelos.modelo.Reserva;
import sistemavuelos.modelo.Vuelo;
import sistemavuelos.servicio.GestorReservas;
import sistemavuelos.servicio.GestorVuelos;
import sistemavuelos.util.Consola;

/**
 * Datos de ejemplo para no tener que teclear todo cada vez que probamos o presentamos el programa
 * (como el sistema no guarda nada en archivos, siempre arranca vacio).
 *
 * Carga: 20 vuelos hacia 15 destinos (algunos destinos se repiten a proposito para que el arbol
 * tenga varios vuelos por nodo), 20 pasajeros, unas 30 reservas, 3 vuelos llenos con gente en
 * lista de espera y 1 cancelacion para que el historial tenga de todo.
 *
 * IMPORTANTE: no se salta ninguna regla. Todo pasa por agregarVuelo y reservar, o sea, por los mismos
 * metodos que usa el menu. Asi las listas quedan ordenadas, el arbol se llena solo y los historiales
 * se arman como si alguien hubiera hecho las operaciones a mano.
 *
 * Para apagarla basta con poner CARGAR_EJEMPLOS = false en Main.
 */
public class DatosEjemplo {

    // Cada fila es un pasajero: {DUI, nombre, telefono}. Despues los llamamos por su posicion (0 a 19).
    private static final String[][] PASAJEROS = {
        {"01234567-8", "Ana Lopez",          "7123-4567"},   // 0
        {"02345678-9", "Luis Perez",         "7234-5678"},   // 1
        {"03456789-0", "Marta Diaz",         "7345-6789"},   // 2
        {"04567890-1", "Carlos Mejia",       "7456-7890"},   // 3
        {"05678901-2", "Sofia Ramirez",      "7567-8901"},   // 4
        {"06789012-3", "Jose Hernandez",     "7678-9012"},   // 5
        {"07890123-4", "Lucia Martinez",     "7789-0123"},   // 6
        {"08901234-5", "Diego Flores",       "7890-1234"},   // 7
        {"09012345-6", "Valeria Castillo",   "6123-4567"},   // 8
        {"10123456-7", "Mateo Guzman",       "6234-5678"},   // 9
        {"11234567-8", "Camila Rivas",       "6345-6789"},   // 10
        {"12345678-9", "Andres Molina",      "6456-7890"},   // 11
        {"13456789-0", "Isabella Ortiz",     "6567-8901"},   // 12
        {"14567890-1", "Daniel Alvarado",    "6678-9012"},   // 13
        {"15678901-2", "Gabriela Campos",    "6789-0123"},   // 14
        {"16789012-3", "Fernando Cruz",      "7012-3456"},   // 15
        {"17890123-4", "Paola Menjivar",     "7101-2345"},   // 16
        {"18901234-5", "Ricardo Salazar",    "7210-3456"},   // 17
        {"19012345-6", "Daniela Quintanilla", "7321-4567"},  // 18
        {"20123456-7", "Oscar Henriquez",    "7432-5678"}    // 19
    };

    // Clase de utilidades: no se crean objetos de ella.
    private DatosEjemplo() { }

    /**
     * Mete todos los datos de ejemplo. Si algo falla (por ejemplo si ya estaban cargados y los codigos
     * se repiten) avisa y el programa sigue funcionando con lo que haya alcanzado a cargar.
     */
    public static void cargar(GestorVuelos gestorVuelos, GestorReservas gestorReservas) {
        try {
            cargarVuelos(gestorVuelos);
            cargarReservas(gestorReservas);
        } catch (DatosInvalidosException | VueloNoEncontradoException
                | AsientoOcupadoException | ReservaNoEncontradaException e) {
            Consola.error("No se pudieron cargar los datos de ejemplo: " + e.getMessage());
        }
    }

    // ---------------- Vuelos ----------------

    private static void cargarVuelos(GestorVuelos gv) throws DatosInvalidosException {
        //                codigo   origen          destino             fecha         hora     precio   asientos
        agregar(gv, "AV101", "San Salvador", "Miami",             "2026-12-15", "06:30", 289.90,  4);   // se llena
        agregar(gv, "TA202", "San Salvador", "Miami",             "2026-12-18", "14:45", 312.50, 20);
        agregar(gv, "CM410", "San Salvador", "Panama",            "2026-12-16", "08:15", 198.00,  3);   // se llena
        agregar(gv, "CM412", "San Salvador", "Panama",            "2026-12-20", "17:40", 214.75, 25);
        agregar(gv, "IB301", "San Salvador", "Madrid",            "2026-12-22", "21:10", 845.00,  2);   // se llena
        agregar(gv, "IB305", "San Salvador", "Madrid",            "2027-01-05", "20:30", 799.99, 30);
        agregar(gv, "AM520", "San Salvador", "Ciudad de Mexico",  "2026-12-17", "09:00", 265.40, 24);
        agregar(gv, "AM522", "San Salvador", "Ciudad de Mexico",  "2026-12-24", "12:20", 281.00, 24);
        agregar(gv, "AV620", "San Salvador", "Guatemala",         "2026-12-12", "07:45",  89.50, 18);
        agregar(gv, "AV622", "San Salvador", "Guatemala",         "2026-12-19", "16:00",  95.00, 18);
        agregar(gv, "UA777", "San Salvador", "Los Angeles",       "2026-12-28", "05:55", 540.00, 16);
        agregar(gv, "AA880", "San Salvador", "Nueva York",        "2026-12-30", "11:35", 468.30, 22);
        agregar(gv, "AC940", "San Salvador", "Toronto",           "2027-01-08", "10:05", 612.00, 15);
        agregar(gv, "LA150", "San Salvador", "Lima",              "2026-12-21", "13:25", 385.00, 20);
        agregar(gv, "AV700", "San Salvador", "Bogota",            "2026-12-14", "15:50", 234.60, 28);
        agregar(gv, "CM601", "San Salvador", "San Jose",          "2026-12-13", "06:10", 112.00, 12);
        agregar(gv, "DL410", "San Salvador", "Houston",           "2027-01-10", "18:15", 425.00, 18);
        agregar(gv, "AM990", "San Salvador", "Cancun",            "2026-12-26", "08:40", 330.25, 20);
        agregar(gv, "IB410", "San Salvador", "Barcelona",         "2027-01-12", "19:50", 872.50, 20);
        agregar(gv, "AV800", "San Salvador", "Santo Domingo",     "2027-01-15", "09:35", 298.00, 14);
    }

    // Arma el Vuelo desde textos para que cada linea de arriba quede cortita y facil de leer.
    private static void agregar(GestorVuelos gv, String codigo, String origen, String destino,
                                String fecha, String hora, double precio, int capacidad)
            throws DatosInvalidosException {
        Vuelo vuelo = new Vuelo(codigo, origen, destino, LocalDate.parse(fecha), LocalTime.parse(hora),
                precio, capacidad);
        gv.agregarVuelo(vuelo);
    }

    // ---------------- Reservas ----------------

    private static void cargarReservas(GestorReservas gr)
            throws VueloNoEncontradoException, AsientoOcupadoException,
                   DatosInvalidosException, ReservaNoEncontradaException {

        // AV101 (4 asientos): lo llenamos y dejamos a dos personas esperando. El 4 y el 5 llegan en ese orden,
        // asi que si alguien cancela, el primero en recibir el asiento es el 4 (Sofia).
        reservar(gr, 0, "AV101", 1);
        reservar(gr, 1, "AV101", 2);
        reservar(gr, 2, "AV101", 3);
        reservar(gr, 3, "AV101", 4);
        enEspera(gr, 4, "AV101");
        enEspera(gr, 5, "AV101");

        // CM410 (3 asientos): lleno, con una persona en espera.
        reservar(gr, 6, "CM410", 1);
        reservar(gr, 7, "CM410", 2);
        reservar(gr, 8, "CM410", 3);
        enEspera(gr, 9, "CM410");

        // IB301 (2 asientos): lleno, con dos personas en espera.
        reservar(gr, 10, "IB301", 1);
        reservar(gr, 11, "IB301", 2);
        enEspera(gr, 12, "IB301");
        enEspera(gr, 13, "IB301");

        // El resto de vuelos con algunas reservas (hay pasajeros que viajan mas de una vez).
        reservar(gr, 0,  "TA202", 5);
        reservar(gr, 1,  "TA202", 6);
        reservar(gr, 14, "CM412", 12);
        reservar(gr, 15, "CM412", 13);
        reservar(gr, 16, "AM520", 7);
        reservar(gr, 17, "AM520", 8);
        reservar(gr, 18, "AM520", 9);
        reservar(gr, 19, "AV620", 1);
        reservar(gr, 0,  "AV620", 2);
        reservar(gr, 2,  "UA777", 10);
        reservar(gr, 3,  "AA880", 4);
        reservar(gr, 5,  "AA880", 11);
        reservar(gr, 6,  "LA150", 15);
        reservar(gr, 7,  "AV700", 3);
        reservar(gr, 8,  "AV700", 4);
        reservar(gr, 9,  "AV700", 5);
        reservar(gr, 11, "AM990", 7);
        reservar(gr, 12, "AM990", 8);
        reservar(gr, 13, "IB305", 20);
        reservar(gr, 14, "DL410", 1);
        reservar(gr, 15, "AV622", 3);
        reservar(gr, 16, "AC940", 2);

        // Una cancelacion de verdad: Camila reserva el asiento 6 de CM601 y luego se arrepiente.
        // Asi su historial muestra RESERVA, RESERVA y CANCELACION. Como CM601 no tiene lista de espera,
        // el asiento queda libre y se lo damos a Andres.
        Reserva deCamila = reservar(gr, 10, "CM601", 6);
        gr.cancelar(deCamila.getId());
        reservar(gr, 11, "CM601", 6);
    }

    // Reserva usando al pasajero numero "quien" de la tabla de arriba.
    private static Reserva reservar(GestorReservas gr, int quien, String vuelo, int asiento)
            throws VueloNoEncontradoException, AsientoOcupadoException, DatosInvalidosException {
        String[] p = PASAJEROS[quien];
        return gr.reservar(p[0], p[1], p[2], vuelo, asiento);
    }

    // Como el vuelo ya esta lleno, reservar() lo manda solo a la lista de espera (el asiento 0 no se usa).
    private static void enEspera(GestorReservas gr, int quien, String vuelo)
            throws VueloNoEncontradoException, AsientoOcupadoException, DatosInvalidosException {
        reservar(gr, quien, vuelo, 0);
    }
}
