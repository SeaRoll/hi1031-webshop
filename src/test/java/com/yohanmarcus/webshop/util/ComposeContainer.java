package com.yohanmarcus.webshop.util;

import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;

import java.io.File;

public class ComposeContainer {
  @ClassRule private static DockerComposeContainer environment = null;

  public static DockerComposeContainer getInstance() {
    if (environment == null) {
      environment =
          new DockerComposeContainer(new File("docker-compose-test.yaml"))
              .withExposedService("db", 5432);
      environment.start();
      return environment;
    }

    return environment;
  }
}
