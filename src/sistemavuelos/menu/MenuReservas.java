package sistemavuelos.menu;

import java.util.Scanner;
import sistemavuelos.servicio.GestorReservas;
import sistemavuelos.servicio.GestorVuelos;

/**
 * Menu de reservas. PERSONA B.
 * Solo lee y muestra; la logica esta en GestorReservas.
 */
public class MenuReservas {

    private final GestorReservas gestorReservas;
    private final GestorVuelos gestorVuelos;

    public MenuReservas(GestorReservas gestorReservas, GestorVuelos gestorVuelos) {
        this.gestorReservas = gestorReservas;
        this.gestorVuelos = gestorVuelos;
    }

    public void mostrar(Scanner sc) {
        // TODO (Persona B)
    }
}
