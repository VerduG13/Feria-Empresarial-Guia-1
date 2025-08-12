package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class Stand {
    private final String numero;         // único
    private String ubicacion;
    private StandSize tamano;
    private Empresa empresaAsignada; // null si disponible
    private final List<Comentario> comentarios = new ArrayList<>();

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

    public String getNumero() { return numero; }
    public String getUbicacion() { return ubicacion; }
    public StandSize getTamano() { return tamano; }
    public Optional<Empresa> getEmpresaAsignada() { return Optional.ofNullable(empresaAsignada); }
    public boolean estaDisponible() { return empresaAsignada == null; }

    public void asignarEmpresa(Empresa e) {
        if (!estaDisponible())
            throw new IllegalStateException("El stand ya está asignado a: " + empresaAsignada.getNombre());
        this.empresaAsignada = e;
    }
    public void desasignar() { this.empresaAsignada = null; }

    public void agregarComentario(Comentario c) { comentarios.add(c); }
    public List<Comentario> getComentarios() { return List.copyOf(comentarios); }

    public OptionalDouble promedioCalificacion() {
        return comentarios.stream().mapToInt(Comentario::calificacion).average();
    }

    @Override public String toString() {
        String estado = estaDisponible() ? "DISPONIBLE" : "OCUPADO por " + empresaAsignada.getNombre();
        String prom = promedioCalificacion().isPresent()
                ? String.format(" | ★%.2f", promedioCalificacion().getAsDouble())
                : "";
        return "Stand " + numero + " [" + tamano + "] " + ubicacion + " (" + estado + ")" + prom;
    }
}
