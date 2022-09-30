package com.yohanmarcus.webshop.util;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.testcontainers.containers.DockerComposeContainer;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public abstract class IntegrationTest {
  private final DockerComposeContainer environment = ComposeContainer.getInstance();

  @BeforeAll
  void beforeAll() {
    DatabaseConfig.setURL("jdbc:postgresql://localhost:5432/postgres");
  }
}
