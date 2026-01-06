<div align="center">

<!-- (Opzionale) Se vuoi un banner tuo:
<img src="docs/banner.png" alt="JTime Banner" width="900" />
-->

# ⏱️ JTime (125132)
### Gestione del tempo, progetti e attività — Desktop App in JavaFX

<!-- Badges (Shields + SimpleIcons logos) -->
![Java](https://img.shields.io/badge/Java-21-ED8B00?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-21-2C2255?logo=javafx&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.8-02303A?logo=gradle&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit-5-25A162?logo=junit5&logoColor=white)
![OS](https://img.shields.io/badge/OS-Windows%20%7C%20Linux%20%7C%20macOS-555?logo=windows&logoColor=white)

<!-- Build badge: funziona se aggiungi il workflow sotto -->
![CI](https://img.shields.io/github/actions/workflow/status/TUO_USERNAME/JTime-125132/ci.yml?branch=main&label=CI&logo=githubactions&logoColor=white)

</div>

---

## 🚀 Cos’è JTime
**JTime** è un’app desktop per **gestire progetti, attività e tempo**:
- crea e organizza progetti
- aggiunge attività con stime (minuti)
- completa attività registrando il tempo effettivo
- genera report (per progetto o per intervallo)
- export report in `.txt`

---

## ✨ Funzionalità principali
- **Progetti**
  - Creazione / chiusura
  - Eliminazione progetto (con conferma)
- **Attività**
  - Creazione con stima (minuti)
  - Completamento con tempo effettivo
  - Eliminazione attività
- **Pianificazione**
  - Pianificazione su calendario (intervalli/date)
- **Report**
  - Report progetto
  - Report intervallo temporale
  - Export su file TXT

---

## 🧰 Tecnologie & Tools (con logo)
<div>

<img height="22" src="https://cdn.simpleicons.org/openjdk" /> Java 21  
<img height="22" src="https://cdn.simpleicons.org/javafx" /> JavaFX (UI)  
<img height="22" src="https://cdn.simpleicons.org/gradle" /> Gradle (build & run)  
<img height="22" src="https://cdn.simpleicons.org/junit5" /> JUnit 5 (test)  
<img height="22" src="https://cdn.simpleicons.org/githubactions" /> GitHub Actions (CI)

</div>

---

## 📦 Requisiti
- **Java 21** installato
- Nessuna installazione Gradle necessaria (usa il **Gradle Wrapper**)

---

## ▶️ Come avviare (modalità sviluppo)
```powershell
git clone git clone https://github.com/kassarahmad/JTIME
cd JTime-125132
.\gradlew clean run
