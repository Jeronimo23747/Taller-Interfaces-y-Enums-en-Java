package ejemplos;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 * Ejemplos Prácticos de Enums (Parte II de la Guía)
 * 
 * Incluye demostraciones didácticas de:
 * 1. Enums con atributos, constructores y métodos de conversión/búsqueda.
 * 2. Comportamiento polimórfico en enums (métodos abstractos por constante).
 * 3. Enums implementando interfaces.
 * 4. Colecciones optimizadas: EnumSet y EnumMap.
 * 5. Patrón Singleton seguro con Enum.
 */

// 1. Enum con atributos
enum MonedaDemo {
    COP("Peso colombiano", "$", 1.0),
    USD("Dólar estadounidense", "US$", 4_000.0),
    EUR("Euro", "€", 4_400.0);

    private final String nombre;
    private final String simbolo;
    private final double tasaCop;

    MonedaDemo(String nombre, String simbolo, double tasaCop) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.tasaCop = tasaCop;
    }

    public String getNombre() { return nombre; }
    public String getSimbolo() { return simbolo; }

    public double convertirA(MonedaDemo destino, double cantidad) {
        return cantidad * this.tasaCop / destino.tasaCop;
    }

    public static MonedaDemo desdeSimbolo(String simbolo) {
        for (MonedaDemo m : values()) {
            if (m.simbolo.equalsIgnoreCase(simbolo)) return m;
        }
        throw new IllegalArgumentException("Símbolo desconocido: " + simbolo);
    }
}

// 2. Enum con método abstracto por constante
enum OperacionAritmetica {
    SUMAR {
        @Override
        public double calcular(double a, double b) { return a + b; }
    },
    MULTIPLICAR {
        @Override
        public double calcular(double a, double b) { return a * b; }
    };

    public abstract double calcular(double a, double b);
}

// 3. Enum implementando interfaz
interface Descontable {
    double aplicarDescuento(double total);
}

enum Membresia implements Descontable {
    ESTANDAR(0.0),
    VIP(0.15),
    PLATINO(0.25);

    private final double porcentaje;

    Membresia(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    @Override
    public double aplicarDescuento(double total) {
        return total * (1.0 - porcentaje);
    }
}

// 4. Singleton con Enum
enum ConfiguracionApp {
    INSTANCIA;

    private String tema = "DARK";
    private int tamanoFuente = 14;

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public int getTamanoFuente() { return tamanoFuente; }
    public void setTamanoFuente(int tamanoFuente) { this.tamanoFuente = tamanoFuente; }
}

public class DemoEnums {
    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("   DEMOSTRACIÓN COMPLETA: ENUMS EN JAVA   ");
        System.out.println("==========================================\n");

        // 1. Monedas y conversiones
        System.out.println("--- 1. Enums con atributos y métodos ---");
        double usd100EnCop = MonedaDemo.USD.convertirA(MonedaDemo.COP, 100);
        System.out.printf("100 USD = $%,.2f COP%n", usd100EnCop);
        System.out.println("Búsqueda por símbolo '€': " + MonedaDemo.desdeSimbolo("€").getNombre());

        // 2. Comportamiento por constante
        System.out.println("\n--- 2. Comportamiento abstracto por constante ---");
        System.out.println("SUMAR(10, 20): " + OperacionAritmetica.SUMAR.calcular(10, 20));
        System.out.println("MULTIPLICAR(10, 20): " + OperacionAritmetica.MULTIPLICAR.calcular(10, 20));

        // 3. Enum implementando interfaces
        System.out.println("\n--- 3. Enums que implementan interfaces ---");
        double compra = 200_000;
        for (Membresia m : Membresia.values()) {
            System.out.printf("Total con membresía %-10s: $%,.0f%n", m, m.aplicarDescuento(compra));
        }

        // 4. EnumSet y EnumMap
        System.out.println("\n--- 4. Colecciones de alto rendimiento (EnumSet & EnumMap) ---");
        EnumSet<MonedaDemo> monedasFuertes = EnumSet.of(MonedaDemo.USD, MonedaDemo.EUR);
        System.out.println("Monedas fuertes: " + monedasFuertes);

        EnumMap<Membresia, String> descripciones = new EnumMap<>(Membresia.class);
        descripciones.put(Membresia.ESTANDAR, "Sin beneficios extra");
        descripciones.put(Membresia.VIP, "15% de descuento en todas las compras");
        descripciones.put(Membresia.PLATINO, "25% de descuento + envíos gratis");
        descripciones.forEach((mem, desc) -> System.out.printf("%-10s -> %s%n", mem, desc));

        // 5. Singleton con Enum
        System.out.println("\n--- 5. Singleton con Enum ---");
        ConfiguracionApp config1 = ConfiguracionApp.INSTANCIA;
        ConfiguracionApp config2 = ConfiguracionApp.INSTANCIA;
        config1.setTema("LIGHT");
        System.out.println("Config2 ve el tema: " + config2.getTema());
        System.out.println("¿Ambas referencias son el mismo objeto en memoria?: " + (config1 == config2));
    }
}
