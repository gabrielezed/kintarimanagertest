# KintariManager - TODO List (Versione Definitiva)

Questa TODO list unificata combina le priorità strategiche del report di avanzamento con i requisiti tecnici del piano di sviluppo originale. È organizzata per fasi per guidare lo sviluppo in modo logico ed efficiente.

---

### FASE 0: Infrastruttura e Sicurezza di Base

Queste attività sono fondamentali e propedeutiche allo sviluppo di nuove funzionalità.

- [ ] **Database Persistente:** Configurare un database di produzione (PostgreSQL) per sostituire H2 temporaneo.
- [ ] **Autenticazione Utente:**
    - [ ] Implementare l'autenticazione tramite `formLogin` in `SecurityConfiguration`.
    - [ ] Creare una pagina di login (`login.html`) per il frontend.
- [ ] **Sicurezza Form:** Riabilitare la protezione CSRF e assicurarsi che tutti i form Thymeleaf la includano correttamente.

---

### ✅ FASE 1: Setup Iniziale e Frontend di Base (Completato)

- [x] **Correzione Bug Iniziali:** Risolto il problema di visualizzazione dei template Thymeleaf e di accesso alle pagine.
- [x] **Struttura Frontend:**
    - [x] Creato un `ViewController` centralizzato.
    - [x] Definito un `layout.html` standard.
    - [x] Create le pagine HTML di base per `Dashboard`, `Ingredienti`, `Prodotti` e `Magazzino`.

---

### ✅ SWAGGER/OPENAPI INTEGRATION (Completato - 24/07/2025)

- [x] **Dipendenza Maven:** Aggiunta `springdoc-openapi-starter-webmvc-ui` v2.2.0
- [x] **Configurazione:** Creata `OpenApiConfiguration` con metadati completi e sicurezza HTTP Basic Auth
- [x] **Properties:** Configurazione Swagger UI personalizzata (`/swagger-ui.html`, filtri API, ordinamento)
- [x] **Annotazioni Controller:** Documentazione completa di tutti i controller REST:
    - [x] `IngredienteController` - Gestione ingredienti e materie prime
    - [x] `ProdottoFinitoController` - Gestione prodotti del menu
    - [x] `RicettaController` - Gestione ricette (nested resource)
    - [x] `MagazzinoController` - Gestione scorte e inventario
    - [x] `OrdineController` - Processamento ordini con scarico automatico
- [x] **Test Integration:** Swagger UI funzionante con autenticazione e test endpoint attivi

---

### FASE 2: Implementazione Funzionalità Core (Criticità: ALTA)

L'obiettivo di questa fase è costruire le fondamenta per la registrazione dei dati e popolare le interfacce di base.

#### 2.1 Backend: Modulo Ordini e Storico Vendite
* [ ] **Database:** Creare le entità JPA `Ordine` e `DettaglioOrdine`
* [ ] **Servizio:** Modificare `OrdineServiceImpl` per salvare un record persistente dell'ordine nel database dopo lo scarico del magazzino
* [ ] **Repository:** Creare i relativi Spring Data JPA Repository per le nuove entità

#### 2.2 Backend: Modulo Finanziario
* [ ] **Database:** Creare l'entità JPA `FatturaAcquisto` per tracciare i costi
* [ ] **Backend:** Sviluppare lo stack CRUD completo (DTO, Controller, Service, Repository) per le fatture d'acquisto

#### 2.3 Frontend: Popolamento Pagine CRUD
* [ ] **Controller MVC:** Implementare nei metodi del `ViewController` il passaggio di dati (`Model`) dal service alle viste
* [ ] **Pagina Ingredienti:** Popolare `ingredienti.html` con una tabella che mostri gli ingredienti esistenti. Aggiungere i form per la creazione e la modifica
* [ ] **Pagina Prodotti:** Popolare `prodotti.html` con la lista dei prodotti e i relativi form di gestione
* [ ] **Pagina Ricette:** Creare e sviluppare `ricetta.html` per visualizzare e modificare la ricetta di un singolo prodotto
* [ ] **Pagina Magazzino:** Popolare `magazzino.html` con una tabella dinamica che mostri lo stato aggiornato delle scorte
* [ ] **Pagina Fatture:** Creare `fatture.html` con un form per l'inserimento delle fatture d'acquisto

---

### FASE 3: Analisi e Visualizzazione Dati (Criticità: MEDIA)

Rendere i dati raccolti utili e accessibili.

* [ ] **Servizio di Analisi:** Implementare un `AnalyticsService` per calcolare metriche di business (es. prodotto più venduto, ricavi per periodo, food cost).
* [ ] **API:** Creare un `AnalyticsController` per esporre i dati analitici.
* [ ] **Dashboard:** Arricchire la `dashboard.html` per visualizzare le metriche più importanti, idealmente con grafici (es. Chart.js).

---

### FASE 4: Funzionalità Aggiuntive e Integrazioni

Completare il software con le funzionalità secondarie.
* [ ] **Dockerizzazione per Deployment (Criticità: MEDIA):**
    * [ ] Dockerfile per l'applicazione Spring Boot
    * [ ] docker-compose.yml con PostgreSQL + App
    * [ ] Script di inizializzazione database
    * [ ] Documentazione deployment per pizzeria

* [ ] **Gestione Personale (Criticità: BASSA):**
    * [ ] Creare l'entità `Fattorino`.
    * [ ] Collegare gli ordini ai fattorini per tracciare le consegne.
    * [ ] Sviluppare un'interfaccia di assegnazione.
* [ ] **Integrazione Hardware (Criticità: MEDIA-BASSA):**
    * [ ] Creare uno `StampaService` per l'integrazione con stampanti ESC/POS.
    * [ ] Aggiungere la funzionalità di stampa comanda dall'interfaccia.
* [ ] **Sicurezza Avanzata (Criticità: BASSA):**
    * [ ] Implementare un `AttributeConverter` per la crittografia AES su campi sensibili.
    * [ ]
    