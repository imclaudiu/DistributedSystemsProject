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