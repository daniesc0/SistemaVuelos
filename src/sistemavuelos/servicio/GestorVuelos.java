package sistemavuelos.servicio;

import java.util.ArrayList;
import java.util.List;
import sistemavuelos.estructuras.ArbolDestinos;
import sistemavuelos.excepciones.DatosInvalidosException;
import sistemavuelos.excepciones.VueloNoEncontradoException;
import sistemavuelos.modelo.Vuelo;
import sistemavuelos.util.BusquedaBinaria;
import sistemavuelos.util.Ordenamiento;

/**
 * Logica de los vuelos.
 * Reglas aplicadas: sin Scanner ni System.in; la lista "vuelos" siempre ordenada por codigo;
 * este gestor es el UNICO que llama a arbol.insertar / arbol.eliminarVuelo.
 */
public class GestorVuelos {

    // Esta lista SIEMPRE se mantiene ordenada por codigo. Porque es lo que hace posible la busqueda binaria.
    private final ArrayList<Vuelo> vuelos = new ArrayList<>();
    private final ArbolDestinos arbol;

    public GestorVuelos(ArbolDestinos arbol) {
        this.arbol = arbol;
    }

    /** Agrega el vuelo (rechaza codigos repetidos) y lo registra tambien en el arbol. */
    public void agregarVuelo(Vuelo vuelo) throws DatosInvalidosException {
        if (vuelo == null) {
            throw new DatosInvalidosException("El vuelo no puede ser nulo");
        }

        // 1. El codigo es la clave unica: si ya existe, no se puede repetir.
        if (BusquedaBinaria.buscarPorCodigo(vuelos, vuelo.getCodigo()) != -1) {
            throw new DatosInvalidosException("Ya existe un vuelo con el codigo " + vuelo.getCodigo());
        }

        // 2. Lo insertamos en su lugar correcto para que la lista siga ordenada por codigo
        //    (asi no hace falta reordenar toda la lista cada vez que entra un vuelo nuevo).
        int posicion = 0;
        while (posicion < vuelos.size()
                && vuelos.get(posicion).getCodigo().compareTo(vuelo.getCodigo()) < 0) {
            posicion++;
        }
        vuelos.add(posicion, vuelo);

        // 3. Tambien lo guardamos en el arbol de destinos para poder buscarlo por destino.
        arbol.insertar(vuelo);
    }

    /** Quita el vuelo de la lista y del arbol. */
    public void eliminarVuelo(String codigo) throws VueloNoEncontradoException {
        // buscarPorCodigo ya lanza la excepcion si el vuelo no existe.
        Vuelo vuelo = buscarPorCodigo(codigo);

        vuelos.remove(vuelo);
        arbol.eliminarVuelo(vuelo);
    }

    /** Busca por codigo (busqueda binaria). Lanza VueloNoEncontradoException si no existe. */
    public Vuelo buscarPorCodigo(String codigo) throws VueloNoEncontradoException {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new VueloNoEncontradoException("Debes indicar el codigo del vuelo");
        }

        // Los codigos se guardan en mayusculas, asi que normalizamos lo que escribio el usuario.
        String codigoNormalizado = codigo.trim().toUpperCase();
        int posicion = BusquedaBinaria.buscarPorCodigo(vuelos, codigoNormalizado);

        if (posicion == -1) {
            throw new VueloNoEncontradoException("No existe un vuelo con el codigo " + codigoNormalizado);
        }
        return vuelos.get(posicion);
    }

    /** Copia de la lista de vuelos (ordenada por codigo). */
    public List<Vuelo> listarVuelos() {
        // Devolvemos una COPIA para que nadie de afuera pueda desordenar nuestra lista real.
        return new ArrayList<>(vuelos);
    }

    /** Copia ordenada por precio. La lista original no se toca. */
    public List<Vuelo> ordenarPorPrecio() {
        List<Vuelo> copia = new ArrayList<>(vuelos);
        Ordenamiento.ordenarPorPrecio(copia);
        return copia;
    }

    /** Copia ordenada por fecha/hora de salida. La lista original no se toca. */
    public List<Vuelo> ordenarPorHora() {
        List<Vuelo> copia = new ArrayList<>(vuelos);
        Ordenamiento.ordenarPorHora(copia);
        return copia;
    }
}
