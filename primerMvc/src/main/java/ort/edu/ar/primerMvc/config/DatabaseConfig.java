package ort.edu.ar.primerMvc.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.net.URI;

/**
 * Configuración de la fuente de datos que normaliza la URL de conexión.
 *
 * Render expone la base PostgreSQL mediante la variable DATABASE_URL con el
 * formato nativo de libpq: postgresql://usuario:clave@host:puerto/base
 *
 * El driver JDBC necesita el formato: jdbc:postgresql://host:puerto/base
 * con usuario y clave por separado. Esta clase hace esa conversión cuando
 * detecta una DATABASE_URL; en caso contrario, usa la configuración estándar
 * de application.yaml (útil para desarrollo local con MySQL).
 */
@Configuration
public class DatabaseConfig {

    private final Environment env;

    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        String databaseUrl = env.getProperty("DATABASE_URL");

        if (databaseUrl != null && !databaseUrl.isBlank()
                && (databaseUrl.startsWith("postgres://") || databaseUrl.startsWith("postgresql://"))) {
            return buildFromRenderUrl(databaseUrl);
        }

        // Desarrollo local: usa spring.datasource.* de application.yaml
        return DataSourceBuilder.create()
                .url(env.getProperty("SPRING_DATASOURCE_URL",
                        env.getProperty("spring.datasource.url")))
                .username(env.getProperty("SPRING_DATASOURCE_USERNAME",
                        env.getProperty("spring.datasource.username")))
                .password(env.getProperty("SPRING_DATASOURCE_PASSWORD",
                        env.getProperty("spring.datasource.password")))
                .driverClassName(env.getProperty("SPRING_DATASOURCE_DRIVER",
                        env.getProperty("spring.datasource.driver-class-name")))
                .type(HikariDataSource.class)
                .build();
    }

    private DataSource buildFromRenderUrl(String databaseUrl) {
        URI uri = URI.create(databaseUrl);

        String userInfo = uri.getUserInfo();
        String username = "";
        String password = "";
        if (userInfo != null) {
            String[] parts = userInfo.split(":", 2);
            username = parts[0];
            password = parts.length > 1 ? parts[1] : "";
        }

        int port = uri.getPort() == -1 ? 5432 : uri.getPort();
        String jdbcUrl = "jdbc:postgresql://" + uri.getHost() + ":" + port + uri.getPath();

        return DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .type(HikariDataSource.class)
                .build();
    }
}
