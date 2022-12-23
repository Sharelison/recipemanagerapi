package nl.abn.recipemanager.service;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class DatabaseIT {

    private static final DockerImageName IMAGE = DockerImageName
        .parse("postgres:latest");

    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer(IMAGE)
        .withDatabaseName("recipemanagerapi-db")
        .withUsername("test")
        .withPassword("test");

    protected static void registerSQLProperties(DynamicPropertyRegistry registry) {
        postgreSQLContainer.start();
        String url = postgreSQLContainer.withUrlParam("preparedStatementCacheQueries", "0").getJdbcUrl();
        registry.add("spring.datasource.url", () -> url);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
}
