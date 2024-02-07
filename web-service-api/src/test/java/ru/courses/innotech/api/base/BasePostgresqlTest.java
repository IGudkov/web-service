package ru.courses.innotech.api.base;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles("test")
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@ContextConfiguration(initializers = {BasePostgresqlTest.Initializer.class})
@TestPropertySource(properties = {"spring.liquibase.enabled = true"})
public class BasePostgresqlTest {

  private static final String DATABASE_NAME = "public";

  @Container
  public static PostgreSQLContainer<?> postgreSQLContainer =
      new PostgreSQLContainer<>("postgres:14.9-alpine3.18").withReuse(true).withDatabaseName(DATABASE_NAME);


  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      postgreSQLContainer.start();
      TestPropertyValues.of("spring.datasource.username = " + postgreSQLContainer.getUsername(),
          "spring.datasource.password = " + postgreSQLContainer.getPassword(),
          "spring.datasource.url = " + postgreSQLContainer.getJdbcUrl()).applyTo(configurableApplicationContext.getEnvironment());
    }

  }

}