package ejercicios;

/**
 * Ejercicio 4 (Reto 4 de la guía - Sección 3.5): Promociones Combinables
 * 
 * Conceptos aplicados:
 * - Métodos default en interfaces funcionales para composición de comportamiento.
 * - Composición de funciones tipo 'andThen' / 'combinar'.
 * - Control de reglas de negocio: el descuento total nunca puede exceder el subtotal.
 */
@FunctionalInterface
interface PromocionCombinable {
    double descuento(double subtotal);

    default PromocionCombinable y(PromocionCombinable otra) {
        return subtotal -> {
            double d1 = this.descuento(subtotal);
            double d2 = otra.descuento(subtotal);
            return Math.min(subtotal, d1 + d2);
        };
    }

    static PromocionCombinable porcentaje(double pct) {
        return subtotal -> subtotal * pct / 100;
    }

    static PromocionCombinable fijaDesde(double minimo, double valor) {
        return subtotal -> subtotal >= minimo ? valor : 0;
    }
}

public class Ejercicio4 {
    public static void main(String[] args) {
        System.out.println("=== Ejercicio 4: Promociones Combinables ===");

        PromocionCombinable promoCompuesta = PromocionCombinable.porcentaje(10)
                .y(PromocionCombinable.fijaDesde(20_000, 1_000));

        double subtotal1 = 25_000;
        double desc1 = promoCompuesta.descuento(subtotal1);
        System.out.printf("Subtotal: $%,.0f -> Descuento (10%% + $1.000): $%,.0f -> Total: $%,.0f%n",
                subtotal1, desc1, subtotal1 - desc1);

        double subtotal2 = 15_000; // No aplica la fija porque es menor a 20.000
        double desc2 = promoCompuesta.descuento(subtotal2);
        System.out.printf("Subtotal: $%,.0f -> Descuento (10%%): $%,.0f -> Total: $%,.0f%n",
                subtotal2, desc2, subtotal2 - desc2);
    }
}
