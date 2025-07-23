package com.kintari.kintarimanager.service;

import com.kintari.kintarimanager.dto.OrdineRequestDTO;

public interface OrdineService {
    void processaOrdine(OrdineRequestDTO ordineRequest);
}