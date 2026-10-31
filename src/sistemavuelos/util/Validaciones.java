package sistemavuelos.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Pattern;
import sistemavuelos.excepciones.DatosInvalidosException;


public class Validaciones {

    private static final Pattern CODIGO_VUELO = Pattern.compile("^[A-Z]{2,3}[0-9]{2,4}$");  // AV123
    private static final Pattern DUI = Pattern.compile("^[0-9]{8}-[0-9]$");                 // 01234567-8
    private static final Pattern TELEFONO = Pattern.compile("^[0-9]{4}-?[0-9]{4}$");        // 7123-4567

    private Validaciones() { }

//Validaciones
    
    public static int validarEntero(String texto, int min, int max) throws DatosInvalidosException {
        int valor;
        try {
            valor = Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new DatosInvalidosException("Debes escribir un numero entero");
        }
        if (valor < min || valor > max) {
            throw new DatosInvalidosException("El numero debe estar entre " + min + " y " + max);
        }
        return valor;
    }

public static double validarPrecio(String texto) throws DatosInvalidosException {
        double valor;
        try {
            valor = Double.parseDouble(texto.trim());
        } catch (NumberFormatException e) {
            throw new DatosInvalidosException("Debes escribir un numero (ejemplo: 125.50)");
        }
        if (valor <= 0) {
            throw new DatosInvalidosException("El precio debe ser mayor que 0");
        }
        return valor;
    }

    public static String validarTexto(String texto) throws DatosInvalidosException {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatosInvalidosException("El dato no puede estar vacio");
        }
        return texto.trim();
    }

    /** Devuelve el codigo en mayusculas. Formato: 2-3 letras y 2-4 numeros (ejemplo: AV123). */
    public static String validarCodigoVuelo(String texto) throws DatosInvalidosException {
        String codigo = validarTexto(texto).toUpperCase();
        if (!CODIGO_VUELO.matcher(codigo).matches()) {
            throw new DatosInvalidosException("Codigo invalido. Formato: 2-3 letras y 2-4 numeros (ejemplo: AV123)");
        }
        return codigo;
    }

    /** Formato: 8 digitos, guion y 1 digito (ejemplo: 01234567-8). */
    public static String validarDui(String texto) throws DatosInvalidosException {
        String dui = validarTexto(texto);
        if (!DUI.matcher(dui).matches()) {
            throw new DatosInvalidosException("DUI invalido. Formato: 8 digitos, guion y 1 digito (ejemplo: 01234567-8)");
        }
        return dui;
    }

    /** 8 digitos, con o sin guion (ejemplo: 7123-4567). */
    public static String validarTelefono(String texto) throws DatosInvalidosException {
        String tel = validarTexto(texto);
        if (!TELEFONO.matcher(tel).matches()) {
            throw new DatosInvalidosException("Telefono invalido. Formato: 8 digitos (ejemplo: 7123-4567)");
        }
        return tel;
    }

    /** Formato yyyy-MM-dd. */
    public static LocalDate validarFecha(String texto) throws DatosInvalidosException {
        try {
            return LocalDate.parse(texto.trim());
        } catch (DateTimeParseException e) {
            throw new DatosInvalidosException("Fecha invalida. Formato: yyyy-MM-dd (ejemplo: 2026-12-25)");
        }
    }

    /** Formato HH:mm en 24 horas. */
    public static LocalTime validarHora(String texto) throws DatosInvalidosException {
        try {
            return LocalTime.parse(texto.trim());
        } catch (DateTimeParseException e) {
            throw new DatosInvalidosException("Hora invalida. Formato: HH:mm en 24 horas (ejemplo: 14:30)");
        }
    }
    //lectores
    // Un "convertidor" recibe el texto escrito y devuelve el dato ya validado.
    private interface Conversor<T> {
        T convertir(String texto) throws DatosInvalidosException;
    }

    // Ciclo comun: pide el dato, intenta validarlo, y si falla muestra el error y vuelve a pedir.
    private static <T> T leer(Scanner sc, String mensaje, Conversor<T> conversor) {
        while (true) {
            System.out.print(mensaje);
            String linea = sc.nextLine();
            try {
                return conversor.convertir(linea);
            } catch (DatosInvalidosException e) {
                System.out.println("  [!] " + e.getMessage());
            }
        }
    }

    public static int leerEntero(Scanner sc, String mensaje, int min, int max) {
        return leer(sc, mensaje, t -> validarEntero(t, min, max));
    }

    public static double leerPrecio(Scanner sc, String mensaje) {
        return leer(sc, mensaje, Validaciones::validarPrecio);
    }

    public static String leerTexto(Scanner sc, String mensaje) {
        return leer(sc, mensaje, Validaciones::validarTexto);
    }

    public static String leerCodigoVuelo(Scanner sc, String mensaje) {
        return leer(sc, mensaje, Validaciones::validarCodigoVuelo);
    }

    public static String leerDui(Scanner sc, String mensaje) {
        return leer(sc, mensaje, Validaciones::validarDui);
    }

    public static String leerTelefono(Scanner sc, String mensaje) {
        return leer(sc, mensaje, Validaciones::validarTelefono);
    }

    public static LocalDate leerFecha(Scanner sc, String mensaje) {
        return leer(sc, mensaje, Validaciones::validarFecha);
    }

    public static LocalTime leerHora(Scanner sc, String mensaje) {
        return leer(sc, mensaje, Validaciones::validarHora);
    }
}