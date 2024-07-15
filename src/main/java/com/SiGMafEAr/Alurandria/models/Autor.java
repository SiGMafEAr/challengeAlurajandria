package com.SiGMafEAr.Alurandria.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")

public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(unique = true)
    private String autor;
    private Integer nacimientoYear;
    private Integer fallecimientoYear;
    //establece la relacion un autor con muchos libros
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor(DatosAutor datosAutor) {
        this.autor = datosAutor.autor();
        this.nacimientoYear = datosAutor.nacimientoYear();
        this.fallecimientoYear = datosAutor.fallecimientoYear();
    }

    public Autor() {}

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Integer getNacimientoYear() {
        return nacimientoYear;
    }

    public void setNacimientoYear(Integer nacimientoYear) {
        this.nacimientoYear = nacimientoYear;
    }

    public Integer getFallecimientoYear() {
        return fallecimientoYear;
    }

    public void setFallecimientoYear(Integer fallecimientoYear) {
        this.fallecimientoYear = fallecimientoYear;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        StringBuilder librosTitulos = new StringBuilder();
        for (Libro libro : libros) {
            librosTitulos.append(libro.getTitulo()).append(", ");
        }

        if (!librosTitulos.isEmpty()) {
            librosTitulos.setLength(librosTitulos.length() - 2);
        }

        String mensaje = String.format("""
                
                ░░░░░░░░░░░░░░░░░░░░ AUTOR ░░░░░░░░░░░░░░░░░░░░
                Nombre: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s
                Libros: %s
                ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                """, autor, nacimientoYear, fallecimientoYear, librosTitulos);

        return mensaje;
    }
}
