package netology.cloudstoragefinal.repository;

import netology.cloudstoragefinal.model.UserEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static netology.cloudstoragefinal.testutils.TestUtils.USER_ENTITY_REPO;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @BeforeEach
    void setUp() {
        userRepository.save(USER_ENTITY_REPO);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(USER_ENTITY_REPO);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void findUserByUsername() {
        UserEntity actualUserEntity = userRepository.findUserByUsername(USER_ENTITY_REPO.getUsername()).orElse(new UserEntity());
        assertThat(actualUserEntity.getUsername()).isEqualTo(USER_ENTITY_REPO.getUsername());
        assertThat(actualUserEntity.getPassword()).isEqualTo(USER_ENTITY_REPO.getPassword());
    }
}