package org.example.service;

import org.example.model.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Gestor para la Feria Empresarial: orquesta operaciones sobre empresas, stands, visitantes y comentarios,
 * manteniendo los datos en listas en memoria para un flujo simple y verificable por consola.
 */
public class FeriaEmpresarial {
    private final List<Empresa> empresas = new ArrayList<>();
    private final List<Stand> stands = new ArrayList<>();
    private final List<Visitante> visitantes = new ArrayList<>();

    /**
     * Agrega una nueva empresa a la Feria Empresarial garantizando nombre único.
     * @param e empresa a registrar (con nombre, sector y correo ya validados)
     * @throws IllegalArgumentException si ya existe una empresa con el mismo nombre
     */
    public void registrarEmpresa(Empresa e) {
        empresas.stream()
                .filter(x -> x.getNombre().equalsIgnoreCase(e.getNombre()))
                .findAny()
                .ifPresent(x -> { throw new IllegalArgumentException("Ya existe empresa: " + e.getNombre()); });
        empresas.add(e);
    }

    /**
     * Devuelve una copia inmutable de todas las empresas registradas.
     * @return lista inmutable de empresas
     */
    public List<Empresa> listarEmpresas() { return List.copyOf(empresas); }

    /**
     * Edita los datos de una empresa existente (sector y/o correo).
     * @param nombre nombre de la empresa a editar; nuevoSector nuevo valor de sector (si no es vacío); nuevoCorreo nuevo valor de correo (si no es vacío)
     * @throws NoSuchElementException si no existe una empresa con el nombre indicado
     */
    public void editarEmpresa(String nombre, String nuevoSector, String nuevoCorreo) {
        Empresa e = buscarEmpresaPorNombre(nombre);
        if (nuevoSector != null && !nuevoSector.isBlank()) e.setSector(nuevoSector);
        if (nuevoCorreo != null && !nuevoCorreo.isBlank()) e.setCorreoElectronico(nuevoCorreo);
    }

    /**
     * Elimina una empresa por nombre y libera cualquier stand que la tuviera asignada.
     * @param nombre nombre de la empresa a eliminar
     * @throws NoSuchElementException si la empresa no existe
     */
    public void eliminarEmpresa(String nombre) {
        Empresa e = buscarEmpresaPorNombre(nombre);
        stands.stream()
                .filter(s -> s.getEmpresaAsignada().map(e::equals).orElse(false))
                .forEach(Stand::desasignar);
        empresas.remove(e);
    }

