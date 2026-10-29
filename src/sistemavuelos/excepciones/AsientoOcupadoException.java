package sistemavuelos.excepciones;

public class AsientoOcupadoException extends Exception {

    private static final long serialVersionUID = 1L;

    public AsientoOcupadoException(String mensaje) {
        super(mensaje);
    }
}
