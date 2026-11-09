# Sistema de Reservas de Vuelos
**Manejo de Estructuras de Datos - Proyecto Final (Tema 4)**

Sistema en Java (POO) para buscar vuelos, reservar asientos y cancelar reservas. Si un vuelo está lleno, el pasajero entra a una lista de espera y, cuando alguien cancela, el primero de esa lista se queda con el asiento.

## Equipo
| Integrante | Qué hizo |
| :--- | :--- |
| **Isela Quintanilla** | La lógica de los vuelos (`GestorVuelos`), el ordenamiento por precio y por fecha/hora (`Ordenamiento`), la búsqueda binaria por código (`BusquedaBinaria`) y el menú de vuelos (`MenuVuelos`). |
| **Glenda Cruz** | La lógica de las reservas (`GestorReservas`): reservar, cancelar, lista de espera e historial del pasajero, y el menú de reservas (`MenuReservas`). |
| **Daniel Escobar** | El árbol de destinos (`ArbolDestinos`) y su menú (`MenuDestinos`), las validaciones de entrada (`Validaciones`) y el `Main` con el menú principal. |

## Cómo abrirlo
**NetBeans:** `New Project > Java with Ant > Java Project with Existing Sources`, agregar la carpeta `src`. Main class: `sistemavuelos.Main`.

Al arrancar se cargan datos de ejemplo (20 vuelos, 20 pasajeros, reservas, listas de espera y una cancelación). Para empezar con el sistema vacío, poner `CARGAR_EJEMPLOS = false` en `Main`.

---

## Lógica general del proyecto

El programa se divide en tres capas que se hablan siempre en el mismo orden:

```
Menu  ->  Gestor (servicio)  ->  Modelo / Estructuras
(lee y muestra)  (reglas y lógica)  (los datos y su estructura)
```

1. **El menú** pide los datos con `Validaciones` (que vuelve a preguntar si el dato es malo) y muestra los resultados. No toma decisiones.
2. **El gestor** aplica las reglas (código repetido, asiento ocupado, vuelo lleno, etc.) y lanza excepciones propias cuando algo está mal. No usa `Scanner` ni imprime nada.
3. **El modelo y las estructuras** guardan los datos: vuelos, pasajeros, reservas, la cola de espera, la pila de historial y el árbol de destinos.

### Qué pasa al reservar
1. Se busca el vuelo por código con **búsqueda binaria**. Si no existe: `VueloNoEncontradoException`.
2. Se busca al pasajero por DUI. Si ya existe se reutiliza el mismo objeto (así conserva su historial); si no, se crea, pero solo se guarda al final si todo salió bien.
3. Se rechaza si ya tiene una reserva en ese vuelo o ya está en su lista de espera.
4. Si el vuelo **está lleno**, el pasajero entra a la **cola** de espera del vuelo y no se le asigna asiento.
5. Si hay cupo, se ocupa el asiento (si ya estaba ocupado o no existe: `AsientoOcupadoException`), se crea la `Reserva` y se **apila** la operación en el historial del pasajero.

### Qué pasa al cancelar
1. Se busca la reserva por su ID. Si no existe: `ReservaNoEncontradaException`.
2. Se libera el asiento, se quita la reserva y se apila la cancelación en el historial.
3. Si había gente esperando en ese vuelo, el **primero de la cola** (FIFO) toma justo el asiento que se liberó y se le crea su reserva nueva.

---

## El árbol de destinos (BST)

Pide el tema: *"Búsqueda de vuelos por destino; como el destino se repite, cada nodo guarda la lista de vuelos hacia ese destino."*

- Es un **árbol binario de búsqueda** cuya **clave es el destino** (texto, comparado sin importar mayúsculas).
- Como varios vuelos van al mismo destino, **cada nodo guarda una lista de vuelos**. Si llega un vuelo con un destino que ya existe, no se crea otro nodo: se suma a la lista de ese nodo.
- Menor alfabéticamente va a la izquierda, mayor a la derecha.

Ejemplo real: así queda el árbol con los primeros destinos de los datos de ejemplo, que se insertan en este orden: Miami, Panama, Madrid, Ciudad de Mexico, Guatemala, Los Angeles, Nueva York y Toronto (Miami y Panama tienen 2 vuelos cada uno, por eso solo aparecen una vez).

```
                      Miami [AV101, TA202]
                     /                    \
               Madrid                      Panama [CM410, CM412]
              [IB301, IB305]              /                     \
              /                    Nueva York                  Toronto
    Ciudad de Mexico                [AA880]                    [AC940]
       [AM520, AM522]
              \
           Guatemala
        [AV620, AV622]
                 \
              Los Angeles
                [UA777]
```

La forma del árbol depende del orden en que llegan los vuelos. Por eso un recorrido **inorden** siempre sale ordenado alfabéticamente aunque el árbol no quede perfectamente balanceado.

Operaciones:

