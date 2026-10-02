package ejercicios;

import java.util.function.DoubleBinaryOperator;

/**
 * Ejercicio 3 (Reto 3 de la guía - Sección 3.5): Calculadora con Lambdas en el Enum
 * 
 * Conceptos aplicados:
 * - Atributo de tipo interfaz funcional (DoubleBinaryOperator de java.util.function).
 * - Lambdas pasadas como argumentos al constructor de cada constante del enum.
 * - Operaciones adicionales: POTENCIA (Math.pow) y MODULO (operador %).
 */
enum OperadorFuncional {
    SUMA("+", (a, b) -> a + b),
    RESTA("-", (a, b) -> a - b),
    MULTIPLICACION("×", (a, b) -> a * b),
    DIVISION("÷", (a, b) -> {
        if (b == 0) throw new ArithmeticException("división por cero");
        return a / b;
    }),
    POTENCIA("^", Math::pow),
    MODULO("%", (a, b) -> {
        if (b == 0) throw new ArithmeticException("módulo por cero");
        return a % b;
    });

    private final String simbolo;
    private final DoubleBinaryOperator operacion;

    OperadorFuncional(String simbolo, DoubleBinaryOperator operacion) {
        this.simbolo = simbolo;
        this.operacion = operacion;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public double aplicar(double a, double b) {
        return operacion.applyAsDouble(a, b);
    }
}

public class Ejercicio3 {
    public static void main(String[] args) {
        System.out.println("=== Ejercicio 3: Calculadora con Lambdas en Enum ===");
        double x = 12.0;
        double y = 4.0;

        for (OperadorFuncional op : OperadorFuncional.values()) {
            System.out.printf("%.1f %s %.1f = %.2f%n", x, op.getSimbolo(), y, op.aplicar(x, y));
        }

        try {
            OperadorFuncional.DIVISION.aplicar(10, 0);
        } catch (ArithmeticException e) {
            System.out.println("Validación: " + e.getMessage());
        }
    }
}
