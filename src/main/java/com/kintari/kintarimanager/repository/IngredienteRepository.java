// docs/source/repository/IngredienteRepository.html

package com.kintari.kintarimanager.repository;

import com.kintari.kintarimanager.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
}