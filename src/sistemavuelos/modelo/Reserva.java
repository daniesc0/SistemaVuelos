package sistemavuelos.modelo;

/** Une un pasajero, un vuelo y un numero de asiento. El ID se genera solo. */
public class Reserva {

    private static int contador = 1;

    private final int id;
    private final Pasajero pasajero;
    private final Vuelo vuelo;
    private final int asiento;

    public Reserva(Pasajero pasajero, Vuelo vuelo, int asiento) {
        this.id = contador++;
        this.pasajero = pasajero;
        this.vuelo = vuelo;
        this.asiento = asiento;
    }

    public int getId() { return id; }
    public Pasajero getPasajero() { return pasajero; }
    public Vuelo getVuelo() { return vuelo; }
    public int getAsiento() { return asiento; }

    @Override
    public String toString() {
        return "Reserva #" + id + " | " + pasajero.getNombre() + " | vuelo "
                + vuelo.getCodigo() + " | asiento " + asiento;
    }
}
