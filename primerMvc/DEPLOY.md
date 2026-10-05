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

Por eso Render necesita saber que el proyecto está en `primerMvc/`. El `render.yaml`
de la raíz ya lo resuelve con `rootDir: primerMvc`. Si configurás el servicio a mano,
tenés que setear el **Root Directory** en `primerMvc`, o te dará el error
`failed to read dockerfile`.

## Archivos relevantes

- `render.yaml` (en la raíz): Blueprint que define el web service y la base PostgreSQL.
- `primerMvc/Dockerfile`: build multi-etapa (compila con Maven y corre con un JRE liviano).
- `primerMvc/src/main/java/.../config/DatabaseConfig.java`: convierte la `DATABASE_URL`
  de Render (formato libpq) al formato JDBC que necesita el driver.
- `application.yaml`: config de BD y puerto con defaults para desarrollo local.

## Opción A: Deploy con Blueprint (recomendado)

1. Subí el proyecto a un repositorio de GitHub/GitLab.
2. En Render, elegí **New > Blueprint** y conectá el repo.
3. Render detecta `render.yaml` en la raíz y crea automáticamente:
   - El web service `primermvc` (Docker, con rootDir en `primerMvc`).
   - La base de datos PostgreSQL `primermvc-db`.
4. La variable `DATABASE_URL` se inyecta sola desde la base; la app la convierte a JDBC.

## Opción B: Deploy manual

1. **New > PostgreSQL**: creá una base de datos (plan Free). Copiá su **Internal Database URL**.
2. **New > Web Service**: conectá el repo y elegí runtime **Docker**.
3. Seteá el **Root Directory** en `primerMvc`.
4. Agregá estas variables de entorno en el web service:

   | Variable | Valor |
   |---|---|
   | `DATABASE_URL` | la Internal Database URL de la base (empieza con `postgresql://`) |
   | `SPRING_JPA_DDL_AUTO` | `update` |
   | `SPRING_JPA_SHOW_SQL` | `false` |

   Render inyecta `PORT` automáticamente; la app ya lo usa.

## Desarrollo local

Sin variables de entorno, la app usa MySQL local (`jdbc:mysql://localhost:3306/first`,
usuario/clave `root`). Para correrla:

```bash
cd primerMvc
./mvnw spring-boot:run
```

## Notas

- `DatabaseConfig` solo convierte la URL cuando detecta una `DATABASE_URL` que empieza
  con `postgres://` o `postgresql://`. En local, sin esa variable, usa la config de
  `application.yaml` (MySQL).
- El modelo usa JPA con `ddl-auto: update`, así que las tablas se crean solas.