| Operación | Cómo funciona |
| :--- | :--- |
| `insertar(vuelo)` | Recursiva: baja comparando destinos hasta encontrar el nodo o un hueco para crearlo. |
| `buscarPorDestino(destino)` | Iterativa: baja por el árbol; devuelve una **copia** de la lista de vuelos, o lista vacía si no existe. |
| `eliminarVuelo(vuelo)` | Quita el vuelo de la lista de su nodo. Si el nodo se queda sin vuelos **no se borra del árbol**: simplemente se omite al listar (así no hay que reacomodar el árbol). |
| `listarDestinosInorden()` | **Recursión del proyecto**: recorrido inorden (izquierda, nodo, derecha). Como es un BST, los destinos salen en **orden alfabético**. Caso base: nodo nulo. |

Regla importante: **solo `GestorVuelos` llama a `insertar` y `eliminarVuelo`**. Así la lista de vuelos y el árbol nunca quedan distintos.

---

## Por qué lo dividimos en paquetes

Cada paquete tiene una sola responsabilidad. Así cada integrante trabajaba su parte sin pisar a los demás y, si algo falla, sabemos en qué capa buscar.

```
src/sistemavuelos/
├── Main.java            Arma las piezas, carga los datos de ejemplo y muestra el menú principal
├── modelo/              Los datos: Vuelo, Pasajero, Reserva, Operacion
├── excepciones/         4 excepciones propias
├── estructuras/         El árbol de destinos: NodoDestino, ArbolDestinos
├── servicio/            La lógica: GestorVuelos, GestorReservas
├── util/                Herramientas: Ordenamiento, BusquedaBinaria, Validaciones, Consola
├── menu/                Lo que ve el usuario: MenuVuelos, MenuReservas, MenuDestinos
└── datos/               DatosEjemplo (para no teclear todo en cada prueba)
```

| Paquete | Por qué está separado |
| :--- | :--- |
| `modelo` | Solo guarda datos y se valida a sí mismo (el constructor rechaza datos vacíos o inválidos). No sabe nada de menús. |
| `excepciones` | Un error por cada caso del tema: vuelo inexistente, asiento ocupado, reserva inexistente y datos inválidos. |
| `estructuras` | El BST es una estructura de datos con su propia lógica, así que no se mezcla con las reglas del negocio. |
| `servicio` | Aquí viven las reglas. No usa `Scanner`, por lo que se puede probar sin menús. |
| `util` | Código que no pertenece a ninguna clase en particular: ordenar, buscar, validar entradas y dibujar el menú principal. |
| `menu` | Solo lee y muestra. Si cambiamos cómo se ve el programa, no se toca la lógica. |

---

## El modelo que elegimos

| Clase | Qué representa | Estructura que usa |
| :--- | :--- | :--- |
| `Vuelo` | Código, origen, destino, fecha, hora, precio y capacidad. | Arreglo `boolean[]` de asientos (true = ocupado) y una **cola** (`Queue`) de lista de espera. |
| `Pasajero` | DUI, nombre y teléfono. Dos pasajeros son el mismo si tienen el mismo DUI. | Una **pila** (`Stack`) con su historial. |
| `Reserva` | Une un pasajero, un vuelo y un número de asiento. Su ID se genera solo. | - |
| `Operacion` | Un registro del historial: reserva, cancelación o lista de espera, con fecha y hora. Es inmutable. | Es lo que se apila en la pila del pasajero. |

Decisiones de diseño:
- **Cada vuelo tiene su propia cola** y **cada pasajero su propia pila**, así los datos viven junto a quien los usa.
- **Nada interno sale por getters**: el arreglo de asientos y la cola solo se tocan con métodos de `Vuelo` (`ocuparAsiento`, `liberarAsiento`, `agregarAEspera`...).
- El historial se devuelve como **copia** (se desapila una copia de la pila), así la pila real no se modifica desde afuera. Lo más reciente sale primero.
- `GestorReservas` guarda también la lista de pasajeros, así un mismo DUI siempre usa el mismo objeto `Pasajero` y por tanto la misma pila.

---

## Estructuras y requisitos del proyecto

| Requisito del tema | Dónde está |
| :--- | :--- |
| **ArrayList**: vuelos y reservas | `GestorVuelos.vuelos`, `GestorReservas.reservas` y `pasajeros` |
| **Ordenamiento** por precio u hora de salida | `Ordenamiento` (inserción). Se ordena una **copia**, la lista original no se toca. |
| **Colas**: lista de espera | `Vuelo.listaEspera`; al cancelar, `GestorReservas.cancelar` da el asiento al primero |
| **Pilas**: historial del pasajero | `Pasajero.historial`; se muestra lo más reciente primero |
| **Árbol (BST)**: vuelos por destino | `ArbolDestinos` y `NodoDestino` |
| **Búsqueda binaria** por código | `BusquedaBinaria`; `GestorVuelos` mantiene la lista **siempre ordenada por código** insertando cada vuelo en su lugar |
| **Manejo de excepciones** | Vuelo inexistente, asiento ocupado, reserva inexistente y datos inválidos; `Validaciones` vuelve a preguntar si el dato es malo |
| **Recursión**: inorden del BST | `ArbolDestinos.listarDestinosInorden` |
| **Menú de operaciones** | `Main` y los tres menús del paquete `menu` |
