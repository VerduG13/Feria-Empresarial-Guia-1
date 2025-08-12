package org.example.model;

import java.util.Objects;

public class Empresa {
    private final String nombre; // identificador único
    private String sector;
    private String correoElectronico;

    public Empresa(String nombre, String sector, String correoElectronico) {
        if (nombre == null || nombre.isBlank())
            throw new IllegalArgumentException("El nombre de la empresa es obligatorio");
        if (sector == null || sector.isBlank())
            throw new IllegalArgumentException("El sector es obligatorio");
        if (correoElectronico == null || correoElectronico.isBlank())
            throw new IllegalArgumentException("El correo es obligatorio");
        this.nombre = nombre.trim();
        this.sector = sector.trim();
        this.correoElectronico = correoElectronico.trim();
    }

    public String getNombre() { return nombre; }
    public String getSector() { return sector; }
    public String getCorreoElectronico() { return correoElectronico; }

    public void setSector(String sector) {
        if (sector == null || sector.isBlank())
            throw new IllegalArgumentException("Sector inválido");
        this.sector = sector.trim();
    }

    public void setCorreoElectronico(String correo) {
        if (correo == null || correo.isBlank())
            throw new IllegalArgumentException("Correo inválido");
        this.correoElectronico = correo.trim();
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empresa e)) return false;
        return nombre.equalsIgnoreCase(e.nombre);
    }
    @Override public int hashCode() { return Objects.hash(nombre.toLowerCase()); }

    @Override public String toString() {
        return nombre + " (" + sector + ") - " + correoElectronico;
    }
}
