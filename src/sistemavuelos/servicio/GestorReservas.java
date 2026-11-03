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
import sistemavuelos.modelo.Vuelo;

/**
 *Glenda Beatriz Cruz Garcia
 *En la clase GestorReservas no se utilizara Scanner.
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
     */
    public Reserva reservar(String dui, String nombre, String telefono, String codigoVuelo, int asiento)
            throws VueloNoEncontradoException, AsientoOcupadoException, DatosInvalidosException {

        // 1. Primero lo primero: sin vuelo no hay nada que reservar.
        //    buscarPorCodigo ya lanza VueloNoEncontradoException si no existe.
        Vuelo vuelo = gestorVuelos.buscarPorCodigo(codigoVuelo);

        // 2. Busco al pasajero por DUI. Si ya existe uso ese mismo objeto (asi conserva su historial);
        //    si no existe lo creo, pero todavia NO lo guardo en la lista. Lo guardo hasta el final,
        //    cuando ya se que la operacion salio bien, para no dejar pasajeros "fantasma" si algo falla.
        Pasajero pasajero = buscarPasajero(dui);
        boolean esNuevo = false;
        if (pasajero == null) {
            pasajero = new Pasajero(dui, nombre, telefono); // aqui se validan dui, nombre y telefono vacios
            esNuevo = true;
        }

        // 3. Rechazo de duplicados: los que no tengan ya una reserva en este vuelo...
        for (Reserva r : reservas) {
            if (r.getVuelo() == vuelo && r.getPasajero().equals(pasajero)) {
                throw new DatosInvalidosException("El pasajero " + pasajero.getNombre()
                        + " ya tiene una reserva en el vuelo " + vuelo.getCodigo());
            }
        }
        // ...ni que ya esten esperando en la cola de ese mismo vuelo.
        if (vuelo.estaEnEspera(pasajero)) {
            throw new DatosInvalidosException("El pasajero " + pasajero.getNombre()
                    + " ya esta en la lista de espera del vuelo " + vuelo.getCodigo());
        }

        // 4. Si el vuelo esta lleno, el pasajero se va a la cola y no se le asigna asiento.
        if (vuelo.estaLleno()) {
            vuelo.agregarAEspera(pasajero);
            pasajero.registrarOperacion(new Operacion(Operacion.Tipo.LISTA_ESPERA, vuelo.getCodigo(), 0));
            guardarSiEsNuevo(pasajero, esNuevo);
            return null; // null significa "quedo en espera", el menu lo interpreta asi
        }

        // 5. Hay cupo: ocuparAsiento lanza AsientoOcupadoException si el numero no existe o ya esta tomado,
        //    y en ese caso no se llega a crear nada de lo que sigue.
        vuelo.ocuparAsiento(asiento);
        Reserva reserva = new Reserva(pasajero, vuelo, asiento);
        reservas.add(reserva);
        pasajero.registrarOperacion(new Operacion(Operacion.Tipo.RESERVA, vuelo.getCodigo(), asiento));
        guardarSiEsNuevo(pasajero, esNuevo);
        return reserva;
    }

    /**
     * Cancela una reserva y devuelve la reserva cancelada.
     * Si habia gente esperando en ese vuelo, el primero de la cola se queda con el asiento liberado.
     */
    public Reserva cancelar(int idReserva) throws ReservaNoEncontradaException {

        // 1. Busco la reserva por su id. Las reservas se guardan por orden de creacion,
        //    asi que con un recorrido simple basta.
        Reserva reserva = null;
        for (Reserva r : reservas) {
            if (r.getId() == idReserva) {
                reserva = r;
                break;
            }
        }
        if (reserva == null) {
            throw new ReservaNoEncontradaException("No existe una reserva con el id " + idReserva);
        }

        // 2. Libero el asiento en el vuelo y quito la reserva de mi lista.
        Vuelo vuelo = reserva.getVuelo();
        int asientoLiberado = reserva.getAsiento();
        vuelo.liberarAsiento(asientoLiberado);
        reservas.remove(reserva);

        // 3. Apilo la cancelacion en el historial del pasajero (queda como lo mas reciente).
        reserva.getPasajero().registrarOperacion(
                new Operacion(Operacion.Tipo.CANCELACION, vuelo.getCodigo(), asientoLiberado));

        // 4. Si habia alguien en espera, el primero de la cola toma justo ese asiento que se libero.
        if (vuelo.hayEnEspera()) {
            Pasajero siguiente = vuelo.siguienteEnEspera();
            try {
                vuelo.ocuparAsiento(asientoLiberado);
            } catch (AsientoOcupadoException e) {
                // Esto no deberia pasar nunca: acabo de liberar ese asiento hace dos lineas.
                // Si pasa es un error mio de programacion, por eso lo convierto en una excepcion no controlada.
                throw new IllegalStateException("El asiento recien liberado aparece ocupado", e);
            }
            Reserva nueva = new Reserva(siguiente, vuelo, asientoLiberado);
            reservas.add(nueva);
            siguiente.registrarOperacion(
                    new Operacion(Operacion.Tipo.RESERVA, vuelo.getCodigo(), asientoLiberado));
        }

        return reserva;
    }

    /** Busca un pasajero por DUI. Devuelve null si no existe. */
    public Pasajero buscarPasajero(String dui) {
        if (dui == null) {
            return null;
        }
        String duiLimpio = dui.trim();
        for (Pasajero p : pasajeros) {
            if (p.getDui().equals(duiLimpio)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Historial del pasajero, la operacion mas reciente primero.
     * Lanza DatosInvalidosException si no existe un pasajero con ese DUI.
     */
    public List<Operacion> historialDe(String dui) throws DatosInvalidosException {
        Pasajero pasajero = buscarPasajero(dui);
        if (pasajero == null) {
            throw new DatosInvalidosException("No existe un pasajero con el DUI " + (dui == null ? "" : dui.trim()));
        }
        // verHistorial() ya me devuelve una copia con lo mas reciente primero (sale de la pila).
        return pasajero.verHistorial();
    }

    /** Copia de la lista de reservas. */
    public List<Reserva> listarReservas() {
        return new ArrayList<>(reservas);
    }

    // ---------------- Ayuda interna ----------------

    // Guarda al pasajero en la lista solo si es la primera vez que lo vemos.
    private void guardarSiEsNuevo(Pasajero pasajero, boolean esNuevo) {
        if (esNuevo) {
            pasajeros.add(pasajero);
        }
    }
}