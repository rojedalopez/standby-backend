package co.com.bancolombia.usecase.util;

import co.com.bancolombia.model.usuario.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Hex;
import reactor.core.publisher.Mono;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class SecurityUseCase {

    private static SecurityUseCase useCase;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    public static SecurityUseCase getInstance(){
        if(useCase == null)
            useCase = new SecurityUseCase();

        return useCase;
    }

    public Mono<Usuario> getPasswordSecured(Usuario usuario) {
        UUID hash = UUID.randomUUID();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(hash.toString().concat(usuario.getPassword()).getBytes());
            byte[] mb = md.digest();

            usuario.setPassword(Hex.encodeHexString(mb));
            usuario.setHash(hash.toString());
            return Mono.just(usuario);
        } catch (NoSuchAlgorithmException e) {
            return Mono.error(new InterruptedException("Se genero un error encriptando la clave"));
        }
    }

    public Mono<String> getPasswordSecured(String hash, String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(hash.concat(password).getBytes());
            byte[] mb = md.digest();

            return Mono.just(Hex.encodeHexString(mb));
        } catch (NoSuchAlgorithmException e) {
            return Mono.error(new InterruptedException("Se genero encriptando la clave"));
        }
    }

    //generate token for user
    public String generateToken(Usuario usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("nivel", usuario.getNivel());
        claims.put("subdominio", usuario.getFk_subdominio());
        claims.put("actualizar", usuario.isActualizar());
        claims.put("bienvenido", usuario.isBienvenido());

        return Jwts.builder().setClaims(claims).setSubject(usuario.getId_usuario())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, "R3N4T42O2O".getBytes()).compact();
    }
}
