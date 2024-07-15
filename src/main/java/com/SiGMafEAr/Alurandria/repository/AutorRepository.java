package com.SiGMafEAr.Alurandria.repository;

import com.SiGMafEAr.Alurandria.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Autor findByAutorIgnoreCase(String nombre);

    List<Autor> findByNacimientoYearLessThanEqualAndFallecimientoYearGreaterThanEqual(int inicialYear, int finalYear);
}