package ejemplos;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Ejemplos Prácticos de Interfaces (Parte I de la Guía)
 * 
 * Incluye demostraciones didácticas de:
 * 1. Contratos básicos y polimorfismo dinámico.
 * 2. Implementación múltiple de capacidades.
 * 3. Métodos default, static y private (Java 8/9+).
 * 4. Interfaces funcionales, lambdas y referencias a métodos.
 * 5. Interfaces selladas (sealed) y pattern matching (Java 17/21+).
 */

// 1. Contrato básico
interface Sonoro {
    String hacerSonido();
}

class Perro implements Sonoro {
    @Override
    public String hacerSonido() {
        return "¡Guau!";
    }
}

class Gato implements Sonoro {
    @Override
    public String hacerSonido() {
        return "¡Miau!";
    }
}

class Vaca implements Sonoro {
    @Override
    public String hacerSonido() {
        return "¡Muuu!";
    }
}

// 2. Implementación múltiple
interface Volador {
    void volar();
}

interface Nadador {
    void nadar();
}

class Pato implements Volador, Nadador {
    @Override
    public void volar() {
        System.out.println("Pato: aleteo sobre el lago");
    }

    @Override
    public void nadar() {
        System.out.println("Pato: remo con mis patas");
    }
}

class Avion implements Volador {
    @Override
    public void volar() {
        System.out.println("Avión: despegue con turbinas a 900 km/h");
    }
}

// 3. Métodos default, static y private
interface FormateadorTexto {
    String formatear(String texto);

    default String conMarco(String texto) {
        String contenido = "| " + formatear(texto) + " |";
        String borde = linea(contenido.length());
        return borde + "\n" + contenido + "\n" + borde;
    }

    static String limpiar(String texto) {
        return texto.trim().replaceAll("\\s+", " ");
    }

    private String linea(int largo) {
        return "+" + "-".repeat(largo - 2) + "+";
    }
}

class MayusculasFormateador implements FormateadorTexto {
    @Override
    public String formatear(String texto) {
        return texto.toUpperCase();
    }
}

// 4. Interfaz funcional personalizada
@FunctionalInterface
interface OperacionMatematica {
    int aplicar(int a, int b);
}

// 5. Jerarquía sellada (Java 17/21+)
sealed interface Forma permits CirculoForma, RectanguloForma {}

record CirculoForma(double radio) implements Forma {}
record RectanguloForma(double base, double altura) implements Forma {}

public class DemoInterfaces {

    static void hacerDespegar(Volador v) {
        v.volar();
    }

    static double calcularArea(Forma forma) {
        return switch (forma) {
            case CirculoForma c -> Math.PI * c.radio() * c.radio();
            case RectanguloForma(double b, double h) -> b * h;
        };
    }

    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("   DEMOSTRACIÓN COMPLETA: INTERFACES EN JAVA   ");
        System.out.println("==============================================\n");

        // 1. Polimorfismo básico
        System.out.println("--- 1. Polimorfismo y despacho dinámico ---");
        List<Sonoro> animales = List.of(new Perro(), new Gato(), new Vaca());
        for (Sonoro s : animales) {
            System.out.printf("%-6s dice %s%n", s.getClass().getSimpleName(), s.hacerSonido());
        }

        // 2. Múltiples interfaces
        System.out.println("\n--- 2. Implementación múltiple de capacidades ---");
        Pato lucas = new Pato();
        lucas.nadar();
        hacerDespegar(lucas);
        hacerDespegar(new Avion());

        // 3. Métodos default, static y private
        System.out.println("\n--- 3. Métodos default, static y private ---");
        String textoLimpio = FormateadorTexto.limpiar("   programación orientada a objetos en java   ");
        FormateadorTexto formateador = new MayusculasFormateador();
        System.out.println(formateador.conMarco(textoLimpio));

        // 4. Lambdas e interfaces funcionales del JDK
        System.out.println("\n--- 4. Interfaces funcionales y Lambdas ---");
        OperacionMatematica suma = (a, b) -> a + b;
        OperacionMatematica multiplicacion = (a, b) -> a * b;
        System.out.println("Suma (15 + 27): " + suma.aplicar(15, 27));
        System.out.println("Multiplicación (6 x 7): " + multiplicacion.aplicar(6, 7));

        Predicate<String> esLarga = s -> s.length() > 5;
        Function<String, Integer> contar = String::length;
        Consumer<String> imprimir = s -> System.out.println("Procesado: " + s);

        List<String> lenguajes = List.of("Java", "Kotlin", "TypeScript", "Go");
        lenguajes.stream().filter(esLarga).map(s -> s + " (" + contar.apply(s) + " letras)").forEach(imprimir);

        // 5. Jerarquía sellada y switch moderno
        System.out.println("\n--- 5. Interfaces selladas y Pattern Matching ---");
        Forma f1 = new CirculoForma(3.0);
        Forma f2 = new RectanguloForma(4.0, 5.0);
        System.out.printf("Área Círculo: %.2f%n", calcularArea(f1));
        System.out.printf("Área Rectángulo: %.2f%n", calcularArea(f2));
    }
}
