package netology.cloudstoragefinal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static netology.cloudstoragefinal.testutils.TestUtils.USER_DTO;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileServiceApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static final String LOGIN_ENDPOINT = "/login";

    public static final int DB_PORT = 5432;
    public static final String DB_NAME = "cloud_db";
    public static final String DB_USERNAME = "postgres";
    public static final String DB_PASSWORD = "postgres";
    public static final Network NETWORK = Network.newNetwork();

    @Container
    public static final PostgreSQLContainer<?> dbContainer = new PostgreSQLContainer<>("postgres")
            .withReuse(true)
            .withExposedPorts(DB_PORT)
            .withDatabaseName(DB_NAME)
            .withUsername(DB_USERNAME)
            .withPassword(DB_PASSWORD)
            .withNetwork(NETWORK);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", dbContainer::getJdbcUrl);
        registry.add("spring.datasource.username", dbContainer::getUsername);
        registry.add("spring.datasource.password", dbContainer::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }


    @Test
    void testPostgresLoads() {
        Assertions.assertTrue(dbContainer.isRunning());
    }

    @Test
    void login() {
        ResponseEntity<?> response = restTemplate.exchange(
                LOGIN_ENDPOINT, HttpMethod.POST, new HttpEntity<>(USER_DTO), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
