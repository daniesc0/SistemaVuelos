package sistemavuelos.modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Registro del historial de un pasajero. Es lo que se apila en su Stack.
 * Es inmutable: una vez creada no se puede modificar.
 */
public class Operacion {

    public enum Tipo { RESERVA, CANCELACION, LISTA_ESPERA }

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final Tipo tipo;
    private final String codigoVuelo;
    private final int asiento;          // 0 cuando no aplica (LISTA_ESPERA)
    private final LocalDateTime fechaHora;

    public Operacion(Tipo tipo, String codigoVuelo, int asiento) {
        this.tipo = tipo;
        this.codigoVuelo = codigoVuelo;
        this.asiento = asiento;
        this.fechaHora = LocalDateTime.now();
    }

    public Tipo getTipo() { return tipo; }
    public String getCodigoVuelo() { return codigoVuelo; }
    public int getAsiento() { return asiento; }
    public LocalDateTime getFechaHora() { return fechaHora; }

    @Override
    public String toString() {
        String detalleAsiento = (asiento > 0) ? " | asiento " + asiento : "";
        return fechaHora.format(FORMATO) + " | " + tipo + " | vuelo " + codigoVuelo + detalleAsiento;
    }
}
