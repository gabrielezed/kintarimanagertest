# Changelog del Progetto

Questo file documenta lo storico di tutte le modifiche e le attività significative completate durante lo sviluppo del progetto, in ordine cronologico inverso (la più recente in alto).

## Formato
Ogni voce deve seguire questo formato:
`## YYYY-MM-DD HH:MM - [Nome Task o Modifica]`
`- Descrizione della modifica o del progresso.`

---

## 2025-07-24 10:30 - Integrazione Swagger/OpenAPI Completata
- **Integrazione Swagger**: Aggiunta dipendenza `springdoc-openapi-starter-webmvc-ui` versione 2.2.0 al progetto
- **Configurazione OpenAPI**: Creata classe `OpenApiConfiguration` con metadati API completi e configurazione HTTP Basic Auth
- **Annotazioni Controller**: Tutti i controller REST (`IngredienteController`, `ProdottoFinitoController`, `RicettaController`, `MagazzinoController`, `OrdineController`) sono stati completamente annotati con documentazione OpenAPI dettagliata
- **Configurazione Properties**: Aggiunta configurazione Swagger UI personalizzata in `application.properties`
- **Database Temporaneo**: Configurato H2 come database temporaneo per completare i test (PostgreSQL da configurare successivamente)
- **Test Swagger UI**: Interfaccia Swagger UI accessibile su `http://localhost:8080/swagger-ui.html` con autenticazione funzionante
- **Documentazione API**: Ogni endpoint è ora completamente documentato con esempi, codici di risposta e descrizioni dettagliate

## 2025-07-21 23:28 - Stato Iniziale del Progetto
- **Backend**: Sviluppo del backend completo e build stabile. Include tutte le entità (Model, DTO, Repository, Service, Controller) e struttura di documentazione iniziale. L'applicazione è avviata su `http://localhost:8080`.
- **Test API**: L'attività di test degli endpoint è in corso. È stato risolto un problema iniziale di accesso (`401 Unauthorized` e `403 Forbidden`) tramite una configurazione di sicurezza temporaneamente permissiva. È in corso la verifica della connessione al database H2.
- **Frontend**: Non iniziato.
- **Sicurezza**: La configurazione di sicurezza robusta non è stata ancora implementata. Attualmente è in uso una configurazione temporanea per permettere i test.