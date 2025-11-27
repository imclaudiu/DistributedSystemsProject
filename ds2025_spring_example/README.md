# Demo — Spring Boot API

A simple Spring Boot REST API (people, auth, device) with PostgreSQL db associated for each service.

## Contents

## Project structure
```
├───auth
│   ├───.idea
│   ├───.mvn
│   │   └───wrapper
│   ├───src
│   │   ├───main
│   │   │   ├───java
│   │   │   │   └───com
│   │   │   │       └───example
│   │   │   │           └───demo
│   │   │   │               ├───config
│   │   │   │               ├───controllers
│   │   │   │               ├───dtos
│   │   │   │               │   ├───builders
│   │   │   │               │   └───validators
│   │   │   │               │       └───annotation
│   │   │   │               ├───entities
│   │   │   │               ├───handlers
│   │   │   │               │   └───exceptions
│   │   │   │               │       └───model
│   │   │   │               ├───repositories
│   │   │   │               ├───security
│   │   │   │               └───services
│   │   │   └───resources
│   │   └───test
│   │       └───java
│   │           └───com
│   │               └───example
│   │                   └───demo
│   └───target
│       ├───classes
│       │   └───com
│       │       └───example
│       │           └───demo
│       │               ├───config
│       │               ├───controllers
│       │               ├───dtos
│       │               │   ├───builders
│       │               │   └───validators
│       │               │       └───annotation
│       │               ├───entities
│       │               ├───handlers
│       │               │   └───exceptions
│       │               │       └───model
│       │               ├───repositories
│       │               ├───security
│       │               └───services
│       └───generated-sources
│           └───annotations
├───device
│   ├───.idea
│   ├───.mvn
│   │   └───wrapper
│   ├───src
│   │   ├───main
│   │   │   ├───java
│   │   │   │   └───com
│   │   │   │       └───example
│   │   │   │           └───demo
│   │   │   │               ├───config
│   │   │   │               ├───controllers
│   │   │   │               ├───dtos
│   │   │   │               │   └───builders
│   │   │   │               ├───entities
│   │   │   │               ├───handlers
│   │   │   │               │   └───exceptions
│   │   │   │               │       └───model
│   │   │   │               ├───repositories
│   │   │   │               ├───security
│   │   │   │               └───services
│   │   │   └───resources
│   │   └───test
│   │       └───java
│   │           └───com
│   │               └───example
│   │                   └───demo
│   └───target
│       ├───classes
│       │   └───com
│       │       └───example
│       │           └───demo
│       │               ├───config
│       │               ├───controllers
│       │               ├───dtos
│       │               │   └───builders
│       │               ├───entities
│       │               ├───handlers
│       │               │   └───exceptions
│       │               │       └───model
│       │               ├───repositories
│       │               ├───security
│       │               └───services
│       └───generated-sources
│           └───annotations
├───dynamic
├───frontend
│   ├───.idea
│   │   └───inspectionProfiles
│   ├───dist
│   │   └───assets
│   └───src
│       ├───assets
│       ├───components
│       ├───contexts
│       ├───services
│       └───styles
└───user
    ├───.idea
    │   └───inspectionProfiles
    ├───.mvn
    │   └───wrapper
    ├───src
    │   ├───main
    │   │   ├───java
    │   │   │   └───com
    │   │   │       └───example
    │   │   │           └───demo
    │   │   │               ├───config
    │   │   │               ├───controllers
    │   │   │               ├───dtos
    │   │   │               │   ├───builders
    │   │   │               │   └───validators
    │   │   │               │       └───annotation
    │   │   │               ├───entities
    │   │   │               ├───handlers
    │   │   │               │   └───exceptions
    │   │   │               │       └───model
    │   │   │               ├───repositories
    │   │   │               ├───security
    │   │   │               └───services
    │   │   └───resources
    │   └───test
    │       └───java
    │           └───com
    │               └───example
    │                   └───demo
    └───target
        ├───classes
        │   └───com
        │       └───example
        │           └───demo
        │               ├───config
        │               ├───controllers
        │               ├───dtos
        │               │   ├───builders
        │               │   └───validators
        │               │       └───annotation
        │               ├───entities
        │               ├───handlers
        │               │   └───exceptions
        │               │       └───model
        │               ├───repositories
        │               ├───security
        │               └───services
        └───generated-sources
            └───annotations
```

Smart Device Management System
Un sistem complet de management pentru dispozitive smart, construit cu o arhitectură microservices folosind Spring Boot în backend și React în frontend.

🚀 Tehnologii Utilizate
Backend (Spring Boot Microservices)
Java 25 + Spring Boot 4.0.0-SNAPSHOT

PostgreSQL - Bază de date relatională

Spring Security - Autentificare și autorizare

JWT - JSON Web Tokens pentru securitate

Hibernate - ORM pentru managementul bazei de date

Maven - Managementul dependințelor

Frontend (React)
React 18 - Biblioteca UI

React Router - Routing

Context API - Managementul stării

CSS3 - Stilizare

Fetch API - Comunicare cu backend-ul

📋 Structura Proiectului
text
demo/
├── auth/                 # Serviciu de autentificare
├── user/                 # Serviciu de management utilizatori
├── device/               # Serviciu de management dispozitive
├── frontend/             # Aplicația React
└── dynamic/              # Configurații dinamice
🏗️ Arhitectura Sistemului
Backend Microservices
Fiecare serviciu are propria bază de date PostgreSQL și funcționalități specifice:

