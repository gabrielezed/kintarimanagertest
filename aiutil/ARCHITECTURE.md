### Architettura Completa del Software

```markdown
# Architettura Software: Gestionale Pizzeria

Questo documento delinea l'architettura completa per un software gestionale per pizzerie, progettato per essere robusto, scalabile e manutenibile.

---

## 1. Filosofia e Stack Tecnologico 🏗️

L'architettura si basa su un modello a **Strati (Layered Architecture)**, un approccio collaudato che separa le responsabilità logiche del software, e sullo stack tecnologico dell'ecosistema Spring.

- **Linguaggio:** Java 17+ (LTS)
- **Framework:** Spring Boot 3.x
- **Accesso Dati:** Spring Data JPA con Hibernate
- **Database:** H2 (per sviluppo e produzione locale iniziale)
- **Sicurezza:** Spring Security
- **Frontend/Viste:** Thymeleaf

---

## 2. Architettura a Strati

Il flusso dei dati e delle responsabilità è suddiviso in tre livelli principali:

1.  **Presentation Layer (Controller):**
    -   Responsabilità: Ricevere le richieste HTTP dall'utente (tramite browser), validare l'input di base e invocare i metodi del Service Layer. Non contiene logica di business.
    -   Componenti: Classi annotate con `@RestController` o `@Controller`.

2.  **Service Layer (Logica di Business):**
    -   Responsabilità: È il cuore dell'applicazione. Contiene tutta la logica di business, coordina le operazioni, gestisce le transazioni e interagisce con il Data Access Layer.
    -   Componenti: Classi annotate con `@Service`.

3.  **Data Access Layer (Repository):**
    -   Responsabilità: Unico punto di contatto con il database. Si occupa esclusivamente della persistenza dei dati (salvataggio, lettura, aggiornamento, cancellazione).
    -   Componenti: Interfacce che estendono `JpaRepository`, annotate con `@Repository`.

```

[Browser Utente] -\> [Controller Layer] -\> [Service Layer] -\> [Repository Layer] -\> [Database]

```

---

## 3. Progettazione del Database (Modello Entità) 🗄️

Il modello dati è progettato per supportare materie prime, semilavorati, prodotti finiti, vendite e analisi.

#### Entità Principali:

- **`Ingrediente`**
  -   `id`: `Long` (PK)
  -   `nome`: `String`
  -   `quantitaInStock`: `Double`
  -   `unitaDiMisura`: `String` (es. "kg", "lt", "pz")
  -   `tipo`: `Enum` (**`TipoIngrediente`**: `MATERIA_PRIMA`, `SEMILAVORATO`)
  -   `prezzoAcquistoMedio`: `BigDecimal` (calcolato dalle fatture)

- **`Ricetta`**
  -   `id`: `Long` (PK)
  -   `nome`: `String`
  -   `prodottoFinito`: `@OneToOne` con `Prodotto` (nullable)
  -   `ingredienteFinito`: `@OneToOne` con `Ingrediente` (nullable)
  -   `dettagliRicetta`: `@OneToMany` con `DettaglioRicetta`

- **`DettaglioRicetta`**
  -   `id`: `Long` (PK)
  -   `ricetta`: `@ManyToOne` con `Ricetta`
  -   `ingredienteComponente`: `@ManyToOne` con `Ingrediente`
  -   `quantita`: `Double` (quantità teorica)
  -   `consumoMedioEffettivo`: `Double` (quantità appresa dalle rettifiche)

- **`Prodotto`**
  -   `id`: `Long` (PK)
  -   `nome`: `String`
  -   `prezzoVendita`: `BigDecimal`

- **`Ordine`**
  -   `id`: `Long` (PK)
  -   `dataOra`: `LocalDateTime`
  -   `totale`: `BigDecimal`
  -   `dettagliOrdine`: `@OneToMany` con `DettaglioOrdine`

- **`DettaglioOrdine`**
  -   `id`: `Long` (PK)
  -   `ordine`: `@ManyToOne` con `Ordine`
  -   `prodotto`: `@ManyToOne` con `Prodotto`
  -   `quantita`: `Integer`

- **`RettificaInventario`**
  -   `id`: `Long` (PK)
  -   `ingrediente`: `@ManyToOne` con `Ingrediente`
  -   `dataRettifica`: `LocalDateTime`
  -   `quantitaTeoricaSistema`: `Double`
  -   `quantitaConteggiataFisica`: `Double`

- **`Utente`**, **`FatturaAcquisto`**, **`Fattorino`** (come da piani precedenti).

---

## 4. Flussi Logici Chiave (Service Layer) 🧠

#### Flusso di Produzione Interna (`ProduzioneService`)
_Operazione eseguita all'interno di una transazione (`@Transactional`)._
1.  **Input:** ID del semilavorato da produrre, quantità prodotta.
2.  **Recupero Ricetta:** Trova la `Ricetta` associata al semilavorato.
3.  **Scarico Materie Prime:** Per ogni `DettaglioRicetta`, calcola il fabbisogno (`quantita * quantitaProdotta`) e invoca il `MagazzinoService` per diminuire lo stock dei componenti. Se lo stock è insufficiente, la transazione fallisce (rollback).
4.  **Carico Semilavorato:** Se lo scarico ha successo, invoca il `MagazzinoService` per aumentare lo stock del semilavorato prodotto.

#### Flusso di Vendita (`OrdineService`)
_Operazione eseguita all'interno di una transazione (`@Transactional`)._
1.  **Input:** Dati dell'ordine (lista prodotti e quantità).
2.  **Salvataggio Ordine:** Crea e salva le entità `Ordine` e `DettaglioOrdine`.
3.  **Scarico Componenti:** Per ogni `DettaglioOrdine`, recupera la `Ricetta` del prodotto venduto. Per ogni componente (`DettaglioRicetta`), invoca il `MagazzinoService` per diminuire lo stock usando il valore `consumoMedioEffettivo`.

#### Flusso di Rettifica Inventario (`RettificaService`)
1.  **Input:** ID ingrediente, quantità fisica contata.
2.  **Calcolo Consumo Reale:** Calcola il consumo effettivo dell'ingrediente dall'ultima rettifica.
3.  **Conteggio Prodotti Venduti:** Conta quanti prodotti contenenti quell'ingrediente sono stati venduti nello stesso periodo.
4.  **Aggiornamento Media:** Calcola e aggiorna il campo `consumoMedioEffettivo` nel `DettaglioRicetta` corrispondente.
5.  **Allineamento Stock:** Aggiorna la `quantitaInStock` dell'ingrediente al valore fisico contato.

---

## 5. Sicurezza e Funzionalità Speciali 🔐

- **Autenticazione:** Gestita da Spring Security con login basato su `username` e `password`.
- **Autorizzazione:** Basata su ruoli (es. `ROLE_ADMIN`, `ROLE_STAFF`). L'accesso a determinate funzionalità viene limitato tramite annotazioni (`@PreAuthorize`).
- **Hashing Password:** Le password degli utenti sono salvate nel database come hash **BCrypt**.
- **Crittografia Dati ("Secret Admin"):**
  -   **Approccio:** Viene implementato un **`AttributeConverter`** di JPA.
  -   **Algoritmo:** Crittografia simmetrica **AES**. La chiave di cifratura è gestita esternamente alla codebase (es. `application.properties`).
  -   **Funzionamento:** Il converter cifra automaticamente i dati di campi specifici (`@Convert(converter = MioConverter.class)`) prima del salvataggio e li decifra dopo la lettura, in modo trasparente per il resto dell'applicazione.
```

