package co.com.bancolombia.api;

import co.com.bancolombia.model.evento.Evento;
import co.com.bancolombia.usecase.evento.EventoUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.ion.apps.SymtabApp;

import javax.validation.Valid;
import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/evento", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class EventoController {
    private final EventoUseCase useCase;

    @GetMapping
    public Flux<Evento> get() {
        return useCase.buscarTodo();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Evento>> getById(@PathVariable int id) {
        return useCase.buscarPorId(id).flatMap(e -> {
            return Mono.just(ResponseEntity.ok().body(e));
        }).switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/componente/{id}")
    public Mono<ResponseEntity<List<Evento>>> getByComponente(@PathVariable int id) {
        return useCase.buscarPorComponente(id)
                .collectList()
                .flatMap(eventos -> Mono.just(ResponseEntity.ok().body(eventos)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PostMapping
    public Mono<ResponseEntity<Map<String, Object>>> create(@Valid @RequestBody Mono<Evento> monoEvento) {
        Map<String, Object> response = new HashMap<>();

        return monoEvento.flatMap(evento -> {
                return useCase.crear(evento).flatMap(ec -> {
                    response.put("evento", ec);
                    return Mono.just(ResponseEntity
                            .created(URI.create("/api/evento/" + ec.getId_evento()))
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
    public Mono<ResponseEntity<?>> update(@Valid @RequestBody Mono<Evento> monoEvento){
        Map<String, Object> response = new HashMap<>();

        return monoEvento.flatMap(evento -> {
            return useCase.buscarPorId(evento.getId_evento())
                    .flatMap(e -> {
                        return useCase.editar(evento).flatMap(ec -> {
                            if(ec.equals(new Evento())){
                                return Mono.just(ResponseEntity.notFound().build());
                            }else {
                                response.put("evento", ec);
                                return Mono.just(ResponseEntity
                                        .created(URI.create("/api/evento/" + ec.getId_evento()))
                                        .body(response));
                            }
                        });
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

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable int id){
        return useCase.eliminar(id).flatMap(r -> {
            if(r.equalsIgnoreCase("ok"))
                return Mono.just(ResponseEntity.ok().build());
            else
                return Mono.just(ResponseEntity.notFound().build());
        });
    }

    @PostMapping("/upload")
    public Mono<Void> putUpload(@RequestPart FilePart file) {
        System.out.println("entro");
        String filename = UUID.randomUUID().toString().concat("-").concat(file.filename()
                .replace(" ", "")
                .replace(":", "")
                .replace("\\", ""));

        file.transferTo(new File(useCase.getPathFile().concat(filename)))
                .then(useCase.subirArchivo(filename));

        return Mono.empty().then();
    }

    @PostMapping("/upload/v2")
    public Mono<Object> putUpload(@RequestBody Flux<Part> parts) {
        System.out.println("entro");

        return parts.log().collectList().map(mparts -> {
            return mparts.stream().map(mmp -> {
                if (mmp instanceof FilePart) {
                    FilePart fp = (FilePart) mmp;
                    String filename = UUID.randomUUID().toString().concat("-").concat(fp.filename()
                            .replace(" ", "")
                            .replace(":", "")
                            .replace("\\", ""));

                    System.out.println(filename);

                    fp.transferTo(new File(useCase.getPathFile().concat(filename)))
                            .then(useCase.subirArchivo(filename));
                } else {
                    // process the other non file parts
                }
                return mmp instanceof FilePart ? mmp.name() + ":" + ((FilePart) mmp).filename() : mmp.name();
            }).collect(Collectors.joining(",", "[", "]"));
        });
    }
}
