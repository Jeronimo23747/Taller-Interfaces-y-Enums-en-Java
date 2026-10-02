package laboratorios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Laboratorio 1: Mi playlist ordenada
 * 
 * Conceptos aplicados:
 * - Implementación de la interfaz Comparable<T> (orden natural por título).
 * - Uso de la interfaz funcional Comparator<T> con métodos estáticos y por defecto (comparingInt, reversed).
 * - Uso de la interfaz funcional Predicate<T> y procesamiento funcional con la API Stream.
 */
class Cancion implements Comparable<Cancion> {
    private final String titulo;
    private final String artista;
    private final int duracionSeg;

    public Cancion(String titulo, String artista, int duracionSeg) {
        this.titulo = titulo;
        this.artista = artista;
        this.duracionSeg = duracionSeg;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getDuracionSeg() {
        return duracionSeg;
    }

    /**
     * Formatea la duración en formato "m:ss".
     */
    public String duracion() {
        return String.format("%d:%02d", duracionSeg / 60, duracionSeg % 60);
    }

    /**
     * Orden natural alfabético por título.
     */
    @Override
    public int compareTo(Cancion otra) {
        return this.titulo.compareTo(otra.titulo);
    }

    @Override
    public String toString() {
        return titulo + " (" + duracion() + ")";
    }
}

public class Lab1 {
    public static void main(String[] args) {
        List<Cancion> playlist = new ArrayList<>(List.of(
            new Cancion("Tusa", "Karol G", 200),
            new Cancion("La Bicicleta", "Carlos Vives", 227),
            new Cancion("Despacito", "Luis Fonsi", 228),
            new Cancion("Bailando", "Enrique Iglesias", 243)
        ));

        // 1. Orden natural por título usando Comparable
        Collections.sort(playlist);
        System.out.println("Orden natural (título):");
        for (Cancion c : playlist) {
            System.out.printf("  %-14s %-17s %s%n", c.getTitulo(), c.getArtista(), c.duracion());
        }

        // 2. Orden de mayor a menor duración usando Comparator
        playlist.sort(Comparator.comparingInt(Cancion::getDuracionSeg).reversed());
        System.out.println("De la más larga a la más corta:");
        playlist.forEach(c -> System.out.println("  " + c.getTitulo() + " (" + c.duracion() + ")"));

        // 3. Filtrar canciones de más de 200 segundos con Predicate
        Predicate<Cancion> esLarga = c -> c.getDuracionSeg() > 200;
        List<String> largas = playlist.stream()
                .filter(esLarga)
                .map(c -> c.getTitulo().toUpperCase())
                .toList();

        System.out.println("Canciones largas: " + largas);
    }
}
