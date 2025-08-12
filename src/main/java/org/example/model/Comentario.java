package org.example.model;

import java.time.LocalDate;

public record Comentario(
        String visitanteId,
        LocalDate fecha,
        int calificacion,   // 1..5
        String texto
) {
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
