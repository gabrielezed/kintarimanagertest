// docs/source/repository/RicettaRepository.html

package com.kintari.kintarimanager.repository;

import com.kintari.kintarimanager.model.Ricetta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

    List<Ricetta> findByProdottoFinitoId(Long prodottoFinitoId);
}