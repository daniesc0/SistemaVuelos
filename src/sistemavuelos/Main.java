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
 * Punto de entrada. PERSONA C.
 * Es el UNICO lugar donde se hace "new" de los gestores y el arbol, y se pasan por
 * constructor, asi todos trabajan con las mismas instancias. Aqui solo se conecta.
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArbolDestinos arbol = new ArbolDestinos();
        GestorVuelos gestorVuelos = new GestorVuelos(arbol);
        GestorReservas gestorReservas = new GestorReservas(gestorVuelos);

        MenuVuelos menuVuelos = new MenuVuelos(gestorVuelos);
        MenuReservas menuReservas = new MenuReservas(gestorReservas, gestorVuelos);
        MenuDestinos menuDestinos = new MenuDestinos(arbol);

        // TODO (Persona C): menu principal (1 Vuelos, 2 Reservas, 3 Destinos, 0 Salir)
        // que llame a menuVuelos.mostrar(sc), menuReservas.mostrar(sc) y menuDestinos.mostrar(sc)
        sc.close();
    }
}
