package sistemavuelos.menu;

import java.util.List;
import java.util.Scanner;
import sistemavuelos.excepciones.AsientoOcupadoException;
import sistemavuelos.excepciones.DatosInvalidosException;
import sistemavuelos.excepciones.ReservaNoEncontradaException;
import sistemavuelos.excepciones.VueloNoEncontradoException;
import sistemavuelos.modelo.Operacion;
import sistemavuelos.modelo.Pasajero;
import sistemavuelos.modelo.Reserva;
import sistemavuelos.modelo.Vuelo;
import sistemavuelos.servicio.GestorReservas;
import sistemavuelos.servicio.GestorVuelos;
import sistemavuelos.util.Validaciones;

/**
 * Menu de reservas. PERSONA B.
 * Solo lee y muestra; la logica esta en GestorReservas.
 * Toda lectura del teclado pasa por Validaciones, que vuelve a preguntar si el dato es malo.
 */
public class MenuReservas {

    private final GestorReservas gestorReservas;
    private final GestorVuelos gestorVuelos;

    public MenuReservas(GestorReservas gestorReservas, GestorVuelos gestorVuelos) {
        this.gestorReservas = gestorReservas;
        this.gestorVuelos = gestorVuelos;
    }

    public void mostrar(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== MENU DE RESERVAS =====");
            System.out.println("1. Ver vuelos disponibles");
            System.out.println("2. Reservar asiento");
            System.out.println("3. Cancelar reserva");
            System.out.println("4. Listar reservas");
            System.out.println("5. Ver historial de un pasajero");
            System.out.println("0. Volver");
            opcion = Validaciones.leerEntero(sc, "Opcion: ", 0, 5);

            switch (opcion) {
                case 1: verVuelos(); break;
                case 2: reservar(sc); break;
                case 3: cancelar(sc); break;
                case 4: listarReservas(); break;
                case 5: verHistorial(sc); break;
                default: break; // 0: salimos del ciclo
            }
        } while (opcion != 0);
    }

    // ---------------- Opciones del menu ----------------

    // Solo muestro los vuelos para que el usuario sepa que codigos puede usar al reservar.
    private void verVuelos() {
        List<Vuelo> vuelos = gestorVuelos.listarVuelos();
        if (vuelos.isEmpty()) {
            System.out.println("No hay vuelos registrados todavia (agregalos desde el menu de vuelos).");
            return;
        }
        for (Vuelo v : vuelos) {
            System.out.println(v);
        }
    }

    private void reservar(Scanner sc) {
        System.out.println("\n--- Nueva reserva ---");

        // Primero el DUI: si el pasajero ya existe no tiene caso volver a pedirle nombre y telefono.
        String dui = Validaciones.leerDui(sc, "DUI (ej. 01234567-8): ");
        Pasajero existente = gestorReservas.buscarPasajero(dui);
        String nombre;
        String telefono;
        if (existente != null) {
            System.out.println("Pasajero ya registrado: " + existente);
            nombre = existente.getNombre();
            telefono = existente.getTelefono();
        } else {
            nombre = Validaciones.leerTexto(sc, "Nombre completo: ");
            telefono = Validaciones.leerTelefono(sc, "Telefono (ej. 7123-4567): ");
        }

        String codigo = Validaciones.leerCodigoVuelo(sc, "Codigo del vuelo: ");

        // Busco el vuelo aqui solo para mostrar info y saber cuantos asientos tiene;
        // la reserva de verdad la hace el gestor.
        Vuelo vuelo;
        try {
            vuelo = gestorVuelos.buscarPorCodigo(codigo);
        } catch (VueloNoEncontradoException e) {
            System.out.println("  [!] " + e.getMessage());
            return;
        }
        System.out.println(vuelo);

        int asiento;
        if (vuelo.estaLleno()) {
            // Si esta lleno no hay asiento que escoger, el pasajero se va directo a la lista de espera.
            System.out.println("El vuelo esta lleno: si continuas, quedaras en la lista de espera.");
            asiento = 0;
        } else {
            mostrarAsientosLibres(vuelo);
            asiento = Validaciones.leerEntero(sc, "Numero de asiento (1-" + vuelo.getCapacidad() + "): ",
                    1, vuelo.getCapacidad());
        }

        try {
            Reserva reserva = gestorReservas.reservar(dui, nombre, telefono, codigo, asiento);
            if (reserva == null) {
                // null = el vuelo estaba lleno y quedo en la cola
                System.out.println("Vuelo lleno. " + nombre + " quedo en la lista de espera del vuelo "
                        + vuelo.getCodigo() + ".");
            } else {
                System.out.println("Reserva creada: " + reserva);
            }
        } catch (VueloNoEncontradoException | AsientoOcupadoException | DatosInvalidosException e) {
            System.out.println("  [!] No se pudo reservar: " + e.getMessage());
        }
    }

    private void cancelar(Scanner sc) {
        System.out.println("\n--- Cancelar reserva ---");
        int id = Validaciones.leerEntero(sc, "ID de la reserva: ", 1, Integer.MAX_VALUE);

        try {
            Reserva cancelada = gestorReservas.cancelar(id);
            System.out.println("Reserva cancelada: " + cancelada);

            // Si alguien estaba esperando, el gestor ya le dio el asiento liberado.
            // Para avisarlo busco la reserva nueva que quedo en ese mismo vuelo y asiento.
            for (Reserva r : gestorReservas.listarReservas()) {
                if (r.getVuelo() == cancelada.getVuelo() && r.getAsiento() == cancelada.getAsiento()) {
                    System.out.println("El asiento paso al primero de la lista de espera: " + r);
                }
            }
        } catch (ReservaNoEncontradaException e) {
            System.out.println("  [!] " + e.getMessage());
        }
    }

    private void listarReservas() {
        List<Reserva> lista = gestorReservas.listarReservas();
        if (lista.isEmpty()) {
            System.out.println("No hay reservas registradas.");
            return;
        }
        for (Reserva r : lista) {
            System.out.println(r);
        }
    }

    private void verHistorial(Scanner sc) {
        String dui = Validaciones.leerDui(sc, "DUI del pasajero: ");
        try {
            List<Operacion> historial = gestorReservas.historialDe(dui);
            System.out.println("Historial (lo mas reciente primero):");
            if (historial.isEmpty()) {
                System.out.println("  (sin operaciones)");
            }
            for (Operacion op : historial) {
                System.out.println("  " + op);
            }
        } catch (DatosInvalidosException e) {
            System.out.println("  [!] " + e.getMessage());
        }
    }

    // ---------------- Ayuda ----------------

    // Imprime los numeros de asiento que siguen libres para que el usuario no adivine.
    private void mostrarAsientosLibres(Vuelo vuelo) {
        StringBuilder libres = new StringBuilder("Asientos libres:");
        for (int i = 1; i <= vuelo.getCapacidad(); i++) {
            if (vuelo.asientoLibre(i)) {
                libres.append(' ').append(i);
            }
        }
        System.out.println(libres);
    }
}