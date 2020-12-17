package co.com.bancolombia.api;

import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.model.usuario.UsuarioLogin;
import co.com.bancolombia.usecase.usuario.UsuarioUseCase;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/login", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor

public class LoginController {

    private final UsuarioUseCase useCase;

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> post(@RequestBody UsuarioLogin usuarioLogin){
        return useCase.login(usuarioLogin)
                .flatMap(usuario ->
                     Mono.just(ResponseEntity.ok().body(usuario))
                ).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping
    public Mono<ResponseEntity<String>> status(){
        return Mono.just(ResponseEntity.ok().body(":)"));
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<Usuario>> signup(@RequestBody Usuario usuario){
        usuario.setPassword(usuario.getId_usuario());
        return useCase.crear(usuario)
                .flatMap(u ->
                        Mono.just(ResponseEntity.ok().body(u))
                ).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }
}
