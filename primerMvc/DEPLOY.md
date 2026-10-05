# Despliegue en Render

Este proyecto está preparado para desplegarse en [Render](https://render.com) usando Docker y una base de datos PostgreSQL gestionada.

## Archivos relevantes

- `Dockerfile`: build multi-etapa (compila con Maven y corre con un JRE liviano).
- `render.yaml`: Blueprint que define el web service y la base de datos PostgreSQL.
- `application.yaml`: la configuración de BD y el puerto se leen de variables de entorno, con defaults para desarrollo local.

## Opción A: Deploy con Blueprint (recomendado)

1. Subí el proyecto a un repositorio de GitHub/GitLab.
2. En Render, elegí **New > Blueprint** y conectá el repo.
3. Render detecta `render.yaml` y crea automáticamente:
   - El web service `primermvc` (Docker).
   - La base de datos PostgreSQL `primermvc-db`.
4. Las variables de entorno de conexión se inyectan solas desde la base de datos.

## Opción B: Deploy manual

1. **New > PostgreSQL**: creá una base de datos (plan Free). Anotá host, puerto, nombre, usuario y contraseña.
2. **New > Web Service**: conectá el repo y elegí runtime **Docker**.
3. Agregá estas variables de entorno en el web service:

   | Variable | Valor |
   |---|---|
   | `SPRING_DATASOURCE_URL` | `jdbc:postgresql://<host>:<port>/<db>` |
   | `SPRING_DATASOURCE_USERNAME` | usuario de la BD |
   | `SPRING_DATASOURCE_PASSWORD` | contraseña de la BD |
   | `SPRING_DATASOURCE_DRIVER` | `org.postgresql.Driver` |
   | `SPRING_JPA_DDL_AUTO` | `update` |
   | `SPRING_JPA_SHOW_SQL` | `false` |

   Render inyecta `PORT` automáticamente; la app ya lo usa.

## Desarrollo local

Sin variables de entorno, la app usa MySQL local (`jdbc:mysql://localhost:3306/first`, usuario/clave `root`). Para correrla:

```bash
./mvnw spring-boot:run
```

## Notas

- El entity model usa JPA con `ddl-auto: update`, por lo que las tablas se crean solas en PostgreSQL.
- Los tipos de datos entre MySQL y PostgreSQL son compatibles para este modelo simple; si usaras tipos específicos de MySQL habría que revisarlos.
