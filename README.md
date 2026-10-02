# Seguimiento 3: Interfaces y Enums en Java 🚀

Proyecto académico correspondiente al **Seguimiento 3** de Programación Orientada a Objetos en Java. Este repositorio aborda en profundidad el uso de **interfaces**, **expresiones lambda**, **records**, **interfaces selladas** (`sealed`) y **enumeraciones** (`enum`), con especial énfasis en el desacoplamiento, el polimorfismo y las nuevas características del lenguaje (Java 21 LTS / 26).

---

## 📁 Estructura del Repositorio

La organización del proyecto sigue exactamente la convención solicitada en la guía:

```text
seguimiento3-interfaces/
├── README.md               <- Descripción detallada del proyecto y guía de ejecución
├── ejercicios/             <- Retos y ejercicios prácticos de la guía
│   ├── Ejercicio1.java     <- Reto 1: Figuras selladas con perímetro y pattern matching
│   ├── Ejercicio2.java     <- Reto 2: Baraja española completa (48 cartas) con enums y records
│   ├── Ejercicio3.java     <- Reto 3: Calculadora con lambdas (DoubleBinaryOperator) en el enum
│   ├── Ejercicio4.java     <- Reto 4: Promociones combinables con método default y(...)
│   └── Ejercicio5.java     <- Reto 5: Control de acceso RBAC con EnumSet y roles
├── ejemplos/               <- Demostraciones didácticas de los temas
│   ├── DemoInterfaces.java <- Ejemplos guiados de la Parte I (Interfaces, Lambdas, Sealed)
│   └── DemoEnums.java      <- Ejemplos guiados de la Parte II (Enums, EnumSet, EnumMap, Singleton)
└── laboratorios/           <- Solución a los 4 laboratorios guiados de la guía
    ├── Lab1.java           <- Lab 1: Mi playlist ordenada (Comparable, Comparator, Predicate)
    ├── Lab2.java           <- Lab 2: Pasarela de pagos (MetodoPago, Polimorfismo, Caja)
    ├── Lab3.java           <- Lab 3: Semáforo inteligente (Semaforo, Switch exhaustivo)
    ├── Lab4.java           <- Lab 4: Cafetería "El Algoritmo" (Integrador en archivo único)
    └── cafeteria/          <- Lab 4: Estructura modular (un archivo por tipo según la guía)
        ├── Bebida.java
        ├── Tamano.java
        ├── EstadoPedido.java
        ├── Promocion.java
        ├── Item.java
        ├── Pedido.java
        └── Main.java
```

---

## 🧪 Laboratorios Realizados

### Laboratorio 1: Mi playlist ordenada
- **Objetivo**: Implementar `Comparable<Cancion>` para el orden natural alfabético, ordenar por duración descendente mediante `Comparator.comparingInt(...).reversed()`, y filtrar canciones largas con `Predicate<Cancion>` y la API `Stream`.
- **Archivo**: `laboratorios/Lab1.java`
- **Salida esperada**:
  ```text
  Orden natural (título):
    Bailando       Enrique Iglesias  4:03
    Despacito      Luis Fonsi        3:48
    La Bicicleta   Carlos Vives      3:47
    Tusa           Karol G           3:20
  De la más larga a la más corta:
    Bailando (4:03)
    Despacito (3:48)
    La Bicicleta (3:47)
    Tusa (3:20)
  Canciones largas: [BAILANDO, DESPACITO, LA BICICLETA]
  ```

### Laboratorio 2: Pasarela de pagos
- **Objetivo**: Diseñar la interfaz `MetodoPago` con métodos abstractos y métodos `default` (`comision`, `totalACobrar`). Crear implementaciones para `TarjetaCredito`, `BilleteraDigital`, `Efectivo` y una clase `Caja` que cobra de forma desacoplada. Se incluye además una implementación de `Criptomoneda` demostrando extensibilidad abierta sin modificar `Caja`.
- **Archivo**: `laboratorios/Lab2.java`
- **Salida esperada**:
  ```text
  Tarjeta de crédito   $   120.000 comisión $  3.600 APROBADO
  Billetera digital    $    80.000 comisión $      0 RECHAZADO
  Efectivo             $    35.000 comisión $      0 APROBADO
  Tarjeta de crédito   $    99.000 comisión $  2.970 RECHAZADO
  ```

