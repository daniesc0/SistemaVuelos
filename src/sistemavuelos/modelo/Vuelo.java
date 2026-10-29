package sistemavuelos.modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.Queue;
import sistemavuelos.excepciones.AsientoOcupadoException;
import sistemavuelos.excepciones.DatosInvalidosException;

/**
 * Un vuelo con sus asientos y su propia lista de espera (COLA).
 * Los asientos se numeran de 1 a capacidad. Nada interno sale por getters:
 * el arreglo de asientos y la cola solo se tocan con los metodos de esta clase.
 */
public class Vuelo {

    private final String codigo;
    private final String origen;
    private final String destino;
    private final LocalDate fecha;
    private final LocalTime horaSalida;
    private final double precio;
    private final int capacidad;
    private final boolean[] asientos;            // true = ocupado
    private final Queue<Pasajero> listaEspera = new LinkedList<>();

    public Vuelo(String codigo, String origen, String destino, LocalDate fecha,
                 LocalTime horaSalida, double precio, int capacidad) throws DatosInvalidosException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new DatosInvalidosException("El codigo del vuelo no puede estar vacio");
        }
        if (origen == null || origen.trim().isEmpty()) {
            throw new DatosInvalidosException("El origen no puede estar vacio");
        }
        if (destino == null || destino.trim().isEmpty()) {
            throw new DatosInvalidosException("El destino no puede estar vacio");
        }
        if (fecha == null || horaSalida == null) {
            throw new DatosInvalidosException("La fecha y la hora de salida son obligatorias");
        }
        if (precio <= 0) {
            throw new DatosInvalidosException("El precio debe ser mayor que 0");
        }
        if (capacidad <= 0) {
            throw new DatosInvalidosException("La capacidad debe ser mayor que 0");
        }
        this.codigo = codigo.trim().toUpperCase();
        this.origen = origen.trim();
        this.destino = destino.trim();
        this.fecha = fecha;
        this.horaSalida = horaSalida;
        this.precio = precio;
        this.capacidad = capacidad;
        this.asientos = new boolean[capacidad];
    }

    // ---------------- Asientos ----------------

    /** true si el numero esta entre 1 y capacidad. */
    public boolean asientoValido(int numero) {
        return numero >= 1 && numero <= capacidad;
    }

    /** true si el asiento existe y esta libre. */
    public boolean asientoLibre(int numero) {
        return asientoValido(numero) && !asientos[numero - 1];
    }

    /** Marca el asiento como ocupado. Lanza excepcion si no existe o ya esta ocupado. */
    public void ocuparAsiento(int numero) throws AsientoOcupadoException {
        if (!asientoValido(numero)) {
            throw new AsientoOcupadoException("El asiento " + numero + " no existe (valido: 1 a " + capacidad + ")");
        }
        if (asientos[numero - 1]) {
            throw new AsientoOcupadoException("El asiento " + numero + " del vuelo " + codigo + " ya esta ocupado");
        }
        asientos[numero - 1] = true;
    }

    /**
     * Libera un asiento. Solo se llama con numeros que ya fueron reservados,
     * por eso un numero invalido es un error de programacion (IllegalArgumentException).
     */
    public void liberarAsiento(int numero) {
        if (!asientoValido(numero)) {
            throw new IllegalArgumentException("Asiento invalido: " + numero);
        }
        asientos[numero - 1] = false;
    }

    public int cantidadOcupados() {
        int ocupados = 0;
        for (boolean ocupado : asientos) {
            if (ocupado) ocupados++;
        }
        return ocupados;
    }

    public int cantidadLibres() {
        return capacidad - cantidadOcupados();
    }

    public boolean estaLleno() {
        return cantidadLibres() == 0;
    }

    /** Numero (1..capacidad) del primer asiento libre, o -1 si el vuelo esta lleno. */
    public int primerAsientoLibre() {
        for (int i = 0; i < asientos.length; i++) {
            if (!asientos[i]) return i + 1;
        }
        return -1;
    }

    // ---------------- Lista de espera (cola) ----------------

    public void agregarAEspera(Pasajero pasajero) {
        listaEspera.add(pasajero);
    }

    /** Saca y devuelve al primero de la cola, o null si no hay nadie esperando. */
    public Pasajero siguienteEnEspera() {
        return listaEspera.poll();
    }

    public boolean hayEnEspera() {
        return !listaEspera.isEmpty();
    }

    public int cantidadEnEspera() {
        return listaEspera.size();
    }

    /** true si ese pasajero (por DUI) ya esta esperando en este vuelo. */
    public boolean estaEnEspera(Pasajero pasajero) {
        return listaEspera.contains(pasajero);
    }

    // ---------------- Getters ----------------

    public String getCodigo() { return codigo; }
    public String getOrigen() { return origen; }
    public String getDestino() { return destino; }
    public LocalDate getFecha() { return fecha; }
    public LocalTime getHoraSalida() { return horaSalida; }
    public double getPrecio() { return precio; }
    public int getCapacidad() { return capacidad; }

    @Override
    public String toString() {
        return String.format("%s | %s -> %s | %s %s | $%.2f | libres %d/%d | en espera %d",
                codigo, origen, destino, fecha, horaSalida, precio,
                cantidadLibres(), capacidad, cantidadEnEspera());
    }
}
