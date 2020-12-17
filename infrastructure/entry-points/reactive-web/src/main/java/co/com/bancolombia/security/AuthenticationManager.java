package co.com.bancolombia.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {

    private final String SUPER_SECRET_KEY = "R3N4T42O2O";

    @Override
    @SuppressWarnings("unchecked")
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        try {
            if (!validateToken(authToken)) {
                return Mono.empty();
            }
            Claims claims = getAllClaimsFromToken(authToken);
            //List<String> rolesMap = claims.get("role", List.class);
            List<GrantedAuthority> authorities = new ArrayList<>();
            /*for (String rolemap : rolesMap) {
                authorities.add(new SimpleGrantedAuthority(rolemap));
            }*/
            return Mono.just(new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities));
        } catch (Exception e) {
            return Mono.empty();
        }
    }

    private Claims getAllClaimsFromToken(String jwtToken) {
        try {
            return Jwts.parser().setSigningKey(SUPER_SECRET_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
        }catch (Exception e){
            throw e;
        }
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

}
