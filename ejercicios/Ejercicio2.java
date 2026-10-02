package ejercicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Ejercicio 2 (Reto 2 de la guía - Sección 3.5): Baraja Española
 * 
 * Conceptos aplicados:
 * - Creación de Enums con atributos y constructores: Palo y Valor.
 * - Iteración sobre constantes de un enum con values().
 * - Records inmutables: Carta(Valor, Palo).
 * - Algoritmos de utilidades de colecciones: Collections.shuffle.
 */
enum Palo {
    OROS, COPAS, ESPADAS, BASTOS
}

enum Valor {
    AS(1, "As"),
    DOS(2, "Dos"),
    TRES(3, "Tres"),
    CUATRO(4, "Cuatro"),
    CINCO(5, "Cinco"),
    SEIS(6, "Seis"),
    SIETE(7, "Siete"),
    OCHO(8, "Ocho"),
    NUEVE(9, "Nueve"),
    SOTA(10, "Sota"),
    CABALLO(11, "Caballo"),
    REY(12, "Rey");

    private final int numero;
    private final String nombre;

    Valor(int numero, String nombre) {
        this.numero = numero;
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public String getNombre() {
        return nombre;
    }
}

record Carta(Valor valor, Palo palo) {
    @Override
    public String toString() {
        return valor.getNombre() + " de " + palo;
    }
}

public class Ejercicio2 {
    public static void main(String[] args) {
        System.out.println("=== Ejercicio 2: Baraja Española Completa (48 Cartas) ===");

        List<Carta> baraja = new ArrayList<>();

        // Generar baraja completa con dos ciclos for anidados sobre values()
        for (Palo p : Palo.values()) {
            for (Valor v : Valor.values()) {
                baraja.add(new Carta(v, p));
            }
        }

        System.out.println("Total de cartas generadas: " + baraja.size());
        System.out.println("Primeras 4 cartas ordenadas:");
        for (int i = 0; i < 4; i++) {
            System.out.println("  - " + baraja.get(i));
        }

        // Barajar la baraja
        Collections.shuffle(baraja);
        System.out.println("\nBaraja mezclada con éxito.");

        // Repartir una mano de 5 cartas
        System.out.println("Mano de 5 cartas repartidas al azar:");
        for (int i = 0; i < 5; i++) {
            System.out.println("  " + (i + 1) + ". " + baraja.get(i));
        }
    }
}
