package co.com.bancolombia.api;

import co.com.bancolombia.model.evento.Evento;
import co.com.bancolombia.model.usuario.Usuario;
import co.com.bancolombia.usecase.usuario.UsuarioUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioUseCase useCase;

    @GetMapping
    public Flux<Usuario> get() {
        return useCase.buscarTodo();
    }

    @GetMapping("/{id_usuario}")
    public Mono<ResponseEntity<Usuario>> getById(@PathVariable String id_usuario) {

        return useCase.buscarPorId(id_usuario)
                .map(ResponseEntity.ok()::body)
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<Usuario> monoUsuario) {
        Map<String, Object> response = new HashMap<>();

        return monoUsuario.flatMap(usuario -> {
            usuario.setPassword(usuario.getId_usuario());
            return useCase.crear(usuario).flatMap(ec -> {
                response.put("usuario", ec);
                return Mono.just(ResponseEntity
                        .created(URI.create("/api/usuario/" + ec.getId_usuario()))
                        .body(response));
            });
        }).onErrorResume(t -> {
            return Mono.just(t)
                    .cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(it -> Flux.fromIterable(it))
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        response.put("errors", list);
                        return Mono.just(ResponseEntity.badRequest().body(response));
                    });
        });
    }

    @PutMapping
    public Mono<ResponseEntity<Map<String, Object>>> editar(@Valid @RequestBody Mono<Usuario> monoUsuario) {
        return monoUsuario.flatMap(usuario -> {
            return useCase.editar(usuario)
                    .flatMap(res -> {
                        return Mono.just(ResponseEntity
                                .created(URI.create("/api/usuario/" + usuario.getId_usuario()))
                                .body(res));
                    });
        }).onErrorResume(t -> {
            return Mono.just(t)
                    .cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(it -> Flux.fromIterable(it))
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("errors", list);
                        return Mono.just(ResponseEntity.badRequest().body(response));
                    });
        });
    }

    @PutMapping("/actualizar")
    public Mono<ResponseEntity<Map<String, Object>>> actualizar(@Valid @RequestBody Mono<Usuario> monoUsuario) {
        return monoUsuario.flatMap(usuario -> {
            return useCase.actualizar(usuario)
                    .flatMap(res -> {
                        return Mono.just(ResponseEntity
                                .created(URI.create("/api/usuario/" + usuario.getId_usuario()))
                                .body(res));
                    });
        }).onErrorResume(t -> {
            return Mono.just(t)
                    .cast(WebExchangeBindException.class)
                    .flatMap(e -> Mono.just(e.getFieldErrors()))
                    .flatMapMany(it -> Flux.fromIterable(it))
                    .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                    .collectList()
                    .flatMap(list -> {
                        Map<String, Object> response = new HashMap<>();
                        response.put("errors", list);
                        return Mono.just(ResponseEntity.badRequest().body(response));
                    });
        });
    }
}
