package ejercicios;

import java.util.EnumSet;

/**
 * Ejercicio 5 (Reto 5 de la guía - Sección 3.5): Permisos con EnumSet
 * 
 * Conceptos aplicados:
 * - Uso eficiente de memoria y alta velocidad con EnumSet.
 * - Asociación de conjuntos de enums como atributo inmutable en otro enum (Rol).
 * - Verificación de pertenencia y control de acceso basado en roles (RBAC).
 */
enum Permiso {
    LEER, ESCRIBIR, BORRAR, ADMINISTRAR
}

enum Rol {
    INVITADO(EnumSet.of(Permiso.LEER)),
    USUARIO(EnumSet.of(Permiso.LEER, Permiso.ESCRIBIR)),
    MODERADOR(EnumSet.of(Permiso.LEER, Permiso.ESCRIBIR, Permiso.BORRAR)),
    ADMINISTRADOR(EnumSet.allOf(Permiso.class));

    private final EnumSet<Permiso> permisos;

    Rol(EnumSet<Permiso> permisos) {
        this.permisos = permisos;
    }

    public boolean puede(Permiso p) {
        return permisos.contains(p);
    }

    public EnumSet<Permiso> getPermisos() {
        return EnumSet.copyOf(permisos);
    }
}

public class Ejercicio5 {
    public static void main(String[] args) {
        System.out.println("=== Ejercicio 5: Sistema de Roles y Permisos con EnumSet ===");

        for (Rol rol : Rol.values()) {
            System.out.printf("Rol: %-15s Permisos: %s%n", rol, rol.getPermisos());
        }

        System.out.println("\nVerificaciones de seguridad:");
        System.out.println("¿INVITADO puede LEER?: " + Rol.INVITADO.puede(Permiso.LEER));
        System.out.println("¿INVITADO puede BORRAR?: " + Rol.INVITADO.puede(Permiso.BORRAR));
        System.out.println("¿MODERADOR puede BORRAR?: " + Rol.MODERADOR.puede(Permiso.BORRAR));
        System.out.println("¿ADMINISTRADOR puede ADMINISTRAR?: " + Rol.ADMINISTRADOR.puede(Permiso.ADMINISTRAR));
    }
}