### Laboratorio 3: Semáforo inteligente
- **Objetivo**: Representar un semáforo mediante un `enum` con atributos `segundos` y `accion`. Implementar la transición de estados `siguiente()` mediante expresiones `switch` modernas (con verificación de exhaustividad en compilación) y calcular la duración de ciclo con `duracionCiclo()`. Incluye además el reto extra de `AMARILLO_INTERMITENTE`.
- **Archivo**: `laboratorios/Lab3.java`
- **Salida esperada**:
  ```text
  Paso 1: VERDE     30 s → Avance
  Paso 2: AMARILLO   5 s → Precaución
  Paso 3: ROJO      35 s → Pare
  Paso 4: VERDE     30 s → Avance
  Duración del ciclo completo: 70 s
  ```

### Laboratorio 4: Cafetería "El Algoritmo" (Integrador)
- **Objetivo**: Sistema integral que combina:
  - Enums: `Bebida`, `Tamano` y `EstadoPedido` (máquina de estados).
  - Interfaces funcionales y lambdas: `Promocion` con métodos estáticos `ninguna()`, `porcentaje(...)` y `fijaDesde(...)`.
  - Records: `Item` con cálculo de subtotal.
  - Clase con API fluida y Streams: `Pedido` con métodos encadenables (`agregar`, `avanzar`, `cancelar`, `imprimirTicket`).
- **Archivos**: Disponible tanto en `laboratorios/Lab4.java` (ejecución directa) como modular en `laboratorios/cafeteria/`.
- **Salida esperada**:
  ```text
   = Cafetería El Algoritmo =
  Cliente: Camila | Estado: LISTO
  2 x CAPUCHINO  GRANDE   $  16.000
  1 x TINTO      PEQUENO  $   2.500
  1 x CHOCOLATE  MEDIANO  $   6.000
  Subtotal                $  24.500
  Descuento               $   2.450
  TOTAL                   $  22.050
  Aviso: No se puede cancelar en estado LISTO
  ```

---

## 💡 Ejercicios y Retos Adicionales

1. **`Ejercicio1.java` (Reto 1)**: Ampliación de la jerarquía sellada `Figura` (`sealed interface`) agregando `Cuadrado`, método `perimetro()` y evaluación exhaustiva con *pattern matching* en `switch`.
2. **`Ejercicio2.java` (Reto 2)**: Modelado de la Baraja Española completa (48 cartas) utilizando los enums `Palo` y `Valor`, un record `Carta`, generación funcional y mezcla con `Collections.shuffle`.
3. **`Ejercicio3.java` (Reto 3)**: Calculadora aritmética moderna que almacena un `DoubleBinaryOperator` dentro de cada constante del enum, incorporando `POTENCIA` y `MODULO`.
4. **`Ejercicio4.java` (Reto 4)**: Composición funcional de promociones mediante el método default `y(...)`, asegurando que el descuento total no supere el subtotal.
5. **`Ejercicio5.java` (Reto 5)**: Control de acceso basado en roles (RBAC) altamente eficiente utilizando `EnumSet<Permiso>`.

---

## 🛠️ Compilación y Ejecución

Para compilar y ejecutar cualquier clase desde la raíz de `seguimiento3-interfaces`:

```bash
# Compilar todo el proyecto
javac laboratorios/*.java laboratorios/cafeteria/*.java ejercicios/*.java ejemplos/*.java

# Ejecutar los Laboratorios
java laboratorios.Lab1
java laboratorios.Lab2
java laboratorios.Lab3
java laboratorios.Lab4
java laboratorios.cafeteria.Main

# Ejecutar los Ejercicios
java ejercicios.Ejercicio1
java ejercicios.Ejercicio2
java ejercicios.Ejercicio3
java ejercicios.Ejercicio4
java ejercicios.Ejercicio5

# Ejecutar los Ejemplos
java ejemplos.DemoInterfaces
java ejemplos.DemoEnums
```

> **Requisito**: Java 21 LTS o superior.
