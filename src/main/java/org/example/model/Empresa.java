package org.example.model;

import java.util.Objects;

/**
 * Representa una empresa participante de la feria con nombre único, sector y correo de contacto.
 */
public class Empresa {
    private final String nombre; // identificador único
    private String sector;
    private String correoElectronico;

    /**
     * Crea una empresa validando que los datos obligatorios no estén vacíos.
     * @param nombre nombre único de la empresa; sector sector económico; correoElectronico correo de contacto
     * @throws IllegalArgumentException si algún campo obligatorio es nulo o vacío
     */
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

    /**
     * Devuelve el nombre único de la empresa.
     * @return nombre de la empresa
     */
    public String getNombre() { return nombre; }

    /**
     * Devuelve el sector económico de la empresa.
     * @return sector de la empresa
     */
    public String getSector() { return sector; }

    /**
     * Devuelve el correo electrónico de contacto de la empresa.
     * @return correo electrónico de la empresa
     */
    public String getCorreoElectronico() { return correoElectronico; }

    /**
     * Actualiza el sector económico (no vacío).
     * @param sector nuevo sector económico
     * @throws IllegalArgumentException si el sector es nulo o vacío
     */
    public void setSector(String sector) {
        if (sector == null || sector.isBlank())
            throw new IllegalArgumentException("Sector inválido");
        this.sector = sector.trim();
    }

    /**
     * Actualiza el correo electrónico (no vacío).
     * @param correo nuevo correo electrónico
     * @throws IllegalArgumentException si el correo es nulo o vacío
     */
    public void setCorreoElectronico(String correo) {
        if (correo == null || correo.isBlank())
            throw new IllegalArgumentException("Correo inválido");
        this.correoElectronico = correo.trim();
    }

    /**
     * Compara por nombre (ignorando mayúsculas/minúsculas) para definir igualdad lógica.
     * @param o objeto a comparar
     * @return true si representan la misma empresa por nombre; false en caso contrario
     */
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Empresa e)) return false;
        return nombre.equalsIgnoreCase(e.nombre);
    }

    /**
     * Devuelve el hash consistente con equals (basado en el nombre en minúsculas).
     * @return código hash de la empresa
     */
    @Override public int hashCode() { return Objects.hash(nombre.toLowerCase()); }

    /**
     * Representación legible con nombre, sector y correo.
     * @return cadena descriptiva de la empresa
     */
    @Override public String toString() {
        return nombre + " (" + sector + ") - " + correoElectronico;
    }
}
