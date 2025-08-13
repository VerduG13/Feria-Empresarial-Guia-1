package org.example.model;

import java.time.LocalDate;

/**
 * Comentario/calificación hecho por un visitante sobre un stand (inmutable por ser record).
 * @param visitanteId identificación del visitante autor; fecha fecha del comentario; calificacion entero 1..5; texto contenido del comentario
 */
public record Comentario(
        String visitanteId,
        LocalDate fecha,
        int calificacion,   // 1..5
        String texto
) {
    /**
     * Constructor compacto que valida campos obligatorios y rangos.
     * @throws IllegalArgumentException si visitanteId o texto están vacíos, si fecha es nula, o si calificacion no está entre 1 y 5
     */
    public Comentario {
        if (visitanteId == null || visitanteId.isBlank())
            throw new IllegalArgumentException("visitanteId es obligatorio");
        if (fecha == null) throw new IllegalArgumentException("fecha es obligatoria");
        if (calificacion < 1 || calificacion > 5)
            throw new IllegalArgumentException("calificación debe estar entre 1 y 5");
        if (texto == null || texto.isBlank())
            throw new IllegalArgumentException("texto del comentario es obligatorio");
    }
}
