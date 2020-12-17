package co.com.bancolombia.api;

import co.com.bancolombia.model.componente.Componente;
import co.com.bancolombia.usecase.componente.ComponenteUseCase;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/componente")
@AllArgsConstructor
public class ComponenteController {
    private final ComponenteUseCase useCase;

    @GetMapping
    public Flux<Componente> get(){
        return useCase.buscarTodo();
    }

    @GetMapping("/buscar/{id_componente}")
    public Mono<ResponseEntity<Componente>> getById(@PathVariable int id_componente){
        return useCase.buscarPorId(id_componente)
                .map(componente -> ResponseEntity.accepted().body(componente))
                .flatMap(Mono::just)
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/{nombre}")
    public Flux<Componente> getByName(@PathVariable String nombre){
        return useCase.buscarPorNombre(nombre);
    }

    @PostMapping
    public Mono<Componente> post(@RequestBody Componente componente){
        return  useCase.crear(componente);
    }

    @PutMapping
    public Mono<Componente> put(@RequestBody Componente componente){
        return  useCase.editar(componente);
    }

    @PostMapping("/upload")
    public Mono<Void> putUpload(@RequestPart Flux<FilePart> files) {
        System.out.println("entro");
        return  files.collectList()
                .flatMap(list -> {
                    System.out.println("convirtio en list");
                    list.forEach(file -> {
                        String filename = UUID.randomUUID().toString().concat("-").concat(file.filename()
                                .replace(" ", "")
                                .replace(":", "")
                                .replace("\\", ""));

                        file.transferTo(new File(useCase.getPathFile().concat(filename)))
                                .then(useCase.subirArchivo(filename));
                    });
                    return Mono.empty().then();
                }).then();
    }

    @PostMapping("/upload/v2")
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
}
