// docs/source/repository/MagazzinoRepository.html

package com.kintari.kintarimanager.repository;

import com.kintari.kintarimanager.model.Magazzino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MagazzinoRepository extends JpaRepository<Magazzino, Long> {

    Optional<Magazzino> findByIngredienteId(Long ingredienteId);
}