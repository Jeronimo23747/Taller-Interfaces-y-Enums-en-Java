package laboratorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Laboratorio 4: Cafetería "El Algoritmo" (Proyecto Integrador)
 * 
 * Conceptos aplicados:
 * - Enums para conjuntos cerrados: Bebida, Tamano, EstadoPedido.
 * - Interfaces funcionales y lambdas: Promocion con generadores estáticos (ninguna, porcentaje, fijaDesde).
 * - Records para transportar datos inmutables con métodos propios: Item(Bebida, Tamano, cantidad).
 * - Máquina de estados controlada con transiciones seguras en EstadoPedido.
 * - Clases de negocio con estado mutable, métodos encadenables (fluent API) y Stream API para agregaciones.
 */

enum Bebida {
    TINTO(2_500),
    CAPUCHINO(6_000),
    CHOCOLATE(5_000),
    AROMATICA(3_000);

    private final double precioBase;

    Bebida(double precioBase) {
        this.precioBase = precioBase;
    }

    public double getPrecioBase() {
        return precioBase;
    }
}

enum Tamano {
    PEQUENO(0),
    MEDIANO(1_000),
    GRANDE(2_000);

    private final double recargo;

    Tamano(double recargo) {
        this.recargo = recargo;
    }

    public double getRecargo() {
        return recargo;
    }
}

enum EstadoPedido {
    RECIBIDO,
    EN_PREPARACION,
    LISTO,
    ENTREGADO,
    CANCELADO;

    public boolean esFinal() {
        return this == ENTREGADO || this == CANCELADO;
    }

    public EstadoPedido siguiente() {
        return switch (this) {
            case RECIBIDO -> EN_PREPARACION;
            case EN_PREPARACION -> LISTO;
            case LISTO -> ENTREGADO;
            case ENTREGADO, CANCELADO ->
                throw new IllegalStateException("El pedido ya terminó: " + this);
        };
    }

    public boolean puedeCancelarse() {
        return this == RECIBIDO || this == EN_PREPARACION;
    }

    public EstadoPedido cancelar() {
        if (!puedeCancelarse()) {
            throw new IllegalStateException("No se puede cancelar en estado " + this);
        }
        return CANCELADO;
    }
}

@FunctionalInterface
interface Promocion {
    double descuento(double subtotal);

    static Promocion ninguna() {
        return subtotal -> 0;
    }

    static Promocion porcentaje(double pct) {
        return subtotal -> subtotal * pct / 100;
    }

    static Promocion fijaDesde(double minimo, double valor) {
        return subtotal -> subtotal >= minimo ? valor : 0;
    }
}

record Item(Bebida bebida, Tamano tamano, int cantidad) {
    public double subtotal() {
        return (bebida.getPrecioBase() + tamano.getRecargo()) * cantidad;
    }
}

class Pedido {
    private final String cliente;
    private final List<Item> items = new ArrayList<>();
    private EstadoPedido estado = EstadoPedido.RECIBIDO;
    private Promocion promocion = Promocion.ninguna();

    public Pedido(String cliente) {
        this.cliente = cliente;
    }

    public Pedido agregar(Bebida bebida, Tamano tamano, int cantidad) {
        items.add(new Item(bebida, tamano, cantidad));
        return this; // Permite encadenamiento fluido (method chaining)
    }

    public void aplicarPromocion(Promocion p) {
        this.promocion = p;
    }

    public void avanzar() {
        estado = estado.siguiente();
    }

    public void cancelar() {
        estado = estado.cancelar();
    }

    public double subtotal() {
        return items.stream().mapToDouble(Item::subtotal).sum();
    }

    public double total() {
        return subtotal() - promocion.descuento(subtotal());
    }

    public void imprimirTicket() {
        Locale esCO = Locale.forLanguageTag("es-CO");
        System.out.println(" = Cafetería El Algoritmo =");
        System.out.println("Cliente: " + cliente + " | Estado: " + estado);
        for (Item i : items) {
            System.out.printf(esCO, "%d x %-10s %-8s $%,8.0f%n",
                    i.cantidad(), i.bebida(), i.tamano(), i.subtotal());
        }
        System.out.printf(esCO, "%-24s$%,8.0f%n", "Subtotal", subtotal());
        System.out.printf(esCO, "%-24s$%,8.0f%n", "Descuento", subtotal() - total());
        System.out.printf(esCO, "%-24s$%,8.0f%n", "TOTAL", total());
    }
}

public class Lab4 {
    public static void main(String[] args) {
        Pedido pedido = new Pedido("Camila")
                .agregar(Bebida.CAPUCHINO, Tamano.GRANDE, 2)
                .agregar(Bebida.TINTO, Tamano.PEQUENO, 1)
                .agregar(Bebida.CHOCOLATE, Tamano.MEDIANO, 1);

        pedido.aplicarPromocion(Promocion.porcentaje(10));
        pedido.avanzar(); // RECIBIDO -> EN_PREPARACION
        pedido.avanzar(); // EN_PREPARACION -> LISTO
        pedido.imprimirTicket();

        try {
            pedido.cancelar();
        } catch (IllegalStateException e) {
            System.out.println("Aviso: " + e.getMessage());
        }
    }
}
