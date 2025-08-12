package org.example.service;

import org.example.model.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Gestor en memoria para la Feria Empresarial.
 * Avance 1: Empresas + Stands + Visitantes + Comentarios (1..5).
 * TODO Avance 2: reportes avanzados y operaciones extra.
 */
public class FeriaEmpresarial {
    private final List<Empresa> empresas = new ArrayList<>();
    private final List<Stand> stands = new ArrayList<>();
    private final List<Visitante> visitantes = new ArrayList<>();

    // ---------- EMPRESAS ----------
    public void registrarEmpresa(Empresa e) {
        empresas.stream()
                .filter(x -> x.getNombre().equalsIgnoreCase(e.getNombre()))
                .findAny()
                .ifPresent(x -> { throw new IllegalArgumentException("Ya existe empresa: " + e.getNombre()); });
        empresas.add(e);
    }

    public List<Empresa> listarEmpresas() { return List.copyOf(empresas); }

    public void editarEmpresa(String nombre, String nuevoSector, String nuevoCorreo) {
        Empresa e = buscarEmpresaPorNombre(nombre);
        if (nuevoSector != null && !nuevoSector.isBlank()) e.setSector(nuevoSector);
        if (nuevoCorreo != null && !nuevoCorreo.isBlank()) e.setCorreoElectronico(nuevoCorreo);
    }

    public void eliminarEmpresa(String nombre) {
        Empresa e = buscarEmpresaPorNombre(nombre);
        // liberar stands ocupados por esta empresa
        stands.stream()
                .filter(s -> s.getEmpresaAsignada().map(e::equals).orElse(false))
                .forEach(Stand::desasignar);
        empresas.remove(e);
    }

    private Empresa buscarEmpresaPorNombre(String nombre) {
        return empresas.stream()
                .filter(x -> x.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe empresa: " + nombre));
    }

    // ---------- STANDS ----------
    public void crearStand(String numero, String ubicacion, StandSize tamano) {
        stands.stream()
                .filter(s -> s.getNumero().equalsIgnoreCase(numero))
                .findAny()
                .ifPresent(s -> { throw new IllegalArgumentException("Ya existe stand: " + numero); });
        stands.add(new Stand(numero, ubicacion, tamano));
    }

    public List<Stand> listarStands() { return List.copyOf(stands); }

    public List<Stand> listarStandsDisponibles() {
        return stands.stream().filter(Stand::estaDisponible).collect(Collectors.toUnmodifiableList());
    }

    public List<Stand> listarStandsOcupados() {
        return stands.stream().filter(s -> !s.estaDisponible()).collect(Collectors.toUnmodifiableList());
    }

    public void asignarStandAEmpresa(String numeroStand, String nombreEmpresa) {
        Stand stand = buscarStandPorNumero(numeroStand);
        Empresa empresa = buscarEmpresaPorNombre(nombreEmpresa);
        stand.asignarEmpresa(empresa);
    }

    public void desasignarStand(String numeroStand) {
        Stand stand = buscarStandPorNumero(numeroStand);
        stand.desasignar();
    }

    private Stand buscarStandPorNumero(String numero) {
        return stands.stream()
                .filter(s -> s.getNumero().equalsIgnoreCase(numero))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe stand: " + numero));
    }

    // ---------- VISITANTES ----------
    public void registrarVisitante(Visitante v) {
        visitantes.stream()
                .filter(x -> x.getIdentificacion().equalsIgnoreCase(v.getIdentificacion()))
                .findAny()
                .ifPresent(x -> { throw new IllegalArgumentException("Ya existe visitante: " + v.getIdentificacion()); });
        visitantes.add(v);
    }

    public List<Visitante> listarVisitantes() { return List.copyOf(visitantes); }

    private Visitante buscarVisitantePorId(String id) {
        return visitantes.stream()
                .filter(v -> v.getIdentificacion().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No existe visitante: " + id));
    }

    // ---------- COMENTARIOS ----------
    /**
     * Registra un comentario/calificación (1..5) de un visitante sobre un stand.
     * La fecha por defecto es LocalDate.now() si no se especifica.
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

    public List<Comentario> listarComentariosDeStand(String numeroStand) {
        Stand s = buscarStandPorNumero(numeroStand);
        return s.getComentarios();
    }

    public OptionalDouble promedioCalificacionStand(String numeroStand) {
        Stand s = buscarStandPorNumero(numeroStand);
        return s.promedioCalificacion();
    }
}
