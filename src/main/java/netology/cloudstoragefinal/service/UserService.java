package netology.cloudstoragefinal.service;

import netology.cloudstoragefinal.config.CustomUserDetails;
import netology.cloudstoragefinal.exception.ErrorInputData;
import netology.cloudstoragefinal.model.UserEntity;
import netology.cloudstoragefinal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = getUserFromDatabaseByUsername(username);
        return new CustomUserDetails(user);
    }

    public UserEntity getUserFromDatabaseByUsername(String username) {
        return userRepository.findUserByUsername(username).orElseThrow(() -> new ErrorInputData("Bad credentials"));
    }
}
