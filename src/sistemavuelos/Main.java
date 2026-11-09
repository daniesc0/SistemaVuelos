package sistemavuelos;

import java.util.Scanner;
import sistemavuelos.datos.DatosEjemplo;
import sistemavuelos.estructuras.ArbolDestinos;
import sistemavuelos.menu.MenuDestinos;
import sistemavuelos.menu.MenuReservas;
import sistemavuelos.menu.MenuVuelos;
import sistemavuelos.servicio.GestorReservas;
import sistemavuelos.servicio.GestorVuelos;
import sistemavuelos.util.Consola;
import sistemavuelos.util.Validaciones;

/**
 * Punto de entrada del sistema. Aqui solo armamos las piezas, las conectamos y mostramos el menu principal.
 */
public class Main {

    // true = al arrancar se cargan los vuelos, pasajeros y reservas de ejemplo (clase DatosEjemplo).
    // Ponerlo en false si queremos empezar con el sistema vacio.
    private static final boolean CARGAR_EJEMPLOS = true;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // --- Estructuras y logica ---
        // El arbol se crea primero porque GestorVuelos lo necesita para registrar cada vuelo.
        ArbolDestinos arbol = new ArbolDestinos();
        GestorVuelos gestorVuelos = new GestorVuelos(arbol);
        GestorReservas gestorReservas = new GestorReservas(gestorVuelos);

        // --- Menus ---
        MenuVuelos menuVuelos = new MenuVuelos(gestorVuelos);
        MenuReservas menuReservas = new MenuReservas(gestorReservas, gestorVuelos);
        MenuDestinos menuDestinos = new MenuDestinos(arbol);

        Consola.banner("SISTEMA DE RESERVAS DE VUELOS", "Manejo de Estructuras de Datos");

        // Los datos de ejemplo se cargan DESPUES de crear los gestores y ANTES de mostrar el menu.
        if (CARGAR_EJEMPLOS) {
            DatosEjemplo.cargar(gestorVuelos, gestorReservas);
        }

        // --- Menu principal ---
        int opcion;
        do {
            Consola.caja("MENU PRINCIPAL",
                    "1. Vuelos",
                    "2. Reservas",
                    "3. Destinos",
                    "0. Salir");
            opcion = Validaciones.leerEntero(sc, "  Elige una opcion: ", 0, 3);

            switch (opcion) {
                case 1:
                    menuVuelos.mostrar(sc);
                    break;
                case 2:
                    menuReservas.mostrar(sc);
                    break;
                case 3:
                    menuDestinos.mostrar(sc);
                    break;
                default:
                    break; // 0: salimos del ciclo
            }
        } while (opcion != 0);

        System.out.println();
        Consola.aviso("Gracias por usar el sistema. Hasta pronto!");
        sc.close();
    }
}
