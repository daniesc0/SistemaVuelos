package sistemavuelos.menu;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import sistemavuelos.excepciones.DatosInvalidosException;
import sistemavuelos.excepciones.VueloNoEncontradoException;
import sistemavuelos.modelo.Vuelo;
import sistemavuelos.servicio.GestorVuelos;
import sistemavuelos.util.Consola;
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
            Consola.caja("MENU DE VUELOS",
                    "1. Agregar vuelo",
                    "2. Listar vuelos (por codigo)",
                    "3. Listar vuelos por precio (menor a mayor)",
                    "4. Listar vuelos por fecha y hora de salida",
                    "5. Buscar vuelo por codigo",
                    "6. Eliminar vuelo",
                    "0. Volver al menu principal");
            opcion = Validaciones.leerEntero(sc, "  Elige una opcion: ", 0, 6);

            switch (opcion) {
                case 1: agregar(sc); break;
                case 2: listar("Vuelos ordenados por codigo", gestor.listarVuelos(), sc); break;
                case 3: listar("Vuelos del mas barato al mas caro", gestor.ordenarPorPrecio(), sc); break;
                case 4: listar("Vuelos por fecha y hora de salida", gestor.ordenarPorHora(), sc); break;
                case 5: buscar(sc); break;
                case 6: eliminar(sc); break;
                default: break; // 0: salimos del ciclo
            }
        } while (opcion != 0);
    }

    // ---------------- Opciones del menu ----------------

    private void agregar(Scanner sc) {
        Consola.seccion("Nuevo vuelo");
        String codigo = Validaciones.leerCodigoVuelo(sc, "  Codigo (ej. AV123): ");
        String origen = Validaciones.leerTexto(sc, "  Origen: ");
        String destino = Validaciones.leerTexto(sc, "  Destino: ");
        // La fecha se puede escribir de varias formas, Validaciones se encarga de entenderlas todas.
        LocalDate fecha = Validaciones.leerFecha(sc, "  Fecha (ej. 25-12-2026 o 2026-12-25): ");
        LocalTime hora = Validaciones.leerHora(sc, "  Hora de salida (ej. 8:30 o 14:30): ");
        double precio = Validaciones.leerPrecio(sc, "  Precio en $: ");
        int capacidad = Validaciones.leerEntero(sc, "  Capacidad de asientos (1-500): ", 1, 500);

        try {
            // El constructor de Vuelo y el gestor pueden rechazar el vuelo (datos invalidos o codigo repetido).
            Vuelo vuelo = new Vuelo(codigo, origen, destino, fecha, hora, precio, capacidad);
            gestor.agregarVuelo(vuelo);
            System.out.println();
            Consola.exito("Vuelo agregado correctamente:");
            Consola.mostrarVuelo(vuelo);
        } catch (DatosInvalidosException e) {
            Consola.error("No se pudo agregar: " + e.getMessage());
        }
    }

    // Las tres listas (por codigo, precio y hora) se muestran igual, solo cambia el titulo y el orden.
    private void listar(String titulo, List<Vuelo> lista, Scanner sc) {
        Consola.seccion(titulo);
        if (lista.isEmpty()) {
            Consola.aviso("No hay vuelos registrados todavia. Usa la opcion 1 para agregar uno.");
        } else {
            Consola.listaVuelos(lista, sc);
        }
    }

    private void buscar(Scanner sc) {
        Consola.seccion("Buscar vuelo");
        String codigo = Validaciones.leerCodigoVuelo(sc, "  Codigo a buscar: ");
        try {
            Vuelo vuelo = gestor.buscarPorCodigo(codigo);
            System.out.println();
            Consola.mostrarVuelo(vuelo);
        } catch (VueloNoEncontradoException e) {
            Consola.error(e.getMessage());
        }
    }

    private void eliminar(Scanner sc) {
        Consola.seccion("Eliminar vuelo");
        String codigo = Validaciones.leerCodigoVuelo(sc, "  Codigo a eliminar: ");
        try {
            Vuelo vuelo = gestor.buscarPorCodigo(codigo);

            // Si el vuelo ya tiene gente, borrarlo dejaria reservas apuntando a un vuelo que no existe.
            // Por eso aqui mismo lo frenamos y pedimos cancelar primero las reservas.
            if (vuelo.cantidadOcupados() > 0 || vuelo.cantidadEnEspera() > 0) {
                Consola.aviso("No se puede eliminar el vuelo " + vuelo.getCodigo() + ": tiene "
                        + vuelo.cantidadOcupados() + " asiento(s) reservado(s) y "
                        + vuelo.cantidadEnEspera() + " persona(s) en espera.");
                Consola.aviso("Cancela primero esas reservas desde el menu de reservas.");
                return;
            }

            Consola.mostrarVuelo(vuelo);
            int seguro = Validaciones.leerEntero(sc, "  Seguro que quieres eliminarlo? (1 = si, 0 = no): ", 0, 1);
            if (seguro == 1) {
                gestor.eliminarVuelo(codigo);
                Consola.exito("Vuelo " + codigo + " eliminado.");
            } else {
                Consola.aviso("Listo, no se elimino nada.");
            }
        } catch (VueloNoEncontradoException e) {
            Consola.error(e.getMessage());
        }
    }
}
