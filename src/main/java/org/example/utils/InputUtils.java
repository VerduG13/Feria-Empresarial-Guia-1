package org.example.utils;

import java.util.Scanner;

public class InputUtils {
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

    public static String leerNoVacio(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isBlank()) return s;
            System.out.println("Entrada inválida. No puede estar vacío.");
        }
    }
}
