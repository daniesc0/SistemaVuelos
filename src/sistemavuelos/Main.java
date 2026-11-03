package sistemavuelos;

import java.util.Scanner;
import sistemavuelos.estructuras.ArbolDestinos;
import sistemavuelos.menu.MenuDestinos;
import sistemavuelos.menu.MenuReservas;
import sistemavuelos.menu.MenuVuelos;
import sistemavuelos.servicio.GestorReservas;
import sistemavuelos.servicio.GestorVuelos;
import sistemavuelos.util.Validaciones;

/**
 * MENU PRINCIPAL
 */
public class Main {

    // Usamos false por el momento, para que la opcion 3 diga que esta en construccion para mientras se completa
    // en vez de quedarse muda.
    private static final boolean DESTINOS_LISTO = false;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // --- Estructuras y logica ---
        ArbolDestinos arbol = new ArbolDestinos();
        GestorVuelos gestorVuelos = new GestorVuelos(arbol);
        GestorReservas gestorReservas = new GestorReservas(gestorVuelos);

        // --- Menus ---
        MenuVuelos menuVuelos = new MenuVuelos(gestorVuelos);
        MenuReservas menuReservas = new MenuReservas(gestorReservas, gestorVuelos);
        MenuDestinos menuDestinos = new MenuDestinos(arbol);

        // --- Menu principal ---
        int opcion;
        do {
            System.out.println("\n=========== SISTEMA DE RESERVAS DE VUELOS ===========");
            System.out.println("1. Vuelos");
            System.out.println("2. Reservas");
            System.out.println("3. Destinos");
            System.out.println("0. Salir");
            opcion = Validaciones.leerEntero(sc, "Opcion: ", 0, 3);

            switch (opcion) {
                case 1:
                    menuVuelos.mostrar(sc);
                    break;
                case 2:
                    menuReservas.mostrar(sc);
                    break;
                case 3:
                    if (DESTINOS_LISTO) {
                        menuDestinos.mostrar(sc);
                    } else {
                        System.out.println("Destinos (arbol BST) todavia esta por completarse.");
                    }
                    break;
                default:
                    break; // 0: salimos del ciclo
            }
        } while (opcion != 0);

        System.out.println("Fin del programa.");
        sc.close();
    }
}