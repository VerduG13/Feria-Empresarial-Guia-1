package org.example.utils;

import java.util.Scanner;

/**
 * Utilidades simples para lectura segura por consola: enteros, enteros en rango y textos no vacíos.
 */
public class InputUtils {

    /**
     * Lee un número entero desde consola y no continúa hasta que la entrada sea válida.
     * @param sc scanner ya abierto para leer desde consola; prompt mensaje que se muestra antes de leer
     * @return el entero leído (consumiendo también el salto de línea)
     */
    public static int leerEntero(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int v = sc.nextInt();
                sc.nextLine();
                return v;
            } else {
                sc.nextLine();
                System.out.println("Entrada inválida. Debes digitar un número entero.");
            }
        }
    }

    /**
     * Lee una línea de texto no vacía (se reintenta si el usuario deja la entrada en blanco).
     * @param sc scanner ya abierto para leer desde consola; prompt mensaje que se muestra antes de leer
     * @return el texto leído, ya recortado (trim) y garantizado como no vacío
     */
    public static String leerNoVacio(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.println("Entrada inválida. No puede estar vacío.");
        }
    }

    /**
     * Lee un entero y valida que esté dentro del rango indicado (inclusive); si no, vuelve a pedirlo.
     * @param sc scanner ya abierto; prompt mensaje a mostrar; min valor mínimo permitido (inclusive); max valor máximo permitido (inclusive)
     * @return el entero leído dentro del rango [min, max]
     */
    public static int leerEnteroEnRango(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int v = leerEntero(sc, prompt);
            if (v >= min && v <= max) return v;
            System.out.printf("Entrada inválida. Debe estar entre %d y %d.%n", min, max);
        }
    }
}
