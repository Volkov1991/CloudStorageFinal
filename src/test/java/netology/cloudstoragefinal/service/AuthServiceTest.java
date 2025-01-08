package netology.cloudstoragefinal.service;

import netology.cloudstoragefinal.utils.token.JwtTokenManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static netology.cloudstoragefinal.testutils.TestUtils.USER_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenManager jwtTokenManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserDetails userDetails;

    @Test
    void authenticate() {
        when(userDetailsService.loadUserByUsername(USER_ENTITY.getUsername())).thenReturn(userDetails);
        String token = "test_token";
        when(jwtTokenManager.generateJwtToken(userDetails)).thenReturn(token);
        assertThat(authService.authenticate(USER_ENTITY)).isEqualTo(token);
        verify(authenticationManager, times(1)).authenticate(
                new UsernamePasswordAuthenticationToken(USER_ENTITY.getUsername(), USER_ENTITY.getPassword()));
    }
}