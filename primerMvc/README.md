# primerMvc

API REST desarrollada con **Spring Boot** siguiendo el patrón MVC. Expone endpoints para gestionar elementos, componentes y login de usuarios, con persistencia en base de datos relacional vía JPA/Hibernate.

## Tecnologías

- Java 17
- Spring Boot 4.1.1 (Web MVC + Data JPA)
- Lombok
- MySQL (desarrollo local) / PostgreSQL (producción en Render)
- Maven

## Estructura del proyecto

```
src/main/java/ort/edu/ar/primerMvc/
├── PrimerMvcApplication.java      # Punto de entrada
├── controller/                    # Endpoints REST
│   ├── ComponenteController.java
│   ├── ElementoController.java
│   └── LoginController.java
├── dto/                           # Objetos de transferencia
│   ├── ComponenteDTO.java
│   └── LoginDTO.java
├── model/                         # Entidades JPA
│   ├── Componente.java
│   ├── Elemento.java
│   └── Login.java
├── repository/                    # Acceso a datos (Spring Data JPA)
└── service/                       # Lógica de negocio
```

## Requisitos previos

- JDK 17 o superior
- MySQL corriendo en local (para desarrollo), o configurar variables de entorno hacia otra base
- No necesitás instalar Maven: el proyecto incluye el wrapper (`./mvnw`)

## Configuración

La conexión a la base de datos y el puerto se leen de variables de entorno, con valores por defecto para desarrollo local:

| Variable | Default (local) | Descripción |
|---|---|---|
| `SPRING_DATASOURCE_URL` | `jdbc:mysql://localhost:3306/first` | URL JDBC de la base |
| `SPRING_DATASOURCE_USERNAME` | `root` | Usuario de la base |
| `SPRING_DATASOURCE_PASSWORD` | `root` | Contraseña de la base |
| `SPRING_DATASOURCE_DRIVER` | `com.mysql.cj.jdbc.Driver` | Driver JDBC |
| `SPRING_JPA_DDL_AUTO` | `update` | Estrategia de Hibernate para el esquema |
| `SPRING_JPA_SHOW_SQL` | `true` | Mostrar SQL en consola |
| `PORT` | `8080` | Puerto del servidor |

Las tablas se crean automáticamente al iniciar la app (`ddl-auto: update`).

## Ejecución local

1. Asegurate de tener MySQL corriendo y una base llamada `first` (o ajustá las variables de entorno).
2. Levantá la aplicación:

   ```bash
   ./mvnw spring-boot:run
   ```

3. La API queda disponible en `http://localhost:8080`.

### Compilar el JAR

```bash
./mvnw clean package
java -jar target/primerMvc-0.0.1-SNAPSHOT.jar
```

## Endpoints

### Elemento — `/api/elemento`

| Método | Ruta | Descripción | Body |
|---|---|---|---|
| GET | `/api/elemento` | Lista todos los elementos | — |
| POST | `/api/elemento` | Crea un elemento | `string` (nombre) |
| PUT | `/api/elemento` | Modifica un elemento | — |
| PATCH | `/api/elemento` | Modifica parcialmente un elemento | — |
| DELETE | `/api/elemento` | Elimina un elemento | — |

Entidad `Elemento`: `{ id, nombre }`

### Componente — `/api/componente`

| Método | Ruta | Descripción | Body |
|---|---|---|---|
| POST | `/api/componente` | Crea un componente | `ComponenteDTO` |

Body (`ComponenteDTO`):

```json
{
  "descripcion": "string",
  "color": "string",
  "medida": "string"
}
```

Entidad `Componente`: `{ id, descripcion, color, medida }`

### Login — `/login`

| Método | Ruta | Descripción | Body |
|---|---|---|---|
| POST | `/login` | Valida credenciales | `LoginDTO` |
| POST | `/login/crear` | Crea un login | `LoginDTO` |

Body (`LoginDTO`):

```json
{
  "email": "string",
  "clave": "string"
}
```

Respuestas de `/login`:
- `200 OK` con el objeto `Login` si las credenciales son válidas.
- `401 Unauthorized` si el email o la contraseña son incorrectos.

## Despliegue en Render

El proyecto está listo para desplegarse en [Render](https://render.com) con Docker y PostgreSQL. Ver las instrucciones detalladas en [`DEPLOY.md`](./DEPLOY.md).

Resumen rápido (vía Blueprint):

1. Subí el repo a GitHub/GitLab.
2. En Render: **New > Blueprint** y conectá el repositorio.
3. Render lee `render.yaml` y crea el web service (Docker) junto a la base PostgreSQL, inyectando las credenciales automáticamente.
