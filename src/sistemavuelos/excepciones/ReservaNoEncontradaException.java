package sistemavuelos.excepciones;

public class ReservaNoEncontradaException extends Exception {

    private static final long serialVersionUID = 1L;

    public ReservaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}
