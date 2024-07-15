package com.SiGMafEAr.Alurandria.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String autor,
        @JsonAlias("birth_year") Integer nacimientoYear,
        @JsonAlias("death_year") Integer fallecimientoYear) {
}