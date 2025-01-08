package netology.cloudstoragefinal.config;

import netology.cloudstoragefinal.utils.token.JwtTokenManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    public static final String TOKEN_HEADER = "auth-token";

    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader(TOKEN_HEADER);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.replace(TOKEN_PREFIX, "");
        String username = jwtTokenManager.getUsername(token);
        if (username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenManager.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
