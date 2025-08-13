package org.example;

import org.example.model.*;
import org.example.service.FeriaEmpresarial;
import org.example.utils.InputUtils;

import java.util.Scanner;

/**
 * Punto de entrada por consola para la Feria Empresarial: muestra un menú y delega en el servicio
 * las operaciones sobre empresas, stands, visitantes y comentarios.
 */
public class Main {

    /**
     * Arranca la aplicación y gestiona el bucle del menú por consola.
     * @param args argumentos de línea de comandos (no utilizados)
     */
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
                    case "11" -> editarVisitante(sc, feria);
                    case "12" -> eliminarVisitante(sc, feria);
                    case "13" -> System.out.println("\n" + feria.reporteEmpresasYStands());
                    case "14" -> System.out.println("\n" + feria.reporteVisitantesYStandsVisitados());
                    case "15" -> System.out.println("\n" + feria.reportePromedioPorStand());
                    case "0" -> { System.out.println("Saliendo..."); return; }
                    default -> System.out.println("Opción inválida. Usa 0-10.");
                }
            }
        }
    }

    /**
     * Imprime el menú principal en consola.
     */
    private static void mostrarMenu() {
        System.out.println("\n===== Feria Empresarial=====");
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
        System.out.println("11) Editar visitante");
        System.out.println("12) Eliminar visitante");
        System.out.println("13) Reporte: Empresas y Stands");
        System.out.println("14) Reporte: Visitantes y Stands visitados");
        System.out.println("15) Reporte: Promedio de calificación por Stand");
        System.out.println("0) Salir");
        System.out.print("Elige opción: ");
    }

    /**
     * Registra una nueva empresa solicitando datos por consola.
     * @param sc scanner de entrada por consola; feria servicio de negocio para registrar la empresa
     */
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

    /**
     * Edita una empresa existente pidiendo nuevos valores por consola.
     * @param sc scanner de entrada por consola; feria servicio de negocio para editar la empresa
     */
    private static void editarEmpresa(Scanner sc, FeriaEmpresarial feria) {
        String nombre = InputUtils.leerNoVacio(sc, "Nombre de empresa a editar: ");
        String nuevoSector = InputUtils.leerNoVacio(sc, "Nuevo sector (no vacío): ");
        String nuevoCorreo = InputUtils.leerNoVacio(sc, "Nuevo correo (no vacío): ");
        try {
            feria.editarEmpresa(nombre, nuevoSector, nuevoCorreo);
            System.out.println("Empresa actualizada.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina una empresa por su nombre y libera sus stands.
     * @param sc scanner de entrada por consola; feria servicio de negocio para eliminar la empresa
     */
    private static void eliminarEmpresa(Scanner sc, FeriaEmpresarial feria) {
        String nombre = InputUtils.leerNoVacio(sc, "Nombre de empresa a eliminar: ");
        try {
            feria.eliminarEmpresa(nombre);
            System.out.println("Empresa eliminada (y stands liberados si aplicaba).");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Lista las empresas registradas.
     * @param feria servicio de negocio del cual se lee la lista
     */
    private static void listarEmpresas(FeriaEmpresarial feria) {
        System.out.println("\nEmpresas:");
        feria.listarEmpresas().forEach(System.out::println);
    }

    /**
     * Crea un nuevo stand pidiendo datos por consola (número, ubicación y tamaño).
     * @param sc scanner de entrada por consola; feria servicio de negocio para crear el stand
     */
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

    /**
     * Lista todos los stands, y también los disponibles y los ocupados.
     * @param feria servicio de negocio del cual se leen los stands
     */
    private static void listarStands(FeriaEmpresarial feria) {
        System.out.println("\nStands (todos):");
        feria.listarStands().forEach(System.out::println);
        System.out.println("\nDisponibles:");
        feria.listarStandsDisponibles().forEach(System.out::println);
        System.out.println("\nOcupados:");
        feria.listarStandsOcupados().forEach(System.out::println);
    }

    /**
     * Asigna un stand a una empresa existente.
     * @param sc scanner de entrada por consola; feria servicio de negocio para realizar la asignación
     */
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

    /**
     * Desasigna (libera) un stand por su número.
     * @param sc scanner de entrada por consola; feria servicio de negocio para realizar la desasignación
     */
    private static void desasignarStand(Scanner sc, FeriaEmpresarial feria) {
        String numero = InputUtils.leerNoVacio(sc, "Número de stand: ");
        try {
            feria.desasignarStand(numero);
            System.out.println("Stand desasignado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Registra un nuevo visitante pidiendo identificación, nombre y correo.
     * @param sc scanner de entrada por consola; feria servicio de negocio para registrar al visitante
     */
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

    /**
     * Edita los datos de un visitante (nombre y correo).
     * @param sc scanner de entrada por consola; feria servicio de negocio para editar al visitante
     */
    private static void editarVisitante(Scanner sc, FeriaEmpresarial feria) {
        String id = InputUtils.leerNoVacio(sc, "ID de visitante a editar: ");
        String nuevoNombre = InputUtils.leerNoVacio(sc, "Nuevo nombre (no vacío): ");
        String nuevoCorreo = InputUtils.leerNoVacio(sc, "Nuevo correo (no vacío): ");
        try {
            feria.editarVisitante(id, nuevoNombre, nuevoCorreo);
            System.out.println("Visitante actualizado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina un visitante por su identificación y borra sus comentarios.
     * @param sc scanner de entrada por consola; feria servicio de negocio para eliminar al visitante
     */
    private static void eliminarVisitante(Scanner sc, FeriaEmpresarial feria) {
        String id = InputUtils.leerNoVacio(sc, "ID de visitante a eliminar: ");
        try {
            feria.eliminarVisitante(id);
            System.out.println("Visitante eliminado (y sus comentarios eliminados).");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Lista los visitantes registrados.
     * @param feria servicio de negocio del cual se lee la lista
     */
    private static void listarVisitantes(FeriaEmpresarial feria) {
        System.out.println("\nVisitantes:");
        feria.listarVisitantes().forEach(System.out::println);
    }

    /**
     * Registra un comentario con calificación (1..5) sobre un stand por parte de un visitante.
     * @param sc scanner de entrada por consola; feria servicio de negocio para registrar el comentario
     */
    private static void registrarComentario(Scanner sc, FeriaEmpresarial feria) {
        String id = InputUtils.leerNoVacio(sc, "ID visitante: ");
        String stand = InputUtils.leerNoVacio(sc, "Número de stand: ");
        int calificacion = InputUtils.leerEnteroEnRango(sc, "Calificación (1..5): ", 1, 5);
        String texto = InputUtils.leerNoVacio(sc, "Comentario: ");
        try {
            feria.registrarComentario(id, stand, calificacion, texto);
            System.out.println("Comentario registrado.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Muestra los comentarios y el promedio de calificación de un stand.
     * @param sc scanner de entrada por consola; feria servicio de negocio para obtener datos del stand
     */
    private static void verComentariosYPromedio(Scanner sc, FeriaEmpresarial feria) {
        String stand = InputUtils.leerNoVacio(sc, "Número de stand: ");
        try {
            System.out.println("\nComentarios del stand " + stand + ":");
            var comentarios = feria.listarComentariosDeStand(stand);
            if (comentarios.isEmpty()) {
                System.out.println("(sin comentarios)");
            } else {
                // Resolver nombre del visitante al mostrar
                for (var c : comentarios) {
                    String nombreVisitante = feria.listarVisitantes().stream()
                            .filter(v -> v.getIdentificacion().equalsIgnoreCase(c.visitanteId()))
                            .map(Visitante::getNombre)
                            .findFirst()
                            .orElse("(no registrado)");
                    System.out.println("- [" + c.fecha() + "] (" + c.calificacion() + ") "
                            + c.texto() + " - " + nombreVisitante + " [" + c.visitanteId() + "]");
                }
            }
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
