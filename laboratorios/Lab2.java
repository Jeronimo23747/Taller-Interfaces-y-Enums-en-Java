package laboratorios;

import java.util.Locale;

/**
 * Laboratorio 2: Pasarela de pagos
 * 
 * Conceptos aplicados:
 * - Diseño de interfaces con métodos abstractos y default (comportamiento por defecto).
 * - Polimorfismo: desacoplamiento entre quien consume el servicio (Caja) y las implementaciones concretas.
 * - Adición de nuevos métodos de pago sin alterar el código existente (Principio Open/Closed de SOLID).
 */
interface MetodoPago {
    String nombre();
    boolean pagar(double monto);

    default double comision(double monto) {
        return 0;
    }

    default double totalACobrar(double monto) {
        return monto + comision(monto);
    }
}

class TarjetaCredito implements MetodoPago {
    private double cupoDisponible;

    public TarjetaCredito(double cupo) {
        this.cupoDisponible = cupo;
    }

    @Override
    public String nombre() {
        return "Tarjeta de crédito";
    }

    @Override
    public double comision(double monto) {
        return monto * 0.03; // 3% de comisión
    }

    @Override
    public boolean pagar(double monto) {
        double total = totalACobrar(monto);
        if (total > cupoDisponible) {
            return false;
        }
        cupoDisponible -= total;
        return true;
    }

    public double getCupoDisponible() {
        return cupoDisponible;
    }
}

class BilleteraDigital implements MetodoPago {
    private double saldo;

    public BilleteraDigital(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String nombre() {
        return "Billetera digital";
    }

    @Override
    public boolean pagar(double monto) {
        if (monto > saldo) {
            return false;
        }
        saldo -= monto;
        return true;
    }

    public double getSaldo() {
        return saldo;
    }
}

class Efectivo implements MetodoPago {
    @Override
    public String nombre() {
        return "Efectivo";
    }

    @Override
    public boolean pagar(double monto) {
        return true; // Efectivo siempre aprueba
    }
}

/**
 * Quinto método de pago agregado para demostrar el principio Abierto/Cerrado (Open/Closed)
 * y la potencia del polimorfismo sin modificar la clase Caja.
 */
class Criptomoneda implements MetodoPago {
    private double saldoUSDT;
    private final double redFee = 1.0; // Tarifa fija por transacción en red

    public Criptomoneda(double saldoUSDT) {
        this.saldoUSDT = saldoUSDT;
    }

    @Override
    public String nombre() {
        return "Criptomoneda (USDT)";
    }

    @Override
    public double comision(double monto) {
        return redFee;
    }

    @Override
    public boolean pagar(double monto) {
        double total = totalACobrar(monto);
        if (total > saldoUSDT) {
            return false;
        }
        saldoUSDT -= total;
        return true;
    }
}

class Caja {
    /**
     * Cobra utilizando polimorfismo. La caja desconoce los detalles internos de cada
     * método de pago; únicamente interactúa con el contrato definido en MetodoPago.
     */
    public static void cobrar(MetodoPago m, double monto) {
        String resultado = m.pagar(monto) ? "APROBADO" : "RECHAZADO";
        // Usamos Locale es-CO para formato con puntos de miles (ej. $ 120.000)
        Locale esCO = Locale.forLanguageTag("es-CO");
        System.out.printf(esCO, "%-20s $%,10.0f comisión $%,7.0f %s%n",
                m.nombre(), monto, m.comision(monto), resultado);
    }
}

public class Lab2 {
    public static void main(String[] args) {
        // Pruebas requeridas en la salida esperada del Laboratorio 2
        Caja.cobrar(new TarjetaCredito(500_000), 120_000);
        Caja.cobrar(new BilleteraDigital(50_000), 80_000);
        Caja.cobrar(new Efectivo(), 35_000);
        Caja.cobrar(new TarjetaCredito(100_000), 99_000); // 99.000 + 2.970 comisión = 101.970 > 100.000 cupo

        // Demostración del paso 6: 5to método de pago (Criptomoneda) sin tocar Caja
        System.out.println("\n-- Demostración de extensibilidad (5to método de pago) --");
        Caja.cobrar(new Criptomoneda(100), 50);
        Caja.cobrar(new Criptomoneda(20), 50);
    }
}
