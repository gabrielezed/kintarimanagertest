# Guida alla Gestione del Progetto

Questo documento fornisce un modello flessibile per la gestione di progetti di sviluppo software in collaborazione tra un utente umano e un LLM. L'obiettivo è creare un processo strutturato, scalabile e facile da seguire.

## Fasi del Progetto

Ogni progetto, indipendentemente dalla sua dimensione, può essere suddiviso nelle seguenti fasi principali.

### 1. Definizione e Pianificazione
* **Obiettivo**: Comprendere a fondo i requisiti del progetto.
* **Task**:
    * Definire gli obiettivi principali e le funzionalità chiave.
    * Identificare le tecnologie da utilizzare (linguaggi, framework, database).
    * Creare una struttura di cartelle iniziale per il progetto.
    * Popolare il file `TODO.md` con le macro-attività iniziali.

### 2. Sviluppo del Backend
* **Obiettivo**: Creare la logica di business e la gestione dei dati.
* **Task**:
    * Definire i modelli (entità) del database.
    * Implementare i repository per l'accesso ai dati.
    * Sviluppare i servizi per la logica di business.
    * Creare i controller per esporre le API REST.
    * Scrivere i DTO (Data Transfer Object) per la comunicazione tra i layer.

### 3. Test delle API
* **Obiettivo**: Verificare che il backend funzioni come previsto.
* **Task**:
    * Configurare un client API (es. Postman).
    * Testare ogni endpoint (CRUD: Create, Read, Update, Delete) per tutte le entità.
    * Verificare la gestione degli errori e i codici di stato HTTP.
    * Assicurarsi che i dati vengano salvati e recuperati correttamente dal database.

### 4. Sviluppo del Frontend
* **Obiettivo**: Creare l'interfaccia utente.
* **Task**:
    * Scegliere un framework/libreria (es. Thymeleaf, React, Angular, Vue).
    * Creare le viste HTML e i template.
    * Implementare la logica nel frontend per comunicare con le API del backend.
    * Sviluppare componenti UI riutilizzabili.

### 5. Implementazione della Sicurezza
* **Obiettivo**: Proteggere l'applicazione da accessi non autorizzati.
* **Task**:
    * Configurare un sistema di autenticazione (login/logout).
    * Implementare l'autorizzazione basata su ruoli (es. Utente, Amministratore).
    * Proteggere gli endpoint delle API e le rotte del frontend.

### 6. Documentazione e Refactoring
* **Obiettivo**: Migliorare la qualità e la manutenibilità del codice.
* **Task**:
    * Scrivere o aggiornare il file `README.md` del progetto.
    * Aggiungere commenti chiari al codice dove necessario.
    * Rivedere il codice per migliorarne la struttura, la leggibilità e le performance (refactoring).
    * Aggiornare il `CHANGELOG.md` con le modifiche finali.

## Come Usare Questa Guida

1.  **Copia e Incolla**: All'inizio di un nuovo progetto, copia questo file nella cartella `aiutil`.
2.  **Adatta**: Rivedi le fasi e le task per adattarle alle esigenze specifiche del nuovo progetto.
3.  **Segui il Flusso**: Usa questo documento come riferimento per guidare le richieste all'LLM e per tenere traccia del progresso generale.