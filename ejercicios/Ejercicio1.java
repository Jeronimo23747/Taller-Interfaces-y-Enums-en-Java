package ejercicios;

import java.util.List;

/**
 * Ejercicio 1 (Reto 1 de la guía - Sección 3.5): Figuras con Perímetro
 * 
 * Conceptos aplicados:
 * - Interfaces selladas (sealed interface) y cláusula 'permits': control estricto de subtipos.
 * - Records (Java 16+) como modelos inmutables compactos.
 * - Expresiones switch con coincidencia de patrones (pattern matching para switch, Java 21+).
 * - Exhaustividad del compilador: no se necesita cláusula default porque el compilador
 *   conoce todos los subtipos permitidos en tiempo de compilación.
 */
sealed interface Figura permits Circulo, Rectangulo, Triangulo, Cuadrado {
}

record Circulo(double radio) implements Figura {
}

record Rectangulo(double base, double altura) implements Figura {
}

record Triangulo(double base, double altura, double lado1, double lado2, double lado3) implements Figura {
}

record Cuadrado(double lado) implements Figura {
}

public class Ejercicio1 {

    /**
     * Calcula el área usando switch de patrones con exhaustividad.
     */
    public static double area(Figura f) {
        return switch (f) {
            case Circulo c -> Math.PI * c.radio() * c.radio();
            case Rectangulo(double b, double h) -> b * h; // Desestructuración de record
            case Triangulo t -> t.base() * t.altura() / 2;
            case Cuadrado c -> c.lado() * c.lado();
        };
    }

    /**
     * Calcula el perímetro usando switch con patrones de record.
     */
    public static double perimetro(Figura f) {
        return switch (f) {
            case Circulo c -> 2 * Math.PI * c.radio();
            case Rectangulo(double b, double h) -> 2 * (b + h);
            case Triangulo(double b, double h, double l1, double l2, double l3) -> l1 + l2 + l3;
            case Cuadrado c -> 4 * c.lado();
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Ejercicio 1: Figuras Geométricas Selladas y Pattern Matching ===");

        List<Figura> figuras = List.of(
            new Circulo(5),
            new Rectangulo(4, 6),
            new Triangulo(10, 3, 10, 5, 8),
            new Cuadrado(4)
        );

        for (Figura f : figuras) {
            System.out.printf("%-45s | Área: %6.2f | Perímetro: %6.2f%n",
                    f, area(f), perimetro(f));
        }
    }
}
