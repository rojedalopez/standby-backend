package co.com.bancolombia.api;

import co.com.bancolombia.model.clavevalor.ClaveValor;
import co.com.bancolombia.model.clavevalor.GrupoClave;
import co.com.bancolombia.usecase.clavevalor.ClaveValorUseCase;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping(value = "/api/grupoclave", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class GrupoClaveController {

    private final ClaveValorUseCase useCase;

    @GetMapping
    public Flux<GrupoClave> getGrupoClave(){
        return useCase.buscarTodoGrupoClave();
    }

    @GetMapping("/{id}")
    public Mono<GrupoClave> getById(@PathVariable int id){
        return useCase.buscarPorIdGrupoClave(id);
    }

    @GetMapping("/{id}/clavevalor")
    public Mono<ResponseEntity<List<ClaveValor>>> getByGrupoClave(@PathVariable int id){
        return useCase.buscarPorGrupoClaveClaveValor(id)
                .collectList()
                .flatMap(list -> Mono.just(ResponseEntity.accepted().body(list)));
    }

    @PostMapping
    public Mono<GrupoClave> post(@RequestBody GrupoClave grupoClave){
        return  useCase.crearGrupoClave(grupoClave);
    }

    @PutMapping
    public Mono<GrupoClave> put(@RequestBody GrupoClave grupoClave){
        return  useCase.editarGrupoClave(grupoClave);
    }

    @DeleteMapping
    public Mono<GrupoClave> delete(@RequestBody GrupoClave grupoClave){
        grupoClave.setActivo(false);
        return  useCase.editarGrupoClave(grupoClave);
    }
}
