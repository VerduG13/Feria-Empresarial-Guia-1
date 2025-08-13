package org.example.model;

import java.util.Objects;

/**
 * Representa a un visitante de la feria con identificación única, nombre y correo de contacto.
 */
public class Visitante {
    private final String identificacion; // único
    private String nombre;
    private String correoElectronico;

    /**
     * Crea un visitante validando campos obligatorios.
     * @param identificacion identificación única; nombre nombre completo; correoElectronico correo de contacto
     * @throws IllegalArgumentException si algún campo es nulo o está vacío
     */
    public Visitante(String identificacion, String nombre, String correoElectronico) {
        if (identificacion == null || identificacion.isBlank())
            throw new IllegalArgumentException("La identificación es obligatoria");
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (correoElectronico == null || correoElectronico.isBlank())
            throw new IllegalArgumentException("El correo es obligatorio");
        this.identificacion = identificacion.trim();
        this.nombre = nombre.trim();
        this.correoElectronico = correoElectronico.trim();
    }

    /**
     * Devuelve la identificación única del visitante.
     * @return identificación del visitante
     */
    public String getIdentificacion() { return identificacion; }

    /**
     * Devuelve el nombre del visitante.
     * @return nombre del visitante
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el correo electrónico del visitante.
     * @return correo electrónico del visitante
     */
    public String getCorreoElectronico() { return correoElectronico; }

    /**
     * Actualiza el nombre del visitante.
     * @param nombre nuevo nombre (no vacío)
     * @throws IllegalArgumentException si el nombre es nulo o vacío
     */
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        this.nombre = nombre.trim();
    }

    /**
     * Actualiza el correo electrónico del visitante.
     * @param correo nuevo correo (no vacío)
     * @throws IllegalArgumentException si el correo es nulo o vacío
     */
    public void setCorreoElectronico(String correo) {
        if (correo == null || correo.isBlank()) throw new IllegalArgumentException("Correo inválido");
        this.correoElectronico = correo.trim();
    }

    /**
     * Define igualdad lógica por identificación (ignorando mayúsculas/minúsculas).
     * @param o objeto a comparar
     * @return true si tienen la misma identificación; false en caso contrario
     */
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visitante v)) return false;
        return identificacion.equalsIgnoreCase(v.identificacion);
    }

    /**
     * Hash consistente con equals (basado en la identificación en minúsculas).
     * @return código hash del visitante
     */
    @Override public int hashCode() { return Objects.hash(identificacion.toLowerCase()); }

    /**
     * Representación legible del visitante con identificación, nombre y correo.
     * @return cadena descriptiva del visitante
     */
    @Override public String toString() {
        return identificacion + " - " + nombre + " - " + correoElectronico;
    }
}
