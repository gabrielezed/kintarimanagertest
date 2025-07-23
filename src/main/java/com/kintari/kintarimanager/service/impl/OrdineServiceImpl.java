package com.kintari.kintarimanager.service.impl;

import com.kintari.kintarimanager.dto.DettaglioOrdineDTO;
import com.kintari.kintarimanager.dto.OrdineRequestDTO;
import com.kintari.kintarimanager.model.Ingrediente;
import com.kintari.kintarimanager.model.Magazzino;
import com.kintari.kintarimanager.model.Ricetta;
import com.kintari.kintarimanager.repository.MagazzinoRepository;
import com.kintari.kintarimanager.repository.ProdottoFinitoRepository;
import com.kintari.kintarimanager.repository.RicettaRepository;
import com.kintari.kintarimanager.service.OrdineService;
import com.kintari.kintarimanager.service.exception.InsufficientStockException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrdineServiceImpl implements OrdineService {

	private final ProdottoFinitoRepository prodottoFinitoRepository;
	private final MagazzinoRepository magazzinoRepository;
	private final RicettaRepository ricettaRepository;

	@Override
	public void processaOrdine(OrdineRequestDTO ordineRequest) {

		Map<Long, Double> fabbisognoIngredienti = new HashMap<>();

		for (DettaglioOrdineDTO dettaglio : ordineRequest.getProdotti()) {
			if (!prodottoFinitoRepository.existsById(dettaglio.getProdottoId())) {
				throw new EntityNotFoundException("Prodotto non trovato con id: " + dettaglio.getProdottoId());
			}

			List<Ricetta> ricetta = ricettaRepository.findByProdottoFinitoId(dettaglio.getProdottoId());
			if (ricetta.isEmpty()) {
				continue;
			}

			for (Ricetta rigaRicetta : ricetta) {
				Ingrediente ingrediente = rigaRicetta.getIngrediente();
				double quantitaNecessaria = rigaRicetta.getQuantita() * dettaglio.getQuantita();
				fabbisognoIngredienti.merge(ingrediente.getId(), quantitaNecessaria, Double::sum);
			}
		}

		Map<Long, Magazzino> scorteDaAggiornare = new HashMap<>();
		for (Map.Entry<Long, Double> entry : fabbisognoIngredienti.entrySet()) {
			Long ingredienteId = entry.getKey();
			Double quantitaRichiesta = entry.getValue();

			Magazzino scorta = magazzinoRepository.findByIngredienteId(ingredienteId)
					.orElseThrow(() -> new EntityNotFoundException("Scorta non trovata per l'ingrediente con id: " + ingredienteId));

			if (scorta.getQuantitaDisponibile() < quantitaRichiesta) {
				throw new InsufficientStockException("Scorta insufficiente per l'ingrediente: " + scorta.getIngrediente().getNome() +
						". Richiesti: " + quantitaRichiesta + ", Disponibili: " + scorta.getQuantitaDisponibile());
			}
			scorteDaAggiornare.put(ingredienteId, scorta);
		}

		for (Map.Entry<Long, Double> entry : fabbisognoIngredienti.entrySet()) {
			Long ingredienteId = entry.getKey();
			Double quantitaDaScalare = entry.getValue();

			Magazzino scorta = scorteDaAggiornare.get(ingredienteId);
			scorta.setQuantitaDisponibile(scorta.getQuantitaDisponibile() - quantitaDaScalare);
			magazzinoRepository.save(scorta);
		}
	}
}