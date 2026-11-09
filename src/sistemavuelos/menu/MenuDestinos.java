package sistemavuelos.menu;

import java.util.List;
import java.util.Scanner;
import sistemavuelos.estructuras.ArbolDestinos;
import sistemavuelos.modelo.Vuelo;
import sistemavuelos.util.Validaciones;

public class MenuDestinos {

    private final ArbolDestinos arbol;

    public MenuDestinos(ArbolDestinos arbol) {
        this.arbol = arbol;
    }

    public void mostrar(Scanner sc) {
        int opcion;
        do {
            System.out.println("\n=== MENU DESTINOS ===");
            System.out.println("1. Listar destinos en orden alfabetico");
            System.out.println("2. Buscar vuelos por destino");
            System.out.println("0. Volver");
            opcion = Validaciones.leerEntero(sc, "Opcion: ", 0, 2);
            switch (opcion) {
                case 1:
                    listarDestinos();
                    break;
                case 2:
                    buscarPorDestino(sc);
                    break;
                default:
                    break;
            }
        } while (opcion != 0);
    }

    private void listarDestinos() {
        System.out.println("\nDestinos en orden alfabetico:");
        arbol.listarDestinosInorden();
    }

    private void buscarPorDestino(Scanner sc) {
        String destino = Validaciones.leerTexto(sc, "Destino a buscar: ");
        List<Vuelo> vuelos = arbol.buscarPorDestino(destino);
        if (vuelos.isEmpty()) {
            System.out.println("  No hay vuelos hacia " + destino);
            return;
        }
        for (Vuelo v : vuelos) {
            System.out.println("  " + v);
        }
    }
}