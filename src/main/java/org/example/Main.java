package org.example;

import org.example.model.*;
import org.example.service.FeriaEmpresarial;
import org.example.utils.InputUtils;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        FeriaEmpresarial feria = new FeriaEmpresarial();
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                mostrarMenu();
                String op = sc.nextLine().trim();
                switch (op) {
                    case "1" -> registrarEmpresa(sc, feria);
                    case "2" -> listarEmpresas(feria);
                    case "3" -> crearStand(sc, feria);
                    case "4" -> listarStands(feria);
                    case "5" -> asignarStand(sc, feria);
                    case "6" -> desasignarStand(sc, feria);
                    case "7" -> registrarVisitante(sc, feria);
                    case "8" -> listarVisitantes(feria);
                    case "9" -> registrarComentario(sc, feria);
                    case "10" -> verComentariosYPromedio(sc, feria);
                    case "0" -> { System.out.println("Saliendo..."); return; }
                    default -> System.out.println("Opción inválida. Usa 0-10.");
                }
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n===== Feria Empresarial =====");
        System.out.println("1) Registrar empresa");
        System.out.println("2) Listar empresas");
        System.out.println("3) Crear stand");
        System.out.println("4) Listar stands (todos/disponibles/ocupados)");
        System.out.println("5) Asignar stand a empresa");
        System.out.println("6) Desasignar stand");
        System.out.println("7) Registrar visitante");
        System.out.println("8) Listar visitantes");
        System.out.println("9) Registrar comentario a un stand");
        System.out.println("10) Ver comentarios y promedio de un stand");
        System.out.println("0) Salir");
        System.out.print("Elige opción: ");
    }

    // --- Acciones de menú ---

    private static void registrarEmpresa(Scanner sc, FeriaEmpresarial feria) {
        String nombre = InputUtils.leerNoVacio(sc, "Nombre de empresa: ");
        String sector = InputUtils.leerNoVacio(sc, "Sector: ");
        String correo = InputUtils.leerNoVacio(sc, "Correo: ");
        try {
            feria.registrarEmpresa(new Empresa(nombre, sector, correo));
            System.out.println("Empresa registrada.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarEmpresas(FeriaEmpresarial feria) {
        System.out.println("\nEmpresas:");
        feria.listarEmpresas().forEach(System.out::println);
    }

    private static void crearStand(Scanner sc, FeriaEmpresarial feria) {
        String numero = InputUtils.leerNoVacio(sc, "Número de stand: ");
        String ubicacion = InputUtils.leerNoVacio(sc, "Ubicación: ");
        System.out.print("Tamaño (1) PEQUENO  (2) MEDIANO  (3) GRANDE: ");
        String t = sc.nextLine().trim();
        StandSize size = switch (t) {
            case "1" -> StandSize.PEQUENO;
            case "2" -> StandSize.MEDIANO;
            case "3" -> StandSize.GRANDE;
            default -> null;
        };
        if (size == null) { System.out.println("Tamaño inválido."); return; }
        try {
            feria.crearStand(numero, ubicacion, size);
            System.out.println("Stand creado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarStands(FeriaEmpresarial feria) {
        System.out.println("\nStands (todos):");
        feria.listarStands().forEach(System.out::println);
        System.out.println("\nDisponibles:");
        feria.listarStandsDisponibles().forEach(System.out::println);
        System.out.println("\nOcupados:");
        feria.listarStandsOcupados().forEach(System.out::println);
    }

    private static void asignarStand(Scanner sc, FeriaEmpresarial feria) {
        String numero = InputUtils.leerNoVacio(sc, "Número de stand: ");
        String empresa = InputUtils.leerNoVacio(sc, "Nombre de empresa: ");
        try {
            feria.asignarStandAEmpresa(numero, empresa);
            System.out.println("Stand asignado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void desasignarStand(Scanner sc, FeriaEmpresarial feria) {
        String numero = InputUtils.leerNoVacio(sc, "Número de stand: ");
        try {
            feria.desasignarStand(numero);
            System.out.println("Stand desasignado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void registrarVisitante(Scanner sc, FeriaEmpresarial feria) {
        String id = InputUtils.leerNoVacio(sc, "ID visitante: ");
        String nombre = InputUtils.leerNoVacio(sc, "Nombre: ");
        String correo = InputUtils.leerNoVacio(sc, "Correo: ");
        try {
            feria.registrarVisitante(new Visitante(id, nombre, correo));
            System.out.println("Visitante registrado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarVisitantes(FeriaEmpresarial feria) {
        System.out.println("\nVisitantes:");
        feria.listarVisitantes().forEach(System.out::println);
    }

    private static void registrarComentario(Scanner sc, FeriaEmpresarial feria) {
        String id = InputUtils.leerNoVacio(sc, "ID visitante: ");
        String stand = InputUtils.leerNoVacio(sc, "Número de stand: ");
        int calificacion;
        try {
            calificacion = Integer.parseInt(InputUtils.leerNoVacio(sc, "Calificación (1..5): "));
        } catch (NumberFormatException nfe) {
            System.out.println("Calificación inválida. Debe ser un entero 1..5.");
            return;
        }
        String texto = InputUtils.leerNoVacio(sc, "Comentario: ");
        try {
            feria.registrarComentario(id, stand, calificacion, texto);
            System.out.println("Comentario registrado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void verComentariosYPromedio(Scanner sc, FeriaEmpresarial feria) {
        String stand = InputUtils.leerNoVacio(sc, "Número de stand: ");
        try {
            System.out.println("\nComentarios del stand " + stand + ":");
            feria.listarComentariosDeStand(stand)
                    .forEach(c -> System.out.println("- [" + c.fecha() + "] (" + c.calificacion() + ") " + c.texto() + " - visitante " + c.visitanteId()));
            var prom = feria.promedioCalificacionStand(stand);
            if (prom.isPresent()) {
                System.out.printf("Promedio: %.2f%n", prom.getAsDouble());
            } else {
                System.out.println("Sin calificaciones aún.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}