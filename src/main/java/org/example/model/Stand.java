package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Representa un stand dentro de la feria: número único, ubicación, tamaño, empresa asignada (opcional)
 * y comentarios/calificaciones realizados por visitantes.
 */
public class Stand {
    private final String numero;         // único
    private String ubicacion;
    private StandSize tamano;
    private Empresa empresaAsignada; // null si disponible
    private final List<Comentario> comentarios = new ArrayList<>();

    /**
     * Crea un stand garantizando datos obligatorios y formateo básico.
     * @param numero número único del stand; ubicacion ubicación legible (ej. "Pabellón A, Stand 10"); tamano tamaño del stand (PEQUENO|MEDIANO|GRANDE)
     * @throws IllegalArgumentException si numero o ubicacion son nulos/vacíos, o tamano es nulo
     */
    public Stand(String numero, String ubicacion, StandSize tamano) {
        if (numero == null || numero.isBlank())
            throw new IllegalArgumentException("El número de stand es obligatorio");
        if (ubicacion == null || ubicacion.isBlank())
            throw new IllegalArgumentException("La ubicación es obligatoria");
        if (tamano == null) throw new IllegalArgumentException("El tamaño es obligatorio");
        this.numero = numero.trim();
        this.ubicacion = ubicacion.trim();
        this.tamano = tamano;
    }

    /**
     * Devuelve el número único del stand.
     * @return número del stand
     */
    public String getNumero() { return numero; }

    /**
     * Devuelve la ubicación legible del stand.
     * @return ubicación (ej. "Pabellón A, Stand 10")
     */
    public String getUbicacion() { return ubicacion; }

    /**
     * Devuelve el tamaño del stand.
     * @return tamaño (PEQUENO|MEDIANO|GRANDE)
     */
    public StandSize getTamano() { return tamano; }

    /**
     * Devuelve la empresa asignada, si existe.
     * @return Optional con la empresa asignada o vacío si el stand está disponible
     */
    public Optional<Empresa> getEmpresaAsignada() { return Optional.ofNullable(empresaAsignada); }

    /**
     * Indica si el stand no tiene empresa asignada.
     * @return true si está disponible; false si está ocupado
     */
    public boolean estaDisponible() { return empresaAsignada == null; }

    /**
     * Asigna una empresa al stand si está disponible.
     * @param e empresa a asignar
     * @throws IllegalStateException si el stand ya está asignado a otra empresa
     */
    public void asignarEmpresa(Empresa e) {
        if (!estaDisponible())
            throw new IllegalStateException("El stand ya está asignado a: " + empresaAsignada.getNombre());
        this.empresaAsignada = e;
    }

    /**
     * Desasigna la empresa (deja el stand disponible).
     */
    public void desasignar() { this.empresaAsignada = null; }

    /**
     * Agrega un comentario/calificación al stand (no se realizan validaciones adicionales aquí).
     * @param c comentario ya validado (con visitanteId, fecha, calificación 1..5 y texto)
     */
    public void agregarComentario(Comentario c) { comentarios.add(c); }

    /**
     * Devuelve una copia inmutable de los comentarios del stand.
     * @return lista inmutable de comentarios
     */
    public List<Comentario> getComentarios() { return List.copyOf(comentarios); }

    /**
     * Calcula el promedio de calificaciones (1..5) si existen comentarios.
     * @return OptionalDouble con el promedio o vacío si no hay calificaciones
     */
    public OptionalDouble promedioCalificacion() {
        return comentarios.stream().mapToInt(Comentario::calificacion).average();
    }

    /**
     * Elimina todos los comentarios hechos por un visitante específico.
     * @param visitanteId identificación del visitante cuyos comentarios serán eliminados
     * @return cantidad de comentarios eliminados
     */
    public int eliminarComentariosDeVisitante(String visitanteId) {
        int antes = comentarios.size();
        comentarios.removeIf(c -> c.visitanteId().equalsIgnoreCase(visitanteId));
        return antes - comentarios.size();
    }

    /**
     * Devuelve el total de comentarios almacenados en el stand.
     * @return número de comentarios
     */
    public int totalComentarios() {
        return comentarios.size();
    }

    /**
     * Devuelve una descripción legible del stand con estado y, si aplica, promedio de calificación.
     * @return representación textual del stand
     */
    @Override public String toString() {
        String estado = estaDisponible() ? "DISPONIBLE" : "OCUPADO por " + empresaAsignada.getNombre();
        String prom = promedioCalificacion().isPresent()
                ? String.format(" | ★%.2f", promedioCalificacion().getAsDouble())
                : "";
        return "Stand " + numero + " [" + tamano + "] " + ubicacion + " (" + estado + ")" + prom;
    }
}
