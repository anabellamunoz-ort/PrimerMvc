# Despliegue en Render

Este proyecto está preparado para desplegarse en [Render](https://render.com) usando Docker y una base de datos PostgreSQL gestionada.

## Importante: estructura del repositorio

El código vive en la subcarpeta `primerMvc/`, no en la raíz del repo:

```
PrimerMvc/            <- raíz del repositorio git
├── render.yaml       <- Blueprint (apunta a la subcarpeta con rootDir)
└── primerMvc/        <- proyecto Spring Boot
    ├── Dockerfile
    ├── pom.xml
    └── src/
```

Render necesita saber que el proyecto está en `primerMvc/`. El `render.yaml`
de la raíz lo resuelve con `rootDir: primerMvc`. Si configurás el servicio a mano,
seteá el **Root Directory** en `primerMvc`, o dará el error `failed to read dockerfile`.

## Perfiles de Spring

- **Local (default)**: usa `application.yaml` con MySQL (`jdbc:mysql://localhost:3306/first`).
- **Producción (`prod`)**: usa `application-prod.yaml` con PostgreSQL. Se activa con
  la variable `SPRING_PROFILES_ACTIVE=prod` y lee la conexión de `DB_URL`,
  `DB_USERNAME` y `DB_PASSWORD`.

## Variables de entorno (producción)

| Variable | Descripción | Ejemplo |
|---|---|---|
| `SPRING_PROFILES_ACTIVE` | Activa el perfil de producción | `prod` |
| `DB_URL` | URL JDBC de PostgreSQL | `jdbc:postgresql://host:5432/base` |
| `DB_USERNAME` | Usuario de la base | `primermvc` |
| `DB_PASSWORD` | Contraseña de la base | (secreto) |
| `PORT` | Puerto del servidor (lo inyecta Render) | `10000` |

> `DB_URL` debe estar en formato JDBC (`jdbc:postgresql://...`). La URL que da
> Render por defecto empieza con `postgresql://`; hay que anteponerle `jdbc:` y
> usar host, puerto y nombre de base. El `render.yaml` ya la construye así.

## Opción A: Deploy con Blueprint (recomendado)

1. Subí el proyecto a un repositorio de GitHub/GitLab.
2. En Render, elegí **New > Blueprint** y conectá el repo.
3. Render detecta `render.yaml` en la raíz y crea automáticamente:
   - El web service `primermvc` (Docker, con rootDir en `primerMvc`).
   - La base PostgreSQL `primermvc-db`.
4. Las variables (`SPRING_PROFILES_ACTIVE`, `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`)
   se inyectan solas desde la base.

## Opción B: Deploy manual

1. **New > PostgreSQL**: creá una base (plan Free). Anotá host, puerto, nombre,
   usuario y contraseña.
2. **New > Web Service**: conectá el repo, runtime **Docker**.
3. Seteá el **Root Directory** en `primerMvc`.
4. Agregá las variables de entorno de la tabla de arriba. Para `DB_URL` armá la URL
   JDBC a mano: `jdbc:postgresql://<host>:<port>/<nombre_base>`.

## Desarrollo local

Sin perfil activo, la app usa MySQL local. Para correrla:

```bash
cd primerMvc
./mvnw spring-boot:run
```

## Notas

- El modelo usa JPA con `ddl-auto: update`, así que las tablas se crean solas.
- No hardcodees credenciales en los archivos del repo: usá siempre variables de entorno.
