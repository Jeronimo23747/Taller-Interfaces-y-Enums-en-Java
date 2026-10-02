package laboratorios;

/**
 * Laboratorio 3: Semáforo inteligente
 * 
 * Conceptos aplicados:
 * - Enums con atributos final, constructores privados y métodos consultores (getters).
 * - Expresiones switch modernas (Java 14+) con sintaxis de flecha (->) y exhaustividad del compilador.
 * - Métodos de instancia para transiciones de estado (máquina de estados dentro del enum).
 * - Métodos estáticos auxiliares operando sobre values().
 */
enum Semaforo {
    VERDE(30, "Avance"),
    AMARILLO(5, "Precaución"),
    ROJO(35, "Pare");

    private final int segundos;
    private final String accion;

    Semaforo(int segundos, String accion) {
        this.segundos = segundos;
        this.accion = accion;
    }

    public int getSegundos() {
        return segundos;
    }

    public String getAccion() {
        return accion;
    }

    /**
     * Transición al siguiente estado del semáforo.
     * La expresión switch garantiza exhaustividad: si se agrega un nuevo estado,
     * el compilador exige su tratamiento aquí.
     */
    public Semaforo siguiente() {
        return switch (this) {
            case VERDE -> AMARILLO;
            case AMARILLO -> ROJO;
            case ROJO -> VERDE;
        };
    }

    /**
     * Calcula la suma de segundos de todos los estados del ciclo.
     */
    public static int duracionCiclo() {
        int total = 0;
        for (Semaforo s : values()) {
            total += s.segundos;
        }
        return total;
    }
}

/**
 * Reto extra (paso 6): Semáforo ampliado con modo intermitente
 * para demostrar el chequeo de exhaustividad del compilador en el switch.
 */
enum SemaforoConIntermitente {
    VERDE(30, "Avance"),
    AMARILLO(5, "Precaución"),
    ROJO(35, "Pare"),
    AMARILLO_INTERMITENTE(2, "Precaución / Horario nocturno");

    private final int segundos;
    private final String accion;

    SemaforoConIntermitente(int segundos, String accion) {
        this.segundos = segundos;
        this.accion = accion;
    }

    public int getSegundos() {
        return segundos;
    }

    public String getAccion() {
        return accion;
    }

    public SemaforoConIntermitente siguiente() {
        return switch (this) {
            case VERDE -> AMARILLO;
            case AMARILLO -> ROJO;
            case ROJO -> VERDE;
            case AMARILLO_INTERMITENTE -> AMARILLO_INTERMITENTE;
        };
    }
}

public class Lab3 {
    public static void main(String[] args) {
        Semaforo luz = Semaforo.VERDE;

        for (int paso = 1; paso <= 4; paso++) {
            System.out.printf("Paso %d: %-8s %2d s → %s%n",
                    paso, luz, luz.getSegundos(), luz.getAccion());
            luz = luz.siguiente();
        }

        System.out.println("Duración del ciclo completo: " + Semaforo.duracionCiclo() + " s");

        // Demostración del reto extra
        System.out.println("\n-- Reto Extra: Modo intermitente --");
        SemaforoConIntermitente nocturno = SemaforoConIntermitente.AMARILLO_INTERMITENTE;
        System.out.printf("Modo especial: %s (%d s) → %s%n",
                nocturno, nocturno.getSegundos(), nocturno.getAccion());
    }
}
