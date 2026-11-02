package sistemavuelos.menu;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import sistemavuelos.excepciones.DatosInvalidosException;
import sistemavuelos.excepciones.VueloNoEncontradoException;
import sistemavuelos.modelo.Vuelo;
import sistemavuelos.servicio.GestorVuelos;
import sistemavuelos.util.Validaciones;

/**
 * Menu de vuelos.
 * Solo lee y muestra; la logica esta en GestorVuelos.
 * Toda lectura del teclado pasa por Validaciones, que vuelve a preguntar si el dato es malo.
 */
public class MenuVuelos {

    private final GestorVuelos gestor;

    public MenuVuelos(GestorVuelos gestor) {
        this.gestor = gestor;
    }

    public void mostrar(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n===== MENU DE VUELOS =====");
            System.out.println("1. Agregar vuelo");
            System.out.println("2. Listar vuelos (por codigo)");
            System.out.println("3. Listar vuelos por precio (menor a mayor)");
            System.out.println("4. Listar vuelos por hora de salida");
            System.out.println("5. Buscar vuelo por codigo");
            System.out.println("6. Eliminar vuelo");
            System.out.println("0. Volver");
            opcion = Validaciones.leerEntero(sc, "Opcion: ", 0, 6);

            switch (opcion) {
                case 1: agregar(sc); break;
                case 2: imprimir(gestor.listarVuelos()); break;
                case 3: imprimir(gestor.ordenarPorPrecio()); break;
                case 4: imprimir(gestor.ordenarPorHora()); break;
                case 5: buscar(sc); break;
                case 6: eliminar(sc); break;
                default: break; // 0: salimos del ciclo
            }
        } while (opcion != 0);
    }

    // ---------------- Opciones del menu ----------------

    private void agregar(Scanner sc) {
        System.out.println("\n--- Nuevo vuelo ---");
        String codigo = Validaciones.leerCodigoVuelo(sc, "Codigo (ej. AV123): ");
        String origen = Validaciones.leerTexto(sc, "Origen: ");
        String destino = Validaciones.leerTexto(sc, "Destino: ");
        LocalDate fecha = Validaciones.leerFecha(sc, "Fecha (yyyy-MM-dd): ");
        LocalTime hora = Validaciones.leerHora(sc, "Hora de salida (HH:mm): ");
        double precio = Validaciones.leerPrecio(sc, "Precio: ");
        int capacidad = Validaciones.leerEntero(sc, "Capacidad de asientos (1-500): ", 1, 500);

        try {
            // El constructor de Vuelo y el gestor pueden rechazar el vuelo (datos invalidos o codigo repetido).
            Vuelo vuelo = new Vuelo(codigo, origen, destino, fecha, hora, precio, capacidad);
            gestor.agregarVuelo(vuelo);
            System.out.println("Vuelo agregado: " + vuelo);
        } catch (DatosInvalidosException e) {
            System.out.println("  [!] No se pudo agregar: " + e.getMessage());
        }
    }

    private void buscar(Scanner sc) {
        String codigo = Validaciones.leerCodigoVuelo(sc, "Codigo a buscar: ");
        try {
            System.out.println(gestor.buscarPorCodigo(codigo));
        } catch (VueloNoEncontradoException e) {
            System.out.println("  [!] " + e.getMessage());
        }
    }

    private void eliminar(Scanner sc) {
        String codigo = Validaciones.leerCodigoVuelo(sc, "Codigo a eliminar: ");
        try {
            gestor.eliminarVuelo(codigo);
            System.out.println("Vuelo " + codigo + " eliminado.");
        } catch (VueloNoEncontradoException e) {
            System.out.println("  [!] " + e.getMessage());
        }
    }

    // ---------------- Ayuda ----------------

    private void imprimir(List<Vuelo> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay vuelos registrados.");
            return;
        }
        for (Vuelo v : lista) {
            System.out.println(v);
        }
    }
}
