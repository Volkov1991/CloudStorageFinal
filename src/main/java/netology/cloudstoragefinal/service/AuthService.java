package netology.cloudstoragefinal.service;

import netology.cloudstoragefinal.exception.ErrorBadCredentials;
import netology.cloudstoragefinal.model.UserEntity;
import netology.cloudstoragefinal.utils.token.JwtTokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import static netology.cloudstoragefinal.exception.ErrorMessage.ERROR_BAD_CREDENTIALS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsService userDetailsService;

    public String authenticate(UserEntity user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (BadCredentialsException e) {
            log.error("Bad credentials for username: {}", user.getUsername());
            throw new ErrorBadCredentials(ERROR_BAD_CREDENTIALS.toString());
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        return jwtTokenManager.generateJwtToken(userDetails);
    }
}
