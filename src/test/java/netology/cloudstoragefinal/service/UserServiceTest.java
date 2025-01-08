package netology.cloudstoragefinal.service;

import netology.cloudstoragefinal.config.CustomUserDetails;
import netology.cloudstoragefinal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static netology.cloudstoragefinal.testutils.TestUtils.USER_ENTITY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        when(userRepository.findUserByUsername(USER_ENTITY.getUsername())).thenReturn(Optional.ofNullable(USER_ENTITY));
    }

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = new CustomUserDetails(USER_ENTITY);
        assertThat(userService.loadUserByUsername(USER_ENTITY.getUsername())).isEqualTo(userDetails);
    }

    @Test
    void getUserFromDatabaseByUsername() {
        assertEquals(USER_ENTITY, userService.getUserFromDatabaseByUsername(USER_ENTITY.getUsername()));
    }
}