# Changelog del Progetto

Questo file documenta lo storico di tutte le modifiche e le attività significative completate durante lo sviluppo del progetto, in ordine cronologico inverso (la più recente in alto).

## Formato
Ogni voce deve seguire questo formato:
`## YYYY-MM-DD HH:MM - [Nome Task o Modifica]`
`- Descrizione della modifica o del progresso.`

---

## 2025-07-21 23:28 - Stato Iniziale del Progetto
- **Backend**: Lo sviluppo del backend è completo e la build è stabile. Include tutte le entità (Model, DTO, Repository, Service, Controller) e una struttura di documentazione iniziale. L'applicazione è avviata su `http://localhost:8080`.
- **Test API**: L'attività di test degli endpoint è in corso. È stato risolto un problema iniziale di accesso (`401 Unauthorized` e `403 Forbidden`) tramite una configurazione di sicurezza temporaneamente permissiva. È in corso la verifica della connessione al database H2.
- **Frontend**: Non iniziato.
- **Sicurezza**: La configurazione di sicurezza robusta non è stata ancora implementata. Attualmente è in uso una configurazione temporanea per permettere i test.