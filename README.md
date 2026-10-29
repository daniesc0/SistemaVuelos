# Sistema de Reservas de Vuelos
**Manejo de Estructuras de Datos - Proyecto Final (Tema 4)**

Base del proyecto: paquetes y clases con las firmas acordadas en la guía. Compila; cada `TODO (Persona X)` es un hueco por llenar.

## Estructura
```
src/sistemavuelos/
├── Main.java            C  (solo conecta, falta el menú principal)
├── modelo/              HECHO: Vuelo, Pasajero, Reserva, Operacion
├── excepciones/         HECHO: 4 excepciones propias
├── estructuras/         NodoDestino (hecho), ArbolDestinos (C)
├── servicio/            GestorVuelos (A), GestorReservas (B)
├── util/                Ordenamiento (A), BusquedaBinaria (A), Validaciones (C, primero)
└── menu/                MenuVuelos (A), MenuReservas (B), MenuDestinos (C)
```

## Reparto
| Persona | Archivos |
| :---: | :--- |
| **A** | `GestorVuelos`, `Ordenamiento`, `BusquedaBinaria`, `MenuVuelos` |
| **B** | `GestorReservas`, `MenuReservas` |
| **C** | `ArbolDestinos`, `Validaciones`, `MenuDestinos`, `Main` |

C entrega `Validaciones` lo antes posible: los menús de A y B dependen de ella.

## Cómo abrirlo
**NetBeans:** `New Project > Java with Ant > Java Project with Existing Sources`, agregar la carpeta `src`. Main class: `sistemavuelos.Main`.

## Reglas
1. No cambiar `modelo/` ni `excepciones/` sin avisar.
2. Las firmas públicas no se cambian sin avisar (guía, sección 9).
3. Los gestores no usan `Scanner`; toda lectura pasa por `Validaciones`.
4. Solo `GestorVuelos` llama a `arbol.insertar` / `arbol.eliminarVuelo`.
5. Una rama por persona, commits pequeños, a `main` solo lo que compila.