🔐 Auth Service
Autentificare utilizatori

Generare și validare JWT tokens

Management sesiuni

Securitate endpoint-uri

👥 User Service
Management profiluri utilizatori

CRUD operații pentru persoane

Validare date utilizatori

Gestionare roluri (Admin/User)

📱 Device Service
Creare dispozitive

Management consum energetic

Asociere dispozitive cu utilizatori

Ștergere și actualizare dispozitive

Frontend React
Aplicația single-page cu următoarele caracteristici:

🎯 Componente Principale
Dashboard - Panou principal utilizator

AuthContext - Management stare autentificare

Modal Components - Popup-uri pentru creare dispozitive

🔐 Sistem de Autentificare
Login/Logout

Verificare token JWT

Redirect automat pe baza stării autentificării

Management permisiuni (Admin vs User)

📊 Funcționalități Dashboard
javascript
// Vizualizare profil utilizator
- Detalii personale (nume, adresă, vârstă)
- Roluri și permisiuni

// Management dispozitive
- Creare dispozitive noi
- Vizualizare lista dispozitive personale
- Ștergere dispozitive
- Monitorizare consum energetic

// Admin Features
- Vizualizare toți utilizatorii
- Management persoane
⚙️ Configurare și Instalare
Cerințe Sistem
Java JDK 25

PostgreSQL (versiune 12+)

Node.js (versiune 18+)

Maven (inclus via wrapper)

Configurare Baze de Date
bash
# Variabile de mediu pentru fiecare serviciu
export DB_IP=localhost
export DB_PORT=5432
export DB_USER=postgres
export DB_PASSWORD=root
export DB_DBNAME=auth-db    # user-db, device-db
export PORT=8080            # 8081, 8082 pentru alte servicii
Pornire Aplicație
Backend Services
bash
# Pornire serviciu Auth
cd auth
./mvnw spring-boot:run

# Pornire serviciu User  
cd ../user
./mvnw spring-boot:run

# Pornire serviciu Device
cd ../device
./mvnw spring-boot:run
Frontend React
bash
cd frontend
npm install
npm run dev
🎮 Utilizare Sistem
Autentificare
Accesați pagina de login

Introduceți credențialele

Sistemul generează token JWT automat

Redirect către dashboard

Management Dispozitive
Creare Dispozitiv
javascript
// Datele necesare pentru un dispozitiv nou
{
    "name": "Smart Thermostat",
    "maxConsumption": 1500,
    "ownerId": "user-uuid-here"
}
Operațiuni Disponibile
Create - Adăugare dispozitiv nou via modal

Read - Vizualizare lista dispozitive în tabel

Delete - Ștergere dispozitiv cu confirmare

Funcționalități Admin
Vizualizare lista completă utilizatori

Refresh date în timp real

Gestionare persoane în sistem

🔒 Securitate
Măsuri Implementate
JWT Tokens pentru autentificare

Spring Security pe endpoint-uri backend

Validare input pe ambele părți

Confirmare acțiuni critice (ștergere)

Gestionare erori securizată

Roluri și Permisiuni
USER - Poate gestiona doar dispozitivele personale

ADMIN - Acces la toți utilizatorii și dispozitivele

🐛 Depanare
Probleme Comune
Conexiune Database
bash
# Verifică conexiunea PostgreSQL
psql -h localhost -U postgres -d auth-db
Porturi Ocupate
properties
# Schimbă porturile în application.properties
server.port=8081
Token Expirat
Logout automat la expirare token

Redirect la pagina de login

Logging și Monitorizare
Logs detaliate în consolă

Mesaje de eroare descriptive

Stări de loading pentru operațiuni async

📈 Extindere Sistem
Adăugare Funcționalități Noi
Creare serviciu nou în backend

Adăugare endpoint-uri în controller

Integrare în frontend via services

Adăugare componente React

Scalare
Microservices pot rula pe servere separate

Load balancing posibil

Baze de date separate pentru fiecare serviciu

- `src/main/...` — SpringBoot source
- `src/main/resources/application.properties` — app configuration
- `postman_collection.json` — Postman collection to import
- `pom.xml` — Maven project wht Spring Boot 4.0.0-SNAPSHOT and Java 25

## Prerequisites
- **Java JDK 25**
- **PostgreSQL** server accessible from the app (can be changed to any other db from application.properties)
- **Postman** account to import & run the test collection

> Note: Hibernate is set to `spring.jpa.hibernate.ddl-auto=update`, so tables will be created/updated automatically on first run

## Configuration
All important settings are in `src/main/resources/application.properties`. You can override them via environment variables:

| Purpose | Property | Env var | Default |
|---|---|---|---|
| DB host | `database.ip` | `DB_IP` | `localhost` |
| DB port | `database.port` | `DB_PORT` | `5432` |
| DB user | `database.user` | `DB_USER` | `postgres` |
| DB password | `database.password` | `DB_PASSWORD` | `root` |
| DB name | `database.name` | `DB_DBNAME` | `example-db` |
| HTTP port | `server.port` | `PORT` | `8080` |

Effective JDBC URL:
```
jdbc:postgresql://${DB_IP}:${DB_PORT}/${DB_DBNAME}
```

## How to run (local)
From the project root (`demo/`), run with the Maven Wrapper:

```bash
# 1) export env vars if you need non-defaults
export DB_IP=localhost
export DB_PORT=5432
export DB_USER=postgres
export DB_PASSWORD=root
export DB_DBNAME=example-db
export PORT=8080

```

---