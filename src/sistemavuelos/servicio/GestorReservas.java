package sistemavuelos.servicio;

import java.util.ArrayList;
import java.util.List;
import sistemavuelos.excepciones.AsientoOcupadoException;
import sistemavuelos.excepciones.DatosInvalidosException;
import sistemavuelos.excepciones.ReservaNoEncontradaException;
import sistemavuelos.excepciones.VueloNoEncontradoException;
import sistemavuelos.modelo.Operacion;
import sistemavuelos.modelo.Pasajero;
import sistemavuelos.modelo.Reserva;

/**
 * Logica de las reservas. PERSONA B es la duena de esta clase.
 * Aqui NO se usa Scanner ni System.in.
 *
 * Esta clase guarda las reservas Y los pasajeros (se buscan por DUI, asi un mismo
 * DUI siempre usa el mismo objeto Pasajero y por tanto la misma pila de historial).
 */
public class GestorReservas {

    private final GestorVuelos gestorVuelos;
    private final ArrayList<Reserva> reservas = new ArrayList<>();
    private final ArrayList<Pasajero> pasajeros = new ArrayList<>();

    public GestorReservas(GestorVuelos gestorVuelos) {
        this.gestorVuelos = gestorVuelos;
    }

    /**
     * Reserva un asiento. Si el vuelo esta lleno, el pasajero entra a la cola de espera.
     *
     * @return la Reserva creada, o null si el vuelo estaba lleno y el pasajero quedo en espera
     *
     * TODO (Persona B), en este orden:
     *  1. Buscar el vuelo (gestorVuelos.buscarPorCodigo).
     *  2. Buscar el pasajero por DUI; si no existe, crearlo y guardarlo en "pasajeros".
     *  3. Rechazar duplicados: mismo pasajero con reserva en ese vuelo, o ya en su espera (DatosInvalidosException).
     *  4. Si vuelo.estaLleno(): vuelo.agregarAEspera, registrar Operacion LISTA_ESPERA, devolver null.
     *  5. Si no: vuelo.ocuparAsiento(asiento), crear Reserva, guardarla, registrar Operacion RESERVA.
     */
    public Reserva reservar(String dui, String nombre, String telefono, String codigoVuelo, int asiento)
            throws VueloNoEncontradoException, AsientoOcupadoException, DatosInvalidosException {
        throw new UnsupportedOperationException("Persona B: falta implementar reservar");
    }

    /**
     * Cancela una reserva y devuelve la reserva cancelada.
     *
     * TODO (Persona B), en este orden:
     *  1. Buscar la reserva por id; si no existe, ReservaNoEncontradaException.
     *  2. vuelo.liberarAsiento(asiento) y quitarla de "reservas".
     *  3. Registrar Operacion CANCELACION en el historial del pasajero.
     *  4. Si vuelo.hayEnEspera(): sacar al primero (siguienteEnEspera), ocuparle ESE asiento,
     *     crear su Reserva, guardarla y registrar Operacion RESERVA en su historial.
     */
    public Reserva cancelar(int idReserva) throws ReservaNoEncontradaException {
        throw new UnsupportedOperationException("Persona B: falta implementar cancelar");
    }

    /** Busca un pasajero por DUI. Devuelve null si no existe. */
    public Pasajero buscarPasajero(String dui) {
        throw new UnsupportedOperationException("Persona B: falta implementar buscarPasajero");
    }

    /**
     * Historial del pasajero, la operacion mas reciente primero.
     * Lanza DatosInvalidosException si no existe un pasajero con ese DUI.
     */
    public List<Operacion> historialDe(String dui) throws DatosInvalidosException {
        throw new UnsupportedOperationException("Persona B: falta implementar historialDe");
    }

    /** Copia de la lista de reservas. */
    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }
}
