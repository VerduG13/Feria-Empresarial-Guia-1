package org.example.model;

import java.util.Objects;

public class Visitante {
    private final String identificacion; // único
    private String nombre;
    private String correoElectronico;

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

    public String getIdentificacion() { return identificacion; }
    public String getNombre() { return nombre; }
    public String getCorreoElectronico() { return correoElectronico; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido");
        this.nombre = nombre.trim();
    }
    public void setCorreoElectronico(String correo) {
        if (correo == null || correo.isBlank()) throw new IllegalArgumentException("Correo inválido");
        this.correoElectronico = correo.trim();
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Visitante v)) return false;
        return identificacion.equalsIgnoreCase(v.identificacion);
    }
    @Override public int hashCode() { return Objects.hash(identificacion.toLowerCase()); }

    @Override public String toString() {
        return identificacion + " - " + nombre + " - " + correoElectronico;
    }
}
