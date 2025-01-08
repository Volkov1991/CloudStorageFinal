package netology.cloudstoragefinal.utils.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenManager {

    private final SecretKey key;

    @Value("${jwt.lifetime}")
    private Duration jwtLifetime;

    public JwtTokenManager(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    public String generateJwtToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        Date issuedDate = new Date();
        Date expirationDate = new Date(issuedDate.getTime() + jwtLifetime.toMillis());
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(issuedDate)
                .expiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = getUsername(token);
        return (username != null && !isTokenExpired(token) && username.equals(userDetails.getUsername()));
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
