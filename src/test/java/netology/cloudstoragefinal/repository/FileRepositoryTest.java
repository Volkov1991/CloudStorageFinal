package netology.cloudstoragefinal.repository;

import netology.cloudstoragefinal.model.FileEntity;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static netology.cloudstoragefinal.testutils.TestUtils.USER_ENTITY_REPO;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FileRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileRepository fileRepository;

    private static final FileEntity FILE_ENTITY_REPO = FileEntity.builder()
            .fileName("test.txt")
            .size(4L)
            .content("test".getBytes())
            .user(USER_ENTITY_REPO)
            .build();

    @BeforeAll
    void setUp() {
        userRepository.save(USER_ENTITY_REPO);
        fileRepository.save(FILE_ENTITY_REPO);

    }

    @AfterAll
    void tearDown() {
        userRepository.delete(USER_ENTITY_REPO);
    }

    @Test
    void findByFileNameAndUser() {
        assertThat(fileRepository.findByFileNameAndUser(FILE_ENTITY_REPO.getFileName(), USER_ENTITY_REPO).orElse(null))
                .isEqualTo(FILE_ENTITY_REPO);
    }

    @Test
    void findAllByUser() {
        assertThat(fileRepository.findAllByUser(USER_ENTITY_REPO).orElse(null))
                .isEqualTo(List.of(FILE_ENTITY_REPO));
    }
}