    /**
     * Busca una empresa por nombre (ignorando mayúsculas/minúsculas).
     * @param nombre nombre a buscar
     * @return empresa encontrada
     * @throws NoSuchElementException si no existe una empresa con ese nombre
     */
    private Empresa buscarEmpresaPorNombre(String nombre) {
        return empresas.stream()
                .filter(x -> x.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe empresa: " + nombre));
    }

    /**
     * Crea un nuevo stand garantizando número único.
     * @param numero identificador único del stand; ubicacion ubicación legible; tamano tamaño del stand (PEQUENO|MEDIANO|GRANDE)
     * @throws IllegalArgumentException si ya existe un stand con el mismo número
     */
    public void crearStand(String numero, String ubicacion, StandSize tamano) {
        stands.stream()
                .filter(s -> s.getNumero().equalsIgnoreCase(numero))
                .findAny()
                .ifPresent(s -> { throw new IllegalArgumentException("Ya existe stand: " + numero); });
        stands.add(new Stand(numero, ubicacion, tamano));
    }

    /**
     * Devuelve una copia inmutable de todos los stands.
     * @return lista inmutable de stands
     */
    public List<Stand> listarStands() { return List.copyOf(stands); }

    /**
     * Devuelve los stands actualmente disponibles (sin empresa asignada).
     * @return lista inmutable de stands disponibles
     */
    public List<Stand> listarStandsDisponibles() {
        return stands.stream().filter(Stand::estaDisponible).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Devuelve los stands actualmente ocupados (con empresa asignada).
     * @return lista inmutable de stands ocupados
     */
    public List<Stand> listarStandsOcupados() {
        return stands.stream().filter(s -> !s.estaDisponible()).collect(Collectors.toUnmodifiableList());
    }

    /**
     * Asigna un stand existente a una empresa existente.
     * @param numeroStand número del stand a asignar; nombreEmpresa nombre de la empresa destino
     * @throws NoSuchElementException si no existe el stand o la empresa
     * @throws IllegalStateException si el stand ya está asignado a otra empresa
     */
    public void asignarStandAEmpresa(String numeroStand, String nombreEmpresa) {
        Stand stand = buscarStandPorNumero(numeroStand);
        Empresa empresa = buscarEmpresaPorNombre(nombreEmpresa);
        stand.asignarEmpresa(empresa);
    }

    /**
     * Desasigna (libera) un stand por su número.
     * @param numeroStand número del stand a desasignar
     * @throws NoSuchElementException si el stand no existe
     */
    public void desasignarStand(String numeroStand) {
        Stand stand = buscarStandPorNumero(numeroStand);
        stand.desasignar();
    }

    /**
     * Busca un stand por su número (ignorando mayúsculas/minúsculas).
     * @param numero número del stand
     * @return stand encontrado
     * @throws NoSuchElementException si no existe un stand con ese número
     */
    private Stand buscarStandPorNumero(String numero) {
        return stands.stream()
                .filter(s -> s.getNumero().equalsIgnoreCase(numero))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe stand: " + numero));
    }

    /**
     * Registra un nuevo visitante con identificación única.
     * @param v visitante a registrar (con identificación, nombre y correo ya validados)
     * @throws IllegalArgumentException si ya existe un visitante con la misma identificación
     */
    public void registrarVisitante(Visitante v) {
        visitantes.stream()
                .filter(x -> x.getIdentificacion().equalsIgnoreCase(v.getIdentificacion()))
                .findAny()
                .ifPresent(x -> { throw new IllegalArgumentException("Ya existe visitante: " + v.getIdentificacion()); });
        visitantes.add(v);
    }

    /**
     * Devuelve una copia inmutable de todos los visitantes.
     * @return lista inmutable de visitantes
     */
    public List<Visitante> listarVisitantes() { return List.copyOf(visitantes); }

    /**
     * Busca un visitante por su identificación (ignorando mayúsculas/minúsculas).
     * @param id identificación del visitante a buscar
     * @return visitante encontrado
     * @throws NoSuchElementException si no existe un visitante con esa identificación
     */
    private Visitante buscarVisitantePorId(String id) {
        return visitantes.stream()
                .filter(v -> v.getIdentificacion().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe visitante: " + id));
    }

    /**
     * Registra un comentario/calificación (1 a 5) de un visitante sobre un stand.
     * @param visitanteId identificación del visitante existente; numeroStand número del stand existente; calificacion entero 1..5; texto comentario no vacío
     * @throws NoSuchElementException si el visitante o el stand no existen
     * @throws IllegalArgumentException si la calificación está fuera de 1..5 o el texto está vacío
     */
    public void registrarComentario(String visitanteId, String numeroStand, Integer calificacion, String texto) {
        if (calificacion == null || calificacion < 1 || calificacion > 5)
            throw new IllegalArgumentException("La calificación debe estar entre 1 y 5");
        if (texto == null || texto.isBlank())
            throw new IllegalArgumentException("El texto del comentario es obligatorio");

        Visitante v = buscarVisitantePorId(visitanteId);
        Stand s = buscarStandPorNumero(numeroStand);

        Comentario c = new Comentario(v.getIdentificacion(), LocalDate.now(), calificacion, texto.trim());
        s.agregarComentario(c);
    }

    /**
     * Lista los comentarios registrados en un stand.
     * @param numeroStand número del stand del que se desean obtener comentarios
     * @return lista inmutable de comentarios del stand
     * @throws NoSuchElementException si el stand no existe
     */
    public List<Comentario> listarComentariosDeStand(String numeroStand) {
        Stand s = buscarStandPorNumero(numeroStand);
        return s.getComentarios();
    }

    /**
     * Calcula el promedio de calificación (1..5) de un stand.
     * @param numeroStand número del stand a evaluar
     * @return OptionalDouble con el promedio o vacío si no hay calificaciones
     * @throws NoSuchElementException si el stand no existe
     */
    public OptionalDouble promedioCalificacionStand(String numeroStand) {
        Stand s = buscarStandPorNumero(numeroStand);
        return s.promedioCalificacion();
    }

    /**
     * Edita los datos de un visitante (nombre y/o correo).
     * @param id identificación del visitante a editar; nuevoNombre nuevo nombre (si no es vacío); nuevoCorreo nuevo correo (si no es vacío)
     * @throws NoSuchElementException si el visitante no existe
     */
    public void editarVisitante(String id, String nuevoNombre, String nuevoCorreo) {
        Visitante v = buscarVisitantePorId(id);
        if (nuevoNombre != null && !nuevoNombre.isBlank()) v.setNombre(nuevoNombre);
        if (nuevoCorreo != null && !nuevoCorreo.isBlank()) v.setCorreoElectronico(nuevoCorreo);
    }

    /**
     * Elimina un visitante por identificación y borra sus comentarios en todos los stands.
     * @param id identificación del visitante a eliminar
     * @throws NoSuchElementException si el visitante no existe
     */
    public void eliminarVisitante(String id) {
        Visitante v = buscarVisitantePorId(id);
        for (Stand s : stands) {
            s.eliminarComentariosDeVisitante(v.getIdentificacion());
        }
        visitantes.remove(v);
    }

    /**
     * Genera un reporte de empresas con su stand, empresas sin stand y stands sin asignar.
     * @return cadena formateada con el reporte
     */
    public String reporteEmpresasYStands() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE: Empresas y Stands ===\n");

        sb.append("\nEmpresas con stand:\n");
        Set<String> empresasConStand = new HashSet<>();
        stands.stream()
                .filter(s -> s.getEmpresaAsignada().isPresent())
                .forEach(s -> {
                    String emp = s.getEmpresaAsignada().get().getNombre();
                    empresasConStand.add(emp);
                    sb.append(" - ").append(emp).append(" -> Stand ").append(s.getNumero()).append("\n");
                });
        if (empresasConStand.isEmpty()) sb.append(" (ninguna)\n");

        sb.append("\nEmpresas SIN stand:\n");
        List<String> sinStand = empresas.stream()
                .map(Empresa::getNombre)
                .filter(n -> !empresasConStand.contains(n))
                .sorted(String::compareToIgnoreCase)
                .toList();
        if (sinStand.isEmpty()) {
            sb.append(" (ninguna)\n");
        } else {
            sinStand.forEach(n -> sb.append(" - ").append(n).append("\n"));
        }

        sb.append("\nStands SIN asignar:\n");
        List<String> libres = stands.stream()
                .filter(Stand::estaDisponible)
                .map(Stand::getNumero)
                .sorted(String::compareToIgnoreCase)
                .toList();
        if (libres.isEmpty()) {
            sb.append(" (ninguno)\n");
        } else {
            libres.forEach(n -> sb.append(" - Stand ").append(n).append("\n"));
        }

        return sb.toString();
    }

    /**
     * Genera un reporte de visitantes y los stands que han visitado (derivado de sus comentarios).
     * Incluye también visitantes que comentaron pero no están registrados actualmente.
     * @return cadena formateada con el reporte
     */
    public String reporteVisitantesYStandsVisitados() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE: Visitantes y Stands visitados ===\n");

        Map<String, Set<String>> visitasPorVisitante = new HashMap<>();
        Map<String, Integer> comentariosPorVisitante = new HashMap<>();

        for (Stand s : stands) {
            for (Comentario c : s.getComentarios()) {
                visitasPorVisitante
                        .computeIfAbsent(c.visitanteId(), k -> new HashSet<>())
                        .add(s.getNumero());
                comentariosPorVisitante.merge(c.visitanteId(), 1, Integer::sum);
            }
        }

        if (visitasPorVisitante.isEmpty()) {
            sb.append("(sin datos de visitas/comentarios)\n");
            return sb.toString();
        }

        visitantes.stream()
                .sorted(Comparator.comparing(Visitante::getIdentificacion, String.CASE_INSENSITIVE_ORDER))
                .forEach(v -> {
                    Set<String> standsVisitados = visitasPorVisitante.getOrDefault(v.getIdentificacion(), Set.of());
                    int total = comentariosPorVisitante.getOrDefault(v.getIdentificacion(), 0);
                    sb.append(" - ").append(v.getIdentificacion()).append(" (").append(v.getNombre()).append(")")
                            .append(" -> Stands: ").append(standsVisitados.isEmpty() ? "-" : standsVisitados)
                            .append(" | Comentarios: ").append(total).append("\n");
                });

        Set<String> idsConComentarios = new HashSet<>(visitasPorVisitante.keySet());
        Set<String> idsEnSistema = visitantes.stream()
                .map(Visitante::getIdentificacion)
                .map(String::toLowerCase)
                .collect(Collectors.toSet());
        idsConComentarios.stream()
                .filter(id -> !idsEnSistema.contains(id.toLowerCase()))
                .sorted(String::compareToIgnoreCase)
                .forEach(id -> {
                    Set<String> standsVisitados = visitasPorVisitante.getOrDefault(id, Set.of());
                    int total = comentariosPorVisitante.getOrDefault(id, 0);
                    sb.append(" - ").append(id)
                            .append(" (no registrado actualmente)")
                            .append(" -> Stands: ").append(standsVisitados.isEmpty() ? "-" : standsVisitados)
                            .append(" | Comentarios: ").append(total).append("\n");
                });

        return sb.toString();
    }

    /**
     * Genera un reporte de promedios de calificación por stand (ordenado desc, sin calificaciones al final).
     * @return cadena formateada con el reporte
     */
    public String reportePromedioPorStand() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== REPORTE: Promedio de calificación por Stand ===\n");

        List<Stand> ordenados = new ArrayList<>(stands);
        ordenados.sort((a, b) -> {
            double promA = a.promedioCalificacion().orElse(Double.NaN);
            double promB = b.promedioCalificacion().orElse(Double.NaN);
            // NaN al final
            if (Double.isNaN(promA) && Double.isNaN(promB)) return 0;
            if (Double.isNaN(promA)) return 1;
            if (Double.isNaN(promB)) return -1;
            return Double.compare(promB, promA);
        });

        for (Stand s : ordenados) {
            var prom = s.promedioCalificacion();
            if (prom.isPresent()) {
                sb.append(" - Stand ").append(s.getNumero())
                        .append(": ★").append(String.format("%.2f", prom.getAsDouble()))
                        .append(" (").append(s.totalComentarios()).append(" comentarios)\n");
            } else {
                sb.append(" - Stand ").append(s.getNumero())
                        .append(": sin calificaciones\n");
            }
        }

        return sb.toString();
    }
}
