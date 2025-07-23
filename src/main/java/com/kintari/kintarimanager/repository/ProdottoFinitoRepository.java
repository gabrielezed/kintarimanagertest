// docs/source/repository/ProdottoFinitoRepository.html

package com.kintari.kintarimanager.repository;

import com.kintari.kintarimanager.model.ProdottoFinito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottoFinitoRepository extends JpaRepository<ProdottoFinito, Long> {
}