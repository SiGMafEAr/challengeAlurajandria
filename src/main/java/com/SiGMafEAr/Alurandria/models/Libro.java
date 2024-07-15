package com.SiGMafEAr.Alurandria.models;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")

public class Libro {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne

    private Autor autor;
    private String nombreAutor;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Integer numeroDeDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datosLibro, Autor autor) {
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idioma().get(0);
        this.numeroDeDescargas = datosLibro.numeroDeDescargas();
        this.nombreAutor = autor.getAutor();
        this.autor = autor;
    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Integer numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    @Override
    public String toString() {
        String mensaje = String.format("""
                
                ░░░░░░░░░░░░░░░░░░░░ LIBRO ░░░░░░░░░░░░░░░░░░░░
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %s
                ░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
                """, titulo, nombreAutor, idioma, numeroDeDescargas);
        return mensaje;

    }
}