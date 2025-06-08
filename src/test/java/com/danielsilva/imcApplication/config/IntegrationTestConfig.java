package com.danielsilva.imcApplication.config;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTestConfig {

    public static final String BASE_URL = "http://localhost:8086";

    private static final GenericContainer<?> WIREMOCK =
            new GenericContainer<>("wiremock/wiremock:2.27.2")
                    .withExposedPorts(8087)
                    .withCopyFileToContainer(MountableFile.forClasspathResource("wiremock/__files/"), "/home/wiremock/__files/")
                    .withCopyFileToContainer(MountableFile.forClasspathResource("wiremock/mappings.json") , "/home/wiremock/mappings.json");

    static {
        WIREMOCK.start();
    }
}
